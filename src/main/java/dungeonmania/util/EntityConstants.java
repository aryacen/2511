package dungeonmania.util;

import org.json.JSONArray;
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
    private static HashMap<String, Double> defaultConfig = new HashMap<>();
    static {
        config.put("ally_attack", 3.0);
        config.put("ally_defence", 3.0);
        config.put("bomb_radius", 1.0);
        config.put("bow_durability", 1.0);
        config.put("bribe_amount", 1.0);
        config.put("bribe_radius", 1.0);
        config.put("enemy_goal", 1.0);
        config.put("invincibility_potion_duration", 1.0);
        config.put("invisibility_potion_duration", 1.0);
        config.put("mercenary_attack", 1.0);
        config.put("mercenary_health", 1.0);
        config.put("player_attack", 1.0);
        config.put("player_health", 1.0);
        config.put("shield_defence", 1.0);
        config.put("shield_durability", 1.0);
        config.put("spider_attack", 1.0);
        config.put("spider_health", 1.0);
        config.put("sword_attack", 1.0);
        config.put("sword_durability", 1.0);
        config.put("treasure_goal", 1.0);
        config.put("zombie_attack", 1.0);
        config.put("zombie_health", 1.0);
        config.put("zombie_spawn_rate", 1.0);
        config.put("assassin_attack", 1.0);
        config.put("assassin_bribe_amount", 1.0);
        config.put("assassin_bribe_fail_rate", 1.0);
        config.put("assassin_health", 1.0);
        config.put("assassin_recon_radius", 1.0);
        config.put("hydra_attack", 1.0);
        config.put("hydra_health", 1.0);
        config.put("hydra_health_increase_rate", 1.0);
        config.put("hydra_health_increase_amount", 1.0);
        config.put("mind_control_duration", 1.0);
        config.put("midnight_armour_attack", 1.0);
        config.put("midnight_armour_defence", 1.0);
    }
    private static int idCounter = 0;

    /**
     * Generates a new unique id
     */
    public static synchronized String newId() {
        EntityConstants.idCounter++;
        return String.valueOf(idCounter);
    }

    public static synchronized void setId(String i) {
        EntityConstants.idCounter = Integer.parseInt(i);
    }


    /**
     * Reset id to zero and returns what the counter was initially
     */
    public static synchronized void resetId() {
        idCounter = 0;
    }

    /**
     * Returns most recent id used
     */
    public static synchronized String getIdCounter() {
        return String.valueOf(idCounter);
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
    private static Double getDefault(String field) {
        return EntityConstants.defaultConfig.get(field);
    }
    /**
     * Uses the singleton pattern
     * Returns specified field in the config file
     */
     public static synchronized Double getInstance(String field) {
        return config.get(field);
    }

    public static JSONObject getJSON() {
        JSONObject j = new JSONObject();
        config.keySet().forEach(c -> j.put(c, config.get(c)));
        return j;
    }

}
