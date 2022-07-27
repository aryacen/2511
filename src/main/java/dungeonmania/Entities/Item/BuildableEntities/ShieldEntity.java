package dungeonmania.Entities.Item.BuildableEntities;

import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.HashMap;
import dungeonmania.util.EntityConstants;

public class ShieldEntity extends BuildableEntity {
    private int durability;
    //private int 

    public ShieldEntity(String id, String type, Position position) {
        super(id, type, position);
        this.durability = EntityConstants.getInstance("shield_durability").intValue();
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
}
