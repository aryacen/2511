package dungeonmania.Entities.StaticEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.MovingEntities.PlayerEntity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Position;

public class ZombieToastSpawnerEntity extends StaticEntity {
    public ZombieToastSpawnerEntity(String id, String type, Position position) {
        super(id, type, position);
        this.isInteractable = true;
    }

    @Override
    public boolean canPass(String type) {
        return true;
    }

    // Spawns zombie toasts in an open square cardinally adjacent to the spawner.
    
    // The Player can destroy a zombie spawner if they have a weapon and are 
    // cardinally adjacent to the spawner.
    
    @Override
    public boolean isSpawner() {
        return true;
    }

    public void interact(Entity spawner, PlayerEntity player, ArrayList<StaticEntity> staticEntities) throws InvalidActionException {
        Position playerPos = player.getPosition();
        List<Position> validPositions = playerPos.getCardinallyAdjacentPositions();
        if (validPositions.contains(spawner.getPosition()) && (player.getCountOfItems("sword") >= 1 || player.getCountOfItems("bow") >= 1)) {
            staticEntities.remove(spawner);
            return;
        } else {
            throw new InvalidActionException("Unable to destroy zombie toast spawner");
        }
    }
}
