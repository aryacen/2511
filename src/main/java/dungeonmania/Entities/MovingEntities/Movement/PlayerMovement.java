package dungeonmania.Entities.MovingEntities.Movement;

import com.sun.jdi.ArrayReference;
import dungeonmania.Entities.Entity;
import dungeonmania.Entities.StaticEntities.BoulderEntity;
import dungeonmania.Entities.StaticEntities.StaticEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.ArrayList;

public class PlayerMovement extends Movement {

    /**
     * Move the player from current position in the direction given
     * @param currentPosition
     * @param direction
     * @param staticEntities
     */
    @Override
    public void move(Position currentPosition, Direction direction, ArrayList<StaticEntity> staticEntities) {
        Position offset = direction.getOffset();
        Position newPosition = currentPosition.translateBy(offset);

        StaticEntity entityAtNewPosition = Movement.entityAtPosition(newPosition, staticEntities);
        // If we can walk through the static entity or there are no static entity, move
        if (entityAtNewPosition.canPass("player") || entityAtNewPosition.equals(null)) {
            currentPosition = newPosition;
        }
        else {
            // If we can not walk pass the entity, check if it is a special type
            switch (entityAtNewPosition.getType()) {
                case "boulder":
                    // Check if we can move the boulder
                    BoulderEntity b = (BoulderEntity) entityAtNewPosition;
                    StaticEntity entityBehindBoulder = entityAtPosition(newPosition.translateBy(direction), staticEntities);
                    // If we can move the boulder, move it
                    if (entityBehindBoulder == null || entityBehindBoulder.canPass("player")) {
                        b.push(direction);
                        currentPosition = newPosition;
                    }
                    // Otherwise do nothing
                    break;
                case "portal":
                    // TODO: FIGURE OUT WHAT TO DO WITH A PORTAL
                    break;
                default:
                    // If it is not a special type, do nothing
                    break;
            }
        }
    }
}
