package me.spring.connect4.controllers.dto;

import me.spring.connect4.models.constants.GamePiece;
import me.spring.connect4.models.player.Player;

public class GamePlayRequest {

    private String gameID;
    private GamePiece gamePiece;
    private int col;
    private Player player;


    public String getGameID() {
        return gameID;
    }

    public GamePiece getGamePiece() {
        return gamePiece;
    }

    public int getCol() {
        return col;
    }

    public Player getPlayer() {
        return player;
    }
}
