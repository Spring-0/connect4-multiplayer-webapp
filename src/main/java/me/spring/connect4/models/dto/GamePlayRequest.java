package me.spring.connect4.models.dto;

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
