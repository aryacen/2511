package dungeonmania.Entities.Item.BuildableEntities;

import dungeonmania.util.Position;

import java.util.HashMap;

import dungeonmania.Entities.MovingEntities.PlayerEntity;
import dungeonmania.util.EntityConstants;

public class BowEntity extends BuildableEntity {
    public BowEntity(String id, String type, Position position) {
        super(id, type, position);
    }

    // Format is item name: quantity required
    // Items that are necessary for creation
    private final static HashMap<String, Integer> essential = new HashMap<>();
    static {
        essential.put("wood", 1);
        essential.put("arrow", 3);
    }

    @Override
    public HashMap<String, Integer> getEssential() {
        return essential;
    }

    @Override
    public HashMap<String, Integer> getOptions() {
        return new HashMap<String, Integer>();
    }

    // Can be crafted with 1 wood + 3 arrows.

    @Override
    public boolean isWeapon() {
        return true;
    }

    @Override
    public void setDurability(int durability) {
        super.setDurability(EntityConstants.getInstance("bow_durability").intValue());
    }

    @Override
    public double getExtraAttack(double currentAttack) {
        return currentAttack;
    }

    @Override
    public boolean canBeCrafted(HashMap<String, Integer> itemsToRemove) {
        if (itemsToRemove.equals(essential)) {
            return true;
        } else {
            return false;
        }
    }

    // Bows give the Player double damage in a single round, to simulate being able
    // to
    // attack an enemy at range (it can't actually attack an enemy at range).
}
