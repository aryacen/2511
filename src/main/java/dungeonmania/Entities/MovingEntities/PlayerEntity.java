package dungeonmania.Entities.MovingEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Item.CraftingSystem;
import dungeonmania.Entities.Item.Inventory;
import dungeonmania.Entities.Item.Item;
import dungeonmania.Entities.MovingEntities.Movement.PlayerMovement;
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
     * Need to be given a set of adjacent entities such as items and static entities
     * @param direction
     * @param surroundingEntities
     * @return Returns a list of the surrounding entities that may have been modified by the movement of the player
     */
    public ArrayList<Entity> move(Direction direction, ArrayList<Entity> surroundingEntities) {
        // Movement will handle whether the player has moved for not
        this.movement.move(this.position, direction, surroundingEntities);

        // Check and pick up any items
        ArrayList<Item> items = (ArrayList<Item>) surroundingEntities.stream().filter(e->e.getEntityType().equals("Item"));
        for (Item item: items) {
            // pick if entity is an item, pick it up
            if (item.getPosition().equals(this.position)) {
                surroundingEntities.remove(item);
                this.i.addItem(item);
            }
        }
        return surroundingEntities;
    }
    // Can bribe the mercenary
    
}
