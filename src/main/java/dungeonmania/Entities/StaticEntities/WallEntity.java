package dungeonmania.Entities.StaticEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.util.Position;

public class WallEntity extends StaticEntity {
    public WallEntity(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
    }

    @Override
    public boolean canPass(String type) {
        return false;
    }

    // Blocks the movement of the Player, enemies and boulders.
    
}
