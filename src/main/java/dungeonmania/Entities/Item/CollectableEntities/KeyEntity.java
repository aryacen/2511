package dungeonmania.Entities.Item.CollectableEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Item.Item;

public class KeyEntity extends Entity implements Item {

    public KeyEntity(String EntityName, String EntityType) {
        super("Key", "CollectableEntity");
    }

    // Can be picked up by the player when they move into the square containing it.
    
    // The player can carry only one key at a time, and only one door has a lock that fits the key.

    // Keys disappear once used in any context, i.e. opening a door, building an item.

    // If a key is used before opening its door, its corresponding door will be locked forever.

    @Override
    public void collectItem(Entity entity) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void buildItem(Entity entity) {
        // TODO Auto-generated method stub
        
    }
    
}
