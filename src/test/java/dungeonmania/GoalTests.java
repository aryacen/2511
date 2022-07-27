package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
// import dungeonmania.util.EntityConstants;
import dungeonmania.util.Position;

/*
This test is regarding anything that will cause the player to win the game
 */
public class GoalTests {
    /*
    ! Basic Goals
     */
    /*
    * Exit
     */
    // @Test
    @DisplayName("Getting to exit")
    public void testExit() {

    }

    /*
    * Destroy enemies and spawners
     */
    // @Test
    @DisplayName("Destroy 1 enemy")
    public void testOneEnemy() {

    }

    // @Test
    @DisplayName("Destroy 1 enemy and 1 spawner")
    public void testOneEnemyAndSpawner() {

    }

    // @Test
    @DisplayName("Destroy multiple enemies")
    public void testMultipleEnemy() {

    }

    // @Test
    @DisplayName("Destroy multiple enemies and 1 spawner")
    public void testMultipleEnemyAndOneSpawner() {

    }

    // @Test
    @DisplayName("Destroy multiple enemies and multiple spawner")
    public void testMultipleEnemyAndMultipleSpawner() {

    }

    /*
    * Floor switches
     */
    // @Test
    @DisplayName("Move boulder onto 1 floor switch")
    public void testOneFloorSwitch() {

    }

    // @Test
    @DisplayName("Activate a floor switch with a bomb")
    public void testBombActivatesFloorSwitch() {

    }

    // @Test
    @DisplayName("Move boulder onto 2 floor switch")
    public void testMultipleFloorSwitches() {

    }

    /*
    * Collect treasure
     */
    // @Test
    @DisplayName("Collect 1 treasure")
    public void testOneTreasure() {

    }

    // @Test
    @DisplayName("Collect multiple treasure")
    public void testMultipleTreasure() {

    }

    /*
    ! Complex Goals
     */
    // TODO: WRITE COMPLEX GOAL TESTS
    @Test
    @DisplayName("Testing a map with 4 conjunction goal")
    public void testFourGoals() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_complexGoalsTest_andAll", "c_complexGoalsTest_andAll");

        assertTrue(getGoals(res).contains(":exit"));
        assertTrue(getGoals(res).contains(":treasure"));
        assertTrue(getGoals(res).contains(":boulders"));
        assertTrue(getGoals(res).contains(":enemies"));

        // kill spider
        res = dmc.tick(Direction.RIGHT);
        assertTrue(getGoals(res).contains(":exit"));
        assertTrue(getGoals(res).contains(":treasure"));
        assertTrue(getGoals(res).contains(":boulders"));
        assertFalse(getGoals(res).contains(":enemies"));

        // move boulder onto switch
        res = dmc.tick(Direction.RIGHT);
        assertTrue(getGoals(res).contains(":exit"));
        assertTrue(getGoals(res).contains(":treasure"));
        assertFalse(getGoals(res).contains(":boulders"));
        assertFalse(getGoals(res).contains(":enemies"));

        // pickup treasure
        res = dmc.tick(Direction.DOWN);
        assertTrue(getGoals(res).contains(":exit"));
        assertFalse(getGoals(res).contains(":treasure"));
        assertFalse(getGoals(res).contains(":boulders"));
        assertFalse(getGoals(res).contains(":enemies"));

        // move to exit
        res = dmc.tick(Direction.DOWN);
        assertEquals("", getGoals(res));
    }
}
