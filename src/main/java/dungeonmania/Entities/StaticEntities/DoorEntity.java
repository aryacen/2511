package dungeonmania.Entities.StaticEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.util.Position;

public class DoorEntity extends StaticEntity {
    private final int keyId;
    public DoorEntity(String id, String type, Position position, boolean isInteractable, int key) {
        super(id, type, position, isInteractable);
        this.keyId = key;
    }

    public int getKey() {
        return keyId;
    }

    @Override
    public boolean canPass(String type) {
        return false;
    }
    // If the Player holds the key, they can open the door by moving through it.
    
    // Once open, it remains open.
}
