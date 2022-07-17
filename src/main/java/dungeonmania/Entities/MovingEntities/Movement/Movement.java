package dungeonmania.Entities.MovingEntities.Movement;

import dungeonmania.Entities.Item.Inventory;
import dungeonmania.Entities.MovingEntities.MovingEntities;
import dungeonmania.Entities.StaticEntities.StaticEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.ArrayList;

/**
 * This interface governs how an entity can move
 */
public abstract class Movement {
    /**
     * Moves the entity given a certain direction and returns the new position of
     * the entity
     */
    public abstract Position move(Position currentPosition,
            Direction direction,
            ArrayList<StaticEntity> staticEntities,
            ArrayList<MovingEntities> movingEntities,
            Inventory i
    );

    /**
     * Checks if there is an entity at a given position given a list of static
     * entities and a position
     * 
     * @return null if no item is at position pos, otherwise returns the static
     *         entity
     */
    protected static ArrayList<StaticEntity> staticEntityAtPosition(Position pos, ArrayList<StaticEntity> staticEntities) {
        ArrayList<StaticEntity> entityAtPos = new ArrayList<>();
        for (StaticEntity staticEntity : staticEntities) {
            if (staticEntity.getPosition().equals(pos)) {
                entityAtPos.add(staticEntity);
            }
        }
        return entityAtPos;
    }

    /**
     * Checks if there is an entity at a given position given a list of static
     * entities and a position
     *
     * @return null if no item is at position pos, otherwise returns the static
     *         entity
     */
    protected static ArrayList<MovingEntities> movingEntityAtPosition(Position pos, ArrayList<MovingEntities> movingEntities) {
        ArrayList<MovingEntities> entityAtPos = new ArrayList<>();
        for (MovingEntities movingEntity : movingEntities) {
            if (movingEntity.getPosition().equals(pos)) {
                entityAtPos.add(movingEntity);
            }
        }
        return entityAtPos;
    }

}
