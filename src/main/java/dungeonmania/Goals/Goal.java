package dungeonmania.Goals;

import java.util.ArrayList;

import dungeonmania.Entities.MovingEntities.PlayerEntity;
import dungeonmania.Entities.StaticEntities.StaticEntity;

public interface Goal {
    public boolean achievedGoal();
    public String getGoal();
    public boolean setAchievedGoal(ArrayList<StaticEntity> staticEntities, PlayerEntity playerEntity, int enemyGoal, int treasureGoal);
}
