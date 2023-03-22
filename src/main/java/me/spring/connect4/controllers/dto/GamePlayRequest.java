package me.spring.connect4.controllers.dto;

import me.spring.connect4.models.constants.GamePiece;
import me.spring.connect4.models.player.Player;

public class GamePlayRequest {

    private String gameID;
    private int col;
    private String playerID;


    public String getGameID() {
        return gameID;
    }

    public int getCol() {
        return col;
    }

    public String getPlayerID() {
        return playerID;
    }
}
