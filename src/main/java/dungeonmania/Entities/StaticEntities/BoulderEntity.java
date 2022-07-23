package dungeonmania.Entities.StaticEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BoulderEntity extends StaticEntity {
    public BoulderEntity(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
    }

    @Override
    public boolean canPass(String type) {
        return false;
    }

    /**
     * Moves the boulder in given direction
     * @Pre-condition is only called when there is nothing in the boulders way
     */
    public void push(Direction direction) {
        this.position = this.position.translateBy(direction);
    }
    // Acts like a wall in most cases.

    // The only difference is that it can be pushed by the Player into cardinally adjacent squares.

    // The Player is only strong enough to push one boulder at a time.

    // When the player pushes a boulder, they move into the spot the boulder was previously in. 
    
    // Boulders can be pushed onto collectable entities.
}
