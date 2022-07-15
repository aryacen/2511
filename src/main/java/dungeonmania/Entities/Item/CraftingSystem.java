package dungeonmania.Entities.Item;

import dungeonmania.Entities.Item.BuildableEntities.BowEntity;
import dungeonmania.Entities.Item.BuildableEntities.BuildableEntity;
import dungeonmania.Entities.Item.BuildableEntities.ShieldEntity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.EntityConstants;

import java.util.Arrays;
import java.util.HashMap;

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
        }
        String newId = EntityConstants.newId();

        BuildableEntity itemToCraft = null;
        HashMap<String, Integer> essential;
        HashMap<String, Integer> options;

        // Create an item that may or may not be craft-able (needed to check whether it can be crafted)
        switch (itemName) {
            case "bow":
                itemToCraft = new BowEntity(newId, "bow", EntityConstants.notOnMap, false);
                break;
            case "shield":
                itemToCraft = new ShieldEntity(newId, "bow", EntityConstants.notOnMap, false);
                break;
        }


        // Format is item name : quantity for all three
        essential = itemToCraft.getEssential();
        options = itemToCraft.getOptions();
        HashMap<String, Integer> itemsToRemove = new HashMap<>();

        /* Check that the inventory has all the essential items */
        for (String materialName: essential.keySet()) {
            /*
            If the inventory does not have the sufficient number of essential materials, set itemToCraft to
            null and break
            */
            if (i.hasItem(materialName) < essential.get(materialName)) {
                itemToCraft = null;
                break;
            }
            else {
                // Add it into the list of items that need to be removed
                itemsToRemove.put(materialName, essential.get(materialName));
            }
        }

        /* Check that inventory has enough for at least one of the optional items */
        boolean hasOptionMaterials = false;
        for (String materialName: options.keySet()) {
            /*
            If the inventory has more than what is required for a certain option material, break the loop since we
            can create the item
            */
            if (i.hasItem(materialName) >= options.get(materialName)) {
                hasOptionMaterials = true;
                itemsToRemove.put(materialName, options.get(materialName));
                break;
            }
        }

        /* Check that the item has passed has all the item it needs */
        if (itemToCraft == null || !hasOptionMaterials) {
            throw new InvalidActionException("insufficient material");
        }
        else {
            /* Remove item from inventory */
            for (String itemToRemove: itemsToRemove.keySet()) {
                i.removeItem(itemName, itemsToRemove.get(itemToRemove));
            }
            /* Add item crafted ot inventory */
            i.addItem(itemToCraft);
        }
    }
}
