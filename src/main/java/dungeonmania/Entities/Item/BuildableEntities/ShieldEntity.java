package dungeonmania.Entities.Item.BuildableEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Item.Item;
import dungeonmania.util.EntityConstants;
import dungeonmania.util.Position;

public class ShieldEntity extends BuildableEntity {
    public ShieldEntity(String id, String type, Position position, boolean isInteractable) {
        super(id, type, EntityConstants.notOnMap, false);
    }


    // Can be crafted with 2 wood + (1 treasure OR 1 key).

    // Shields decrease the effect of enemy attacks.

    // Each shield has a specific durability that dictates the number of battles
    // it can be used before it deteriorates.

}
