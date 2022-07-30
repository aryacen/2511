package dungeonmania.Entities.Item.BuildableEntities;

import dungeonmania.Entities.Item.Item;
import dungeonmania.Entities.MovingEntities.PlayerEntity;
import dungeonmania.util.EntityConstants;
import dungeonmania.util.Position;

import java.util.HashMap;

public abstract class BuildableEntity extends Item {
    // Each buildable entity has a recipe that needs to be met

    public BuildableEntity(String id, String type, Position position) {
        super(id, type, EntityConstants.notOnMap);
    }

    @Override
    public boolean canBeCrafted(HashMap<String, Integer> itemsToRemove) {
        return false;
    }

    @Override
    public void setDurability(int durability) {
        super.setDurability(durability);
    }

    @Override
    public double getExtraAttack(double currentAttack) {
        return currentAttack;
    }

    @Override
    public double getExtraDefence() {
        return 0;
    }

    /*
     * E.g. Shield needs 2 wood + (1 treasure or 1 key)
     * Essential would be 2 wood
     * Option would be 1 treasure and 1 key
     */
    /**
     * Returns essential ingredient for a buildable entity
     */
    public abstract HashMap<String, Integer> getEssential();

    /**
     * Returns option ingredient
     */
    public abstract HashMap<String, Integer> getOptions();
}
