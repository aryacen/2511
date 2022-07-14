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
    public static Entity createEntity(String id, String type, Position position, boolean isInteractable) {
        Entity e = null;
        switch (type) {
            /*
            Moving Entities
             */
            case "player":
                e = new PlayerEntity(id, type, position, isInteractable);
                break;
            case "mercenary":
                e = new MercenaryEntity(id, type, position, isInteractable);
                break;
            case "spider":
                e = new SpiderEntity(id, type, position, isInteractable);
                break;
            case "zombie_toast":
                e = new ZombieToastEntity(id, type, position, isInteractable);
                break;
            /*
            Items
             */
            case "arrow":
                e = new ArrowsEntity(id, type, position, isInteractable);
                break;
            case "bomb":
                e = new BombEntity(id, type, position, isInteractable);
                break;
            case "invincibility_potion":
                e = new InvincibilityPotion(id, type, position, isInteractable);
                break;
            case "invisibility_potion":
                e = new InvisibilityPotionEntity(id, type, position, isInteractable);
                break;
            case "sword":
                e = new SwordEntity(id, type, position, isInteractable);
                break;
            case "treasure":
                e = new TreasureEntity(id, type, position, isInteractable);
                break;
            case "wood":
                e = new WoodEntity(id, type, position, isInteractable);
                break;
            /*
            Static Entities
             */
            case "boulder":
                e = new BoulderEntity(id, type, position, isInteractable);
                break;
            case "exit":
                e = new ExitEntity(id, type, position, isInteractable);
                break;
            case "switch":
                e = new FloorSwitchEntity(id, type, position, isInteractable);
                break;
            case "wall":
                e = new WallEntity(id, type, position, isInteractable);
                break;
            case "zombie_toast_spawner":
                e = new ZombieToastSpawnerEntity(id, type, position, isInteractable);
                break;
        }
        return e;
    }

    /**
     * Doors and keys both need the 'key' field
     * @Pre-condition this is only called for keys or doors
     */
    public static Entity createEntity(String id, String type, Position position, boolean isInteractable, int key) {
        if (type.equals("door")) {
            return new DoorEntity(id, type, position, isInteractable, key);
        }
        else if (type.equals("key")) {
            return new KeyEntity(id, type, position, isInteractable, key);
        }
        // Prevent error msg
        return null;
    }

    /**
     * Portals have an extra field for color
     * @Pre-condition this is only called for portals
     */
    public static Entity createEntity(String id, String type, Position position, boolean isInteractable, String color) {
        return new PortalEntity(id, type, position, isInteractable, color);
    }

}
