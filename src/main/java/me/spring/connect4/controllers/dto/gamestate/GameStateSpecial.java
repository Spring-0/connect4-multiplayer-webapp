package me.spring.connect4.controllers.dto.gamestate;

import me.spring.connect4.models.player.Player;

public class GameStateSpecial extends GameState{

    private String specialMessage;

    public GameStateSpecial(String gameID, Player lastPlayed, String errorMessage) {
        super(gameID, lastPlayed, -1, -1, false);
        this.specialMessage = errorMessage;
    }

    public String getSpecialMessage(){
        return this.specialMessage;
    }

}
