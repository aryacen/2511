package dungeonmania.Entities.MovingEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Item.CraftingSystem;
import dungeonmania.Entities.Item.Inventory;
import dungeonmania.util.Position;

public class PlayerEntity extends Entity{

    private CraftingSystem c;
    private Inventory i;

    public PlayerEntity(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, false);
        c = new CraftingSystem();
        i = new Inventory();
    }


    // Can bribe the mercenary
    
}
