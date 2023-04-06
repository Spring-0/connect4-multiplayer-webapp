package me.spring.connect4.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import me.spring.connect4.db.PlayerRepo;
import me.spring.connect4.models.Player;
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
        return player;
    }


    /**
     * Method to authenticate player login
     *
     * @param username
     * @param password
     * @return
     */
    public Player login(String username, String password){

        // Player exists in the database
        Player player = playerRepo.findPlayerByUsernameAndPassword(username, password);
        return player;

        // Player does not exist
    }


    /**
     * Method to register a new player
     *
     * @param username
     * @param password
     * @return
     */
    public Player register(String username, String password){

        // Player does not exist in the database
        if(playerRepo.findByUsername(username) == null){
            Player player = new Player(username, password);
            playerRepo.save(player); // Add player to db
            return player;
        }

        // Username taken
        return null;

    }


    /**
     * Method to save player's ID as a cookie in "/" path
     *
     * @param playerID
     * @param response
     */
    public void savePlayerIdCookie(String playerID, HttpServletResponse response){

        Cookie userIdCookie = new Cookie("userId", playerID);
        userIdCookie.setMaxAge(30 * 24 * 60 * 60); // 30 day expiry
        userIdCookie.setPath("/");
        response.addCookie(userIdCookie);
    }


    /**
     * Method to delete player ID cookie from "/" path
     *
     * @param response
     */
    public void deletePlayerIdCookie(HttpServletResponse response){
        Cookie userIdCookie = new Cookie("userId", "");
        userIdCookie.setMaxAge(0);
        userIdCookie.setPath("/");
        response.addCookie(userIdCookie);
    }
    
}
