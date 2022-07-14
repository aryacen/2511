package dungeonmania.Entities.MovingEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.util.Position;

public class ZombieToastEntity extends Entity {
    public ZombieToastEntity(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
    }
    // Zombies spawn at zombie spawners and move in random directions.
    // Zombies are limited by the same movement constraints as the Player,
    // except portals have no effect on them.

}
