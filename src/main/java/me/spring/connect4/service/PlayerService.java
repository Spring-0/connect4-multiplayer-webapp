package me.spring.connect4.service;

import me.spring.connect4.db.PlayerRepo;
import me.spring.connect4.models.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    @Autowired
    PlayerRepo playerRepo;

    /**
     * Get player by ID
     *
     * @param playerID
     * @return Player Object
     */
    public Player getPlayerByID(String playerID){
        Player player = playerRepo.findPlayerByPlayerID(playerID);
        if(player != null){
            return player;
        }
        throw new RuntimeException("Player with that id is not found");
    }


}
