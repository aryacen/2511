package dungeonmania.Entities.Item.BuildableEntities;

import dungeonmania.util.Position;

import java.util.HashMap;
import dungeonmania.util.EntityConstants;

public class SceptreEntity extends BuildableEntity {

    public SceptreEntity(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
    }

    // Format is item name: quantity required
    // Items that are necessary for creation
    private final static HashMap<String, Integer> essential = new HashMap<>();
    static {
        essential.put("sun_stone", 1);
    }

    @Override
    public HashMap<String, Integer> getEssential() {
        return essential;
    }

    @Override
    public HashMap<String, Integer> getOptions() {
        return new HashMap<String, Integer>();
    }
}
