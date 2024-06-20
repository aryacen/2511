package dungeonmania.Entities.MovingEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Item.Item;
import dungeonmania.Entities.MovingEntities.Movement.Movement;
import dungeonmania.Entities.StaticEntities.StaticEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.json.JSONObject;

import java.util.ArrayList;

public abstract class MovingEntity extends Entity {
    protected Movement movement;
    protected double hp;
    protected double attack;

    public MovingEntity(String id, String type, Position position) {
        super(id, type, position);
        this.entityType = "Moving";
    }

    public MovingEntity(JSONObject j) {
        super(j);
        this.entityType = "Moving";
    }
    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public double getAttack() {
        return this.attack;
    }

    public abstract void move(Direction direction,
                     ArrayList<Item> items,
                     ArrayList<StaticEntity> staticEntities,
                     ArrayList<MovingEntity> movingEntities);
    
    public Boolean isDead() {
        return (this.getHp() <= 0.0);
    }

    // Uncomment when persistence is done
//    @Override
//    public JSONObject getJSON() {
//        JSONObject j = super.getJSON();
//        j.put("hp", this.hp);
//        j.put("attack", this.attack);
//        return j;
//    }
}
