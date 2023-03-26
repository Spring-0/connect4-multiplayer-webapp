package me.spring.connect4.controllers;


import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private Gson gson = new Gson();

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
     * Endpoint to allow player2 to connect to a random game
     *
     * @param playerID
     * @return Updated game object
     */
    @PostMapping("/connect/random")
    public ResponseEntity<Game> connectRandom(@RequestBody String playerID){
        Player player = playerService.getPlayerByID(playerID);
        log.info(player + " is connecting to a random game");
        return ResponseEntity.ok(gameService.connectToRandomGame(player));
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
