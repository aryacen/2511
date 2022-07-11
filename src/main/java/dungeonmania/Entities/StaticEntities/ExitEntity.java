package dungeonmania.Entities.StaticEntities;

import dungeonmania.Entities.Entity;

public class ExitEntity extends Entity{

    public ExitEntity() {
        super("Exit", "StaticEntity");
    }
    
    // If the Player goes through it, the puzzle is complete.
}
