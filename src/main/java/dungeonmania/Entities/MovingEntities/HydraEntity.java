package dungeonmania.Entities.MovingEntities;

import dungeonmania.Entities.Item.Item;
import dungeonmania.Entities.MovingEntities.Movement.ZombieToastMovement;
import dungeonmania.Entities.StaticEntities.StaticEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.EntityConstants;
import dungeonmania.util.Position;

import java.util.ArrayList;

public class HydraEntity extends MovingEntity {
    public HydraEntity(String id, String type, Position position) {
        super(id, type, position);
        // Hydra movement is same as zombie toast
        this.movement = new ZombieToastMovement();
        this.hp = EntityConstants.getInstance("hydra_health");
        this.attack = EntityConstants.getInstance("hydra_attack");
    }

    @Override
    public void move(Direction direction,
                     ArrayList<Item> items,
                     ArrayList<StaticEntity> staticEntities,
                     ArrayList<MovingEntity> movingEntities) {
        this.movement.move(this, null, staticEntities, null);
    }
}
