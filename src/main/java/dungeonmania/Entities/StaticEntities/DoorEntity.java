package dungeonmania.Entities.StaticEntities;

import dungeonmania.Entities.Entity;

public class DoorEntity extends Entity {

    public DoorEntity() {
        super("Door", "StaticEntity");
    }
    
    // Exists in conjunction with a single key that can open it.

    // If the Player holds the key, they can open the door by moving through it. 
    
    // Once open, it remains open.
}
