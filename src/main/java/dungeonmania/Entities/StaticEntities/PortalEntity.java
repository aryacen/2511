package dungeonmania.Entities.StaticEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.util.Position;

public class PortalEntity extends Entity {
    private final String color;
    public PortalEntity(String id, String type, Position position, boolean isInteractable, String color) {
        super(id, type, position, isInteractable);
        this.color = color;
    }

    public String getColor() {
        return color;
    }
    // Teleports entities to a corresponding portal. The player must
    // end up in a square cardinally adjacent to the corresponding portal.
    // The square they teleport onto must also be within movement 
    // constraints - e.g. the player cannot teleport and end up on a wall. 
    // If all squares cardinally adjacent to the corresponding portal are 
    // walls, then the player should remain where they are.
}
