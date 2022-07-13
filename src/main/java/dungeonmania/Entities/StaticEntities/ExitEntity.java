package dungeonmania.Entities.StaticEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.util.Position;

public class ExitEntity extends Entity{
    public ExitEntity(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
    }


    // If the Player goes through it, the puzzle is complete.
}
