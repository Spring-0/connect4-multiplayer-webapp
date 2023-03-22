package me.spring.connect4.controllers;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.spring.connect4.controllers.dto.ConnectRequest;
import me.spring.connect4.controllers.dto.GamePlayRequest;
import me.spring.connect4.models.game.Game;
import me.spring.connect4.models.player.Player;
import me.spring.connect4.service.GameService;
import me.spring.connect4.service.PlayerService;
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
    private final SimpMessagingTemplate simpMessagingTemplate;


    /**
     * Endpoint to allow player1 to create a new game.
     *
     * @param playerID
     * @return Updated game state
     */
    @PostMapping("/create")
    public ResponseEntity<Game> start(@RequestBody String playerID){
        log.info(playerID + " requested start game");
        Player player = playerService.getPlayerByID(playerID);
        return ResponseEntity.ok(gameService.createGame(player));
    }

    /**
     * Endpoint to allow player2 to connect to a specific game
     *
     * @param connectRequest contains playerID and gameID
     * @return updated game state
     */
    @PostMapping("/connect")
    public ResponseEntity<Game> connect(@RequestBody ConnectRequest connectRequest){
        log.info(playerService.getPlayerByID(connectRequest.getPlayerID()) + " has requested to join: " + connectRequest.getGameID());
        return ResponseEntity.ok(gameService.connectToGame(playerService.getPlayerByID(connectRequest.getPlayerID()), connectRequest.getGameID()));
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
    public ResponseEntity<Game> makeMove(@RequestBody GamePlayRequest gamePlayRequest){
        Game game = gameService.makeMove(gamePlayRequest.getGameID(), playerService.getPlayerByID(gamePlayRequest.getPlayerID()), gamePlayRequest.getCol());

        simpMessagingTemplate.convertAndSend("/topic/progress/" + game.getGameID(), game);

        return ResponseEntity.ok(game);

    }


}
