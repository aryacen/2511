package dungeonmania.Entities.Item.BuildableEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Item.Item;
import dungeonmania.util.EntityConstants;
import dungeonmania.util.Position;

public class BowEntity extends BuildableEntity {
    public BowEntity(String id, String type, Position position, boolean isInteractable) {
        super(id, type, EntityConstants.notOnMap, false);
    }

    // Can be crafted with 1 wood + 3 arrows.
    
    // The bow has a durability which deteriorates after a certain number of battles.

    // Bows give the Player double damage in a single round, to simulate being able to
    // attack an enemy at range (it can't actually attack an enemy at range).
}
