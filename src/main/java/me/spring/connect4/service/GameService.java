package me.spring.connect4.service;

import me.spring.connect4.db.GameRepo;
import me.spring.connect4.models.constants.GamePiece;
import me.spring.connect4.models.constants.GameStatus;
import me.spring.connect4.models.game.Game;
import me.spring.connect4.models.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    @Autowired
    private GameRepo gameRepo;


    public GameService(){}


    public Game createGame(Player player1){
        Game game = new Game(player1);

        gameRepo.save(game);

        return game;
    }

    /**
     * Functionality behind the connect endpoint
     * Validates gameID and update fields
     *
     * @param player2
     * @param gameID
     * @return
     */
    public Game connectToGame(Player player2, String gameID){
        if(!gameExists(gameID)){
            System.out.println("GameID does not exist");
            return null;
        }
        Game game = getGameById(gameID);
        player2.setGamePiece(GamePiece.RED);
        game.setPlayer2(player2);
        game.setGameStatus(GameStatus.IN_PROGRESS);

        gameRepo.save(game);

        return game;
    }

    /**
     * Functionality behind connect/random endpoint
     * Queries database for games with "new" status
     *
     * @param player2
     * @return
     */
    public Game connectToRandomGame(Player player2){
        List<Game> newGameEntities = gameRepo.findGameEntitiesByGameStatus(GameStatus.NEW);

        if(newGameEntities.size() > 0){
            Game game = newGameEntities.get(0);
            player2.setGamePiece(GamePiece.RED);
            game.setPlayer2(player2);
            game.setGameStatus(GameStatus.IN_PROGRESS);

            gameRepo.save(game);

            return game;

        } else {
            throw new RuntimeException("No games to join");
        }

    }

    /**
     * Helper method to check if gameID exists
     *
     * @param gameID
     * @return
     */
    private boolean gameExists(String gameID){
        Optional<Game> optionalGameEntity = gameRepo.findById(gameID);
        return optionalGameEntity.isPresent();
    }

    /**
     * Helper method to get game by ID
     *
     * @param gameID
     * @return Game Object
     */
    private Game getGameById(String gameID){
        Optional<Game> optionalGameEntity = gameRepo.findById(gameID);
        if(gameExists(gameID)){
            Game gameEntity = optionalGameEntity.get();
            return gameEntity;
        }
        throw new RuntimeException("Game does not exist");

    }

    /**
     * Functionality behind playgame endpoint
     * Allows user to place a game piece, checks for valid locations, checks for winner
     *
     * @param gameID
     * @param player
     * @param col
     * @return Updated game state
     */
    public Game makeMove(String gameID, Player player, int col){

        Game game = getGameById(gameID);

        if(game.getTurn().equals(player)){

            int rowIndex = game.colSpace(col);

            if(rowIndex != -1) { // Column is not full
                game.getBoard()[rowIndex][col] = player.getGamePiece().getValue();

                Player winner = game.checkWinner(rowIndex, col);
                if(winner != null){
                    game.setWinner(winner);
                    System.out.println(winner.getUsername() + " has won the game.");
                    game.swapTurn();
                    game.end();
                }
                game.swapTurn();
                gameRepo.save(game);
            }

        }

        return game;
    }

}
