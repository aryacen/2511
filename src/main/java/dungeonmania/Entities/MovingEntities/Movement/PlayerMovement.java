package dungeonmania.Entities.MovingEntities.Movement;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class PlayerMovement implements Movement {

    @Override
    public Position move(Position currentPosition, Direction direction) {
        Position offset = direction.getOffset();
        int newX = currentPosition.getX() + offset.getX();
        int newY = currentPosition.getY() + offset.getY();
        int newLayer = currentPosition.getLayer() + offset.getLayer();
        // TODO: Check what static entity that the player is moving into
        /*
         Cases that need to be checked:
         Is the player walking into a boulder?
         Is the player walking into a wall?
         Is the player walking into a portal?
         Is the player walking onto a switch? (not sure about this one, maybe need an observer pattern for this one)
        */
        // TODO: FIGURE THIS OUT
        Position newPosition = null;
        String type = null;
        switch (type) {
            case "boulder":
                break;
            case "wall":
                break;
            case "portal":
                break;
            default:
                newPosition = new Position(newX, newY, newLayer);
                break;
        }


        // No weird case, move the player in the direction specified
        return newPosition;
    }

    /**
     * This should never be called for player
     */
    @Override
    public Position move(Position currentPosition) {
        return currentPosition;
    }
}
