package me.spring.connect4.models.dto.gamestate;

import me.spring.connect4.constants.SpecialGameCases;
import me.spring.connect4.models.Player;

public class GameStateSpecial extends GameState{

    private SpecialGameCases specialCase;

    public GameStateSpecial(String gameID, Player lastPlayed, SpecialGameCases errorMessage) {
        super(gameID, lastPlayed, -1, -1);
        this.specialCase = errorMessage;
    }

    public SpecialGameCases getSpecialMessage(){
        return this.specialCase;
    }

}
