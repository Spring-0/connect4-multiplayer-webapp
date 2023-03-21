package me.spring.connect4.db;

import me.spring.connect4.models.player.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepo extends CrudRepository<Player, String> {

    Player findPlayerByPlayerID(String playerID);

}
