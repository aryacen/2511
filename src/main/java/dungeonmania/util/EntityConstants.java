package dungeonmania.util;

/**
 * This file contains any pre-sets that are in the config files + anything else that are useful
 */
public class EntityConstants {
    // This constant is used to denote that item is not on the map / in the players inventory
    public final static Position notOnMap = new Position(-1, -1);
    // TODO: DONT KNOW HOW TO MAKE THIS BETTER SINCE WE WANT IT TO BE STATIC AND FINAL

    public static int ally_attack;
    public static int ally_defence;
    public static int bomb_radius;
    public static int bow_durability;
    public static int bribe_amount;
    public static int bribe_radius;
    public static int enemy_goals;
    public static int invincibility_potion_duration;
    public static int invisibility_potion_duration;
    public static int mercenary_attack;
    public static int mercenary_health;
    public static int player_attack;
    public static int player_health;
    public static int shield_defence;
    public static int shield_durability;
    public static int spider_attack;
    public static int spider_health;
    public static int spider_spawn_rate;
    public static int sword_attack;
    public static int sword_durability;
    public static int treasure_goal;
    public static int zombie_attack;
    public static int zombie_health;
    public static int zombie_spawn_rate;

    public EntityConstants(
        int ally_attack,
        int ally_defence,
        int bomb_radius,
        int bow_durability,
        int bribe_amount,
        int bribe_radius,
        int enemy_goals,
        int invincibility_potion_duration,
        int invisibility_potion_duration,
        int mercenary_attack,
        int mercenary_health,
        int player_attack,
        int player_health,
        int shield_defence,
        int shield_durability,
        int spider_attack,
        int spider_health,
        int spider_spawn_rate,
        int sword_attack,
        int sword_durability,
        int treasure_goal,
        int zombie_attack,
        int zombie_health,
        int zombie_spawn_rate
    ) {
        EntityConstants.ally_attack = ally_attack;
        EntityConstants.ally_defence = ally_defence;
        EntityConstants.bomb_radius = bomb_radius;
        EntityConstants.bow_durability = bow_durability;
        EntityConstants.bribe_amount = bribe_amount;
        EntityConstants.bribe_radius = bribe_radius;
        EntityConstants.enemy_goals = enemy_goals;
        EntityConstants.invincibility_potion_duration = invincibility_potion_duration;
        EntityConstants.invisibility_potion_duration = invisibility_potion_duration;
        EntityConstants.mercenary_attack = mercenary_attack;
        EntityConstants.mercenary_health = mercenary_health;
        EntityConstants.player_attack = player_attack;
        EntityConstants.player_health = player_health;
        EntityConstants.shield_defence = shield_defence;
        EntityConstants.shield_durability = shield_durability;
        EntityConstants.spider_attack =spider_attack;
        EntityConstants.spider_health = spider_health;
        EntityConstants.spider_spawn_rate = spider_spawn_rate;
        EntityConstants.sword_attack = sword_attack;
        EntityConstants.sword_durability = sword_durability;
        EntityConstants.treasure_goal = treasure_goal;
        EntityConstants.zombie_attack = zombie_attack;
        EntityConstants.zombie_health = zombie_health;
        EntityConstants.zombie_spawn_rate = zombie_spawn_rate;
    }
}
