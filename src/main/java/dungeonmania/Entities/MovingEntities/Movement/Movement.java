package dungeonmania.Entities.MovingEntities.Movement;

import dungeonmania.Entities.Entity;
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
     * Will cause the entity to interact with anything nearby if possible (e.g. doors and
     */
    void move(Position currentPosition, Direction direction, ArrayList<Entity> surroundingEntities);

    /**
     * Move entity without needing a direction input (dependent on implementation)
     */
    ArrayList<Entity> move(Position currentPosition, ArrayList<Entity> surroundingEntities);
}
