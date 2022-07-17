package dungeonmania;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static dungeonmania.TestUtils.getPlayer;
import static dungeonmania.TestUtils.getInventory;
import dungeonmania.exceptions.InvalidActionException;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
/*
This test is for the usage of any item
 */
public class ItemTests {
    /*
    Tests with inventory.
     */
    @Test
    @DisplayName("Test player can pick up item")
    public void testPlayerPickUpItem() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_itemTest_testPlayerPickUpItem",
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
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_itemTest_testCantWalkThroughDoor",
                "c_simple");
        EntityResponse initPlayer = getPlayer(res).get();

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 1),
                false);
        //attempt to go through door without a key
        res = dmc.tick(Direction.RIGHT);
        EntityResponse actualPlayer = getPlayer(res).get();
        // check player is still in same position
        assertEquals(expectedPlayer, actualPlayer);
    }

    @Test
    @DisplayName("Test player cannot walk through a door if keys do not match")
    public void testPlayerCannotWalkThroughClosedDoorMismatchKeys() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_itemTest_testCantWalkThroughDoor",
                "c_simple");
        EntityResponse initPlayer = getPlayer(res).get();

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 1),
                false);
        //pick up the wrong key
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.RIGHT);
        // attempt to go through door with wrong key
        res = dmc.tick(Direction.RIGHT);
        EntityResponse actualPlayer = getPlayer(res).get();
        // check player is still in same position
        assertEquals(expectedPlayer, actualPlayer);
    }

    /*
    Tests with Crafting System
     */

    //@Test
    @DisplayName("Test player cannot craft shield or bow without resources")
    public void testPlayerCannotCraftWithoutMaterials() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_itemTest_testCrafting",
                "c_simple");
        // pick up 2 wood but no other materials then attempt to craft
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        // try to craft invalid item, then try to craft with insufficient materials
        assertThrows(IllegalArgumentException.class, ()-> dmc.build("sword"));
        assertThrows(InvalidActionException.class, ()-> dmc.build("bow"));
        // check that materials are stil in inventory and there is no bow
        assertEquals(2, getInventory(res, "wood").size());
        assertEquals(0, getInventory(res, "bow").size());
    }

    //@Test
    @DisplayName("Test player can craft bow")
    public void testPlayerCanCraftBow() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_itemTest_testCrafting",
                "c_simple");
        // pick up 2 wood + 3 arrows
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        // attempt to craft shield instead of bow
        assertThrows(InvalidActionException.class, ()-> dmc.build("shield"));
        
        // UNCOMMENT BELOW LINE ONCE BUILD IMPLEMENTED ***********************************************************
        //res = dmc.build("bow");

        // check that materials are used and there is now a bow
        assertEquals(1, getInventory(res, "wood").size());
        assertEquals(0, getInventory(res, "arrow").size());
        assertEquals(1, getInventory(res, "bow").size());
    }

    //@Test
    @DisplayName("Test player can craft shield (2 wood + 1 treasure)")
    public void testPlayerCanCraftShieldWithTreasure() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_itemTest_testCrafting",
                "c_simple");
        // pick up 2 wood + 3 arrows
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        // attempt to craft bow instead of shield
        assertThrows(InvalidActionException.class, ()-> dmc.build("bow"));
        
        // UNCOMMENT BELOW LINE ONCE BUILD IMPLEMENTED ***********************************************************
        //res = dmc.build("shield");

    
        // check that materials are used and there is now a bow
        assertEquals(0, getInventory(res, "wood").size());
        assertEquals(0, getInventory(res, "treasure").size());
        assertEquals(1, getInventory(res, "shield").size());
    }

    //@Test
    @DisplayName("Test player can craft shield (2 wood + 1 key)")
    public void testPlayerCanCraftShieldWithKey() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_itemTest_testCrafting",
                "c_simple");
        // pick up 2 wood + 3 arrows
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);
        // attempt to craft bow instead of shield
        assertThrows(InvalidActionException.class, ()-> dmc.build("bow"));

        // UNCOMMENT BELOW LINE ONCE BUILD IMPLEMENTED ***********************************************************
        //res = dmc.build("shield");

        // check that materials are used and there is now a bow
        assertEquals(0, getInventory(res, "wood").size());
        assertEquals(0, getInventory(res, "key").size());
        assertEquals(1, getInventory(res, "shield").size());

    }
}
