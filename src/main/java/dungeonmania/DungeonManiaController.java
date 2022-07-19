package dungeonmania;

import dungeonmania.Entities.Item.Item;
import dungeonmania.Entities.MovingEntities.MovingEntities;
import dungeonmania.Entities.MovingEntities.PlayerEntity;
import dungeonmania.Entities.StaticEntities.PortalEntity;
import dungeonmania.Entities.StaticEntities.StaticEntity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.Entities.Battle;
import dungeonmania.Entities.Entity;
import dungeonmania.Entities.EntityFactory.entityCreator;

import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;
import dungeonmania.util.EntityConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.json.*;

public class DungeonManiaController {
    List<String> dungeons = new ArrayList<String>();
    List<DungeonResponse> currentModel = new ArrayList<DungeonResponse>();
    String dungeonId;
    String dungeonName;
    String goals;

    PlayerEntity playerEntity;
    ArrayList<MovingEntities> movingEntities;
    ArrayList<StaticEntity> staticEntities;
    ArrayList<Item> itemEntities;

    ArrayList<BattleResponse> battleResponse = new ArrayList<>();

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
        this.staticEntities = new ArrayList<>();
        this.itemEntities = new ArrayList<>();
        this.movingEntities = new ArrayList<>();
        List<EntityResponse> entitiesResponse = new ArrayList<EntityResponse>();
        List<ItemResponse> inventory = new ArrayList<ItemResponse>();
        List<BattleResponse> battles = new ArrayList<BattleResponse>();
        List<String> buildables = new ArrayList<String>();

        this.dungeonName = dungeonName;

        // Checks if the dungeonName and configName inputted exists in resource
        // directory, and throws an IllegalArgumentException if it does not exist
        if (!DungeonManiaController.dungeons().contains(dungeonName)) {
            throw new IllegalArgumentException("Dungeon does not exist");
        }
        if (!DungeonManiaController.configs().contains(configName)) {
            throw new IllegalArgumentException("Config does not exist");
        }

        // which is denoted by the frequency of that dungeon type
        this.dungeons.add(dungeonName);
        int frequencyOfType = Collections.frequency(this.dungeons, dungeonName);
        String s = String.valueOf(frequencyOfType);
        this.dungeonId = (dungeonName + s);

        try {
            // Load the config file in a string, convert it to JSONObject and store it in
            // EntityConstants
            String configFile = FileLoader.loadResourceFile("configs/" + configName + ".json");

            JSONObject configFileJson = new JSONObject(configFile);
            storeConfig(configFileJson);

            // Load the dungeon file in a String, and convert it to a JSONObject
            String loadedFile = FileLoader.loadResourceFile("/dungeons/" + dungeonName + ".json");
            JSONObject fileJson = new JSONObject(loadedFile);

            // Get entities array and parse it to get a List of EntityResponse
            JSONArray entitiesJson = fileJson.getJSONArray("entities");
            entitiesResponse = parseDungeonEntities(entitiesJson);

            // Get goals object and parse it to get the goals String
            JSONObject goalsJson = fileJson.getJSONObject("goal-condition");
            this.goals = (parseDungeonGoals(goalsJson));

            // If the file is not found, throw IOException
        } catch (IOException e) {
            e.printStackTrace();
        }

        DungeonResponse output = new DungeonResponse(this.dungeonId, this.dungeonName, entitiesResponse,
                inventory,
                battles,
                buildables, this.goals);

        this.currentModel.add(output);
        return output;
    }

    /**
     * /game/dungeonResponseModel
     */
    public DungeonResponse getDungeonResponseModel() {
        return this.currentModel.get(0);
    }

    /**
     * /game/tick/item
     */
    public DungeonResponse tick(String itemUsedId) throws IllegalArgumentException, InvalidActionException {
        this.playerEntity.use(itemUsedId);
        DungeonResponse output = getDungeonResponse();
        return output;
    }

    /**
     * /game/tick/movement
     */
    public DungeonResponse tick(Direction movementDirection) {
        this.playerEntity.move(
                movementDirection,
                this.itemEntities,
                this.staticEntities,
                this.movingEntities);
        this.haveBattle();
        // Create dungeon response
        return getDungeonResponse();
    }

    /**
     * /game/build
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        this.playerEntity.craftItem(buildable);
        return getDungeonResponse();
    }

    /**
     * /game/interact
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        return null;
    }

    /**
     * /game/save
     */
    public DungeonResponse saveGame(String name) throws IllegalArgumentException {
        return null;
    }

    /**
     * /game/load
     */
    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        return null;
    }

    /**
     * /games/all
     */
    public List<String> allGames() {
        return new ArrayList<>();
    }

}
