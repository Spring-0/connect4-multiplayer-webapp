package me.spring.connect4.models;


import jakarta.persistence.*;
import me.spring.connect4.constants.GamePiece;
import me.spring.connect4.constants.GameStatus;
import me.spring.connect4.util.converter.IntArrayConverter;


@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String gameID;

    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;

    @Convert(converter = IntArrayConverter.class)
    private int[][] board;

    @OneToOne
    @JoinColumn(name = "player1_id")
    private Player player1;

    @OneToOne
    @JoinColumn(name = "player2_id")
    private Player player2 = null;

    @OneToOne
    @JoinColumn(name = "winner_id")
    private Player winner = null;

    @OneToOne
    @JoinColumn(name = "turn_id")
    private Player turn;


    // Constructs game given 1 player
    public Game(Player player1){
        this.board = new int[6][7];
        player1.setGamePiece(GamePiece.YELLOW);
        this.player1 = player1;
        this.turn = player1;


        this.gameStatus = GameStatus.NEW;
    }

    public Game(){

    }


    /**
     * Method that calls other methods to check for win
     *
     * @param latestRow
     * @param latestCol
     * @return
     */
    public Player checkWinner(int latestRow, int latestCol) {
        int piece = board[latestRow][latestCol];
        if (checkRow(latestRow, piece) || checkCol(latestCol, piece) || checkDiagonal(latestRow, latestCol, piece)) {
            return turn;
        }
        return null;
    }

    /**
     * Method that checks each row for a connect4
     *
     * @param row
     * @param piece
     * @return
     */
    public boolean checkRow(int row, int piece) {
        int count = 0;
        for (int col = 0; col < board[0].length; col++) {
            if (board[row][col] == piece) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }

    /**
     * Method that checks each col for a connect4
     *
     * @param col
     * @param piece
     * @return
     */
    public boolean checkCol(int col, int piece) {
        int count = 0;
        for (int row = 0; row < board.length; row++) {
            if (board[row][col] == piece) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }


    /**
     * Method to check if the game board is full
     *
     * @return
     */
    public boolean isBoardFull(){
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if(board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Method that checks diagonal combinations for connect4
     *
     * @param row
     * @param col
     * @param piece
     * @return
     */
    public boolean checkDiagonal(int row, int col, int piece) {
        // Check upward diagonal
        int count = 0;
        int i = row;
        int j = col;
        while (i >= 0 && j >= 0) {
            if (board[i][j] == piece) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                break;
            }
            i--;
            j--;
        }
        i = row + 1;
        j = col + 1;
        while (i < board.length && j < board[0].length) {
            if (board[i][j] == piece) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                break;
            }
            i++;
            j++;
        }

        // Check downward diagonal
        count = 0;
        i = row;
        j = col;
        while (i >= 0 && j < board[0].length) {
            if (board[i][j] == piece) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                break;
            }
            i--;
            j++;
        }
        i = row + 1;
        j = col - 1;
        while (i < board.length && j >= 0) {
            if (board[i][j] == piece) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                break;
            }
            i++;
            j--;
        }

        return false;
    }


    /**
     * Method that swaps the user's turn
     *
     */
    public void swapTurn(){
        if(this.turn.equals(this.player1)){
            this.turn = player2;
        } else{
            this.turn = player1;
        }
    }


    /**
     * Method that returns the available row in a col
     *
     * @param col
     * @return
     */
    public int colSpace(int col){

        int bottomRow = -1; // initialize to -1
        for(int i = 0; i < 6; i++) {
            if(this.board[i][col] == 0) {
                bottomRow = i; // update bottomRow to current empty row
            } else {
                break; // break out of loop if non-empty cell is found
            }
        }
        return bottomRow;
    }


    /*
     * Accessors and Mutators
     */


    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Player getTurn() {
        return turn;
    }

    public void setTurn(Player turn) {
        this.turn = turn;
    }
}
