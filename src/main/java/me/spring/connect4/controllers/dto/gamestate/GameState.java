package me.spring.connect4.controllers.dto.gamestate;

import me.spring.connect4.controllers.dto.PlayerDTO;
import me.spring.connect4.models.player.Player;

public class GameState {

    private String gameID;
    private int pieceRow;
    private int pieceCol;
    private PlayerDTO lastPlayed;

    public GameState(String gameID, Player lastPlayed, int pieceRow, int pieceCol) {
        this.gameID = gameID;
        this.lastPlayed = new PlayerDTO(lastPlayed);
        this.pieceCol = pieceCol;
        this.pieceRow = pieceRow;
    }

    public String getGameID() {
        return this.gameID;
    }
}
