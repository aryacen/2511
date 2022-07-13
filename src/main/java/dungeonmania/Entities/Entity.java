package dungeonmania.Entities;

public abstract class Entity {
    // Id is unique to the entity
    private final String id;
    // Type refers to what type of entity it is (e.g. player, wall, etc)
    private final String type;

    public Entity (String id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
