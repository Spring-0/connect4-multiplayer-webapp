package me.spring.connect4.models.player;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import me.spring.connect4.models.constants.GamePiece;

@Data
@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String playerID;
    private String username;
    private GamePiece gamePiece;
    private String password;

    public Player(String username, String password){
        this.username = username;
        this.password = password;
    }

    public Player(){

    }


    /*
     * Accessors and Mutators
     */

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setGamePiece(GamePiece gamePiece){
        this.gamePiece = gamePiece;
    }

    public GamePiece getGamePiece(){
        return this.gamePiece;
    }

}
