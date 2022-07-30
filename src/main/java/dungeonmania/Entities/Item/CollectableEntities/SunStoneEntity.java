package dungeonmania.Entities.Item.CollectableEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Item.Item;
import dungeonmania.util.Position;

public class SunStoneEntity extends Item {
    public SunStoneEntity(String id, String type, Position position) {
        super(id, type, position);
    }

    // Can be collected by the player.
}
