package dungeonmania.Entities.Item.BuildableEntities.CraftingRequirements;

import java.util.ArrayList;
import java.util.HashMap;

public class EntityShield implements CraftableEntity {

    private static ArrayList<HashMap<String, Integer>> itemRecipe = new ArrayList<>();
    private static HashMap<String, Integer> firstItem = new HashMap<>();
    private static HashMap<String, Integer> secondItem = new HashMap<>();

    static {
        firstItem.put("wood", 2);
        secondItem.put("treasure", 1);
        secondItem.put("key", 1);
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
        return true;
    }

    @Override
    public ArrayList<HashMap<String, Integer>> getItemRecipe() {
        return itemRecipe;
    }

}
