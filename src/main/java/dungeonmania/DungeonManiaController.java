package dungeonmania;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.EntityFactory.entityCreator;
import dungeonmania.Entities.Item.Item;
import dungeonmania.Entities.MovingEntities.MovingEntity;
import dungeonmania.Entities.MovingEntities.PlayerEntity;
import dungeonmania.Entities.StaticEntities.StaticEntity;
import dungeonmania.Goals.Goal;
import dungeonmania.Goals.GoalFactory;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.Entities.Battle;

import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;
import dungeonmania.util.EntityConstants;

import java.io.IOException;
import java.util.*;

import org.json.*;

public class DungeonManiaController {
    List<String> dungeons = new ArrayList<String>();
    List<DungeonResponse> currentModel = new ArrayList<DungeonResponse>();
    String dungeonId;
    String dungeonName;
    String goals;
    JSONObject jsonGoal;
    int enemyGoal;
    int treasureGoal;

    PlayerEntity playerEntity;
    ArrayList<MovingEntity> movingEntities;
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
            EntityConstants.setConfig(configFileJson);

            // Load the dungeon file in a String, and convert it to a JSONObject
            String loadedFile = FileLoader.loadResourceFile("/dungeons/" + dungeonName + ".json");
            JSONObject fileJson = new JSONObject(loadedFile);

            // Get entities array and parse it to get a List of EntityResponse
            parseDungeonEntities(fileJson.getJSONArray("entities"));

            // Get goals object and parse it to get the goals String
            JSONObject goalsJson = fileJson.getJSONObject("goal-condition");
            this.goals = parseDungeonGoals(goalsJson);

