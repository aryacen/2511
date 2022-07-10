package dungeonmania.Entities.StaticEntities;

import dungeonmania.Entities.Entity;

public class PortalEntity extends Entity {
    public PortalEntity() {
        super("Portal", "StaticEntity");
    }
    
    // Teleports entities to a corresponding portal. The player must 
    // end up in a square cardinally adjacent to the corresponding portal.
    // The square they teleport onto must also be within movement 
    // constraints - e.g. the player cannot teleport and end up on a wall. 
    // If all squares cardinally adjacent to the corresponding portal are 
    // walls, then the player should remain where they are.
}
