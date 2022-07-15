package dungeonmania.Entities.StaticEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.util.Position;

public class DoorEntity extends StaticEntity {
    private final int key;
    public DoorEntity(String id, String type, Position position, boolean isInteractable, int key) {
        super(id, type, position, isInteractable);
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    @Override
    public boolean canPass(String type) {
        return false;
    }
    // If the Player holds the key, they can open the door by moving through it.
    
    // Once open, it remains open.
}
