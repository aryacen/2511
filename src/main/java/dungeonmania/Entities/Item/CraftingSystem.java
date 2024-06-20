package dungeonmania.Entities.Item;

import dungeonmania.Entities.Item.BuildableEntities.BowEntity;
import dungeonmania.Entities.Item.BuildableEntities.BuildableEntity;
import dungeonmania.Entities.Item.BuildableEntities.MidnightArmourEntity;
import dungeonmania.Entities.Item.BuildableEntities.SceptreEntity;
import dungeonmania.Entities.Item.BuildableEntities.ShieldEntity;
import dungeonmania.Entities.Item.BuildableEntities.CraftingRequirements.*;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.EntityConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Figures out what can be crafted and interacts between inventory
 */
public class CraftingSystem {
    public CraftingSystem() {

    }

    public final static String[] craftableItems = { "bow", "shield", "sceptre", "midnight_armour" };

    public ArrayList<String> getBuildable(Inventory i, boolean zombiesPresent) {
        ArrayList<String> output = new ArrayList<>();
        ArrayList<HashMap<String, Integer>> itemRecipe = null;
        BuildableEntity itemToCraft = null;

        for (String item : craftableItems) {
            switch (item) {
                case "bow":
                    itemToCraft = new BowEntity("temp", "bow", EntityConstants.notOnMap);
                    RecipeBow tempBow = new RecipeBow();
                    itemRecipe = tempBow.getItemRecipe();
                    break;
                case "shield":
                    itemToCraft = new ShieldEntity("temp", "shield", EntityConstants.notOnMap);
                    RecipeShield tempShield = new RecipeShield();
                    itemRecipe = tempShield.getItemRecipe();
                    break;
                case "midnight_armour":
                    itemToCraft = new MidnightArmourEntity("temp", "midnight_armour", EntityConstants.notOnMap);
                    RecipeMidnightArmour tempMidnightArmour = new RecipeMidnightArmour();
                    itemRecipe = tempMidnightArmour.getItemRecipe();
                    break;
                case "sceptre":
                    itemToCraft = new SceptreEntity("temp", "sceptre", EntityConstants.notOnMap);
                    RecipeSceptre tempSceptre = new RecipeSceptre();
                    itemRecipe = tempSceptre.getItemRecipe();
                    break;
            }
            HashMap<String, Integer> itemsToRemove = new HashMap<>();

            for (HashMap<String, Integer> tempHash : itemRecipe) {
                for (Entry<String, Integer> entry : tempHash.entrySet()) {
                    if (i.hasItem(entry.getKey()) < entry.getValue()) {
                        // do nothing...
                    } else {
                        itemsToRemove.put(entry.getKey(), entry.getValue());
                        break;
                    }
                }
            }

            boolean craftable = itemToCraft.canBeCrafted(itemsToRemove);
            if (itemToCraft.getType().equals("midnight_armour") && zombiesPresent) {
                craftable = false;
            }

            if (craftable) {
                output.add(item);
            }
        }
        return output;

    }

    /**
     * Craft a specific item
     * Will remove the item from the inventory and add the crafted item in
     *
     * @throws InvalidActionException
     * @throws IllegalArgumentException
     *
     * @Pre-condition: Item can be crafted
     */
    public void craft(String itemName, boolean zombiesPresent, Inventory i)
            throws IllegalArgumentException, InvalidActionException {
        // Check that the item can be crafted
        if (!Arrays.asList(craftableItems).contains(itemName)) {
            throw new IllegalArgumentException("not buildable");
        }
        String newId = EntityConstants.newId();

        BuildableEntity itemToCraft = null;

        ArrayList<HashMap<String, Integer>> itemRecipe = null;

        // Create an item that may or may not be craft-able (needed to check whether it
        // can be crafted)
        switch (itemName) {
            case "bow":
                itemToCraft = new BowEntity(newId, "bow", EntityConstants.notOnMap);
                RecipeBow tempBow = new RecipeBow();
                itemRecipe = tempBow.getItemRecipe();
                break;
            case "shield":
                itemToCraft = new ShieldEntity(newId, "shield", EntityConstants.notOnMap);
                RecipeShield tempShield = new RecipeShield();
                itemRecipe = tempShield.getItemRecipe();
                break;
            case "midnight_armour":
                itemToCraft = new MidnightArmourEntity(newId, "midnight_armour", EntityConstants.notOnMap);
                RecipeMidnightArmour tempMidnightArmour = new RecipeMidnightArmour();
                itemRecipe = tempMidnightArmour.getItemRecipe();
                break;
            case "sceptre":
                itemToCraft = new SceptreEntity(newId, "sceptre", EntityConstants.notOnMap);
                RecipeSceptre tempSceptre = new RecipeSceptre();
                itemRecipe = tempSceptre.getItemRecipe();
                break;
        }

        // Format is item name : quantity for all three
        HashMap<String, Integer> itemsToRemove = new HashMap<>();

        for (HashMap<String, Integer> tempHash : itemRecipe) {
            for (Entry<String, Integer> entry : tempHash.entrySet()) {
                if (i.hasItem(entry.getKey()) < entry.getValue()) {
                    // do nothing...
                } else {
                    itemsToRemove.put(entry.getKey(), entry.getValue());
                    break;
                }
            }
        }

        boolean craftable = itemToCraft.canBeCrafted(itemsToRemove);
        if (itemToCraft.getType().equals("midnight_armour") && zombiesPresent) {
            craftable = false;
        }

        /* Check that the item has passed has all the item it needs */
        if (!craftable) {
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
