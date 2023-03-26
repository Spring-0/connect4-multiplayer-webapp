package me.spring.connect4.models.dto;


public class ConnectRequest {
    private String playerID;
    private String gameID;


    public String getGameID(){
        return this.gameID;
    }

    public String getPlayerID(){
        return this.playerID;
    }
}
