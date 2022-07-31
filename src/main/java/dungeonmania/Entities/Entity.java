package dungeonmania.Entities;

import dungeonmania.Entities.EntityFactory.EntityCreator;
import dungeonmania.util.EntityConstants;
import dungeonmania.util.Position;
import org.json.JSONObject;

public abstract class Entity {
    // id is unique to the entity
    protected final String id;
    // Type refers to what type of entity it is (e.g. player, wall, etc)
    protected final String type;

    protected Position position;
    protected boolean isInteractable;
    protected String entityType;

    public Entity (String id, String type, Position position) {
        this.id = id;
        this.type = type;
        this.position = position;
        this.isInteractable = false;
        this.entityType = "";
    }

    public Entity(JSONObject entityInfo) {
        if (entityInfo.has("id")) {
            this.id = (String) entityInfo.get("id");
        }
        else {
            this.id = EntityConstants.newId();
        }
        this.type = (String) entityInfo.get("type");
        this.position = new Position(entityInfo.getInt("x"), entityInfo.getInt("y"));
        this.isInteractable = false;
        this.entityType = "";
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getEntityType() {
        return entityType;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isInteractable() {
        return isInteractable;
    }

    // Uncomment when persistence is done
    /**
     * Return the entity as a JSONObject so that they can be saved
     */
//    public JSONObject getJSON() {
//        JSONObject j = new JSONObject();
//        j.put("id", this.id);
//        j.put("type", this.type);
//        j.put("x", this.position.getX());
//        j.put("y", this.position.getY());
//        return j;
//    }

    public void setIsInteractable(boolean isInteractable) {
        this.isInteractable = isInteractable;
    }
}
