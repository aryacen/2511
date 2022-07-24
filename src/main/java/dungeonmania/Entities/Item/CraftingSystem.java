package dungeonmania.Entities.Item;

import dungeonmania.Entities.Item.BuildableEntities.BowEntity;
import dungeonmania.Entities.Item.BuildableEntities.BuildableEntity;
import dungeonmania.Entities.Item.BuildableEntities.ShieldEntity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.EntityConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Figures out what can be crafted and interacts between inventory
 */
public class CraftingSystem {
    public CraftingSystem() {

    }

    public final static String[] craftableItems = { "bow", "shield" };
    // public final static String[] noOptionalItems = { "bow" };

    public ArrayList<String> getBuildable(Inventory i) {

        // ArrayList<String> output = new ArrayList<>();

        // for (String item : craftableItems) {
        // HashMap<String, Integer> essentials = new HashMap<>();
        // HashMap<String, Integer> optionals = new HashMap<>();
        // essentials = getEssentials(item);
        // optionals = getOptionals(item);
        // boolean hasEssentials = false;
        // boolean hasOptionals = false;

        // for (String materialName : essentials.keySet()) {
        // if (i.hasItem(materialName) >= essentials.get(materialName)) {
        // hasEssentials = true;
        // } else {
        // hasEssentials = false;
        // }
        // }

        // for (String materialName : optionals.keySet()) {
        // if (i.hasItem(materialName) >= optionals.get(materialName)) {
        // hasOptionals = true;
        // } else {
        // hasOptionals = false;
        // }
        // }

        // if (Arrays.asList(noOptionalItems).contains(item)) {
        // hasOptionals = true;
        // }

        // if (hasEssentials && hasOptionals) {
        // output.add(item);
        // }
        // }
        // return output;
        return null;

    }

    // public HashMap<String, Integer> getEssentials(String item) {
    // BuildableEntity itemToCraft = null;
    // switch (item) {
    // case "bow":
    // itemToCraft = new BowEntity("temp", "bow", EntityConstants.notOnMap, false);
    // case "shield":
    // itemToCraft = new ShieldEntity("temp", "shield", EntityConstants.notOnMap,
    // false);
    // }
    // return itemToCraft.getEssential();
    // }

    // public HashMap<String, Integer> getOptionals(String item) {
    // BuildableEntity itemToCraft = null;
    // switch (item) {
    // case "bow":
    // itemToCraft = new BowEntity("temp", "bow", EntityConstants.notOnMap, false);
    // case "shield":
    // itemToCraft = new ShieldEntity("temp", "shield", EntityConstants.notOnMap,
    // false);
    // }
    // return itemToCraft.getOptions();
    // }

    /**
     * Craft a specific item
     * Will remove the item from the inventory and add the crafted item in
     * 
     * @throws InvalidActionException
     * 
     * @Pre-condition: Item can be crafted
     */
    public void craft(String itemName, Inventory i) throws IllegalArgumentException, InvalidActionException {
        // Check that the item can be crafted
        if (!Arrays.asList(craftableItems).contains(itemName)) {
            throw new IllegalArgumentException("not buildable");
        }
        String newId = EntityConstants.newId();

        BuildableEntity itemToCraft = null;
        HashMap<String, Integer> essential;
        HashMap<String, Integer> options;

        // Create an item that may or may not be craft-able (needed to check whether it
        // can be crafted)
        switch (itemName) {
            case "bow":
                itemToCraft = new BowEntity(newId, "bow", EntityConstants.notOnMap, false);
                break;
            case "shield":
                itemToCraft = new ShieldEntity(newId, "shield", EntityConstants.notOnMap, false);
                break;
        }

        // Format is item name : quantity for all three
        essential = itemToCraft.getEssential();
        options = itemToCraft.getOptions();
        HashMap<String, Integer> itemsToRemove = new HashMap<>();

        /* Check that the inventory has all the essential items */
        for (String materialName : essential.keySet()) {
            /*
             * If the inventory does not have the sufficient number of essential materials,
             * set itemToCraft to
             * null and break
             */
            if (i.hasItem(materialName) < essential.get(materialName)) {
                itemToCraft = null;
                break;
            } else {
                // Add it into the list of items that need to be removed
                itemsToRemove.put(materialName, essential.get(materialName));
            }
        }

        /* Check that inventory has enough for at least one of the optional items */
        boolean hasOptionalMaterials = false;
        for (String materialName : options.keySet()) {
            /*
             * If the inventory has more than what is required for a certain option
             * material, break the loop since we
             * can create the item
             */
            if (i.hasItem(materialName) >= options.get(materialName)) {
                hasOptionalMaterials = true;
                itemsToRemove.put(materialName, options.get(materialName));
                break;
            }
        }

        if (itemToCraft != null && itemToCraft.getOptions().isEmpty()) {
            hasOptionalMaterials = true;
        }

        /* Check that the item has passed has all the item it needs */
        if (itemToCraft == null || !hasOptionalMaterials) {
            throw new InvalidActionException("insufficient material");
        } else {
            /* Remove item from inventory */
            for (String itemToRemove : itemsToRemove.keySet()) {
                i.removeItem(itemToRemove, itemsToRemove.get(itemToRemove));
            }
            /* Add item crafted to inventory */
            i.addItem(itemToCraft);
        }

    }
}
