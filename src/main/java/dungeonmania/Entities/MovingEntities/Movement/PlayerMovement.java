package dungeonmania.Entities.MovingEntities.Movement;

import dungeonmania.Entities.Item.Inventory;
import dungeonmania.Entities.MovingEntities.MovingEntity;
import dungeonmania.Entities.StaticEntities.BoulderEntity;
import dungeonmania.Entities.StaticEntities.DoorEntity;
import dungeonmania.Entities.StaticEntities.PortalEntity;
import dungeonmania.Entities.StaticEntities.StaticEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.ArrayList;

public class PlayerMovement extends Movement {
    @Override
    public Position move(Position currentPosition,
                         Direction direction,
                         ArrayList<StaticEntity> staticEntities,
                         ArrayList<MovingEntity> movingEntities,
                         Inventory i) {
        Position offset = direction.getOffset();
        Position newPosition = currentPosition.translateBy(offset);
        // Note the only case so far where there are two static entities in with a boulder and a switch
        ArrayList<StaticEntity> entityAtNewPosition = Movement.staticEntityAtPosition(newPosition, staticEntities);
        // If we can walk through the static entity or there are no static entity, move
        if (
                entityAtNewPosition.isEmpty() ||
                (entityAtNewPosition.stream().allMatch(e -> e.canPass("player"))) &&
                entityAtNewPosition.stream().noneMatch(e -> e.getType().equals("portal"))) {
            currentPosition = newPosition;
            return currentPosition;
        }
        // If there is a boulder at the location, check if there is an entity behind it
        else if (entityAtNewPosition.stream().anyMatch(e -> e.getType().equals("boulder"))) {
            // There can only be one boulder at specific location
//            BoulderEntity b = (BoulderEntity) entityAtNewPosition.stream().filter(e -> e.getType().equals("boulder"));
            BoulderEntity b = (BoulderEntity) entityAtNewPosition.stream()
                    .filter(e -> e.getType().equals("boulder"))
                    .findFirst()
                    .get();
                ArrayList<StaticEntity> entityBehindBoulder = Movement.staticEntityAtPosition(
                    newPosition.translateBy(direction),
                    staticEntities);
            ArrayList<MovingEntity> movingEntitiesBehindBoulder = Movement.movingEntityAtPosition(
                    newPosition.translateBy(direction),
                    movingEntities
            );
            /*
             For us to be able to move the boulder, we need
             1) no moving entities behind
             2) no entities that can't be passed behind the boulder
            */
            if (
                    (entityBehindBoulder.isEmpty()|| entityBehindBoulder.stream().allMatch(e->e.canPass("player")))
                    && movingEntitiesBehindBoulder.isEmpty()) {
                b.push(direction);
                currentPosition = newPosition;
            }
        }
        else if (entityAtNewPosition.stream().anyMatch(e -> e.getType().equals("door"))) {
            // There can only be one door at a specific location
//            DoorEntity d = (DoorEntity) entityAtNewPosition.stream().filter(e -> e.getType().equals("door"));
            DoorEntity d = (DoorEntity) entityAtNewPosition.stream()
                    .filter(e -> e.getType().equals("door"))
                    .findFirst()
                    .get();

            int keyId = d.getKey();
            // If the player has a correct key, unlock the door
            if (i.validKey(keyId)) {
                d.unlockDoor();
                currentPosition = newPosition;
            }
        }
        else if (entityAtNewPosition.stream().anyMatch(e -> e.getType().equals("portal"))) {
            // Extract the first portal
            PortalEntity portal = (PortalEntity) entityAtNewPosition.stream()
                    .filter(e -> e.getType().equals("portal"))
                    .findFirst()
                    .get();
            currentPosition = portal.teleport(direction, currentPosition, staticEntities, "player");
        }
        // Default to just not doing anything
        return currentPosition;
    }
}
