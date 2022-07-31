package dungeonmania;

import dungeonmania.Entities.EntityFactory.EntityCreator;
import dungeonmania.Entities.Item.Item;
import dungeonmania.Entities.MovingEntities.MovingEntity;
import dungeonmania.Entities.MovingEntities.PlayerEntity;
import dungeonmania.Entities.StaticEntities.StaticEntity;
import dungeonmania.util.EntityConstants;
import dungeonmania.util.FileLoader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Will save the game as a JSON File
 * Current Configuration
 * name: name of the game as a String
 * player: player info as a JSON Object
 * movingEntities: info of all moving entities excluding the player in a JsonArray
 * staticEntities: info of all static entities
 * itemEntities: info of all items on the ground currently
 */
public class GameSaver {
    /*
    public static void saveGame(String name,
                                DungeonManiaController dmc) throws IOException {
        JSONObject gameInfo = new JSONObject();
        // CHECK THE STORAGE IS CORRECT
        gameInfo.put("goals", dmc.getJsonGoal());
        // Create save game for player
        gameInfo.put("player", dmc.getPlayerEntity().getJSON());

        // Create save game for items
        JSONArray movingEntitiesInfo = new JSONArray();
        dmc.getMovingEntities().forEach(e -> movingEntitiesInfo.put(e.getJSON()));
        gameInfo.put("movingEntities", movingEntitiesInfo);

        // Create save game for items
        JSONArray staticEntitiesInfo = new JSONArray();
        dmc.getStaticEntities().forEach(e -> staticEntitiesInfo.put(e.getJSON()));
        gameInfo.put("staticEntities", staticEntitiesInfo);

        // Create save game for items
        JSONArray itemInfo = new JSONArray();
        dmc.getItemEntities().forEach(i -> itemInfo.put(i.getJSON()));
        gameInfo.put("itemEntities", itemInfo);

        gameInfo.put("config", EntityConstants.getJSON());
        gameInfo.put("idCounter", EntityConstants.getIdCounter());

        try {
            FileLoader.saveJSON(gameInfo, name);
        }
        catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    public static void loadGame(String name,
                                DungeonManiaController dmc) throws IOException {
        List<String> savedGames = FileLoader.listFileNamesInResourceDirectory("savedgames");
        if (!savedGames.contains(name)) {
            throw new IllegalArgumentException("id is not a valid game name");
        }
        String savedGameString = FileLoader.loadResourceFile("savedgames/" + name + ".json");

        JSONObject j = new JSONObject(savedGameString);
        EntityConstants.setId((String) j.get("idCounter"));
        EntityConstants.setConfig((JSONObject) j.get("configs"));

        // Create the goals
        // TODO: FOR WILL: CHECK THIS
//        dmc.parseDungeonGoals((JSONObject) j.get("goals"));
        // Create player
        dmc.setPlayerEntity((PlayerEntity) EntityCreator.createEntity((JSONObject) j.get("player")));

        // Create all static entities
        ArrayList<StaticEntity> staticEntities = new ArrayList<>();
        JSONArray staticStored = (JSONArray) j.get("staticEntities");
        Iterator staticIterator = staticStored.iterator();

        while (staticIterator.hasNext()) {
            staticEntities.add((StaticEntity) EntityCreator.createEntity((JSONObject) staticIterator.next()));
        }
        dmc.setStaticEntities(staticEntities);

        // Create all moving entities
        ArrayList<MovingEntity> movingEntities = new ArrayList<>();
        JSONArray movingStored = (JSONArray) j.get("movingEntities");
        Iterator movingIterator = movingStored.iterator();

        while (movingIterator.hasNext()) {
            movingEntities.add((MovingEntity) EntityCreator.createEntity((JSONObject) movingIterator.next()));
        }
        dmc.setMovingEntities(movingEntities);

        // Create all item entities
        ArrayList<Item> items = new ArrayList<>();
        JSONArray itemsStored = (JSONArray) j.get("itemEntities");
        Iterator itemIterator = itemsStored.iterator();

        while (itemIterator.hasNext()) {
            items.add((Item) EntityCreator.createEntity((JSONObject) itemIterator.next()));
        }
        dmc.setItemEntities(items);
    }
     */
}
