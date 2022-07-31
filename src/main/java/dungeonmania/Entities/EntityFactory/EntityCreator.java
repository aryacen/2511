package dungeonmania.Entities.EntityFactory;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Item.BuildableEntities.BowEntity;
import dungeonmania.Entities.Item.BuildableEntities.ShieldEntity;
import dungeonmania.Entities.Item.CollectableEntities.*;
import dungeonmania.Entities.MovingEntities.*;
import dungeonmania.Entities.StaticEntities.*;
import dungeonmania.util.EntityConstants;
import dungeonmania.util.Position;
import org.json.JSONObject;

/**
 * Utilise a Factory Method to create entities
 * 
 * @Pre-condition parameters are valid
 */
public class EntityCreator {

    public static Entity createEntity(JSONObject entityInfo) {
        /*
        NOTE: THE UNPACKING OF THE JSON FILE SHOULD BE ABSTRACTED TO EACH OBJECT AS THEY CAN UTILISE CLASS SPECIFIC
        PARAMETERS
        */
        String type = entityInfo.getString("type");
        // IF ENTITY INFO HAS NO ID CREATE A NEW ID FOR IT (SHOULD BE ABSTRACTED TO THE CLASS)
        String id;
        if (entityInfo.has("id")) {
            id = (String) entityInfo.get("id");
        }
        else {
            id = EntityConstants.newId();
        }
        Position position = new Position(entityInfo.getInt("x"), entityInfo.getInt("y"));
        Entity e = null;
        switch (type) {
            /*
             * Moving Entities
             */
            case "player":
                e = new PlayerEntity(entityInfo);
                break;
            case "mercenary":
                e = new MercenaryEntity(id, type, position);
                // TODO: CHANGE THIS TO CHECK IF MERCENARY IS AFFECTED BY A STATUS
                break;
            case "spider":
                e = new SpiderEntity(entityInfo);
                break;
            case "zombie_toast":
                e = new ZombieToastEntity(id, type, position);
                break;
            case "hydra":
                e = new HydraEntity(id, type, position);
                break;
            case "assassin":
                e = new AssassinEntity(id, type, position);
                // TODO: CHANGE THIS TO CHECK IF ASSASSIN IS AFFECTED BY A STATUS
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
                e = new SwordEntity(entityInfo);
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
            /*
            Restricted (these should only be called by inventory when loading game)
             */
            case "bow":
                e = new BowEntity(entityInfo);
                break;
            case "shield":
                e = new ShieldEntity(entityInfo);
                break;
        }
        return e;
    }
}
