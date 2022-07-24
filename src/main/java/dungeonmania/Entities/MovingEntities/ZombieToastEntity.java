package dungeonmania.Entities.MovingEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Item.Item;
import dungeonmania.Entities.MovingEntities.Movement.Movement;
import dungeonmania.Entities.MovingEntities.Movement.ZombieToastMovement;
import dungeonmania.Entities.StaticEntities.StaticEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.EntityConstants;
import dungeonmania.util.Position;

import java.util.ArrayList;

public class ZombieToastEntity extends MovingEntity {
    public ZombieToastEntity(String id, String type, Position position) {
        super(id, type, position);
        this.hp = EntityConstants.zombie_health;
        this.attack = EntityConstants.zombie_attack;
        this.movement = new ZombieToastMovement();
    }

    @Override
    public void move(Direction direction,
                     ArrayList<Item> items,
                     ArrayList<StaticEntity> staticEntities,
                     ArrayList<MovingEntity> movingEntities) {
        this.position = this.movement.move(this.position, null, staticEntities, null, null);
    }

    // Zombies spawn at zombie spawners and move in random directions.
    // Zombies are limited by the same movement constraints as the Player,
    // except portals have no effect on them.
}
