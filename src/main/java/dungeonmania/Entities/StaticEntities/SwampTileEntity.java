package dungeonmania.Entities.StaticEntities;

import dungeonmania.util.Position;

import java.util.HashMap;

public class SwampTileEntity extends StaticEntity {
    protected int movementFactor;
    // Key is id of entity: how many ticks they have been of the tile for
    HashMap<String, Integer> stuckTickCounter;

    public SwampTileEntity(String id, String type, Position position, int movementFactor) {
        super(id, type, position);
        this.movementFactor = movementFactor;
        stuckTickCounter = new HashMap<>();
    }

    @Override
    public boolean canPass(String type) {
        return true;
    }

    @Override
    public boolean isSpawner() {
        return false;
    }

    /**
     * Checks whether the entity above the swamp tile is stuck or not
     * @param id
     * @return
     */
    public boolean STUCK(String id) {
        // Will set counter to 1 if no counter exists, otherwise the counter will be incremented by 1 tick
        this.stuckTickCounter.merge(id, 1, Integer::sum);
        if (stuckTickCounter.get(id) == 2 * movementFactor - 1) {
            this.stuckTickCounter.remove(id);
            return false;
        }
        else {
            return true;
        }
    }
}
