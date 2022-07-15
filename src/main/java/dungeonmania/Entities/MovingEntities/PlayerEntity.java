package dungeonmania.Entities.MovingEntities;

import dungeonmania.Entities.Item.CraftingSystem;
import dungeonmania.Entities.Item.Inventory;
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

    public void move(Direction direction, ArrayList<StaticEntity> staticEntities) {
        this.position = this.movement.move(position, direction, staticEntities);
    }
    // Can bribe the mercenary
    
}
