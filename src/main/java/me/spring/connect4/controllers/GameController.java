package me.spring.connect4.controllers;


import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.spring.connect4.constants.SpecialGameCases;
import me.spring.connect4.models.dto.ConnectRequest;
import me.spring.connect4.models.dto.GameMessage;
import me.spring.connect4.models.dto.GamePlayRequest;
import me.spring.connect4.models.dto.gamestate.GameState;
import me.spring.connect4.models.Game;
import me.spring.connect4.models.Player;
import me.spring.connect4.service.GameService;
import me.spring.connect4.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/game")
public class GameController {


    private final PlayerService playerService;
    private final GameService gameService;

    @Autowired
    private final SimpMessagingTemplate simpMessagingTemplate;

    private Gson gson;


    /**
     * Endpoint to allow player1 to create a new game.
     *
     * @param playerID
     * @return Updated game state
     */
    @PostMapping("/create")
    public ResponseEntity<Game> start(@RequestBody String playerID){
        Player player = playerService.getPlayerByID(playerID);
        Game game = gameService.createGame(player);
        return ResponseEntity.ok(game);
    }


    /**
     * Endpoint to allow player2 to connect to a specific game
     *
     * @param connectRequest contains playerID and gameID
     * @return updated game state
     */
    @PostMapping("/connect")
    public ResponseEntity<Game> connect(@RequestBody ConnectRequest connectRequest){

        Player player = playerService.getPlayerByID(connectRequest.getPlayerID());
        Game game = gameService.connectToGame(player, connectRequest.getGameID());
        if (game == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        simpMessagingTemplate.convertAndSend("/topic/connect/" + game.getGameID(), new GameMessage(player.getUsername(), connectRequest.getPlayerID()));
        return ResponseEntity.ok(game);
    }


    /**
     * Endpoint to get a random game
     *
     * @return Updated game object
     */
    @PostMapping("/connect/random")
    public ResponseEntity<Game> getRandomGame(){
        Game game = gameService.getRandomGame();

        if(game == null){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        return ResponseEntity.ok(game);
    }


    @PostMapping("/verify")
    public ResponseEntity<SpecialGameCases> verify(@RequestBody ConnectRequest connectRequest){
        System.out.println(connectRequest.getGameID() + " " + connectRequest.getPlayerID());
        Game game = gameService.getGame(connectRequest.getGameID());
        Player player = playerService.getPlayerByID(connectRequest.getPlayerID());

        if(game == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(SpecialGameCases.GAME_NOT_FOUND);
        } else if (player == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(SpecialGameCases.PLAYER_ID_DOES_NOT_EXIST);
        } else if (game.getPlayer2() != null && game.getPlayer1() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(SpecialGameCases.GAME_IS_FULL);
        } else if (game.getPlayer1().getPlayerID().equals(player.getPlayerID())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(SpecialGameCases.CANNOT_PLAY_AGAINST_YOURSELF);
        }

        return ResponseEntity.ok(SpecialGameCases.GAME_IS_VALID);

    }


    /**
     * Endpoint to allow player to make a move
     *
     * @param gamePlayRequest contains game ID, player, and column location
     * @return updated game state
     */
    @PostMapping("/playgame")
    public ResponseEntity makeMove(@RequestBody GamePlayRequest gamePlayRequest){

        Player player = playerService.getPlayerByID(gamePlayRequest.getPlayerID());
        GameState gameState = gameService.makeMove(gamePlayRequest.getGameID(), player, gamePlayRequest.getCol());

        simpMessagingTemplate.convertAndSend("/topic/makeMove/" + gameState.getGameID(), gson.toJson(gameState));

        return ResponseEntity.ok(gameState);

    }
}

