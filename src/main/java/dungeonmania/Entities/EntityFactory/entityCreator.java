package dungeonmania.Entities.EntityFactory;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Item.CollectableEntities.*;
import dungeonmania.Entities.MovingEntities.MercenaryEntity;
import dungeonmania.Entities.MovingEntities.PlayerEntity;
import dungeonmania.Entities.MovingEntities.SpiderEntity;
import dungeonmania.Entities.MovingEntities.ZombieToastEntity;
import dungeonmania.Entities.StaticEntities.*;
import dungeonmania.util.Position;

/**
 * Utilise a Factory Method to create entities
 * @Pre-condition parameters are valid
 */
public class entityCreator {
    public static Entity createEntity(String id, String type, Position position) {
        Entity e = null;
        switch (type) {
            /*
            Moving Entities
             */
            case "player":
                e = new PlayerEntity(id, type, position);
                break;
            case "mercenary":
                e = new MercenaryEntity(id, type, position);
                break;
            case "spider":
                e = new SpiderEntity(id, type, position);
                break;
            case "zombie_toast":
                e = new ZombieToastEntity(id, type, position);
                break;
            /*
            Items
             */
            case "arrow":
                e = new ArrowsEntity(id, type, position);
                break;
            case "bomb":
                e = new BombEntity(id, type, position);
                break;
            case "invincibility_potion":
                e = new InvincibilityPotion(id, type, position);
                break;
            case "invisibility_potion":
                e = new InvisibilityPotionEntity(id, type, position);
                break;
            case "sword":
                e = new SwordEntity(id, type, position);
                break;
            case "treasure":
                e = new TreasureEntity(id, type, position);
                break;
            case "wood":
                e = new WoodEntity(id, type, position);
                break;
            /*
            Static Entities
             */
            case "boulder":
                e = new BoulderEntity(id, type, position);
                break;
            case "exit":
                e = new ExitEntity(id, type, position);
                break;
            case "switch":
                e = new FloorSwitchEntity(id, type, position);
                break;
            case "wall":
                e = new WallEntity(id, type, position);
                break;
            case "zombie_toast_spawner":
                e = new ZombieToastSpawnerEntity(id, type, position);
                break;
        }
        return e;
    }

    /**
     * Doors and keys both need the 'key' field
     * @Pre-condition this is only called for keys or doors
     */
    public static Entity createEntity(String id, String type, Position position, int key) {
        if (type.equals("door")) {
            return new DoorEntity(id, type, position, key);
        }
        else if (type.equals("key")) {
            return new KeyEntity(id, type, position, key);
        }
        // Prevent error msg
        return null;
    }

    /**
     * Portals have an extra field for color
     * @Pre-condition this is only called for portals
     */
    public static PortalEntity createEntity(String id, String type, Position position, String color) {
        return new PortalEntity(id, type, position, color);
    }

}
