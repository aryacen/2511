package dungeonmania.Entities.Item.BuildableEntities;

import dungeonmania.util.Position;

import java.util.HashMap;
import dungeonmania.util.EntityConstants;

public class ShieldEntity extends BuildableEntity {
    private int durability;

    public ShieldEntity(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
        this.durability = EntityConstants.shield_durability;
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
    public int getDurability() {
        return durability;
    }

    public void decreaseDurability() {
        this.durability -= 1;
    }
}
