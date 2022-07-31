package dungeonmania.Entities.Item.CollectableEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Item.Item;
import dungeonmania.util.Position;
import org.json.JSONObject;

public class KeyEntity extends Item {
    // Keys need to match the door
    private final int keyId;

    public KeyEntity(String id, String type, Position position, int key) {
        super(id, type, position);
        this.keyId = key;
    }

    public int getKey() {
        return keyId;
    }


    // Uncomment when persistence is done
//    @Override
//    public JSONObject getJSON() {
//        JSONObject j = super.getJSON();
//        j.put("key", this.keyId);
//        return j;
//    }

    // Can be picked up by the player when they move into the square containing it.
    
    // The player can carry only one key at a time, and only one door has a lock that fits the key.

    // Keys disappear once used in any context, i.e. opening a door, building an item.

    // If a key is used before opening its door, its corresponding door will be locked forever.

}
