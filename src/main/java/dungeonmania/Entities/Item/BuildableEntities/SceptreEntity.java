package dungeonmania.Entities.Item.BuildableEntities;

import dungeonmania.util.Position;

import java.util.HashMap;
import java.util.Map.Entry;

import dungeonmania.util.EntityConstants;

public class SceptreEntity extends BuildableEntity {

    public SceptreEntity(String id, String type, Position position) {
        super(id, type, position);
    }

    // Format is item name: quantity required
    // Items that are necessary for creation
    private final static HashMap<String, Integer> essential = new HashMap<>();
    static {
        essential.put("sun_stone", 1);
    }

    private final static HashMap<String, Integer> option = new HashMap<>();
    static {
        option.put("wood", 1);
        option.put("arrow", 2);
    }

    private final static HashMap<String, Integer> option2 = new HashMap<>();
    static {
        option2.put("key", 1);
        option2.put("treasure", 1);
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
    public boolean canBeCrafted(HashMap<String, Integer> itemsToRemove) {
        for (Entry<String, Integer> entry : option.entrySet()) {
            for (Entry<String, Integer> entry2 : option2.entrySet()) {
                HashMap<String, Integer> recipes = new HashMap<>();
                recipes = essential;
                recipes.put(entry.getKey(), entry.getValue());
                recipes.put(entry2.getKey(), entry2.getValue());
                if (itemsToRemove.equals(recipes)) {
                    return true;
                } else {
                    recipes.remove(entry.getKey());
                    recipes.remove(entry2.getKey());
                }
            }
        }
        return false;
    }
}
