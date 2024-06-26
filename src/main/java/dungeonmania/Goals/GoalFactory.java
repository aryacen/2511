package dungeonmania.Goals;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import dungeonmania.Entities.MovingEntities.PlayerEntity;
import dungeonmania.Entities.StaticEntities.StaticEntity;

public class GoalFactory {
    
    public static Goal getGoal(JSONObject obj, ArrayList<StaticEntity> staticEntities, PlayerEntity playerEntity, int enemyGoal, int treasureGoal) {
        String goal = obj.getString("goal");

        if (!goal.equals("AND") && !goal.equals("OR")) {
            return new SimpleGoal(goal, staticEntities, playerEntity, enemyGoal, treasureGoal);
        }

        JSONArray subgoals = (JSONArray) obj.getJSONArray("subgoals");
        JSONObject subgoal1 = subgoals.getJSONObject(0);
        JSONObject subgoal2 = subgoals.getJSONObject(1);

        if (goal.equals("AND")) {
            return new AndGoal(getGoal(subgoal1, staticEntities, playerEntity, enemyGoal, treasureGoal), getGoal(subgoal2, staticEntities, playerEntity, enemyGoal, treasureGoal), staticEntities, playerEntity, enemyGoal, treasureGoal);
        } else if (goal.equals("OR")) {
            return new OrGoal(getGoal(subgoal1, staticEntities, playerEntity, enemyGoal, treasureGoal), getGoal(subgoal2, staticEntities, playerEntity, enemyGoal, treasureGoal), staticEntities, playerEntity, enemyGoal, treasureGoal);
        }

        return null;

    }

}
