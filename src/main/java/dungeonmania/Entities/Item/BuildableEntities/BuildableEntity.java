package dungeonmania.Entities.Item.BuildableEntities;

import dungeonmania.Entities.Item.Item;

import java.util.HashMap;

public abstract class BuildableEntity extends Item {
    // Each buildable entity has a recipe that needs to be met
    // TODO: FIGURE HOW HOW TO CRAFT THE ITEMS
    public static HashMap<String, Integer> recipe;
    public BuildableEntity(String id, String type) {
        super(id, type);
    }
    @Override
    public boolean canBeCrafted() {
        return true;
    }
}
