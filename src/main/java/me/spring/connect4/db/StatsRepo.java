package me.spring.connect4.db;

import jakarta.transaction.Transactional;
import me.spring.connect4.models.PlayerStatistics;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsRepo extends CrudRepository<PlayerStatistics, String> {

    @Modifying
    @Transactional
    @Query("UPDATE PlayerStatistics p SET p.wins = p.wins + 1 WHERE p.player.playerID = :playerId")
    void incrementWin(@Param("playerId") String playerId);


    @Modifying
    @Transactional
    @Query("UPDATE PlayerStatistics p SET p.gamesPlayed = p.gamesPlayed + 1 WHERE p.player.playerID = :playerId")
    void incrementGamePlayed(@Param("playerId") String playerId);


    @Modifying
    @Transactional
    @Query("UPDATE PlayerStatistics p SET p.losses = p.losses + 1 WHERE p.player.playerID = :playerId")
    void incrementLoss(@Param("playerId") String playerId);
}
