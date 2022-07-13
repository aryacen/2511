package dungeonmania.Entities.MovingEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Item.CraftingSystem;
import dungeonmania.Entities.Item.Inventory;

public class PlayerEntity extends Entity{

    private CraftingSystem c;
    private Inventory i;

    public PlayerEntity() {
        super("Player", "MovingEntities");
        c = new CraftingSystem();
        i = new Inventory();
    }

    // Can bribe the mercenary
    
}
