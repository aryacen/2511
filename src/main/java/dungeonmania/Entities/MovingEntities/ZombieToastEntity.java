package dungeonmania.Entities.MovingEntities;

import dungeonmania.Entities.Entity;

public class ZombieToastEntity extends Entity {
    public ZombieToastEntity() {
        super("ZombieToast", "MovingEntity");
    }

    // Zombies spawn at zombie spawners and move in random directions.
    // Zombies are limited by the same movement constraints as the Player,
    // except portals have no effect on them.

}
