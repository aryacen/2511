package dungeonmania.Entities.Item.CollectableEntities;

import dungeonmania.Entities.Item.Item;
import dungeonmania.util.Position;

public class TreasureEntity extends Item{
    public TreasureEntity(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, false);
    }
}
