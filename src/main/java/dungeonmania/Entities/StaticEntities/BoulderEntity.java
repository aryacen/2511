package dungeonmania.Entities.StaticEntities;

import dungeonmania.Entities.Entity;

public class BoulderEntity extends Entity {
    public BoulderEntity() {
        super("Boulder", "StaticEntity");
    }

    // Acts like a wall in most cases.

    // The only difference is that it can be pushed by the Player into cardinally adjacent squares.

    // The Player is only strong enough to push one boulder at a time.

    // When the player pushes a boulder, they move into the spot the boulder was previously in. 
    
    // Boulders can be pushed onto collectable entities.
}
