package dungeonmania.Entities.MovingEntities;

import dungeonmania.Entities.Item.Item;
import dungeonmania.Entities.StaticEntities.StaticEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.EntityConstants;
import dungeonmania.util.Position;

import java.util.ArrayList;

public class AssassinEntity extends MovingEntity {
    private int bribeAmount;
    private int bribeRadius;

    public AssassinEntity(String id, String type, Position position) {
        super(id, type, position);
        this.isInteractable = true;
        // TODO: GIVE MERCENARY A MOVEMENT FUNCTION
    }

    @Override
    public void move(Direction direction,
                     ArrayList<Item> items,
                     ArrayList<StaticEntity> staticEntities,
                     ArrayList<MovingEntity> movingEntities) {

    }
}
