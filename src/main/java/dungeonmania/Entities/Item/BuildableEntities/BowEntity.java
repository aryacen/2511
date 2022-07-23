package dungeonmania.Entities.Item.BuildableEntities;

import dungeonmania.util.Position;

import java.util.HashMap;
import dungeonmania.util.EntityConstants;

public class BowEntity extends BuildableEntity {
    private int durability;

    public BowEntity(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
        this.durability = EntityConstants.bow_durability;
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
    
    // The bow has a durability which deteriorates after a certain number of battles.
    public int getDurability() {
        return durability;
    }
    public void decreaseDurability() {
        this.durability -= 1;
    }

    // Bows give the Player double damage in a single round, to simulate being able to
    // attack an enemy at range (it can't actually attack an enemy at range).
}
