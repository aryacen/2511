package dungeonmania.Entities.Item;

import java.util.ArrayList;
import java.util.HashMap;

/*
This class contains the player's inventory
 */
public class Inventory {
    // Each item is stored in a list that will keep track of how many items there are
    // Each item list is stored as a string list pair in a hash map
    HashMap<String, ArrayList> items;
    // TODO: Think about how battles will interact with

    public Inventory() {
        items = new HashMap<>();
    }


    /**
     * Checks if inventory has a certain item and in what quantity
     * @return -1 if item has not found, otherwise returns how many instances of the item the player has
     */
    public int hasItem() {
        return -1;
    }

    /**
     * Adds an item into the players inventory
     */
    public void addItem() {

    }

    /**
     * Removes one instance of the item
     * @Pre-condition Must have at least one instance of the item in the inventory
     */
    public void removeItem() {
        // Access the item list using the hashmap
        // Remove the last item
        // If the list is empty, remove it from the hashmap
    }

    /**
     * Removes n instances of the item
     * @Pre-condition Must have at least n instances of the item in the inventory
     */
    public void removeItem(int n) {
        // Access the item list using the hashmap
        // Remove the n last item
        // If the list is empty, remove it from the hashmap
    }

    /**
     * Decrease the durability of items that can be used during combat
     * @Pre-condition Must have at least one instance of the item AND item has a durability that can be decreased
     */
    public void decreaseDurability() {
        // Decrease the durability of an item
        // If durability reaches 0, remove the item by calling removeItem()
    }
}
