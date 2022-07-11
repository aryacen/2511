package dungeonmania.Entities.Item.BuildableEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Item.Item;

public class BowEntity extends Entity implements Item {

    public BowEntity(String EntityName, String EntityType) {
        super("Bow", "BuildableEntity");
    }

    // Can be crafted with 1 wood + 3 arrows.
    
    // The bow has a durability which deteriorates after a certain number of battles.

    // Bows give the Player double damage in a single round, to simulate being able to
    // attack an enemy at range (it can't actually attack an enemy at range).

    @Override
    public void collectItem(Entity entity) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void buildItem(Entity entity) {
        // TODO Auto-generated method stub
        
    }
    
}