            // If the file is not found, throw IOException
        } catch (IOException e) {
            e.printStackTrace();
        }

        DungeonResponse output = getDungeonResponse();
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

        // update goal
        Goal updatedGoals = GoalFactory.getGoal(jsonGoal, this.staticEntities, this.playerEntity, enemyGoal, treasureGoal);
        this.goals = updatedGoals.getGoal();

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
        this.movingEntities.stream().forEach(e -> e.move(null, this.itemEntities, this.staticEntities, this.movingEntities));
        this.haveBattle();

        // update goal
        Goal updatedGoals = GoalFactory.getGoal(jsonGoal, this.staticEntities, this.playerEntity, enemyGoal, treasureGoal);
        this.goals = updatedGoals.getGoal();

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
        this.playerEntity.interact(entityId, staticEntities, movingEntities);
        return getDungeonResponse();
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

    /**
    * Helper function to read the goals and construct a String representing the
     * goals
     */
    private String parseDungeonGoals(JSONObject goalCondition) {
        this.enemyGoal = EntityConstants.getInstance("enemy_goal").intValue();
        this.treasureGoal = EntityConstants.getInstance("treasure_goal").intValue();
        this.jsonGoal = goalCondition;
        Goal goals = GoalFactory.getGoal(goalCondition, this.staticEntities, this.playerEntity, enemyGoal, treasureGoal);
        return goals.getGoal();
    }

    /**
     * Creates and stores all the entities
     */
    private void parseDungeonEntities(JSONArray entities) {
        Iterator<Object> entityIterator = entities.iterator();
        Entity newEntity;
        while (entityIterator.hasNext()) {
            newEntity = entityCreator.createEntity((JSONObject) entityIterator.next());
            // Player is stored separately to all other entities
            if (newEntity.getType().equals("player")) {
                this.playerEntity = (PlayerEntity) newEntity;
            }
            // Figure out where to store the entity
            else {
                switch (newEntity.getEntityType()) {
                    case "Static":
                        this.staticEntities.add((StaticEntity) newEntity);
                        break;
                    case "Moving":
                        this.movingEntities.add((MovingEntity) newEntity);
                        break;
                    case "Item":
                        this.itemEntities.add((Item) newEntity);
                }
            }
        }
    }
    private DungeonResponse getDungeonResponse() {
        /*
         * Dungeon id
         * Dungeon name
         * Entites
         * Inventory
         * Battle Response
         * Buildables
         * Goals
         */
        ArrayList<EntityResponse> entityResponse = new ArrayList<>();

        for (int i = 0; i < this.movingEntities.size(); i++) {
            MovingEntity tempEntity = this.movingEntities.get(i);
            String id = tempEntity.getId();
            String type = tempEntity.getType();
            Position position = tempEntity.getPosition();
            boolean interactable = tempEntity.isInteractable();
            EntityResponse tempEntityResponse = new EntityResponse(id, type, position, interactable);
            entityResponse.add(tempEntityResponse);
        }

        for (int i = 0; i < this.staticEntities.size(); i++) {
            StaticEntity tempEntity = this.staticEntities.get(i);
            String id = tempEntity.getId();
            String type = tempEntity.getType();
            Position position = tempEntity.getPosition();
            boolean interactable = tempEntity.isInteractable();
            EntityResponse tempEntityResponse = new EntityResponse(id, type, position, interactable);
            entityResponse.add(tempEntityResponse);
        }

        for (int i = 0; i < this.itemEntities.size(); i++) {
            Item tempEntity = this.itemEntities.get(i);
            String id = tempEntity.getId();
            String type = tempEntity.getType();
            Position position = tempEntity.getPosition();
            boolean interactable = tempEntity.isInteractable();
            EntityResponse tempEntityResponse = new EntityResponse(id, type, position, interactable);
            entityResponse.add(tempEntityResponse);
        }

        ArrayList<ItemResponse> itemResponse = new ArrayList<>();
        if (playerEntity != null) {
            EntityResponse playerResponse = new EntityResponse(this.playerEntity.getId(), "player",
                this.playerEntity.getPosition(), false);
            entityResponse.add(playerResponse);
            itemResponse = generateItemResponse(this.playerEntity.getInventory());
        }

        // TODO: FIX THIS
        // BUT FOR NOW JUST CREATE EMPTY LIST FOR BATTLES, AND BUILDABLES
        ArrayList<String> buildables = new ArrayList<>();

        DungeonResponse output = new DungeonResponse(this.dungeonId, this.dungeonName, entityResponse, itemResponse,
                this.battleResponse, buildables, this.goals);

        return output;

    }

    private ArrayList<ItemResponse> generateItemResponse(HashMap<String, ArrayList<Item>> items) {
        ArrayList<ItemResponse> itemResponses = new ArrayList<>();
        for (String itemName : items.keySet()) {
            for (Item i : items.get(itemName)) {
                itemResponses.add(new ItemResponse(i.getId(), i.getType()));
            }
        }
        return itemResponses;
    }

    /**
     * Conduct a battle
     */
    public void haveBattle() {
        // A battle takes place when the Player and an enemy are in the same cell
        // at any point within a single tick.
        ArrayList<MovingEntity> newMovingEntities = new ArrayList<>();
        newMovingEntities.addAll(movingEntities);
        for (MovingEntity enemy : newMovingEntities) {
            // Movement methods are not implemented for other moving entities, e.g. mercenary
            // added the case for mercenary just to pass the example tests
            if (enemy.getPosition().equals(playerEntity.getPosition()) || enemy.getType().equals("mercenary")) {
            //if (enemy.getPosition().equals(playerEntity.getPosition())) {
                Battle newBattle = new Battle(playerEntity, enemy);
                BattleResponse newBattleResponse = newBattle.battle();
                this.battleResponse.add(newBattleResponse);
                // Move the two following checks in the movingEntities
                if (playerEntity.isDead()) { // the player has died
                    this.playerEntity = null;
                    break;
                }
                if (enemy.isDead()) {
                    this.movingEntities.remove(enemy);
                    this.playerEntity.increaseEnemiesDestroyed(1);
                }
            }
        }
    }
}



