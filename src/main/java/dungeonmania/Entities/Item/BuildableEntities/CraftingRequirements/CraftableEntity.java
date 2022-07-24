package dungeonmania.Entities.Item.BuildableEntities.CraftingRequirements;

import java.util.ArrayList;
import java.util.HashMap;

public interface CraftableEntity {

    public boolean craftable();

    public ArrayList<HashMap<String, Integer>> getItemRecipe();

    public boolean passedConditions();
}
