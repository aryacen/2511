package dungeonmania.Entities.MovingEntities.Movement;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Item.Item;
import dungeonmania.Entities.StaticEntities.StaticEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.ArrayList;

public class PlayerMovement implements Movement {

    @Override
    public void move(Position currentPosition, Direction direction, ArrayList<Entity> surroundingEntities) {
        Position offset = direction.getOffset();
        Position newPosition = currentPosition.translateBy(offset);
        ArrayList<StaticEntity> staticEntities = (ArrayList<StaticEntity>) surroundingEntities.stream().filter(e -> e.getEntityType().equals("Static"));
        // TODO: FINISH THIS FUNCTIONS
        for (StaticEntity e: staticEntities) {
            String type = e.getType();
            switch (type) {
                case "portal":
                    break;
                case "boulder":
                    break;
                case "door":
                    break;
                default:
                    if (e.canPass("player")) {
                        currentPosition = newPosition;
                    }
                    break;
            }
        }
    }

    @Override
    public ArrayList<Entity> move(Position currentPosition, ArrayList<Entity> surroundingEntities) {
        // This should never be called for a player
        return null;
    }
}
