package dungeonmania.Entities.MovingEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Item.CraftingSystem;
import dungeonmania.Entities.Item.Inventory;
import dungeonmania.Entities.Item.Item;
import dungeonmania.Entities.MovingEntities.Movement.PlayerMovement;
import dungeonmania.Entities.StaticEntities.StaticEntity;
import dungeonmania.Entities.StaticEntities.ZombieToastSpawnerEntity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.EntityConstants;
import dungeonmania.util.Position;
import org.json.JSONArray;
import org.json.JSONObject;

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

    public PlayerEntity(JSONObject j) {
        super(j);
        this.c = new CraftingSystem();
        this.movement = new PlayerMovement();
        this.hp = EntityConstants.getInstance("player_health");
        this.attack = EntityConstants.getInstance("player_attack");
        this.enemiesDestroyed = 0;
        this.i = new Inventory();

        // Uncomment when persistence is done
        // if (j.has("inventory")) {
        // this.i = new Inventory((JSONArray) j.get("inventory"));
        // }
        // else {
        // this.i = new Inventory();
        // }
    }

    /**
     * Crafts item with the name buildable
     * 
     * @throws InvalidActionException if the item could not be created
     */
    public void craftItem(String buildable, boolean zombiesPresent)
            throws InvalidActionException, IllegalArgumentException {
        c.craft(buildable, zombiesPresent, this.i);
    }

    /**
     * Moves the player and then pick up any items at the current location
     */
    public void move(Direction direction,
            ArrayList<Item> items,
            ArrayList<StaticEntity> staticEntities,
            ArrayList<MovingEntity> movingEntities) {
        this.position = this.movement.move(this, direction, staticEntities, movingEntities);
        // Get the inventory to pick up any item it can at the current location
        this.i.pickUpItems(items, this.position);
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

    public ArrayList<String> getBuildable(boolean zombiesPresent) {
        return this.c.getBuildable(i, zombiesPresent);
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

    public void interact(String entityId, ArrayList<StaticEntity> staticEntities,
            ArrayList<MovingEntity> movingEntities) throws IllegalArgumentException, InvalidActionException {

        Entity interactable = null;
        ZombieToastSpawnerEntity spawner = null;
        MercenaryEntity merc = null;
        /*
         * interactable = movingEntities.stream().filter(me ->
         * me.getId().equals(entityId)).findFirst();
         * if (interactable == null) {
         * interactable = staticEntities.stream().filter(se ->
         * se.getId().equals(entityId)).findFirst();
         * }
         */
        for (MovingEntity entity : movingEntities) {
            if (entity.getId().equals(entityId)) {
                switch (entity.getType()) {
                    case "mercenary":
                        merc = (MercenaryEntity) entity;
                        break;
                    case "assassin":
                        break;

                }
                interactable = entity;

            }
        }

        if (interactable == null) {
            for (StaticEntity entity : staticEntities) {
                if (entity.getId().equals(entityId)) {
                    spawner = (ZombieToastSpawnerEntity) entity;
                    interactable = entity;
                }
            }
        }

        if (interactable == null || !interactable.isInteractable()) {
            throw new IllegalArgumentException("Object is not interactable");
        } else {
            String objectType = interactable.getType();
            switch (objectType) {
                // ASSUMES spawner can be destroyed with either a sword or bow
                case "zombie_toast_spawner":
                    spawner.interact(spawner, this, staticEntities);
                    break;
                // this.interacter.destroySpawner(interactable, this, this.i, staticEntities);
                case "mercenary":
                    merc.interact(merc, this, staticEntities);
                    // this.interacter.bribeMercenary(interactable, this, i, staticEntities);
                    break;
                case "assassin":

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

    public void decreaseDurability() {
        this.i.decreaseDurability();
    }

    // Uncomment when persistence is done
    // @Override
    // public JSONObject getJSON() {
    // JSONObject j = super.getJSON();
    // j.put("inventory", this.i.getJSON());
    // return j;
    // }

}
