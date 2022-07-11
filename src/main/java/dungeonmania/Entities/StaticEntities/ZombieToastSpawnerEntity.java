package dungeonmania.Entities.StaticEntities;

import dungeonmania.Entities.Entity;

public class ZombieToastSpawnerEntity extends Entity {

    public ZombieToastSpawnerEntity() {
        super("ZombieToastSpawner", "StaticEntity");
    }

    // Spawns zombie toasts in an open square cardinally adjacent to the spawner.
    
    // The Player can destroy a zombie spawner if they have a weapon and are 
    // cardinally adjacent to the spawner.
    
}
