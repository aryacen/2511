package dungeonmania.Entities.StaticEntities;

import dungeonmania.Entities.Entity;

public class FloorSwitchEntity extends Entity {

    public FloorSwitchEntity() {
        super("FloorSwitch", "StaticEntity");
    }

    // Switches behave like empty squares, so other entities can appear on top of them. 
    
    // When a boulder is pushed onto a floor switch, it is triggered.

    // Pushing a boulder off the floor switch untriggers.
    
}
