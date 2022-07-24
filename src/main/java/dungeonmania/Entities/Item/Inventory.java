package dungeonmania.Entities.Item;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Item.BuildableEntities.BowEntity;
import dungeonmania.Entities.Item.BuildableEntities.ShieldEntity;
import dungeonmania.Entities.Item.CollectableEntities.KeyEntity;
import dungeonmania.Entities.Item.CollectableEntities.SwordEntity;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
This class contains the player's inventory
 */
public class Inventory {
    // Each item is stored in a list that will keep track of how many items there
    // are
    // Each item list is stored as a string list pair in a hash map

    // Key = item type, Value = List of Items of that item type
    private HashMap<String, ArrayList<Item>> items;
    // Keys are stored in their own list
    ArrayList<KeyEntity> keys;
    public static int noSuchItem = -1;

    public Inventory() {
        items = new HashMap<>();
        keys = new ArrayList<>();
    }

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
            if (keys.isEmpty()) {
                return noSuchItem;
            } else {
                return keys.size();
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
            keys.add((KeyEntity) i);
            return;
        }
        // If there are no items, created a new list
        if (hasItem(i.getType()) == noSuchItem) {
            ArrayList<Item> tempList = new ArrayList<Item>();
            tempList.add(i);
            this.items.put(i.getType(), tempList);
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
            this.keys.remove(0);
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
            String itemType = weapon.getType();
            switch (itemType) {
                case "bow":
                    BowEntity usedBow = (BowEntity) weapon;
                    usedBow.decreaseDurability();
                    if (usedBow.getDurability() == 0) {
                        removeItem("bow");
                    }
                    break;
                case "sword":
                    SwordEntity usedSword = (SwordEntity) weapon;
                    usedSword.decreaseDurability();
                    if (usedSword.getDurability() == 0) {
                        removeItem("sword");
                    }
                    break;
                case "shield":
                    ShieldEntity usedShield = (ShieldEntity) weapon;
                    usedShield.decreaseDurability();
                    if (usedShield.getDurability() == 0) {
                        removeItem("shield");
                    }
                    break;
            }
        }
    }

    /**
     * Returns all weapons in the inventories.
     */
    public ArrayList<Item> getAllWeapons() {
        ArrayList<Item> weaponsList = new ArrayList<>();
        if (hasItem("bow") >= 1) {
            ArrayList<Item> bows = this.items.get("bow");
            weaponsList.add(bows.get(0));
        }
        if (hasItem("sword") >= 1) {
            ArrayList<Item> swords = this.items.get("sword");
            weaponsList.add(swords.get(0));
        }
        if (hasItem("shield") >= 1) {
            ArrayList<Item> shields = this.items.get("shield");
            weaponsList.add(shields.get(0));
        }
        return weaponsList;
    }

    /**
     * Returns true if the key has been found, will remove the key from the users
     * inventory
     */
    public boolean validKey(int keyId) {
        if (keys.isEmpty()) {
            return false;
        }
        KeyEntity validKey = null;
        for (KeyEntity key : keys) {
            if (key.getKey() == keyId) {
                validKey = key;
                break;
            }
        }
        if (validKey == null) {
            return false;
        } else {
            keys.remove(validKey);
            return true;
        }
    }

    public HashMap<String, ArrayList<Item>> getItems() {
        HashMap<String, ArrayList<Item>> itemList = new HashMap<>();
        itemList.putAll(this.items);
        ArrayList<Item> keyList = new ArrayList<>();
        keyList.addAll(keys);
        itemList.put("key", keyList);
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

    // hash map has keyset function, so for each key in the key set you have a list
    // and then we can iterate through that list using streams exmaple
    // streams.filter.i.getType();
}
