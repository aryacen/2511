package dungeonmania.Entities.MovingEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.MovingEntities.Movement.Movement;
import dungeonmania.util.EntityConstants;
import dungeonmania.util.Position;

public class SpiderEntity extends MovingEntities {
    public SpiderEntity(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
        this.hp = EntityConstants.spider_health;
        this.attack = EntityConstants.spider_attack;
        // TODO: GIVE SPIDER A MOVEMENT FUNCTION
    }

    // Spiders spawn at random locations in the dungeon from the beginning of the game.

    // When the spider spawns, they immediately move the 1 square upwards (towards the top of the screen).

    // Spiders are able to traverse through walls, doors, switches, 
    // portals, exits (which have no effect), but not boulders, in which
    // case it will reverse direction. 

    // When it comes to spawning spiders, since the map is technically infinite, 
    // you can spawn them anywhere - however for better gameplay we suggest you
    // make an assumption and pick a four co-ordinate box to spawn spiders in.
    
}
