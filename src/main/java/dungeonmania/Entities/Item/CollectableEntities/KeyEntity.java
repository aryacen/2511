package dungeonmania.Entities.Item.CollectableEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Item.Item;
import dungeonmania.util.Position;

public class KeyEntity extends Item {
    // Keys need to match the door
    private int key;

    public KeyEntity(String id, String type, Position position, boolean isInteractable, int key) {
        super(id, type, position, isInteractable);
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    // Can be picked up by the player when they move into the square containing it.
    
    // The player can carry only one key at a time, and only one door has a lock that fits the key.

    // Keys disappear once used in any context, i.e. opening a door, building an item.

    // If a key is used before opening its door, its corresponding door will be locked forever.

}
