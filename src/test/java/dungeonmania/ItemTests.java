package dungeonmania;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static dungeonmania.TestUtils.getPlayer;
import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;
import static dungeonmania.TestUtils.getGoals;
import static dungeonmania.TestUtils.countEntityOfType;
import static dungeonmania.TestUtils.getValueFromConfigFile;
import dungeonmania.exceptions.InvalidActionException;



import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
/*
This test is for the usage of any item
 */
public class ItemTests {
    /*
    Tests with inventory
     */
    @Test
    @DisplayName("Test player can pick up item")
    public void testPlayerPickUpItem() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_itemTest_testPlayerPickUpItem.json",
                "c_simple");
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "sword").size());
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "wood").size());
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, getInventory(res, "wood").size());
        
    }
    /*
    Movement with Doors and Keys
     */
    @Test
    @DisplayName("Test player cannot walk through a door without key")
    public void testPlayerCannotWalkThroughClosedDoor () {

    }

    @Test
    @DisplayName("Test player cannot walk through a door if keys do not match")
    public void testPlayerCannotWalkThroughClosedDoorMismatchKeys() {

    }

    /*
    Tests with Crafting System
     */

    @Test
    @DisplayName("Test player cannot craft shield or bow without resources")
    public void testPlayerCannotCraftWithoutMaterials() {

    }

    @Test
    @DisplayName("Test player can craft bow")
    public void testPlayerCanCraftBow() {

    }

    @Test
    @DisplayName("Test player can craft shield (2 wood + 1 treasure)")
    public void testPlayerCanCraftShieldWithTreasure() {

    }

    @Test
    @DisplayName("Test player can craft shield (2 wood + 1 key)")
    public void testPlayerCanCraftShieldWithKey() {

    }
}
