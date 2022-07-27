package dungeonmania.Entities.Item.CollectableEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Item.Item;
import dungeonmania.util.Position;

import dungeonmania.util.EntityConstants;

public class SwordEntity extends Item{
    public SwordEntity(String id, String type, Position position) {
        super(id, type, position);
    }

    @Override
    public boolean isWeapon() {
        return true;
    }

    @Override
    public double getExtraAttack(double currentAttack) {
        return EntityConstants.getInstance("sword_attack").intValue();
    }

    @Override
    public void setDurability(int durability) {
        super.setDurability(EntityConstants.getInstance("sword_durability").intValue());
    }
}
