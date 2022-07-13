package dungeonmania.Entities.Item.BuildableEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Item.Item;

public class ShieldEntity extends BuildableEntity {

    public ShieldEntity() {
        super("Shield", "BuildableEntity");
    }

    // Can be crafted with 2 wood + (1 treasure OR 1 key).

    // Shields decrease the effect of enemy attacks.

    // Each shield has a specific durability that dictates the number of battles
    // it can be used before it deteriorates.

}
