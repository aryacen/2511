package dungeonmania.Entities.Item.CollectableEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Item.Item;

public class ArrowsEntity extends Entity implements Item {
    public ArrowsEntity() {
        super("Arrows", "CollectableEntity");
    }

    // Can be picked by the player.

    @Override
    public void collectItem(Entity entity) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void buildItem(Entity entity) {
        // TODO Auto-generated method stub
        
    }
    
}
