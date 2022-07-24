package dungeonmania.Entities.Item;

import dungeonmania.Entities.Entity;
import dungeonmania.util.Position;

public abstract class Item extends Entity {
    public Item(String id, String type, Position position) {
        super(id, type, position);
        // If item can be picked up, it is interactable
        this.isInteractable = true;
        this.entityType = "Item";
    }

    public boolean canBeCrafted() {
        return false;
    }
}
