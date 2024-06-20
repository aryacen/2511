package dungeonmania.Entities.StaticEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.util.Position;

public class WallEntity extends StaticEntity {
    public WallEntity(String id, String type, Position position) {
        super(id, type, position);
    }

    @Override
    public boolean canPass(String type) {
        return false;
    }

    // Blocks the movement of the Player, enemies and boulders.
    
    @Override
    public boolean isSpawner() {
        return false;
    }
}
