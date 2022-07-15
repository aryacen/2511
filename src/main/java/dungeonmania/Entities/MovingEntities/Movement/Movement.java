package dungeonmania.Entities.MovingEntities.Movement;

import dungeonmania.Entities.StaticEntities.StaticEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.ArrayList;

/**
 * This interface governs how an entity can move
 */
public abstract class Movement {
    /**
     * Moves the entity given a certain direction and returns the new position of the entity
     */
    public abstract void move(Position currentPosition,
                              Direction direction,
                              ArrayList<StaticEntity> staticEntities);

    /**
     * Checks if there is an entity at a given position given a list of static entities and a position
     * @return null if no item is at position pos, otherwise returns the static entity
     */
    protected static StaticEntity entityAtPosition(Position pos, ArrayList<StaticEntity> staticEntities) {
        for (StaticEntity staticEntity: staticEntities) {
            if (staticEntity.getPosition().equals(pos)) {
                return staticEntity;
            }
        }
        return null;
    }


}
