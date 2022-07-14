package dungeonmania.Entities.MovingEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.MovingEntities.Movement.Movement;
import dungeonmania.util.Position;

public abstract class MovingEntities extends Entity {
    protected Movement movement;
    protected int hp;

    public MovingEntities(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
    }
}
