package dungeonmania.Entities.Item.BuildableEntities;

import dungeonmania.util.Position;

import java.util.HashMap;
import dungeonmania.util.EntityConstants;

public class MidnightArmourEntity extends BuildableEntity {

    public MidnightArmourEntity(String id, String type, Position position) {
        super(id, type, position);
    }

    // Format is item name: quantity required
    // Items that are necessary for creation
    private final static HashMap<String, Integer> essential = new HashMap<>();
    static {
        essential.put("sun_stone", 1);
        essential.put("sword", 1);
    }

    @Override
    public HashMap<String, Integer> getEssential() {
        return essential;
    }

    @Override
    public HashMap<String, Integer> getOptions() {
        return new HashMap<String, Integer>();
    }

    @Override
    public boolean isWeapon() {
        return true;
    }

    @Override
    public double getExtraAttack(double currentAttack) {
        return EntityConstants.getInstance("midnight_armour_attack").intValue();
    }

    @Override
    public double getExtraDefence() {
        return EntityConstants.getInstance("midnight_armour_defence").intValue();
    }

    @Override
    public boolean canBeCrafted(HashMap<String, Integer> itemsToRemove) {
        if (itemsToRemove.equals(essential)) {
            return true;
        } else {
            return false;
        }
    }

    // Lasts forever
}
