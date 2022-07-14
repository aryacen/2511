package dungeonmania.Entities.MovingEntities.Movement;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class PlayerMovement implements Movement {

    @Override
    public Position move(Position currentPosition, Direction direction) {
        return null;
    }

    /**
     * This should never be called for player
     */
    @Override
    public Position move(Position currentPosition) {
        return currentPosition;
    }
}
