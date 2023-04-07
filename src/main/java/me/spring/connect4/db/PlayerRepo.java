package me.spring.connect4.db;

import me.spring.connect4.models.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PlayerRepo extends CrudRepository<Player, String> {

    Player findPlayerByPlayerID(String playerID);
    Player findPlayerByUsernameAndPassword(String username, String password);
    Player findByUsername(String username);

    @Query("SELECT p.username, s.gamesPlayed, s.wins, s.losses FROM Player p JOIN p.playerStatistics s ORDER BY s.gamesPlayed DESC, s.wins DESC, s.losses DESC")
    List<Object[]> findTopPlayersByWinLossRatio();

}
