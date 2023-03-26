package me.spring.connect4.models.dto;

import me.spring.connect4.constants.GamePiece;
import me.spring.connect4.models.Player;

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
