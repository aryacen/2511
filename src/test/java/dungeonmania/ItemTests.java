package dungeonmania;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
///import static org.junit.jupiter.api.Assertions.assertTrue;

import static dungeonmania.TestUtils.getPlayer;
//import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;
//import static dungeonmania.TestUtils.getGoals;
//import static dungeonmania.TestUtils.countEntityOfType;
//import static dungeonmania.TestUtils.getValueFromConfigFile;
import dungeonmania.exceptions.InvalidActionException;

//import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
//import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

/*
This test is for the usage of any item
 */
public class ItemTests {
        /*
         * Tests with inventory.
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
         * Movement with Doors and Keys
         */
        @Test
        @DisplayName("Test player cannot walk through a door without key")
        public void testPlayerCannotWalkThroughClosedDoor() {
                DungeonManiaController dmc;
                dmc = new DungeonManiaController();
                DungeonResponse res = dmc.newGame("d_itemTest_testCantWalkThroughDoor",
                                "c_simple");
                EntityResponse initPlayer = getPlayer(res).get();

                // create the expected result
                EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(),
                                new Position(1, 1),
                                false);
                // attempt to go through door without a key
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
                EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(),
                                new Position(1, 1),
                                false);
                // pick up the wrong key
                res = dmc.tick(Direction.LEFT);
                res = dmc.tick(Direction.RIGHT);
                // attempt to go through door with wrong key
                res = dmc.tick(Direction.RIGHT);
                EntityResponse actualPlayer = getPlayer(res).get();
                // check player is still in same position
                assertEquals(expectedPlayer, actualPlayer);
        }

        /*
         * Tests with Crafting System
         */

        @Test
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
                assertThrows(IllegalArgumentException.class, () -> dmc.build("sword"));
                assertThrows(InvalidActionException.class, () -> dmc.build("bow"));
                // check that materials are stil in inventory and there is no bow
                assertEquals(2, getInventory(res, "wood").size());
                assertEquals(0, getInventory(res, "bow").size());
        }

        @Test
        @DisplayName("Test player can craft bow")
        public void testPlayerCanCraftBow() throws IllegalArgumentException, InvalidActionException {
                DungeonManiaController dmc;
                dmc = new DungeonManiaController();
                DungeonResponse res = dmc.newGame("d_itemTest_testCrafting",
                                "c_simple");
                // pick up 2 wood + 3 arrows
                dmc.tick(Direction.RIGHT);
                dmc.tick(Direction.RIGHT);
                dmc.tick(Direction.DOWN);
                dmc.tick(Direction.DOWN);
                res = dmc.tick(Direction.DOWN);

                List<String> expectedBuildables = new ArrayList<String>();
                expectedBuildables.add("bow");
                // assertEquals(expectedBuildables, res.getBuildables());

                // craft bow
                res = dmc.build("bow");

                // check that materials are used and there is now a bow
                assertEquals(1, getInventory(res, "bow").size());
                assertEquals(1, getInventory(res, "wood").size());
                assertEquals(0, getInventory(res, "arrow").size());

        }

        @Test
        @DisplayName("Test player can craft shield (2 wood + 1 treasure)")
        public void testPlayerCanCraftShieldWithTreasure() throws IllegalArgumentException, InvalidActionException {
                DungeonManiaController dmc;
                dmc = new DungeonManiaController();
                DungeonResponse res = dmc.newGame("d_itemTest_testCrafting",
                                "c_simple");
                // pick up 2 wood
                dmc.tick(Direction.RIGHT);
                dmc.tick(Direction.RIGHT);

                assertThrows(InvalidActionException.class, () -> dmc.build("shield"));

                // pick up treasure
                res = dmc.tick(Direction.RIGHT);

                List<String> expectedBuildables = new ArrayList<String>();
                expectedBuildables.add("shield");
                assertEquals(expectedBuildables, res.getBuildables());

                // build shield
                res = dmc.build("shield");

                // check that materials are used and there is now a shield
                assertEquals(0, getInventory(res, "wood").size());
                assertEquals(0, getInventory(res, "treasure").size());
                assertEquals(1, getInventory(res, "shield").size());
        }

        @Test
        @DisplayName("Test player can craft shield (2 wood + 1 key)")
        public void testPlayerCanCraftShieldWithKey() throws IllegalArgumentException, InvalidActionException {
                DungeonManiaController dmc;
                dmc = new DungeonManiaController();
                DungeonResponse res = dmc.newGame("d_itemTest_testCrafting",
                                "c_simple");
                // pick up 2 wood
                dmc.tick(Direction.RIGHT);
                dmc.tick(Direction.RIGHT);

                assertThrows(InvalidActionException.class, () -> dmc.build("shield"));

                // pick up key
                dmc.tick(Direction.UP);
                res = dmc.build("shield");

                // check that materials are used and there is now a shield
                assertEquals(0, getInventory(res, "wood").size());
                assertEquals(0, getInventory(res, "key").size());
                assertEquals(1, getInventory(res, "shield").size());
        }

        @Test
        @DisplayName("Test player can craft sceptre (1 wood + 1 key + 1 sunstone)")
        public void testPlayerCanCraftSceptre() throws IllegalArgumentException, InvalidActionException {
                DungeonManiaController dmc;
                dmc = new DungeonManiaController();
                DungeonResponse res = dmc.newGame("d_itemTest_testCrafting2",
                                "c_simple");
                // pick up 1 wood + 1 sun stone + 2 arrows
                dmc.tick(Direction.RIGHT);
                dmc.tick(Direction.RIGHT);

                assertThrows(InvalidActionException.class, () -> dmc.build("sceptre"));

                // pick up key
                dmc.tick(Direction.UP);
                res = dmc.build("sceptre");

                // check that materials are used and there is now a sceptre
                assertEquals(0, getInventory(res, "wood").size());
                assertEquals(0, getInventory(res, "key").size());
                assertEquals(0, getInventory(res, "sun_stone").size());
                assertEquals(1, getInventory(res, "sceptre").size());
        }

        @Test
        @DisplayName("Test player can craft sceptre (2 arrows + 1 key + 1 sunstone)")
        public void testPlayerCanCraftSceptre2() throws IllegalArgumentException, InvalidActionException {
                DungeonManiaController dmc;
                dmc = new DungeonManiaController();
                DungeonResponse res = dmc.newGame("d_itemTest_testCrafting2",
                                "c_simple");
                // pick up 1 wood + 1 sun stone + 2 arrows
                dmc.tick(Direction.RIGHT);
                dmc.tick(Direction.RIGHT);
                dmc.tick(Direction.DOWN);
                dmc.tick(Direction.DOWN);

                dmc.tick(Direction.UP);
                dmc.tick(Direction.UP);

                assertThrows(InvalidActionException.class, () -> dmc.build("sceptre"));

                // pick up key
                dmc.tick(Direction.UP);
                res = dmc.build("sceptre");

                // check that materials are used and there is now a sceptre
                assertEquals(1, getInventory(res, "wood").size());
                assertEquals(0, getInventory(res, "key").size());
                assertEquals(0, getInventory(res, "sun_stone").size());
                assertEquals(0, getInventory(res, "arrow").size());
                assertEquals(1, getInventory(res, "sceptre").size());
        }

        @Test
        @DisplayName("Test player can craft sceptre (1 wood + 1 treasure + 1 sunstone)")
        public void testPlayerCanCraftSceptre3() throws IllegalArgumentException, InvalidActionException {
                DungeonManiaController dmc;
                dmc = new DungeonManiaController();
                DungeonResponse res = dmc.newGame("d_itemTest_testCrafting2",
                                "c_simple");
                // pick up 1 wood + 1 sun stone
                dmc.tick(Direction.RIGHT);
                dmc.tick(Direction.RIGHT);

                assertThrows(InvalidActionException.class, () -> dmc.build("sceptre"));

                // pick up treasure
                dmc.tick(Direction.RIGHT);
                res = dmc.build("sceptre");

                // check that materials are used and there is now a sceptre
                assertEquals(0, getInventory(res, "wood").size());
                assertEquals(0, getInventory(res, "treasure").size());
                assertEquals(0, getInventory(res, "sun_stone").size());
                assertEquals(1, getInventory(res, "sceptre").size());
        }

        @Test
        @DisplayName("Test player can craft sceptre (2 arrows + 1 treasure + 1 sunstone)")
        public void testPlayerCanCraftSceptre4() throws IllegalArgumentException, InvalidActionException {
                DungeonManiaController dmc;
                dmc = new DungeonManiaController();
                DungeonResponse res = dmc.newGame("d_itemTest_testCrafting2",
                                "c_simple");
                // pick up 1 wood + 1 sun stone + 2 arrows
                dmc.tick(Direction.RIGHT);
                dmc.tick(Direction.RIGHT);
                dmc.tick(Direction.DOWN);
                dmc.tick(Direction.DOWN);

                dmc.tick(Direction.UP);
                dmc.tick(Direction.UP);

                assertThrows(InvalidActionException.class, () -> dmc.build("sceptre"));

                // pick up treasure
                dmc.tick(Direction.RIGHT);
                res = dmc.build("sceptre");

                // check that materials are used and there is now a sceptre
                assertEquals(1, getInventory(res, "wood").size());
                assertEquals(0, getInventory(res, "treasure").size());
                assertEquals(0, getInventory(res, "arrow").size());
                assertEquals(0, getInventory(res, "sun_stone").size());
                assertEquals(1, getInventory(res, "sceptre").size());
        }

        @Test
        @DisplayName("Test player can craft midnight armour")
        public void testPlayerCanCraftMidnightArmour() throws IllegalArgumentException, InvalidActionException {
                DungeonManiaController dmc;
                dmc = new DungeonManiaController();
                DungeonResponse res = dmc.newGame("d_itemTest_testCrafting3",
                                "c_simple");
                // pick up 1 sword + 1 sun stone
                dmc.tick(Direction.RIGHT);
                dmc.tick(Direction.RIGHT);

                res = dmc.build("midnight_armour");

                // check that materials are used and there is now a midnight armour
                assertEquals(0, getInventory(res, "sword").size());
                assertEquals(0, getInventory(res, "sun_stone").size());
                assertEquals(1, getInventory(res, "midnight_armour").size());
        }

        @Test
        @DisplayName("Test player cannot craft midnight armour while a zombie is present")
        public void testPlayerCannotCraftMidnightArmour() throws IllegalArgumentException, InvalidActionException {
                DungeonManiaController dmc;
                dmc = new DungeonManiaController();
                DungeonResponse res = dmc.newGame("d_itemTest_testCrafting4",
                                "c_simple");
                // pick up 1 sword + 1 sun stone
                dmc.tick(Direction.RIGHT);
                res = dmc.tick(Direction.RIGHT);

                assertThrows(InvalidActionException.class, () -> dmc.build("midnight_armour"));

                // check that materials are not used and there is no midnight armour
                assertEquals(1, getInventory(res, "sword").size());
                assertEquals(1, getInventory(res, "sun_stone").size());
                assertEquals(0, getInventory(res, "midnight_armour").size());
        }

        @Test
        @DisplayName("Testing the buildables function return all the craftables")
        public void testBuildables() {
                DungeonManiaController dmc;
                dmc = new DungeonManiaController();
                DungeonResponse res = dmc.newGame("d_itemTest_testCrafting5",
                                "c_simple");

                // pick up 2 woods + 3 arrows + 1 key
                dmc.tick(Direction.RIGHT);
                dmc.tick(Direction.RIGHT);
                dmc.tick(Direction.UP);
                dmc.tick(Direction.DOWN);
                dmc.tick(Direction.DOWN);
                dmc.tick(Direction.DOWN);
                dmc.tick(Direction.DOWN);

                dmc.tick(Direction.UP);
                dmc.tick(Direction.UP);
                dmc.tick(Direction.UP);

                // pick up 1 sword + 1 sun stone + 1 treasure
                dmc.tick(Direction.RIGHT);
                dmc.tick(Direction.RIGHT);
                res = dmc.tick(Direction.RIGHT);

                List<String> expectedBuildables = new ArrayList<String>();
                expectedBuildables.add("bow");
                expectedBuildables.add("shield");
                expectedBuildables.add("sceptre");
                expectedBuildables.add("midnight_armour");
                assertEquals(expectedBuildables, res.getBuildables());
        }

        @Test
        @DisplayName("Testing the buildables function on midnight armour when zombies are present")
        public void testBuildables2() {
                DungeonManiaController dmc;
                dmc = new DungeonManiaController();
                DungeonResponse res = dmc.newGame("d_itemTest_testCrafting4",
                                "c_simple");

                // pick up 1 sword + 1 sun stone
                dmc.tick(Direction.RIGHT);
                res = dmc.tick(Direction.RIGHT);

                List<String> expectedBuildables = new ArrayList<String>();
                assertEquals(expectedBuildables, res.getBuildables());
        }

        @Test
        @DisplayName("Testing the buildables function on midnight armour when zombies are not present")
        public void testBuildables3() {
                DungeonManiaController dmc;
                dmc = new DungeonManiaController();
                DungeonResponse res = dmc.newGame("d_itemTest_testCrafting3",
                                "c_simple");

                // pick up 1 sword + 1 sun stone
                dmc.tick(Direction.RIGHT);
                res = dmc.tick(Direction.RIGHT);

                List<String> expectedBuildables = new ArrayList<String>();
                expectedBuildables.add("midnight_armour");
                assertEquals(expectedBuildables, res.getBuildables());
        }

        @Test
        @DisplayName("Testing the buildables function on sceptre (2 arrows + 1 treasure + 1 sun stone)")
        public void testBuildables4() {
                DungeonManiaController dmc;
                dmc = new DungeonManiaController();
                DungeonResponse res = dmc.newGame("d_itemTest_testCrafting6",
                                "c_simple");

                // pick up 2 arrows + 1 sun stone + 1 treasure
                dmc.tick(Direction.RIGHT);
                dmc.tick(Direction.RIGHT);
                dmc.tick(Direction.RIGHT);
                res = dmc.tick(Direction.RIGHT);

                List<String> expectedBuildables = new ArrayList<String>();
                expectedBuildables.add("sceptre");
                assertEquals(expectedBuildables, res.getBuildables());
        }

        @Test
        @DisplayName("Testing the buildables function on sceptre (1 wood + 1 treasure + 1 sun stone)")
        public void testBuildables5() {
                DungeonManiaController dmc;
                dmc = new DungeonManiaController();
                DungeonResponse res = dmc.newGame("d_itemTest_testCrafting2",
                                "c_simple");

                // pick up 1 wood + 1 sun stone + 1 treasure
                dmc.tick(Direction.RIGHT);
                dmc.tick(Direction.RIGHT);
                res = dmc.tick(Direction.RIGHT);

                List<String> expectedBuildables = new ArrayList<String>();
                expectedBuildables.add("sceptre");
                assertEquals(expectedBuildables, res.getBuildables());
        }

        @Test
        @DisplayName("Testing the buildables function on sceptre (1 wood + 1 key + 1 sun stone)")
        public void testBuildables6() {
                DungeonManiaController dmc;
                dmc = new DungeonManiaController();
                DungeonResponse res = dmc.newGame("d_itemTest_testCrafting2",
                                "c_simple");

                // pick up 1 wood + 1 sun stone + 1 key
                dmc.tick(Direction.RIGHT);
                dmc.tick(Direction.RIGHT);
                res = dmc.tick(Direction.UP);

                List<String> expectedBuildables = new ArrayList<String>();
                expectedBuildables.add("sceptre");
                assertEquals(expectedBuildables, res.getBuildables());
        }
}
