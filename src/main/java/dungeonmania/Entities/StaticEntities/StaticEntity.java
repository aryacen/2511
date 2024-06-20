package dungeonmania.Entities.StaticEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.util.Position;
import org.json.JSONObject;

public abstract class StaticEntity extends Entity {
    public StaticEntity(String id, String type, Position position) {
        super(id, type, position);
        this.entityType = "Static";
    }

    // Tells the movement interface whether the StaticEntity can be walked pass or not
    // by a moving entity
    // E.g. if the player checks if a boulder is passable, then this returns false
    // however if a spider
    public abstract boolean canPass(String type);

    public abstract boolean isSpawner();

}
