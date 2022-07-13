package dungeonmania.Entities.StaticEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.util.Position;

public class FloorSwitchEntity extends Entity {
    public FloorSwitchEntity(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
    }


    // Switches behave like empty squares, so other entities can appear on top of them. 
    
    // When a boulder is pushed onto a floor switch, it is triggered.

    // Pushing a boulder off the floor switch untriggers.
    
}
