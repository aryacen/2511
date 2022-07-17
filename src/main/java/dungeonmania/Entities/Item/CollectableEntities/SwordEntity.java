package dungeonmania.Entities.Item.CollectableEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Item.Item;
import dungeonmania.util.Position;

import dungeonmania.util.EntityConstants;

public class SwordEntity extends Item{
    private int swordDurability;
    public SwordEntity(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, false);
        this.swordDurability = EntityConstants.sword_durability;
    }

    public int getDurability() {
        return this.swordDurability;
    }

    public void decreaseDurability() {
        this.swordDurability -= 1;
    }
}
