package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.response.models.BattleResponse;

import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;

import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.*;

public class DungeonManiaController {
    List<String> dungeons = new ArrayList<String>();
    Map<String, String> dungeonAndConfigs = new HashMap<String, String>();

    public String getSkin() {
        return "default";
    }

    public String getLocalisation() {
        return "en_US";
    }

    /**
     * /dungeons
     */
    public static List<String> dungeons() {
        return FileLoader.listFileNamesInResourceDirectory("dungeons");
    }

    /**
     * /configs
     */
    public static List<String> configs() {
        return FileLoader.listFileNamesInResourceDirectory("configs");
    }

    /**
     * /game/new
     */
    public DungeonResponse newGame(String dungeonName, String configName) throws IllegalArgumentException {
        List<EntityResponse> entitiesResponse = new ArrayList<EntityResponse>();
        List<ItemResponse> itemResponse = new ArrayList<ItemResponse>();
        List<BattleResponse> battleResponse = new ArrayList<BattleResponse>();
        List<String> buildables = new ArrayList<String>();
        List<String> goals = new ArrayList<String>();
        // Checks if the dungeonName and configName inputted exists in resource
        // directory, and throws an IllegalArgumentException if it does not exist
        if (!DungeonManiaController.dungeons().contains(dungeonName)) {
            throw new IllegalArgumentException("Dungeon does not exist");
        }
        if (!DungeonManiaController.configs().contains(configName)) {
            throw new IllegalArgumentException("Config does not exist");
        }

        // Dungeon ID will be dungeonName followed with an integer (starting from 1),
        // which is denoted by the frequency of that dungeon type
        this.dungeons.add(dungeonName);
        int frequencyOfType = Collections.frequency(this.dungeons, dungeonName);
        String s = String.valueOf(frequencyOfType);
        String dungeonId = dungeonName + s;

        // Store the dungeon ID along with the configName, so we can use it for later
        this.dungeonAndConfigs.put(dungeonId, configName);

        try {
            // Load the dungeon file in a String, and convert it to a JSONObject
            String loadedFile = FileLoader.loadResourceFile("/dungeons/" + dungeonName + ".json");
            JSONObject fileJson = new JSONObject(loadedFile);

            // Get entities array and parse it to get a List of EntityResponse
            JSONArray entitiesJson = fileJson.getJSONArray("entities");
            entitiesResponse = parseDungeonEntities(entitiesJson);

            // Get goals object and parse it to get the goals String
            JSONObject goalsJson = fileJson.getJSONObject("goal-condition");
            goals.add(parseDungeonGoals(goalsJson));

            // If the file is not found, throw IOException
        } catch (IOException e) {
            e.printStackTrace();
        }

        DungeonResponse output = new DungeonResponse(dungeonId, dungeonName, entitiesResponse, itemResponse,
                battleResponse,
                buildables, goals.get(0));
        return output;
    }

    // Helper function to read the goals and construct a String representing the
    // goals
    public static String parseDungeonGoals(JSONObject goalCondition) {
        String tempGoal = goalCondition.getString("goal");

        // Checks if the goal is a supergoal ("AND" or "OR")
        if (tempGoal.equals("AND") || tempGoal.equals("OR")) {
            JSONArray subGoals = (JSONArray) goalCondition.getJSONArray("subgoals");
            JSONObject temp1 = subGoals.getJSONObject(0);
            String tempGoal1 = temp1.getString("goal");
            String output = ":" + tempGoal1 + " " + tempGoal + " ";
            JSONObject temp2 = subGoals.getJSONObject(1);
            String tempGoal2 = temp2.getString("goal");
            // Checks if the subgoal's goal is a supergoal
            if (tempGoal2.equals("AND") || tempGoal2.equals("OR")) {
                JSONArray subGoals1 = (JSONArray) temp2.get("subgoals");
                JSONObject temp3 = subGoals1.getJSONObject(0);
                String tempGoal3 = temp3.getString("goal");
                JSONObject temp4 = subGoals1.getJSONObject(1);
                String tempGoal4 = temp4.getString("goal");
                output = output + "(:" + tempGoal3 + " " + tempGoal2 + " " + ":" + tempGoal4
                        + ")";
                return output;
            } else {
                output = output + ":" + tempGoal2;
                return output;
            }
        } else {
            String output = ":" + tempGoal;
            return output;
        }
    }

    // Helper function to read the entities array, create EntityResponse for every
    // entity and store them in a List of EntityResponse
    public static List<EntityResponse> parseDungeonEntities(JSONArray entities) {
        List<EntityResponse> output = new ArrayList<EntityResponse>();
        List<String> types = new ArrayList<String>();
        // Loop through the entities array
        for (int i = 0; i < entities.length(); i++) {
            boolean interactable = false;
            JSONObject temp = entities.getJSONObject(i);

            // entityId will be entity type followed with an integer (starting from 1),
            // which is denoted by the frequency of the entity type in the dungeon
            String type = temp.getString("type");
            types.add(type);
            int frequencyOfType = Collections.frequency(types, type);
            String s = String.valueOf(frequencyOfType);
            String entityId = type + s;

            int x = temp.getInt("x");
            int y = temp.getInt("y");
            Position position = new Position(x, y);

            if (type.equals("mercenary") || type.equals("zombie_toast_spawner")) {
                interactable = true;
            }

            // Create an EntityResponse for the entity and store them in the output List
            EntityResponse tempResponse = new EntityResponse(entityId, type, position, interactable);
            output.add(tempResponse);
        }
        return output;
    }

    /**
     * /game/dungeonResponseModel
     */
    public DungeonResponse getDungeonResponseModel() {
        return null;
    }

    /**
     * /game/tick/item
     */
    public DungeonResponse tick(String itemUsedId) throws IllegalArgumentException, InvalidActionException {
        return null;
    }

    /**
     * /game/tick/movement
     */
    public DungeonResponse tick(Direction movementDirection) {
        return null;
    }

    /**
     * /game/build
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        return null;
    }

    /**
     * /game/interact
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        return null;
    }
}
