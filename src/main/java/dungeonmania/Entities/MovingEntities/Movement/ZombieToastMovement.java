package dungeonmania.Entities.MovingEntities.Movement;

import dungeonmania.Entities.Item.Inventory;
import dungeonmania.Entities.MovingEntities.MovingEntity;
import dungeonmania.Entities.StaticEntities.PortalEntity;
import dungeonmania.Entities.StaticEntities.StaticEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class ZombieToastMovement extends Movement {
    /**
     * For Zombie Toast, the only useful parameter is static entities and current position
     * Note that the restrictions are shared with player hence the duplicate code
     * A better way to code this would be extract method and place it into movement to make them usable across movements
     * TODO: FIX MOVEMENT FOR IT TO BE OK FOR ALL ENTITIES
     * @return new position
     */
    @Override
    public Position move(Position currentPosition,
                         Direction direction,
                         ArrayList<StaticEntity> staticEntities,
                         ArrayList<MovingEntity> movingEntities,
                         Inventory i) {

        ArrayList<StaticEntity> adjacentStaticEntities = Movement.adjacentStaticEntity(currentPosition,
                staticEntities);

        Direction d = chooseARandomDirectionToMove();
        // Sort list of static entities in the direction that the toast wants to teleport in
        adjacentStaticEntities = (ArrayList<StaticEntity>) adjacentStaticEntities.stream().filter(e -> e.getPosition().equals(currentPosition.translateBy(d))).collect(Collectors.toList());

        // Check if the toast is moving on top of a teleporter
        if (adjacentStaticEntities.stream().anyMatch(e ->e.getType().equals("portal"))) {
            PortalEntity portal = (PortalEntity) adjacentStaticEntities.stream()
                    .filter(e -> e.getType().equals("portal"))
                    .findFirst()
                    .get();
            return portal.teleport(direction, currentPosition, staticEntities, "zombie_toast");
        }
        // Check if the toast can move
        else if (adjacentStaticEntities.isEmpty() || adjacentStaticEntities.stream().allMatch(e -> e.canPass("zombie_toast"))) {
            return currentPosition.translateBy(d);
        }
        // If not, simply return original position
        else {
            return currentPosition;
        }
    }

    private Direction chooseARandomDirectionToMove() {
        Direction[] directionsToMove = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};
        Random r = new Random();
        return directionsToMove[r.nextInt(directionsToMove.length)];
    }

}
