package dungeonmania.Entities.MovingEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Item.Item;
import dungeonmania.Entities.MovingEntities.Movement.Movement;
import dungeonmania.Entities.MovingEntities.Movement.SpiderMovement;
import dungeonmania.Entities.StaticEntities.StaticEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.EntityConstants;
import dungeonmania.util.Position;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SpiderEntity extends MovingEntity {
    public SpiderEntity(String id, String type, Position position) {
        super(id, type, position);
        this.hp = EntityConstants.getInstance("spider_health");
        this.attack = EntityConstants.getInstance("spider_attack");
        this.movement = new SpiderMovement();
    }

    public SpiderEntity(JSONObject j) {
        super(j);
        this.hp = EntityConstants.getInstance("spider_health");
        this.attack = EntityConstants.getInstance("spider_attack");
        this.movement = new SpiderMovement();

        // Uncomment when persistences works
//        if (j.has("movement")) {
//            this.movement = new SpiderMovement((JSONObject) j.get("movement"));
//        }
//        else {
//            this.movement = new SpiderMovement();
//        }
    }

    @Override
    public void move(Direction direction,
                     ArrayList<Item> items,
                     ArrayList<StaticEntity> staticEntities,
                     ArrayList<MovingEntity> movingEntities) {
        this.position = this.movement.move(this, null, staticEntities, null);
    }

    // Uncomment when persistence is done
//    @Override
//    public JSONObject getJSON() {
//        JSONObject j = super.getJSON();
//        j.put("movement", this.movement.getJSON());
//        return j;
//    }

    // Spiders spawn at random locations in the dungeon from the beginning of the game.

    // When the spider spawns, they immediately move the 1 square upwards (towards the top of the screen).

    // Spiders are able to traverse through walls, doors, switches, 
    // portals, exits (which have no effect), but not boulders, in which
    // case it will reverse direction. 

    // When it comes to spawning spiders, since the map is technically infinite, 
    // you can spawn them anywhere - however for better gameplay we suggest you
    // make an assumption and pick a four co-ordinate box to spawn spiders in.
    
}
