package me.spring.connect4.controllers.dto;

import me.spring.connect4.models.constants.GamePiece;
import me.spring.connect4.models.player.Player;

public class PlayerDTO {
    private String playerID;
    private String username;
    private GamePiece gamePiece;

    public PlayerDTO(Player player){
        this.playerID = player.getPlayerID();
        this.username = player.getUsername();
        this.gamePiece = player.getGamePiece();
    }
}
