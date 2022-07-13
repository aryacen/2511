package dungeonmania.Entities.Item.BuildableEntities;

import dungeonmania.Entities.Item.Item;

public abstract class BuildableEntity extends Item {

    public BuildableEntity(String EntityName, String EntityType) {
        super(EntityName, EntityType);
    }

    @Override
    public boolean canBeCrafted() {
        return true;
    }
}
