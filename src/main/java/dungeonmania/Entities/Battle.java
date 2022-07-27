package dungeonmania.Entities;

import dungeonmania.Entities.Item.Item;
import dungeonmania.Entities.MovingEntities.MovingEntity;
import dungeonmania.Entities.MovingEntities.PlayerEntity;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.response.models.RoundResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to conduct a battle
 */
public class Battle {
    private MovingEntity player;
    // Moving entity that is fighting the player
    private MovingEntity enemy;
    public Battle(MovingEntity player, MovingEntity combatant) {
        this.player = player;
        this.enemy = combatant;
    }
    public BattleResponse battle() {
        double initialPlayerHealth = player.getHp();
        double initialEnemyHealth = enemy.getHp();
        ArrayList<RoundResponse> rounds = new ArrayList<>();
        PlayerEntity playerEntity = (PlayerEntity) player;
        List<ItemResponse> weaponryUsed = new ArrayList<>();
        List<Item> weapons = playerEntity.getWeapons();

        double enemyAttack = enemy.getAttack();
        double playerAttack = player.getAttack();

        // While either the player or entity is alive, the round continues
        while (player.getHp() > 0 && enemy.getHp() > 0) {
            // What does the player have access to? (a list)
            for (Item weapon : weapons) {
                enemyAttack -= weapon.getExtraDefence();
                playerAttack += weapon.getExtraAttack(playerAttack);
                weaponryUsed.add(new ItemResponse(weapon.getId(), weapon.getType()));
            }

            double deltaPlayerHealth = - (enemyAttack / 10);
            player.setHp(player.getHp() + deltaPlayerHealth);

            double deltaEnemyHealth = - (playerAttack / 5);
            enemy.setHp(enemy.getHp() + deltaEnemyHealth);

            rounds.add(new RoundResponse(deltaPlayerHealth, deltaEnemyHealth, weaponryUsed));
        }

        playerEntity.decreaseDurability();

        return new BattleResponse(enemy.getType(), rounds, initialPlayerHealth, initialEnemyHealth);
        // TODO: AFTER THE BATTLE, NEED TO CHECK WHETHER THE PLAYER HAS DIED OR NOT
    }
}
