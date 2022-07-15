package dungeonmania.Entities.MovingEntities.Movement;

import dungeonmania.Entities.StaticEntities.StaticEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.ArrayList;

/**
 * This interface governs how an entity can move
 */
public interface Movement {
    /**
     * Moves the entity given a certain direction and returns the new position of the entity
     */
    Position move(Position currentPosition, Direction direction, ArrayList<StaticEntity> staticEntities);

    /**
     * Move entity without needing a direction input (dependent on implementation)
     */
    Position move(Position currentPosition, ArrayList<StaticEntity> staticEntities);
}
