package dungeonmania.Entities.Item.CollectableEntities;

import dungeonmania.Entities.Item.Item;
import dungeonmania.util.Position;

import dungeonmania.util.EntityConstants;
import org.json.JSONObject;

public class SwordEntity extends Item {
    public SwordEntity(String id, String type, Position position) {
        super(id, type, position);
        this.durability = EntityConstants.getInstance("sword_durability").intValue();
    }

    public SwordEntity(JSONObject j) {

        super(j);
        this.durability = EntityConstants.getInstance("sword_durability").intValue();
        // Uncomment when persistence is done
//        if (j.has("durability")) {
//            this.durability = j.getInt("durability");
//        }
//        else {
//            this.durability = EntityConstants.getInstance("sword_durability").intValue();
//        }
    }

    @Override
    public boolean isWeapon() {
        return true;
    }

    public int getDurability() {
        return this.durability;
    }
    @Override
    public double getExtraAttack(double currentAttack) {
        return EntityConstants.getInstance("sword_attack").intValue();
    }

    public void decreaseDurability() {
        this.durability -= 1;
    }

    // Uncomment when persistence is done
//    @Override
//    public JSONObject getJSON() {
//            JSONObject j = super.getJSON();
//            j.put("durability", this.durability);
//            return j;
//        }
    @Override
    public void setDurability(int durability) {
        super.setDurability(EntityConstants.getInstance("sword_durability").intValue());
    }
}
