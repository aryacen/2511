package dungeonmania.Goals;

import java.util.ArrayList;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.MovingEntities.PlayerEntity;
import dungeonmania.Entities.StaticEntities.FloorSwitchEntity;
import dungeonmania.Entities.StaticEntities.StaticEntity;
import dungeonmania.util.EntityConstants;

public class SimpleGoal implements Goal {

    private String goal;
    private boolean achievedGoal;

    public SimpleGoal(String goal, ArrayList<StaticEntity> staticEntities, PlayerEntity playerEntity, int enemyGoal, int treasureGoal) {
        this.goal = goal;
        this.achievedGoal = setAchievedGoal(staticEntities, playerEntity, enemyGoal, treasureGoal);
    }

    @Override
    public String getGoal() {
        if (achievedGoal) {
            return "";
        }
        return ":" + goal;
    }

    @Override
    public boolean achievedGoal() {
        return achievedGoal;
    }

    public ArrayList<Goal> getChildren() {
        return null;
    }

    public boolean setAchievedGoal(ArrayList<StaticEntity> staticEntities, PlayerEntity playerEntity, int enemyGoal, int treasureGoal) {
        if (playerEntity == null) { // player is dead
            return false;
        }
        boolean isGoalAchieved = false;
        // Types of goals
        // 1. Getting to an exit
        if (goal.equals("exit")) {
            for (StaticEntity entity : staticEntities) {
                if (entity.getType().equals("exit") && entity.getPosition().equals(playerEntity.getPosition())) {
                    isGoalAchieved = true;
                }
            }
        }
        // 2. Destroying a certain number of enemies (or more) AND all spawners
        else if (goal.equals("enemies")) {
            //System.out.println("Enemy goal = " + enemyGoal);
            if (playerEntity.getEnemiesDestroyed() >= enemyGoal) {
                isGoalAchieved = true;
                for (StaticEntity entity : staticEntities) {
                    if (entity.isSpawner()) { // Not all spawners are destroyed.
                        isGoalAchieved = false;
                    }
                }
            }
        }
        // 3. Having a boulder on all floor switches 
        else if (goal.equals("boulders")) {
            boolean allSwitchesTriggered = true;
            for (StaticEntity entity : staticEntities) {
                if (entity.getType().equals("switch")) {
                    FloorSwitchEntity floorSwitch = (FloorSwitchEntity) entity;
                    boolean switchTriggered = floorSwitch.getTriggered();
                    if (!switchTriggered) {
                        allSwitchesTriggered = false;
                    }
                }
            }
            isGoalAchieved = allSwitchesTriggered;
        }
        // 4. Collecting a certain number of treasure items (or more) 
        else if (goal.equals("treasure")) {
            // for (StaticEntity entity : staticEntities) {
            //     if (entity.getType().equals("treasure")) {
            //         //this.achievedGoal = false;
            //         isGoalAchieved = true;
            //     }
            // }
            if (playerEntity.getCountOfItems("treasure") >= treasureGoal) {
                isGoalAchieved = true;
            }
        }
        return isGoalAchieved;
    }
}
