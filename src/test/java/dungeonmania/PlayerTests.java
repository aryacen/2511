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
This test is for anything interaction/action involving the player as the only moving entity
 */
public class PlayerTests {
    /*
    Movement of the player
    Basic Movements
     */
    @Test
    @DisplayName("Test player can move in any 4 directions")
    public void testCardinalMovement() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_movementTest_testCardinalMovement",
                "c_movementTest_testCardinalMovement");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(2, 2),
                false);

        // move player downward
        DungeonResponse actualDungonRes = dmc.tick(Direction.DOWN);
        actualDungonRes = dmc.tick(Direction.DOWN);
        actualDungonRes = dmc.tick(Direction.RIGHT);
        actualDungonRes = dmc.tick(Direction.RIGHT);
        actualDungonRes = dmc.tick(Direction.UP);
        actualDungonRes = dmc.tick(Direction.LEFT);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
        assertEquals(expectedPlayer, actualPlayer);
        
    }

    /*
    Movement with walls
     */
    @Test
    @DisplayName("Test player is blocked by wall")
    public void testWallBlock() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_movementTest_testWallBlock",
                "c_movementTest_testCardinalMovement");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 2),
                false);
        DungeonResponse actualDungonRes = dmc.tick(Direction.UP);
        actualDungonRes = dmc.tick(Direction.RIGHT);
        actualDungonRes = dmc.tick(Direction.LEFT);
        actualDungonRes = dmc.tick(Direction.DOWN);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
        assertEquals(expectedPlayer, actualPlayer);
        
    } 
    /*
    Movement with Boulders
     */
    @Test
    @DisplayName("Test player can move boulder")
    public void testBoulderCanBePushed() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_movementTest_testBoulderCanBePushed",
                "c_movementTest_testCardinalMovement");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(2, 1),
                false);
        DungeonResponse actualDungonRes = dmc.tick(Direction.RIGHT);
        // player should move onto square where boulder was, and boulder moves one to the right as well
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
        assertEquals(expectedPlayer, actualPlayer);
        Position actualBoulderPos = getEntities(initDungonRes, "boulder").get(0).getPosition();
        // assert boulder moved one space right
        assertEquals(new Position(3, 1), actualBoulderPos);


        
    }

    @Test
    @DisplayName("Test player cannot move boulder into wall")
    public void testBoulderCannotBePushedIntoWalls() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_movementTest_testBoulderCanBePushed",
                "c_movementTest_testCardinalMovement");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(2, 1),
                false);
        DungeonResponse actualDungonRes = dmc.tick(Direction.RIGHT);
        // player should move onto square where boulder was, and boulder moves one to the right as well
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
        assertEquals(expectedPlayer, actualPlayer);
        Position actualBoulderPos = getEntities(initDungonRes, "boulder").get(0).getPosition();
        // assert boulder moved one space right
        assertEquals(new Position(3, 1), actualBoulderPos);

        // boulder and player should now not move right since there is a wall
        actualDungonRes = dmc.tick(Direction.RIGHT);
        assertEquals(expectedPlayer, actualPlayer);
        assertEquals(new Position(3, 1), actualBoulderPos);


    }

    @Test
    @DisplayName("Test player cannot move two boulders")
        public void testCannotPushTwoBoulders() {
            DungeonManiaController dmc = new DungeonManiaController();
            DungeonResponse initDungonRes = dmc.newGame("d_movementTest_testBoulderCanBePushed",
                    "c_movementTest_testCardinalMovement");
            EntityResponse initPlayer = getPlayer(initDungonRes).get();
            EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 1),
                    false);
            // Two boulders in down direction, so none of them should move
            DungeonResponse actualDungonRes = dmc.tick(Direction.DOWN);
            EntityResponse actualPlayer = getPlayer(actualDungonRes).get();   
            assertEquals(expectedPlayer, actualPlayer);
            // *****
            Position actualBoulderPos1 = getEntities(initDungonRes, "boulder").get(1).getPosition();
            Position actualBoulderPos2 = getEntities(initDungonRes, "boulder").get(2).getPosition();
            assertEquals(new Position(1, 2), actualBoulderPos1);
            assertEquals(new Position(1, 3), actualBoulderPos2);
    }

    /*
    Movement with Teleporters
     */
    @Test
    @DisplayName("Test player can be teleported via a teleporter")
    public void testTeleportFunctions() {
        
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_movementTest_testTeleportFunctions",
                "c_movementTest_testCardinalMovement");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
        // ASSUMED BY DEFAULT PORTAL TELEPORT PLAYER TO ONE SQUARE DOWN IF NO WALL
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(3, 4),
                    false);
        
        DungeonResponse actualDungonRes = dmc.tick(Direction.RIGHT);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get(); 
        assertEquals(expectedPlayer, actualPlayer);


    }
    @Test
    @DisplayName("Test player can't be teleported into a wall")
    public void testTeleportIntoWall() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_movementTest_testTeleportIntoWall",
                "c_movementTest_testCardinalMovement");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(2, 3),
                    false);

        DungeonResponse actualDungonRes = dmc.tick(Direction.RIGHT);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
        assertEquals(expectedPlayer, actualPlayer);
        
        


    }
    @Test
    @DisplayName("Test player can't be teleported into a spot with no movements")
    public void testTeleportIntoPositonWithNoValidMovement() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_movementTest_testTeleportNoMovement",
                "c_movementTest_testCardinalMovement");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 1),
                    false);

        // second portal is covered by walls so player should stay in same position
        DungeonResponse actualDungonRes = dmc.tick(Direction.RIGHT);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();
        assertEquals(expectedPlayer, actualPlayer);
    }

    @Test
    @DisplayName("Test teleporter correctly teleports to the correct corresponding teleporter")
    public void testTeleportCorrectColouredTeleporter() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_movementTest_testTeleportCorrectColour",
                "c_movementTest_testCardinalMovement");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();
        // ASSUMED BY DEFAULT PORTAL TELEPORT PLAYER TO ONE SQUARE DOWN IF NO WALL
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(4, 6),
                    false);
        
        DungeonResponse actualDungonRes = dmc.tick(Direction.RIGHT);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get(); 
        assertEquals(expectedPlayer, actualPlayer);
    }

    /*
    Interaction with Zombie Toaster Spawner
     */
    @Test
    @DisplayName("Test player can destroy zombie toast spawner")
    public void testCanDestroyZombieToastSpawner() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombiespawnerTest_testCanDestroySpawner",
                "c_movementTest_testCardinalMovement");
        //pick up sword and be adjacent to spawner by mioving right
        res = dmc.tick(Direction.RIGHT);
        String spawnerId = getEntities(res, "zombie_toast_spawner").get(0).getId();
        //check that spawner exists then disappears after player interacts with a sword
        assertEquals(1, getEntities(res, "zombie_toast_spawner").size());
        res = assertDoesNotThrow(() -> dmc.interact(spawnerId));
        assertEquals(0, getEntities(res, "zombie_toast_spawner").size());

    }

    @Test
    @DisplayName("Test player cannot destroy zombie toast spawner out of range")
    public void testCannotDestroyOutOfRangeZombieToastSpawner() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombiespawnerTest_testCanDestroySpawner",
                "c_movementTest_testCardinalMovement");
        //pick up sword and move down so we are not adjacent to the spawner
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        String spawnerId = getEntities(res, "zombie_toast_spawner").get(0).getId();
        //check itneract throws exception and spawner still exists
        res = assertThrows(InvalidActionException.class, () -> dmc.interact(spawnerId));
        assertEquals(1, getEntities(res, "zombie_toast_spawner").size());

    }

    @Test
    @DisplayName("Test player cannot destroy zombie toast spawner without weapon")
    public void testCannotDestroyZombieToastSpawnerWithoutWeapon() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombiespawnerTest_testCanDestroySpawner",
                "c_movementTest_testCardinalMovement");
        //don't pick up sword but move adjacent to the spawner
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        String spawnerId = getEntities(res, "zombie_toast_spawner").get(0).getId();
        //check interact throws exception and spawner still exists
        res = assertThrows(InvalidActionException.class, () -> dmc.interact(spawnerId));
        assertEquals(1, getEntities(res, "zombie_toast_spawner").size());
    }
}
