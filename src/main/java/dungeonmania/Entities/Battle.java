package dungeonmania.Entities;

import dungeonmania.Entities.MovingEntities.MovingEntities;

/**
 * This class is used to conduct a battle
 */
public class Battle {
    private MovingEntities player;
    // Moving entity that is fighting the player
    private MovingEntities combatant;
    public Battle(MovingEntities player, MovingEntities combatant) {
        this.player = player;
        this.combatant = combatant;
    }
    // Battles
}
