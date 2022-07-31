package dungeonmania.Entities.Item;

import dungeonmania.Entities.EntityFactory.EntityCreator;
import dungeonmania.Entities.Item.CollectableEntities.KeyEntity;
import dungeonmania.util.Position;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Collectors;

/*
This class contains the player's inventory
 */
public class Inventory {
    // Each item is stored in a list that will keep track of how many items there
    // are
    // Each item list is stored as a string list pair in a hash map

    // Key = item type, Value = List of Items of that item type
    private HashMap<String, ArrayList<Item>> items;
    // Player can only hold 1 key at a time
    private KeyEntity key;
    public static int noSuchItem = -1;

    public Inventory() {
        items = new HashMap<>();
        key = null;
    }

    // Uncomment when persistence is done
//    public Inventory(JSONArray j) {
//        Iterator itemIterator = j.iterator();
//        while (itemIterator.hasNext()) {
//            addItem((Item) EntityCreator.createEntity((JSONObject) itemIterator.next()));
//        }
//    }

    /**
     * Checks if inventory has a certain item and in what quantity
     * 
     * @return -1 if item has not found, otherwise returns how many instances of the
     *         item the player has
     */
    public int hasItem(String itemName) {
        if (this.items.containsKey(itemName)) {
            return items.get(itemName).size();
        } else if (itemName.equals("key")) {
            if (key == null) {
                return noSuchItem;
            } else {
                return 1;
            }
        } else {
            return noSuchItem;
        }
    }

    /**
     * Adds an item into the players inventory
     */
    public void addItem(Item i) {
        // If it is a key, add it to its own list
        if (i.getType().equals("key")) {
            this.key = (KeyEntity) i;
            return;
        }
        // If there are no items, create a new list
        if (hasItem(i.getType()) == noSuchItem) {
            ArrayList<Item> tempList = new ArrayList<Item>();
            tempList.add(i);
            this.items.put(i.getType(), tempList);
            // update the existing list of that item
        } else {
            ArrayList<Item> tempList = this.items.get(i.getType());
            tempList.add(i);
            this.items.replace(i.getType(), tempList);
        }
    }

    /**
     * Removes one instance of the item
     * 
     * @Pre-condition Must have at least one instance of the item in the inventory
     *                AND is not used to remove a key
     */
    public void removeItem(String itemName) {
        /*
         * Access the item list using the hashmap
         * Remove the last item
         */
        if (itemName.equals("key")) {
            this.key = null;
            return;
        }
        ArrayList<Item> itemList = this.items.getOrDefault(itemName, new ArrayList<Item>());
        if (itemList.size() > 1) {
            itemList.remove((itemList.size() - 1));
            this.items.replace(itemName, itemList);
        } else {
            this.items.remove(itemName);
        }
    }

    /**
     * Removes n instances of the item
     * 
     * @Pre-condition Must have at least n instances of the item in the inventory
     */
    public void removeItem(String itemName, int n) {
        for (int i = 0; i < n; i++) {
            removeItem(itemName);
        }
    }

    /**
     * Decrease the durability of items that can be used during combat
     * 
     * @Pre-condition Must have at least one instance of the item AND item has a
     *                durability that can be decreased
     */
    public void decreaseDurability() {
        // Decrease the durability of an item
        // If durability reaches 0, remove the item by calling removeItem()
        ArrayList<Item> weapons = getAllWeapons();
        for (Item weapon : weapons) {
            weapon.decreaseDurability();
            if (weapon.getDurability() == 0) {
                removeItem(weapon.getType());
            }
        }
    }

    /**
     * Returns all weapons in the inventories.
     */
    public ArrayList<Item> getAllWeapons() {
        ArrayList<Item> weaponsList = new ArrayList<>();
        for (String itemName : this.items.keySet()) {
            ArrayList<Item> weapons = this.items.get(itemName);
            if (weapons.get(weapons.size() - 1).isWeapon()) {
                weaponsList.add(weapons.get(0));
            }
        }
        return weaponsList;
    }

    /**
     * Returns true if the key has been found, will remove the key from the users
     * inventory
     */
    public boolean validKey(int keyId) {
        // If there is no key or key player has is invalid, return false
        if (this.key == null || !(this.key.getKey() == keyId)) {
            return false;
        }
        // If there is a valid key, remove it and return true
        else {
            this.key = null;
            return true;
        }
    }

    public HashMap<String, ArrayList<Item>> getItems() {
        HashMap<String, ArrayList<Item>> itemList = new HashMap<>();
        itemList.putAll(this.items);
        if (this.key != null) {
            itemList.put("keys", new ArrayList<>(Arrays.asList(this.key)));
        }
        return itemList;
    }

    /**
     * 
     * @param itemId
     * @return itemType
     */
    public String getItemType(String itemId) {
        for (String key : this.items.keySet()) {
            ArrayList<Item> tempItem = this.items.get(key);
            Item item = tempItem.stream().filter(i -> i.getId().equals(itemId)).findFirst().get();
            return item.getType();
        }
        return null;

    }

    // Uncomment when persistence is done
    // Returns each item in the inventory as a JSONArray of JSONObject
    // public JSONArray getJSON() {
    // JSONArray j = new JSONArray();
    // for (String itemName: this.items.keySet()) {
    // this.items.get(itemName).forEach(i -> j.put(i.getJSON()));
    // }
    // if (key != null) {
    // j.put(key.getJSON());
    // }
    // return j;
    // }

    /**
     * Picks up all items that the player can pick up at specified position
     */
    public void pickUpItems(ArrayList<Item> items, Position pos) {
        ArrayList<Item> itemsAtPos = new ArrayList<>(items);
        itemsAtPos = (ArrayList<Item>) itemsAtPos.stream().filter(i -> i.getPosition().equals(pos))
                .collect(Collectors.toList());
        // If the item is at the current location of the player, place it in the
        for (Item i : itemsAtPos) {
            if (canPickUp(i.getType())) {
                addItem(i);
                items.remove(i);
            }
        }
    }

    /**
     * Checks whether an item can be picked up
     */
    private boolean canPickUp(String type) {
        // Can not pick up multiple keys
        return !type.equals("key") || this.key == null;
    }

    // hash map has keyset function, so for each key in the key set you have a list
    // and then we can iterate through that list using streams exmaple
    // streams.filter.i.getType();
}
