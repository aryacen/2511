package dungeonmania.Entities.Item.BuildableEntities.CraftingRequirements;

import java.util.ArrayList;
import java.util.HashMap;

public class EntityBow implements CraftableEntity {

    private static ArrayList<HashMap<String, Integer>> itemRecipe = new ArrayList<>();
    private static HashMap<String, Integer> firstItem = new HashMap<>();
    private static HashMap<String, Integer> secondItem = new HashMap<>();

    static {
        firstItem.put("wood", 1);
        secondItem.put("arrow", 3);
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
