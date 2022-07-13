package dungeonmania.Entities.Item.CollectableEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Item.Item;
import dungeonmania.util.Position;

public class SwordEntity extends Item {
    public SwordEntity(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, false);
    }
}
