package dungeonmania.Entities.Item;

import dungeonmania.Entities.Item.BuildableEntities.ShieldEntity;
import dungeonmania.exceptions.InvalidActionException;

import java.lang.reflect.Array;
import java.util.Arrays;

import static dungeonmania.Entities.Item.BuildableEntities.ShieldEntity.*;

/**
 * Figures out what can be crafted and interacts between inventory
 */
public class CraftingSystem {
    public CraftingSystem() {

    }
    public final static String[] craftableItems = {"bow", "shield"};

    /**
     * Craft a specific item
     * Will remove the item from the inventory and add the crafted item in
     * @Pre-condition: Item can be crafted
     */
    public void craft(String itemName, Inventory i) throws InvalidActionException {
        // Check that the item can be crafted
        if (!Arrays.asList(craftableItems).contains(itemName)) {
            throw new InvalidActionException("not buildable");
        } else if (false) {
            throw new InvalidActionException("insufficient material");
        }
        // TODO: FIGURE OUT how to craft items with variable recipes
        // Remove item from inventory
        // Add item crafted ot inventory
    }

    private boolean sufficentMaterial(String itemName, Inventory i) {
        // TODO FIGURE THIS OUT
        boolean canCraft = true;
        switch (itemName) {
            case "shield":
                break;
            case "bow":
                break;
        }
        return canCraft;
    }
}
