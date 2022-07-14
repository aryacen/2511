package dungeonmania.Entities;

import dungeonmania.Entities.MovingEntities.MovingEntities;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.response.models.RoundResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to conduct a battle
 */
public class Battle {
    private MovingEntities player;
    // Moving entity that is fighting the player
    private MovingEntities enemy;
    public Battle(MovingEntities player, MovingEntities combatant) {
        this.player = player;
        this.enemy = combatant;
    }
    public BattleResponse battle() {
        double initialPlayerHealth = player.getHp();
        double initialEnemyHealth = enemy.getHp();
        ArrayList<RoundResponse> rounds = new ArrayList<>();
        // While either the player or entity is alive, the round continues
        while (player.getHp() > 0 && enemy.getHp() > 0) {
            // What does the player have access to? (a list)
            // TODO: INCLUDE ITEMS
            List<ItemResponse> weaponryUsed = new ArrayList<>();

            // Player Health = Player Health - (Enemy Attack Damage / 10)
            double deltaPlayerHealth = enemy.getAttack() / 10;
            player.setHp(player.getHp() + deltaPlayerHealth);

            // Enemy Health = Enemy Health - (Player Attack / 5)
            double deltaEnemyHealth = - (player.getAttack() / 5);
            enemy.setHp(enemy.getHp() + deltaEnemyHealth);

            rounds.add(new RoundResponse(deltaPlayerHealth, deltaEnemyHealth, weaponryUsed));
        }
        return new BattleResponse(enemy.getType(), rounds, initialPlayerHealth, initialEnemyHealth);
        // TODO: AFTER THE BATTLE, NEED TO CHECK WHETHER THE PLAYER HAS DIED OR NOT
    }
}
