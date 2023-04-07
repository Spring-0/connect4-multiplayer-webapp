package me.spring.connect4.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.spring.connect4.db.StatsRepo;
import me.spring.connect4.models.dto.AuthRequest;
import me.spring.connect4.db.PlayerRepo;
import me.spring.connect4.models.Player;
import me.spring.connect4.models.dto.PlayerDTO;
import me.spring.connect4.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    PlayerRepo playerRepo;

    @Autowired
    StatsRepo statsRepo;

    private PlayerService playerService;


    /**
     * Endpoint to register a new user
     *
     * @param authRequest
     * @return player object
     */
    @PostMapping("/register")
    public ResponseEntity<Object> createPlayer(@RequestBody AuthRequest authRequest, HttpServletResponse response){
        Player player = playerService.register(authRequest.getUsername(), authRequest.getPassword());
        if(player != null){
            playerRepo.save(player);
            playerService.savePlayerIdCookie(player.getPlayerID(), response);
            return ResponseEntity.ok().header("Location", "/").build();
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("This username is taken");
    }


    /**
     * Endpoint to log in an existing user
     *
     * @param authRequest
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AuthRequest authRequest, HttpServletResponse response){

        Player player = playerService.login(authRequest.getUsername(), authRequest.getPassword());
        if(player != null){
            playerService.savePlayerIdCookie(player.getPlayerID(), response);
            return ResponseEntity.ok().header("Location", "/").build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login credentials");

    }


    @PostMapping("/logout")
    public HttpStatus logout(HttpServletResponse response){
        playerService.deletePlayerIdCookie(response);

        return HttpStatus.OK;
    }


    @GetMapping("/player-details")
    public ResponseEntity<PlayerDTO> getPlayerDetails(@RequestParam("playerId") String playerId){
        PlayerDTO player = new PlayerDTO(playerRepo.findPlayerByPlayerID(playerId));
        return ResponseEntity.ok(player);
    }


    @GetMapping("/leaderboard/top-players")
    public ResponseEntity<List<Object[]>> getLeaderboard(){
        List<Object[]> top_players = playerRepo.findTopPlayersByWinLossRatio();
        return ResponseEntity.ok(top_players);
    }

}
