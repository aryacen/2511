package dungeonmania.Entities.StaticEntities;

import dungeonmania.Entities.MovingEntities.Movement.Movement;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import javax.sound.sampled.Port;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PortalEntity extends StaticEntity {
    private final String color;
    public PortalEntity(String id, String type, Position position, String color) {
        super(id, type, position);
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    @Override
    public boolean canPass(String type) {
        return true;
    }
    // Teleports entities to a corresponding portal. The player must
    // end up in a square cardinally adjacent to the corresponding portal.
    // The square they teleport onto must also be within movement 
    // constraints - e.g. the player cannot teleport and end up on a wall. 
    // If all squares cardinally adjacent to the corresponding portal are 
    // walls, then the player should remain where they are.

    /**
     * @Pre-condition Corresponding portal with the same color
     * @Post-condition Returns the corresponding PortalEntity
     */
    public PortalEntity findCorrespondingPortal(ArrayList<StaticEntity> staticEntities) {
        // Extract all the portals into the variable p
        List<StaticEntity> tmp =  staticEntities
                .stream()
                .filter(e -> e.getType().equals("portal"))
                .collect(Collectors.toList());
        ArrayList<PortalEntity> portals = new ArrayList<>();
        tmp.forEach(e -> portals.add((PortalEntity) e));

        // Find corresponding portal
        // Same color but different id
        return portals
                .stream()
                .filter( e -> e.getColor().equals(this.color) && !e.getId().equals(this.id))
                .findAny()
                .get();
    }

    /**
     * @param type What type of entity is being teleported (e.g. player, mercenary)
     * @return null if teleport can not occur
     */
    public Position teleport(Direction direction, Position originalPos, ArrayList<StaticEntity> staticEntities, String type) {
        // Find the corresponding portal
        PortalEntity portal = findCorrespondingPortal(staticEntities);

        // Check if there are entities at the place that the player is trying to teleport to
        ArrayList<StaticEntity> adjacentStaticEntities = Movement.adjacentStaticEntity(portal.getPosition(),
                staticEntities);

        // The potential direction that can be teleported to
        ArrayList<Direction> directionsToTeleport = new ArrayList<>(
                Arrays.asList(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT));
        ArrayList<Direction> directionsToTeleportTmp = new ArrayList<>(directionsToTeleport);

        /*
         Check that player is not surrounded by all unpassable terrain
         For each direction, if there are static entities AND player can't pass all of them, then remove direction
         from teleport
        */
        directionsToTeleportTmp.stream().forEach(d -> {
            if (
                    adjacentStaticEntities.stream()
                            .filter(e -> e.getPosition().equals(portal.getPosition().translateBy(d)))
                            .anyMatch(e -> !e.canPass(type))) {
                directionsToTeleport.remove(d);
            }
        });
        // If the player can not teleport to any surrounding square, then return original position
        if (directionsToTeleport.isEmpty()) {
            return originalPos;
        }

        // Make it so that the first direction that portal tries to teleport player is in direction of movement
        Position newPos = originalPos;
        if (directionsToTeleport.contains(direction)) {
            directionsToTeleport.remove(direction);
            directionsToTeleport.add(0, direction);
        }
        /*
         If not, take any position and teleport them there
        This is to get the recursive case where the portal will still teleport the player even if one of the
        paths does not work
        */
        while (!directionsToTeleport.isEmpty()) {
            newPos = portal.getPosition().translateBy(directionsToTeleport.remove(0));
            // If there is a portal at that new position, then call teleport function recursively
            if (Movement.getStaticEntity("portal", Movement.staticEntityAtPosition(newPos, staticEntities)) != null) {
                PortalEntity p = (PortalEntity) Movement.getStaticEntity("portal", Movement.staticEntityAtPosition(newPos, staticEntities));
                newPos = p.teleport(direction, originalPos, staticEntities, type);
            }
            if (!newPos.equals(originalPos)) {
                break;
            }
        }
            // If all the paths fail, just return original position (i.e. player does not get teleported)
        return newPos;
    }
}
