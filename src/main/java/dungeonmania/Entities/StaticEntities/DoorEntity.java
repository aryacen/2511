package dungeonmania.Entities.StaticEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.util.Position;
import org.json.JSONObject;

public class DoorEntity extends StaticEntity {
    private final int keyId;
    private boolean unlocked;
    public DoorEntity(String id, String type, Position position,  int key) {
        super(id, type, position);
        this.keyId = key;
        unlocked = false;
    }

    public int getKey() {
        return keyId;
    }

    @Override
    public boolean canPass(String type) {
        return unlocked;
    }

    /**
     * @Pre-conditon Only called when player has a valid key that has been used
     */
    public void unlockDoor() {
        unlocked = true;
    }
    // If the Player holds the key, they can open the door by moving through it.
    
    // Once open, it remains open.

    @Override
    public boolean isSpawner() {
        return false;
    }

    // Uncomment when persistence is done
//    @Override
//    public JSONObject getJSON() {
//        JSONObject j = super.getJSON();
//        j.put("key", this.keyId);
//        return j;
//    }

}
