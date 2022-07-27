package dungeonmania.util;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

/**
 * This class contains all the class related preset in config file + whatever
 * constants that can be assigned to an
 * entity
 */
public class EntityConstants {
    // This constant is used to denote that item is not on the map / in the players
    // inventory
    private EntityConstants() {

    }
    private static EntityConstants configInstance = null;
    public final static Position notOnMap = new Position(-1, -1);

    private static HashMap<String, Double> config = new HashMap<>();
    private static int idCounter = 0;

    /**
     * Generates a new unique id
     */
    public static synchronized String newId() {
        EntityConstants.idCounter++;
        return String.valueOf(idCounter);
    }

    public static synchronized void setId(int i) {
        EntityConstants.idCounter = i;
    }

    /**
     * If no counter was specified, set the id counter to 0
     */
    public static synchronized void setId() {
        EntityConstants.idCounter = 0;
    }

    /**
     * Reset id to zero and returns what the counter was initially
     */
    public static synchronized int resetId() {
        int tmp = idCounter;
        idCounter = 0;
        return tmp;
    }

    public static synchronized void setConfig(JSONObject j) throws IOException {
        if (configInstance == null) {
            EntityConstants.configInstance = new EntityConstants();
            config.put("ally_attack", null);
            config.put("ally_defence", null);
            config.put("bomb_radius", null);
            config.put("bow_durability", null);
            config.put("bribe_amount", null);
            config.put("bribe_radius", null);
            config.put("enemy_goal", null);
            config.put("invincibility_potion_duration", null);
            config.put("invisibility_potion_duration", null);
            config.put("mercenary_attack", null);
            config.put("mercenary_health", null);
            config.put("player_attack", null);
            config.put("player_health", null);
            config.put("shield_defence", null);
            config.put("shield_durability", null);
            config.put("spider_attack", null);
            config.put("spider_health", null);
            config.put("sword_attack", null);
            config.put("sword_durability", null);
            config.put("treasure_goal", null);
            config.put("zombie_attack", null);
            config.put("zombie_health", null);
            config.put("zombie_spawn_rate", null);
            config.put("assassin_attack", null);
            config.put("assassin_bribe_amount", null);
            config.put("assassin_bribe_fail_rate", null);
            config.put("assassin_health", null);
            config.put("assassin_recon_radius", null);
            config.put("hydra_attack", null);
            config.put("hydra_health", null);
            config.put("hydra_health_increase_rate", null);
            config.put("hydra_health_increase_amount", null);
            config.put("mind_control_duration", null);
            config.put("midnight_armour_attack", null);
            config.put("midnight_armour_defence", null);
        }
        for (String s: config.keySet()) {
            if (j.has(s)) {
                config.put(s, j.getDouble(s));
            }
            else {
                config.put(s, getDefault(s));
                // TODO: MAYBE INCLUDE DEFAULT HERE INSTEAD OF AS A FILE FOR AUTOTESTS
            }
        }
    }

    /**
     * This is called when the config file does now have a specified file
     * Will look in config file "default" and return required field
     * @param field
     * @return
     */
    private static synchronized Double getDefault(String field) throws IOException {
        String configFile = FileLoader.loadResourceFile("configs/c_default.json");
        JSONObject configFileJson = new JSONObject(configFile);
        return (Double) configFileJson.getDouble(field);
    }
    /**
     * Uses the singleton pattern
     * Returns specified field in the config file
     */
     public static synchronized Double getInstance(String field) {
        return config.get(field);
    }

}
