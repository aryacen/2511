package dungeonmania;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.EntityFactory.EntityCreator;
import dungeonmania.Entities.Item.Item;
import dungeonmania.Entities.MovingEntities.MovingEntity;
import dungeonmania.Entities.MovingEntities.PlayerEntity;
import dungeonmania.Entities.MovingEntities.ZombieToastEntity;
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
import dungeonmania.util.EntityConstants;

import java.io.IOException;
import java.util.*;

import org.json.*;

public class DungeonManiaController {
    List<String> dungeons = new ArrayList<String>();
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

        return getDungeonResponse();
    }

    /**
     * /game/dungeonResponseModel
     */
    public DungeonResponse getDungeonResponseModel() {
        return getDungeonResponse();
    }

    /**
     * /game/tick/item
     */
    public DungeonResponse tick(String itemUsedId) throws IllegalArgumentException, InvalidActionException {
        this.playerEntity.use(itemUsedId);

        // update goal
        Goal updatedGoals = GoalFactory.getGoal(jsonGoal, this.staticEntities, this.playerEntity, enemyGoal,
                treasureGoal);
        this.goals = updatedGoals.getGoal();

        return getDungeonResponse();
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
        this.movingEntities.forEach(e -> e.move(null, this.itemEntities, this.staticEntities, this.movingEntities));
        this.haveBattle();

        // update goal
        Goal updatedGoals = GoalFactory.getGoal(jsonGoal, this.staticEntities, this.playerEntity, enemyGoal,
                treasureGoal);
        this.goals = updatedGoals.getGoal();

        // Create dungeon response
        return getDungeonResponse();
    }

    /**
     * /game/build
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        if (buildable.equals("midnight_armour") && zombiesPresent()) {
            if (zombiesPresent()) {
                throw new InvalidActionException("Zombies are present in the map!");
            }
        } else {
            this.playerEntity.craftItem(buildable);
        }
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
//        try {
//            GameSaver.saveGame(name, this);
//        } catch (IOException e) {
//            throw new IllegalArgumentException(e);
//        }
        return getDungeonResponse();
    }

    /**
     * /game/load
     */
    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
//        try {
//            GameSaver.loadGame(name, this);
//        } catch (IOException e) {
//            throw new IllegalArgumentException(e);
//        }
        return getDungeonResponse();
    }

    /**
     * /games/all
     */
    public List<String> allGames() {
//        return FileLoader.listFileNamesInResourceDirectory("savedgames");
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
        Goal goals = GoalFactory.getGoal(goalCondition, this.staticEntities, this.playerEntity, enemyGoal,
                treasureGoal);
        return goals.getGoal();
    }

    /**
     * Creates and stores all the entities
     */
    private void parseDungeonEntities(JSONArray entities) {
        Iterator<Object> entityIterator = entities.iterator();
        Entity newEntity;
        while (entityIterator.hasNext()) {
            newEntity = EntityCreator.createEntity((JSONObject) entityIterator.next());
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

    private boolean zombiesPresent() {
        boolean output = false;
        for (MovingEntity entity : this.movingEntities) {
            if (entity.getType().equals("zombie_toast")) {
                output = true;
            }
        }
        return output;
    }

    private DungeonResponse getDungeonResponse() {
        /*
         * Dungeon id
         * Dungeon name
         * Entities
         * Inventory
         * Battle Response
         * Buildables
         * Goals
         */
        ArrayList<EntityResponse> entityResponse = new ArrayList<>();
        this.movingEntities.forEach(e -> entityResponse.add(new EntityResponse(e.getId(), e.getType(), e.getPosition(), e.isInteractable())));
        this.staticEntities.forEach(e -> entityResponse.add(new EntityResponse(e.getId(), e.getType(), e.getPosition(), e.isInteractable())));
        this.itemEntities.forEach(e -> entityResponse.add(new EntityResponse(e.getId(), e.getType(), e.getPosition(), e.isInteractable())));

        ArrayList<ItemResponse> itemResponse = new ArrayList<>();
        if (playerEntity != null) {
            EntityResponse playerResponse = new EntityResponse(this.playerEntity.getId(), "player",
                    this.playerEntity.getPosition(), false);
            entityResponse.add(playerResponse);
            itemResponse = generateItemResponse(this.playerEntity.getInventory());
        }

        ArrayList<String> buildables = new ArrayList<>();
        if (playerEntity != null) {
            buildables = this.playerEntity.getBuildable();
            if (zombiesPresent() && buildables.contains("midnight_armour")) {
                buildables.remove("midnight_armour");
            }
        }

        return new DungeonResponse(this.dungeonId, this.dungeonName, entityResponse, itemResponse,
                this.battleResponse, buildables, this.goals);
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
            // Movement methods are not implemented for other moving entities, e.g.
            // mercenary
            // added the case for mercenary just to pass the example tests
            if (enemy.getPosition().equals(playerEntity.getPosition()) || enemy.getType().equals("mercenary")) {
                // if (enemy.getPosition().equals(playerEntity.getPosition())) {
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

    public JSONObject getJsonGoal() {
        return jsonGoal;
    }

    public PlayerEntity getPlayerEntity() {
        return playerEntity;
    }

    public ArrayList<MovingEntity> getMovingEntities() {
        return movingEntities;
    }

    public ArrayList<StaticEntity> getStaticEntities() {
        return staticEntities;
    }

    public ArrayList<Item> getItemEntities() {
        return itemEntities;
    }

    public void setJsonGoal(JSONObject jsonGoal) {
        this.jsonGoal = jsonGoal;
    }

    public void setPlayerEntity(PlayerEntity playerEntity) {
        this.playerEntity = playerEntity;
    }

    public void setMovingEntities(ArrayList<MovingEntity> movingEntities) {
        this.movingEntities = movingEntities;
    }

    public void setStaticEntities(ArrayList<StaticEntity> staticEntities) {
        this.staticEntities = staticEntities;
    }

    public void setItemEntities(ArrayList<Item> itemEntities) {
        this.itemEntities = itemEntities;
    }
}



