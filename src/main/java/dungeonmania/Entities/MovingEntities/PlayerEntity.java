package dungeonmania.Entities.MovingEntities;

import dungeonmania.Entities.Entity;
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

public class PlayerEntity extends MovingEntities {

    private CraftingSystem c;
    private Inventory i;

    public PlayerEntity(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
        this.c = new CraftingSystem();
        this.i = new Inventory();
        this.movement = new PlayerMovement();
        this.hp = EntityConstants.player_health;
        this.attack = EntityConstants.player_attack;
    }

    /**
     * Crafts item with the name buildable
     * @throws InvalidActionException if the item could not be created
     */
    public void craftItem(String buildable) throws InvalidActionException {
        c.craft(buildable, i);
    }

    /**
     * Moves the player and then pick up any items at the current location
     */
    public void move(Direction direction, ArrayList<Item> items,  ArrayList<StaticEntity> staticEntities) {
        this.movement.move(position, direction, staticEntities);

        // Create a duplicate list to avoid modification of list while in the loop
        ArrayList<Item> tmpItemList = new ArrayList<>();
        tmpItemList.addAll(items);
        // If the item is at the current location of the player, place it in the inventory
        for (Item item: tmpItemList)
            if (item.getPosition().equals(this.position)) {
                this.i.addItem(item);
                items.remove(item);
            }
    }

    // Can bribe the mercenary
    
}
