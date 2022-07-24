package dungeonmania.Entities.MovingEntities.Movement;

import dungeonmania.Entities.Item.Inventory;
import dungeonmania.Entities.MovingEntities.MovingEntity;
import dungeonmania.Entities.StaticEntities.StaticEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.reflections.vfs.Vfs;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
            ArrayList<MovingEntity> movingEntities,
            Inventory i
    );

    /**
     * Checks if there is an entity at a given position given a list of static
     * entities and a position
     * 
     * @return null if no item is at position pos, otherwise returns the static
     *         entity
     */
    public static ArrayList<StaticEntity> staticEntityAtPosition(Position pos, ArrayList<StaticEntity> staticEntities) {
        // TODO: MAKE SURE THIS RETURNS AN EMPTY LIST IF NOTHING THERE ARE NO ADJACENT ENTITIES
        return (ArrayList<StaticEntity>) staticEntities.stream().filter(e -> e.getPosition().equals(pos)).collect(Collectors.toList());
    }

    /**
     * Will get any cardinally adjacent static entities around position pos
     * @return
     */
    public static ArrayList<StaticEntity> adjacentStaticEntity(Position pos, ArrayList<StaticEntity> staticEntities) {
        ArrayList<StaticEntity> adjacent =  new ArrayList<>();
        adjacent.addAll(staticEntityAtPosition(pos.translateBy(Direction.DOWN), staticEntities));
        adjacent.addAll(staticEntityAtPosition(pos.translateBy(Direction.UP), staticEntities));
        adjacent.addAll(staticEntityAtPosition(pos.translateBy(Direction.RIGHT), staticEntities));
        adjacent.addAll(staticEntityAtPosition(pos.translateBy(Direction.LEFT), staticEntities));
        return adjacent;
    }

    /**
     * Checks if there is an entity at a given position given a list of static
     * entities and a position
     *
     * @return null if no item is at position pos, otherwise returns the static
     *         entity
     */
    public static ArrayList<MovingEntity> movingEntityAtPosition(Position pos, ArrayList<MovingEntity> movingEntities) {
        return (ArrayList<MovingEntity>) movingEntities.stream().filter(e -> e.getPosition().equals(pos)).collect(Collectors.toList());
    }

    /**
     * Finds an entity of a specified type and returns it
     * This is run after the list has been filtered, so there isn't a need to be specific with the filtering
     * @return null if no object is found
     */
    public static StaticEntity getStaticEntity(String type, ArrayList<StaticEntity> staticEntities) {
        if (staticEntities.stream().noneMatch(e -> e.getType().equals(type))) {
            return null;
        }
        else {
            return staticEntities.stream().filter(e -> e.getType().equals(type)).findAny().get();
        }
    }

}
