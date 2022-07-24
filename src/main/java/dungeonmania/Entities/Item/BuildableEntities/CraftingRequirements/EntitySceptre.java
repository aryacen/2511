package dungeonmania.Entities.Item.BuildableEntities.CraftingRequirements;

import java.util.ArrayList;
import java.util.HashMap;

public class EntitySceptre implements CraftableEntity {

    private static ArrayList<HashMap<String, Integer>> itemRecipe = new ArrayList<>();
    private static HashMap<String, Integer> firstItem = new HashMap<>();
    private static HashMap<String, Integer> secondItem = new HashMap<>();
    private static HashMap<String, Integer> thirdItem = new HashMap<>();

    static {
        firstItem.put("wood", 1);
        firstItem.put("arrow", 2);

        secondItem.put("key", 1);
        secondItem.put("treasure", 1);

        thirdItem.put("sun_stone", 1);

        itemRecipe.add(firstItem);
        itemRecipe.add(secondItem);
        itemRecipe.add(thirdItem);
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
