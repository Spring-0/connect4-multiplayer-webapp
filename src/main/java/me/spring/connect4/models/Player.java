package me.spring.connect4.models;

import jakarta.persistence.*;
import lombok.Data;
import me.spring.connect4.constants.GamePiece;

@Data
@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String playerID;
    private String username;
    private GamePiece gamePiece;
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private PlayerStatistics playerStatistics;

    public Player(String username, String password){
        this.username = username;
        this.password = password;
        this.playerStatistics = new PlayerStatistics(this);
    }

    public Player(){

    }


    /*
     * Accessors and Mutators
     */


    public String getPlayerID() {
        return playerID;
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
