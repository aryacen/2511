package dungeonmania.Entities.Item;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/*
This class contains the player's inventory
 */
public class Inventory {
    // Each item is stored in a list that will keep track of how many items there are
    // Each item list is stored as a string list pair in a hash map
    private HashMap<String, ArrayList<Item>> items;
    public static int noSuchItem = -1;

    public Inventory() {
        items = new HashMap<>();
    }


    /**
     * Checks if inventory has a certain item and in what quantity
     * @return -1 if item has not found, otherwise returns how many instances of the item the player has
     */
    public int hasItem(Item i) {
        if (!this.items.containsKey(i.getType())) {
            return items.get(i.getType()).size();
        }
        else {
            return noSuchItem;
        }
    }

    /**
     * Adds an item into the players inventory
     */
    public void addItem(Item i) {
        // If there are no items, created a new list
        if (hasItem(i) == noSuchItem) {
            this.items.put(i.getType(), new ArrayList<>());
        }
        // Add the item to the list
        this.items.get(i.getType()).add(i);
    }

    /**
     * Removes one instance of the item
     * @Pre-condition Must have at least one instance of the item in the inventory
     */
    public void removeItem(String itemName) {
        /*
         Access the item list using the hashmap
         Remove the last item
        */
        ArrayList<Item> itemList = this.items.get(itemName);
        itemList.remove(itemList.size() - 1);

        /* If the list is empty, remove it from the hashmap */
        if (itemList.isEmpty()) {
            items.remove(itemName);
        }

    }

    /**
     * Removes n instances of the item
     * @Pre-condition Must have at least n instances of the item in the inventory
     */
    public void removeItem(String itemName, int n) {
        for (int i = 0; i < n; i++) {
            removeItem(itemName);
        }
    }

    /**
     * Decrease the durability of items that can be used during combat
     * @Pre-condition Must have at least one instance of the item AND item has a durability that can be decreased
     */
    public void decreaseDurability() {
        // Decrease the durability of an item
        // If durability reaches 0, remove the item by calling removeItem()
        // TODO: FINISH THIS
    }
}
