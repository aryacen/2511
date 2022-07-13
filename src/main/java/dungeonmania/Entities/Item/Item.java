package dungeonmania.Entities.Item;

import dungeonmania.Entities.Entity;

public abstract class Item extends Entity {
    public Item(String id, String type) {
        super(type, type);
    }

    public boolean canBeCrafted() {
        return false;
    }
}
