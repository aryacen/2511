package dungeonmania.Entities.MovingEntities.Movement;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Item.Inventory;
import dungeonmania.Entities.MovingEntities.MovingEntity;
import dungeonmania.Entities.StaticEntities.BoulderEntity;
import dungeonmania.Entities.StaticEntities.StaticEntity;
import dungeonmania.Entities.StaticEntities.SwampTileEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class SpiderMovement extends Movement {
    /**
     * Works similar to an iterator pattern
     */
    private final Direction startingDirection = Direction.UP;
    private boolean firstStepNotMoved;
    private int nextDirectionIndex;
    private boolean inverted;
    private final ArrayList<Direction> directionTrajectory = new ArrayList<>(Arrays.asList(
            Direction.RIGHT, Direction.RIGHT,
            Direction.DOWN, Direction.DOWN,
            Direction.LEFT, Direction.LEFT,
            Direction.UP, Direction.UP
    ));
    private final ArrayList<Direction> invertedDirectionTrajectory = new ArrayList<>(Arrays.asList(
            Direction.LEFT, Direction.LEFT,
            Direction.UP, Direction.UP,
            Direction.RIGHT, Direction.RIGHT,
            Direction.DOWN, Direction.DOWN
    ));

    /*
     Checks if the spider is moving in the opposite direction due to there being a boulder in the way
     */

    public SpiderMovement() {
        /*
          Spider should move up and then begin to move to the right, hence we need to include where the spider will
          start
         */
        firstStepNotMoved = true;
        inverted = false;
        nextDirectionIndex = 0;
    }

    public SpiderMovement(JSONObject j) {
        firstStepNotMoved = j.getBoolean("first_step");
        inverted = j.getBoolean("inverted");
        nextDirectionIndex = j.getInt("direction_index");
    }
    @Override
    public Position move(Entity entity, Direction direction, ArrayList<StaticEntity> staticEntities, ArrayList<MovingEntity> movingEntities) {
        Position currentPosition = entity.getPosition();
        Direction d = null;
        // Check if the spider has moved the first step or not
        SwampTileEntity s = (SwampTileEntity) getStaticEntity("swamp_tile", Movement.staticEntityAtPosition(currentPosition, staticEntities));
        // If the spider is currently on a swamp tile, check if it can move
        if (s != null) {
            if (s.STUCK(entity.getId())) {
                return currentPosition;
            }
        }
        if (firstStepNotMoved) {
            d = startingDirection;
        } else {
            d = this.getNext();
        }

        // Check if there is a boulder in the spider's way
        BoulderEntity b1 = (BoulderEntity) getStaticEntity("boulder",
                Movement.staticEntityAtPosition(currentPosition.translateBy(d), staticEntities));
        if (b1 == null) {
            // If there are no boulders, then simply move the spider
            firstStepNotMoved = false;
            return currentPosition.translateBy(d);
        }

        // Flip direction of the spider if it has not moved yet
        if (firstStepNotMoved) {
            // If this is the first move, retain clockwise motion
            d = Direction.DOWN;
            nextDirectionIndex = nextDirectionIndex + 4;
        }
        else {
            // Otherwise, make the motion anti-clockwise (or clockwise depending)
            d = this.getInvertedPath();
        }

        BoulderEntity b2 = (BoulderEntity) getStaticEntity("boulder",
                Movement.staticEntityAtPosition(currentPosition.translateBy(d), staticEntities));
        // If there is none, move that way
        if (b2 == null) {
            firstStepNotMoved = false;
            return currentPosition.translateBy(d);
        } else {
            // only change movement tick if this is not the first move, do not change the spiders motion
            // if the spider can't move, decrement the movement to retain circular path if boulder is even lifted
            return currentPosition;
        }
    }

    /**
     * Get the next step and increment the movement counter accordingly
     */
    private Direction getNext() {
        if (inverted) {
            nextDirectionIndex--;
            nextDirectionIndex = (nextDirectionIndex + 8) % 8;
            return invertedDirectionTrajectory.get(nextDirectionIndex);
        }
        else {
            nextDirectionIndex++;
            nextDirectionIndex = nextDirectionIndex % 8;
            return directionTrajectory.get(nextDirectionIndex);
        }
    }
    private void invert() {
        this.inverted = !this.inverted;
    }
    private Direction getInvertedPath() {
        this.invert();
        return this.getNext();
    }
    // Uncomment when persistence is done
//    @Override
//    public JSONObject getJSON() {
//        JSONObject j = super.getJSON();
//        j.put("first_step", this.firstStepNotMoved);
//        j.put("inverted", this.inverted);
//        j.put("direction_index", this.nextDirectionIndex);
//        return j;
//    }
}