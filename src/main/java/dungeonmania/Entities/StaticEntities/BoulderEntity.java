package dungeonmania.Entities.StaticEntities;

import java.util.ArrayList;

import dungeonmania.Entities.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BoulderEntity extends StaticEntity {
    public BoulderEntity(String id, String type, Position position) {
        super(id, type, position);
    }

    @Override
    public boolean canPass(String type) {
        return false;
    }

    /**
     * Moves the boulder in given direction
     * @Pre-condition is only called when there is nothing in the boulders way
     */
    public void push(Direction direction, ArrayList<StaticEntity> staticEntities) {
        this.untriggerSwitch(staticEntities);
        this.position = this.position.translateBy(direction);
        this.triggerSwitch(staticEntities);
    }
    // Acts like a wall in most cases.

    // The only difference is that it can be pushed by the Player into cardinally adjacent squares.

    // The Player is only strong enough to push one boulder at a time.

    // When the player pushes a boulder, they move into the spot the boulder was previously in. 
    
    // Boulders can be pushed onto collectable entities.

    @Override
    public boolean isSpawner() {
        return false;
    }

    public void untriggerSwitch(ArrayList<StaticEntity> staticEntities) {
        for (StaticEntity entity : staticEntities) {
            if (entity.getType().equals("switch") && entity.getPosition().equals(this.getPosition())) {
                FloorSwitchEntity floorSwitch = (FloorSwitchEntity) entity;
                floorSwitch.setUntriggered();
            }
        }
    }

    public void triggerSwitch(ArrayList<StaticEntity> staticEntities) {
        for (StaticEntity entity : staticEntities) {
            if (entity.getType().equals("switch") && entity.getPosition().equals(this.getPosition())) {
                FloorSwitchEntity floorSwitch = (FloorSwitchEntity) entity;
                floorSwitch.setTriggered();
            }
        }
    }
}
