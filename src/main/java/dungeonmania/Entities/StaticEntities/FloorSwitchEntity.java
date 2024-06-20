package dungeonmania.Entities.StaticEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.util.Position;

public class FloorSwitchEntity extends StaticEntity {

    private boolean triggered;

    public FloorSwitchEntity(String id, String type, Position position) {
        super(id, type, position);
        this.triggered = false;
    }

    @Override
    public boolean canPass(String type) {
        return true;
    }

    @Override
    public boolean isSpawner() {
        return false;
    }

    public void setUntriggered() {
        this.triggered = false;
    }

    public void setTriggered() {
        this.triggered = true;
    }

    public boolean getTriggered() {
        return this.triggered;
    }

    // Switches behave like empty squares, so other entities can appear on top of them. 
    
    // When a boulder is pushed onto a floor switch, it is triggered.

    // Pushing a boulder off the floor switch untriggers.
    
}
