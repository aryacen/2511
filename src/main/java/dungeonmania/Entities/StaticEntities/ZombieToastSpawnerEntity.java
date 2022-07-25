package dungeonmania.Entities.StaticEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.util.Position;

public class ZombieToastSpawnerEntity extends StaticEntity {
    public ZombieToastSpawnerEntity(String id, String type, Position position) {
        super(id, type, position);
        this.isInteractable = true;
    }

    @Override
    public boolean canPass(String type) {
        return true;
    }

    // Spawns zombie toasts in an open square cardinally adjacent to the spawner.
    
    // The Player can destroy a zombie spawner if they have a weapon and are 
    // cardinally adjacent to the spawner.
    
    @Override
    public boolean isSpawner() {
        return true;
    }
}
