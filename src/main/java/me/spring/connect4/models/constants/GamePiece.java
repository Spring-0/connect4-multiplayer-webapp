package me.spring.connect4.models.constants;

public enum GamePiece {

    YELLOW(1),
    RED(2);

    private int value;

    private GamePiece(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

}
