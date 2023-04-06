package me.spring.connect4.service;

import me.spring.connect4.db.StatsRepo;
import me.spring.connect4.models.dto.gamestate.GameState;
import me.spring.connect4.models.dto.gamestate.GameStateSpecial;
import me.spring.connect4.db.GameRepo;
import me.spring.connect4.constants.GamePiece;
import me.spring.connect4.constants.GameStatus;
import me.spring.connect4.constants.SpecialGameCases;
import me.spring.connect4.models.Game;
import me.spring.connect4.models.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    @Autowired
    private GameRepo gameRepo;

    @Autowired
    private StatsRepo statsRepo;

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
    public Game connectToGame(Player player2, String gameID) {
        Game game = gameRepo.findById(gameID).orElse(null);
        if (game == null) {
            System.out.println("GameID does not exist");
            return null;
        }
        if (game.getPlayer2() != null) {
            System.out.println("Game already has two players");
            return null;
        }
        player2.setGamePiece(GamePiece.RED);
        game.setPlayer2(player2);
        game.setGameStatus(GameStatus.IN_PROGRESS);
        gameRepo.save(game);
        return game;
    }



    public Game getGame(String gameID){
        return gameRepo.findById(gameID).orElse(null);
    }


    /**
     * Functionality behind connect/random endpoint
     * Queries database for games with "new" status and returns game
     *
     * @return
     */
    public Game getRandomGame(){
        List<Game> newGameEntities = gameRepo.findGameEntitiesByGameStatus(GameStatus.NEW);

        if(newGameEntities.size() > 0){
            return newGameEntities.get(0);
        }

        return null;
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
            return optionalGameEntity.get();
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
    public GameState makeMove(String gameID, Player player, int col){

        Game game = getGameById(gameID);
        int rowIndex;

        if(game.getPlayer2() == null){
            return new GameStateSpecial(gameID, player, SpecialGameCases.WAITING_FOR_PLAYER);
        }

        if(game.getTurn().equals(player)){

            rowIndex = game.colSpace(col);

            if(rowIndex != -1) { // Column is not full
                game.getBoard()[rowIndex][col] = player.getGamePiece().getValue();

                Player winner = game.checkWinner(rowIndex, col);
                if(winner != null){
                    game.setWinner(winner);
                    System.out.println(winner.getUsername() + " has won the game.");

                    statsRepo.incrementWin(winner.getPlayerID());
                    statsRepo.incrementLoss(winner.equals(game.getPlayer1()) ? game.getPlayer2().getPlayerID() : game.getPlayer1().getPlayerID());

                    game.swapTurn();
                    end(game);
                    return new GameStateSpecial(gameID, player, SpecialGameCases.PLAYER_WIN);
                }

                // Check if the game board is full
                if(game.isBoardFull()){
                    // No winner meaning game is a tie.
                    end(game);
                    return new GameStateSpecial(gameID, player, SpecialGameCases.GAME_DRAW);
                }

                game.swapTurn();
                gameRepo.save(game);
                return new GameState(gameID, player, rowIndex, col);
            }
            return new GameStateSpecial(gameID, player, SpecialGameCases.COL_IS_FULL);
        }
        return new GameStateSpecial(gameID, player, SpecialGameCases.NOT_YOUR_TURN);
    }


    /**
     * Method that ends the game,
     * updates the game status
     */
    public void end(Game game){
        game.setGameStatus(GameStatus.FINISHED);
        statsRepo.incrementGamePlayed(game.getPlayer1().getPlayerID());
        statsRepo.incrementGamePlayed(game.getPlayer2().getPlayerID());

    }

}
