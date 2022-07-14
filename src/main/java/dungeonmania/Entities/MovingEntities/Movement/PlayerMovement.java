package dungeonmania.Entities.MovingEntities.Movement;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class PlayerMovement implements Movement {

    @Override
    public Position move(Position currentPosition, Direction direction) {
        // Move the player
        Position offset = direction.getOffset();
        Position newPosition = new Position(
                currentPosition.getX() + offset.getX(),
                currentPosition.getY() + offset.getY(),
                currentPosition.getLayer());
        return currentPosition;
    }

    /**
     * This should never be called for player
     */
    @Override
    public Position move(Position currentPosition) {
        return currentPosition;
    }
}
