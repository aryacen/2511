package dungeonmania.Entities.MovingEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.MovingEntities.Movement.Movement;
import dungeonmania.util.Position;

public class ZombieToastEntity extends MovingEntities {
    public ZombieToastEntity(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
        // TODO: GIVE ZOMBIE TOAST A MOVEMENT FUNCTION
    }
    // Zombies spawn at zombie spawners and move in random directions.
    // Zombies are limited by the same movement constraints as the Player,
    // except portals have no effect on them.

}
