package dungeonmania.Entities;

public abstract class Entity {
    private String EntityName;
    private String EntityType;
    public Entity (String EntityName, String EntityType) {
        this.EntityName = EntityName;
        this.EntityType = EntityType;
    }

    public String getEntityName() {
        return this.EntityName;
    }

    public void setEntityName(String EntityName) {
        this.EntityName = EntityName;
    }

    public String getEntityType() {
        return this.EntityType;
    }

    public void setEntityType(String EntityType) {
        this.EntityType = EntityType;
    }

}
