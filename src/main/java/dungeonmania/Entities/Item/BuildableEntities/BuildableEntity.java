package dungeonmania.Entities.Item.BuildableEntities;

import dungeonmania.Entities.Item.Item;

public abstract class BuildableEntity extends Item {
    public BuildableEntity(String id, String type) {
        super(id, type);
    }
    @Override
    public boolean canBeCrafted() {
        return true;
    }
}
