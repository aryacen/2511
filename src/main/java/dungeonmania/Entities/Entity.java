package dungeonmania.Entities;

import dungeonmania.util.Position;

public abstract class Entity {
    // id is unique to the entity
    private final String id;
    // Type refers to what type of entity it is (e.g. player, wall, etc)
    private final String type;

    protected Position position;
    private boolean isInteractable;

    public Entity (String id, String type, Position position, boolean isInteractable) {
        this.id = id;
        this.type = type;
        this.position = position;
        this.isInteractable = isInteractable;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
