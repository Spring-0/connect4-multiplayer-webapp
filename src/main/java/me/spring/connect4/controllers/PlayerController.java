package me.spring.connect4.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.spring.connect4.db.PlayerRepo;
import me.spring.connect4.models.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    PlayerRepo playerRepo;

    /**
     * Allow user to create a new account (player object)
     *
     * @param username
     * @return player object
     */
    @PostMapping("/create")
    public Player createPlayer(@RequestBody String username){
        Player newPlayer = new Player(username);
        playerRepo.save(newPlayer);
        System.out.println(newPlayer.getPlayerID());
        return ResponseEntity.ok(newPlayer).getBody();
    }

}
