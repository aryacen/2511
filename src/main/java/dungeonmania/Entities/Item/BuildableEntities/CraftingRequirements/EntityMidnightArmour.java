package dungeonmania.Entities.Item.BuildableEntities.CraftingRequirements;

import java.util.ArrayList;
import java.util.HashMap;

import dungeonmania.Entities.Item.Item;

public class EntityMidnightArmour implements CraftableEntity {

    private static ArrayList<HashMap<String, Integer>> itemRecipe = new ArrayList<>();
    private static HashMap<String, Integer> firstItem = new HashMap<>();
    private static HashMap<String, Integer> secondItem = new HashMap<>();

    static {
        firstItem.put("sun_stone", 1);
        secondItem.put("sword", 1);

        itemRecipe.add(firstItem);
        itemRecipe.add(secondItem);
    }

    @Override
    public boolean craftable() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean passedConditions() {
        // TODO: FIND OUT HOW TO CHECK IF A ZOMBIE ENTITY IS PRESENT CURRENTLY
        return false;
    }

    @Override
    public ArrayList<HashMap<String, Integer>> getItemRecipe() {
        return itemRecipe;
    }

}
