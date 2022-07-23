package dungeonmania.Entities.Item;

import dungeonmania.Entities.Entity;
import dungeonmania.util.Position;

public abstract class Item extends Entity {
    public Item(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
        this.entityType = "Item";
    }

    public boolean canBeCrafted() {
        return false;
    }
}
