package dungeonmania.Entities.Item.BuildableEntities;

import java.util.HashMap;
import java.util.Map.Entry;

import dungeonmania.util.EntityConstants;
import dungeonmania.util.Position;
import org.json.JSONObject;

public class ShieldEntity extends BuildableEntity {

    public ShieldEntity(String id, String type, Position position) {
        super(id, type, position);
        this.durability = EntityConstants.getInstance("shield_durability").intValue();
    }

    public ShieldEntity(JSONObject j) {
        super(j);
        this.durability = EntityConstants.getInstance("shield_durability").intValue();
        // Uncomment when persistence is done
//        if (j.has("durability")) {
//            this.durability = j.getInt("durability");
//        }
//        else {
//            this.durability = EntityConstants.getInstance("shield_durability").intValue();
//        }
    }

    // Can be crafted with 2 wood + (1 treasure OR 1 key).

    private final static HashMap<String, Integer> essential = new HashMap<>();
    static {
        essential.put("wood", 2);
    }

    private final static HashMap<String, Integer> option = new HashMap<>();
    static {
        option.put("treasure", 1);
        option.put("key", 1);
    }

    @Override
    public HashMap<String, Integer> getEssential() {
        return essential;
    }

    public HashMap<String, Integer> getOptions() {
        return option;
    }

    // Shields decrease the effect of enemy attacks.

    // Each shield has a specific durability that dictates the number of battles
    // it can be used before it deteriorates.
    @Override
    public boolean isWeapon() {
        return true;
    }

    @Override
    public void setDurability(int durability) {
        super.setDurability(EntityConstants.getInstance("shield_durability").intValue());
    }

    @Override
    public double getExtraDefence() {
        return EntityConstants.getInstance("shield_defence").intValue();
    }

    @Override
    public boolean canBeCrafted(HashMap<String, Integer> itemsToRemove) {
        for (Entry<String, Integer> entry : option.entrySet()) {
            HashMap<String, Integer> recipes = new HashMap<>();
            recipes = essential;
            recipes.put(entry.getKey(), entry.getValue());
            if (itemsToRemove.equals(recipes)) {
                return true;
            } else {
                recipes.remove(entry.getKey());
            }
        }
        return false;
    }

    // Uncomment when persistence is done
//    @Override
//    public JSONObject getJSON() {
//        JSONObject j = super.getJSON();
//        j.put("durability", this.durability);
//        return j;
//    }
}
