package dungeonmania.Entities.Item.CollectableEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Item.Item;

public class WoodEntity extends Entity implements Item {

    public WoodEntity() {
        super("Wood", "CollectableEntity");
    }

    // Can be collected by the player.

    @Override
    public void collectItem(Entity entity) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void buildItem(Entity entity) {
        // TODO Auto-generated method stub
        
    }
    
}
