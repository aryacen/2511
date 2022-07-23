package dungeonmania;

import dungeonmania.Entities.Item.Item;
import dungeonmania.Entities.MovingEntities.MercenaryEntity;
import dungeonmania.Entities.MovingEntities.MovingEntities;
import dungeonmania.Entities.MovingEntities.PlayerEntity;
import dungeonmania.Entities.MovingEntities.SpiderEntity;
import dungeonmania.Entities.MovingEntities.ZombieToastEntity;
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

    /**
    * Helper function to read the goals and construct a String representing the
     * goals
     */
    private String parseDungeonGoals(JSONObject goalCondition) {
        String tempGoal = goalCondition.getString("goal");

        // Checks if the goal is a supergoal ("AND" or "OR")
        if (tempGoal.equals("AND") || tempGoal.equals("OR")) {
            JSONArray subGoals = (JSONArray) goalCondition.getJSONArray("subgoals");
            JSONObject temp1 = subGoals.getJSONObject(0);
            String tempGoal1 = temp1.getString("goal");
            if (tempGoal1.equals("AND") || tempGoal1.equals("OR")) {
                JSONArray subGoal1 = (JSONArray) temp1.getJSONArray("subgoals");
                JSONObject tempSubGoal1 = subGoal1.getJSONObject(0);
                JSONObject tempSubGoal2 = subGoal1.getJSONObject(1);
                String tempSubGoalString1 = tempSubGoal1.getString("goal");
                String tempSubGoalString2 = tempSubGoal2.getString("goal");

                JSONObject temp4 = subGoals.getJSONObject(1);
                String superGoal1 = temp4.getString("goal");
                JSONArray subGoal2 = (JSONArray) temp4.getJSONArray("subgoals");
                JSONObject tempSubGoal3 = subGoal2.getJSONObject(0);
                JSONObject tempSubGoal4 = subGoal2.getJSONObject(1);
                String tempSubGoalString3 = tempSubGoal3.getString("goal");
                String tempSubGoalString4 = tempSubGoal4.getString("goal");
                String output = ":" + tempSubGoalString1 + " " + tempGoal + " (:" + tempSubGoalString2 + " " + tempGoal1
                        + " (:" + tempSubGoalString3 + " " + superGoal1 + " :" + tempSubGoalString4 + "))";
                return output;
            }
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

    /**
     * Helper function to read the entities array, create EntityResponse for every
     * entity and store them in a List of EntityResponse
     */
    private List<EntityResponse> parseDungeonEntities(JSONArray entities) {
        List<EntityResponse> output = new ArrayList<EntityResponse>();
        // Loop through the entities array
        for (int i = 0; i < entities.length(); i++) {
            boolean interactable = false;
            JSONObject temp = entities.getJSONObject(i);

            // entityId will be entity type followed with an integer (starting from 1),
            // which is denoted by the frequency of the entity type in the dungeon
            String type = temp.getString("type");
            String entityId = EntityConstants.newId();

            int x = temp.getInt("x");
            int y = temp.getInt("y");
            Position position = new Position(x, y);
            // TODO: THINK THIS SHOULD BE ABSTRACTED TO THE CLASSES
            if (type.equals("mercenary") || type.equals("zombie_toast_spawner")) {
                interactable = true;
            }

            if (type.equals("player")) {
                this.playerEntity = (PlayerEntity) entityCreator.createEntity(entityId, type, position, interactable);
            } else {
                switch (type) {
                    case "portal":
                        String color = temp.getString("color");
                        PortalEntity newPortal = (PortalEntity) entityCreator.createEntity(entityId, type, position,
                                interactable, color);
                        this.staticEntities.add(newPortal);
                        break;
                    case "door":
                    case "key":
                        int keyId = temp.getInt("key");
                        Entity newDoorOrKey = entityCreator.createEntity(entityId, type, position, interactable, keyId);
                        if (newDoorOrKey.getEntityType().equals("Static")) {
                            this.staticEntities.add((StaticEntity) newDoorOrKey);
                        } else {
                            this.itemEntities.add((Item) newDoorOrKey);
                        }
                        break;
                    default:
                        Entity newEntity = entityCreator.createEntity(entityId, type, position, interactable);
                        if (newEntity.getEntityType().equals("Static")) {
                            this.staticEntities.add((StaticEntity) newEntity);
                        } else if (newEntity.getEntityType().equals("Moving")) {
                            this.movingEntities.add((MovingEntities) newEntity);
                        } else {
                            this.itemEntities.add((Item) newEntity);
                        }

                }
            }

            // Create an EntityResponse for the entity and store them in the output List
            EntityResponse tempResponse = new EntityResponse(entityId, type, position, interactable);
            output.add(tempResponse);
        }
        return output;
    }

    private void storeConfig(JSONObject configContent) {
        EntityConstants.ally_attack = configContent.getInt("ally_attack");
        EntityConstants.ally_defence = configContent.getInt("ally_defence");
        EntityConstants.bomb_radius = configContent.getInt("bomb_radius");
        EntityConstants.bow_durability = configContent.getInt("bow_durability");
        EntityConstants.bribe_amount = configContent.getInt("bribe_amount");
        EntityConstants.bribe_radius = configContent.getInt("bribe_radius");
        EntityConstants.enemy_goal = configContent.getInt("enemy_goal");
        EntityConstants.invincibility_potion_duration = configContent.getInt("invincibility_potion_duration");
        EntityConstants.invisibility_potion_duration = configContent.getInt("invisibility_potion_duration");
        EntityConstants.mercenary_attack = configContent.getInt("mercenary_attack");
        EntityConstants.mercenary_health = configContent.getInt("mercenary_health");
        EntityConstants.player_attack = configContent.getInt("player_attack");
        EntityConstants.player_health = configContent.getInt("player_health");
        EntityConstants.shield_defence = configContent.getInt("shield_defence");
        EntityConstants.shield_durability = configContent.getInt("shield_durability");
        EntityConstants.spider_attack = configContent.getInt("spider_attack");
        EntityConstants.spider_health = configContent.getInt("spider_health");
        EntityConstants.spider_spawn_rate = configContent.getInt("spider_spawn_rate");
        EntityConstants.sword_attack = configContent.getInt("sword_attack");
        EntityConstants.sword_durability = configContent.getInt("sword_durability");
        EntityConstants.treasure_goal = configContent.getInt("treasure_goal");
        EntityConstants.zombie_attack = configContent.getInt("zombie_attack");
        EntityConstants.zombie_health = configContent.getInt("zombie_health");
        EntityConstants.zombie_spawn_rate = configContent.getInt("zombie_spawn_rate");
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
            MovingEntities tempEntity = this.movingEntities.get(i);
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

        EntityResponse playerResponse = new EntityResponse(this.playerEntity.getId(), "player",
                this.playerEntity.getPosition(), false);
        entityResponse.add(playerResponse);

        // TODO: FIX THIS
        // BUT FOR NOW JUST CREATE EMPTY LIST FOR BATTLES, AND BUILDABLES
        ArrayList<ItemResponse> itemResponse = generateItemResponse(this.playerEntity.getInventory());
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
        for (MovingEntities enemy : movingEntities) {
            // Movement methods are not implemented for other moving entities.
            // if (enemy.getPosition() == playerEntity.getPosition()) {
            if (true) {
                // System.out.println("same position");
                Battle newBattle = new Battle(playerEntity, enemy);
                BattleResponse newBattleResponse = newBattle.battle();
                this.battleResponse.add(newBattleResponse);
                if (enemy.getHp() <= 0.0) { // the enemy has died
                    // this.movingEntities.remove(enemy);
                }
                if (playerEntity.getHp() <= 0.0) { // the player has died
                    // this.playerEntity = null;
                    // System.out.println("player died");
                    break;
                }
            }
        }
        this.movingEntities = removeDead();
    }

    /**
     * Return a new list of alive moving entities
     *
     * @return ArrayList<MovingEntities>
     */
    public ArrayList<MovingEntities> removeDead() {
        ArrayList<MovingEntities> newMovingEntities = new ArrayList<>();
        for (MovingEntities enemy : movingEntities) {
            if (enemy.getHp() > 0.0) {
                newMovingEntities.add(enemy);
            }
        }
        return newMovingEntities;
    }
}

