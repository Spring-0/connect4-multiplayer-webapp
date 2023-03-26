package me.spring.connect4.controllers.dto.gamestate;

import me.spring.connect4.models.player.Player;

public class GameState {

    private String gameID;
    private boolean winner = false;
    private int pieceRow;
    private int pieceCol;
    private Player lastPlayed;

    public GameState(String gameID, Player lastPlayed, int pieceRow, int pieceCol, boolean winner) {
        this.gameID = gameID;
        this.lastPlayed = lastPlayed;
        this.pieceCol = pieceCol;
        this.pieceRow = pieceRow;
        this.winner = winner;
    }

    public String getGameID() {
        return this.gameID;
    }
}
