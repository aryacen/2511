package dungeonmania.Goals;

import java.util.ArrayList;

import dungeonmania.Entities.MovingEntities.PlayerEntity;
import dungeonmania.Entities.StaticEntities.FloorSwitchEntity;
import dungeonmania.Entities.StaticEntities.StaticEntity;

public class SimpleGoal implements Goal {

    private String goal;
    private boolean achievedGoal;

    public SimpleGoal(String goal, ArrayList<StaticEntity> staticEntities, PlayerEntity playerEntity) {
        this.goal = goal;
        //this.achievedGoal = false;
        this.achievedGoal = setAchievedGoal(staticEntities, playerEntity);
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

    // TODO: Check for the four conditions here
    public boolean setAchievedGoal(ArrayList<StaticEntity> staticEntities, PlayerEntity playerEntity) {
        if (playerEntity == null) { // player is dead
            return false;
        }
        boolean isGoalAchieved = false;
        // Types of goals
        // 1. Getting to an exit
        if (goal.equals("exit")) {
            for (StaticEntity entity : staticEntities) {
                if (entity.getType().equals("exit") && entity.getPosition().equals(playerEntity.getPosition())) {
                    //System.out.println("At exit!");
                    isGoalAchieved = true;
                }
            }
        }
        // 2. Destroying a certain number of enemies (or more) AND all spawners
        else if (goal.equals("enemies")) {
            if (playerEntity.getEnemiesDestroyed() >= 1) {
                isGoalAchieved = true;
                //this.achievedGoal = true;
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
            if (playerEntity.getCountOfItems("treasure") >= 1) {
                isGoalAchieved = true;
            }
        }
        return isGoalAchieved;
    }
}
