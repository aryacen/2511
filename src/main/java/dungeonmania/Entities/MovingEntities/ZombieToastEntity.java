package dungeonmania.Entities.MovingEntities;

import dungeonmania.util.EntityConstants;
import dungeonmania.util.Position;

public class ZombieToastEntity extends MovingEntities {
    public ZombieToastEntity(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
        this.hp = EntityConstants.zombie_health;
        this.attack = EntityConstants.zombie_attack;
        // GIVE ZOMBIE TOAST A MOVEMENT FUNCTION
    }
    // Zombies spawn at zombie spawners and move in random directions.
    // Zombies are limited by the same movement constraints as the Player,
    // except portals have no effect on them.

}
