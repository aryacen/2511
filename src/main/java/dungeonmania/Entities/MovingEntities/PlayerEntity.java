package dungeonmania.Entities.MovingEntities;

import dungeonmania.Entities.Item.CraftingSystem;
import dungeonmania.Entities.Item.Inventory;
import dungeonmania.Entities.Item.Item;
import dungeonmania.Entities.MovingEntities.Movement.PlayerMovement;
import dungeonmania.Entities.StaticEntities.StaticEntity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.EntityConstants;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PlayerEntity extends MovingEntity {

    private CraftingSystem c;
    private Inventory i;
    private int enemiesDestroyed;

    private final static String[] usableItems = { "bomb", "invisibility_potion", "invincibility_potion" };

    public PlayerEntity(String id, String type, Position position) {
        super(id, type, position);
        this.c = new CraftingSystem();
        this.i = new Inventory();
        this.movement = new PlayerMovement();
        this.hp = EntityConstants.getInstance("player_health");
        this.attack = EntityConstants.getInstance("player_attack");
        this.enemiesDestroyed = 0;
    }

    /**
     * Crafts item with the name buildable
     * 
     * @throws InvalidActionException if the item could not be created
     */
    public void craftItem(String buildable) throws InvalidActionException, IllegalArgumentException {
        c.craft(buildable, this.i);
    }

    /**
     * Moves the player and then pick up any items at the current location
     */
    public void move(Direction direction,
            ArrayList<Item> items,
            ArrayList<StaticEntity> staticEntities,
            ArrayList<MovingEntity> movingEntities) {
        Position newPosition = this.movement.move(this, direction, staticEntities, movingEntities);
        this.position = newPosition;
        // Create a duplicate list to avoid modification of list while in the loop
        ArrayList<Item> tmpItemList = new ArrayList<>();
        tmpItemList.addAll(items);
        // If the item is at the current location of the player, place it in the
        // inventory
        for (Item item : tmpItemList)
            if (item.getPosition().equals(this.position)) {
                this.i.addItem(item);
                items.remove(item);
            }
    }

    public Inventory getI() {
        return this.i;
    }

    public HashMap<String, ArrayList<Item>> getInventory() {
        return this.i.getItems();
    }

    public int getCountOfItems(String itemName) {
        return this.i.hasItem(itemName);
    }

    public ArrayList<String> getBuildable() {
        return this.c.getBuildable(i);
    }

    public void use(String itemId) throws IllegalArgumentException, InvalidActionException {
        String itemType = this.i.getItemType(itemId);
        if (!Arrays.asList(usableItems).contains(itemType) && itemType != null) {
            throw new IllegalArgumentException("Item is not usable");
        } else {
            // If itemType is null then it is not present in the inventory
            if (itemType == null) {
                throw new InvalidActionException("Item is not in inventory");
            }
            // TODO: HAS TO BE ABSTRACTED TO ANOTHER CLASS (PLAYER STATUS CLASS)
            this.i.removeItem(itemType);
            switch (itemType) {
                case "invisibility_potion":
                case "invincibility_potion":
                case "bomb":
                    // TODO: PLACE THE BOMB IN THE MAP
            }
        }

    }

    // Can bribe the mercenary

    /**
     * Return the list of weapons the Player has.
     */
    public List<Item> getWeapons() {
        return i.getAllWeapons();
    }

    /**
     * Update the durability of the weapons the Player has after a battle.
     */
    public void updateDurability() {
        this.i.decreaseDurability();
    }

    /**
     * Increment the number of enemies destroyed by the player.
     */
    public void increaseEnemiesDestroyed(int noOfEnemiesDestroyed) {
        this.enemiesDestroyed += noOfEnemiesDestroyed;
    }

    /**
     * Return the number of enemies destroyed by the player.
     */
    public int getEnemiesDestroyed() {
        return this.enemiesDestroyed;
    }

}
