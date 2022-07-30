package dungeonmania.Entities.EntityFactory;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Item.CollectableEntities.*;
import dungeonmania.Entities.MovingEntities.MercenaryEntity;
import dungeonmania.Entities.MovingEntities.PlayerEntity;
import dungeonmania.Entities.MovingEntities.SpiderEntity;
import dungeonmania.Entities.MovingEntities.ZombieToastEntity;
import dungeonmania.Entities.StaticEntities.*;
import dungeonmania.util.EntityConstants;
import dungeonmania.util.Position;
import org.json.JSONObject;

/**
 * Utilise a Factory Method to create entities
 * 
 * @Pre-condition parameters are valid
 */
public class entityCreator {

    public static Entity createEntity(JSONObject entityInfo) {
        String type = entityInfo.getString("type");
        String id = EntityConstants.newId();
        Position position = new Position(entityInfo.getInt("x"), entityInfo.getInt("y"));
        Entity e = null;
        switch (type) {
            /*
             * Moving Entities
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
             * Items
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
            case "sun_stone":
                e = new SunStoneEntity(id, type, position);
                break;
            case "key":
                e = new KeyEntity(id, type, position, entityInfo.getInt("key"));
                break;
            /*
             * Static Entities
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
            case "door":
                e = new DoorEntity(id, type, position, entityInfo.getInt("key"));
                break;
            case "portal":
                e = new PortalEntity(id, type, position, entityInfo.getString("colour"));
                break;
            case "swamp_tile":
                e = new SwampTileEntity(id, type, position, entityInfo.getInt("movement_factor"));
                break;
        }
        return e;
    }
}
