package dungeonmania.Entities.Item;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.MovingEntities.PlayerEntity;
import dungeonmania.util.Position;

public abstract class Item extends Entity {
    private int durability;

    public Item(String id, String type, Position position) {
        super(id, type, position);
        // If item can be picked up, it is interactable
        this.isInteractable = true;
        this.entityType = "Item";
        this.durability = Integer.MAX_VALUE;
    }

    public boolean canBeCrafted() {
        return false;
    }

    public boolean isWeapon() {
        return false;
    }

    public double getExtraAttack(double currentAttack) {
        return 0;
    }

    public double getExtraDefence() {
        return 0;
    }

    public int getDurability() {
        return this.durability;
    }

    public void decreaseDurability() {
        this.durability -= 1;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }
}
