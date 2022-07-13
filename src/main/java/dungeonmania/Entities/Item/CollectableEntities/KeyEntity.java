package dungeonmania.Entities.Item.CollectableEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Item.Item;

public class KeyEntity extends Item {
    public KeyEntity(String id) {
        super(id, "key");
    }

    // Can be picked up by the player when they move into the square containing it.
    
    // The player can carry only one key at a time, and only one door has a lock that fits the key.

    // Keys disappear once used in any context, i.e. opening a door, building an item.

    // If a key is used before opening its door, its corresponding door will be locked forever.

}
