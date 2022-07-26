package dungeonmania.Entities.MovingEntities.Movement;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Item.Inventory;
import dungeonmania.Entities.MovingEntities.MovingEntity;
import dungeonmania.Entities.StaticEntities.BoulderEntity;
import dungeonmania.Entities.StaticEntities.StaticEntity;
import dungeonmania.Entities.StaticEntities.SwampTileEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

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

}
//    @Override
//    public Position move(Position currentPosition, Direction direction, ArrayList<StaticEntity> staticEntities, ArrayList<MovingEntity> movingEntities, Inventory i) {
//        Direction d = null;
//        // Check if the spider has moved the first step or not
//        if (firstStepNotMoved) {
//            d = startingDirection;
//        } else {
//            d = this.getNext();
//        }
//
//        // Check if there is a boulder in the spider's way
//        BoulderEntity b1 = (BoulderEntity) getStaticEntity("boulder", Movement.staticEntityAtPosition(currentPosition.translateBy(d), staticEntities));
//        if (b1 == null) {
//            // If there are no boulders, then simply move the spider
//            firstStepNotMoved = false;
//            return currentPosition.translateBy(d);
//        } else {
//            // If there is a boulder, flip the direction of the spider
//            if (firstStepNotMoved) {
//                this.inverted = true;
//                d = Direction.DOWN;
//            }
//            else {
//                d = this.invertPath();
//            }
//            // Check if there is a boulder in the opposite direction
//            BoulderEntity b2 = (BoulderEntity) getStaticEntity("boulder", Movement.staticEntityAtPosition(currentPosition.translateBy(d), staticEntities));
//            // If there is none, move that way
//            if (b2 == null) {
//                firstStepNotMoved = false;
//                return currentPosition.translateBy(d);
//            } else {
//                // only change movement tick if this is not the first move, do not change the spiders motion
//                // if the spider can't move, decrement the movement to retain circular path if boulder is even lifted
//                if (!firstStepNotMoved) {
//                    this.removeMovementTick();
//                }
//                return currentPosition;
//            }
//        }
//    }
//
//    /**
//     * Get the next movement index
//     */
//    private Direction getNext() {
//        /*
//         Need to reset the direction if we reach the end or reaches the start
//         Will check whether spider should follow normal or inverted path
//        */
//        if (inverted) {
//            nextDirectionIndex--;
//            nextDirectionIndex = (nextDirectionIndex + 8) % 8;
//            return invertedDirectionTrajectory.get(nextDirectionIndex);
//        }
//        else {
//            nextDirectionIndex++;
//            nextDirectionIndex = nextDirectionIndex % 8;
//            return directionTrajectory.get(nextDirectionIndex);
//        }
//    }
//    /**
//     * Make the spider go the opposite way and adjust the direction increment accordingly
//     */
//    private Direction invertPath() {
//        // Is inverted
//        if (inverted) {
//            nextDirectionIndex++;
//            nextDirectionIndex = nextDirectionIndex % 8;
//            return directionTrajectory.get(nextDirectionIndex);
//        }
//        // Not inverted
//        else {
//            nextDirectionIndex--;
//            nextDirectionIndex = (nextDirectionIndex + 8) % 8;
//            inverted = !inverted;
//            return invertedDirectionTrajectory.get(nextDirectionIndex);
//        }
//    }
//    private void removeMovementTick() {
//        if (inverted) {
//            nextDirectionIndex--;
//            nextDirectionIndex = (nextDirectionIndex + 8) % 8;
//        }
//        else {
//            nextDirectionIndex++;
//            nextDirectionIndex = nextDirectionIndex % 8;
//        }
//    }
//}