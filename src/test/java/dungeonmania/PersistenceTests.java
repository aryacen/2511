package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static dungeonmania.TestUtils.getEntities;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersistenceTests {
    //    @Test
    @DisplayName("Basic test for persistence for position")
    public void testPersistencePosition() throws IOException {
        /*
         Load into a game
         Save it
         creates a new game
         Load the old game
         Check that all the entities are in the same position by equating the dungeon response
        */
        DungeonManiaController dmc = new DungeonManiaController();
        // Create a test of one of everything entity possible that can be placed
        DungeonResponse InitRes = dmc.newGame("d_persistenceTest_positon", "c_default");
        dmc.saveGame("initalGame");
        DungeonResponse res = dmc.newGame("d_movementTest_testMovementDown",
                "c_movementTest_testMovementDown");
        res = dmc.loadGame("initialGame");
        assertEquals(res, InitRes);
    }

    @DisplayName("Persistence: inventory is retained")
    public void testPersistenceInventory() throws InvalidActionException, IOException {
        // Game 1 creates a bow
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        dmc.newGame("d_itemTest_testCrafting",
                "c_simple");
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.DOWN);
        dmc.tick(Direction.DOWN);
        dmc.tick(Direction.DOWN);
        DungeonResponse game1Initial = dmc.build("bow");
        dmc.saveGame("game1");

        // Game 2 creates a shield
        dmc.newGame("d_itemTest_testCrafting", "c_simple");
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        DungeonResponse game2Initial = dmc.build("shield");
        dmc.saveGame("game2");

        // Load game 1 and check inventory are equal
        DungeonResponse game1Final = dmc.loadGame("game1");
        assertEquals(game1Final.getInventory(), game1Initial.getInventory());

        DungeonResponse game2Final = dmc.loadGame("game2");
        assertEquals(game2Final.getInventory(), game2Initial.getInventory());
    }

    @DisplayName("Persistence: spider retains movement pattern")
    public void testPersistenceSpiderMovement() throws IOException {
        // Move the spider and then assert that the position of the spider is following path even after game saves
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_Boulder", "c_spiderTest_basicMovement");
        Position pos = getEntities(res, "spider").get(0).getPosition();

        List<Position> movementTrajectory = new ArrayList<Position>();
        int x = pos.getX();
        int y = pos.getY();
        // 1
        movementTrajectory.add(new Position(x, y - 1));
        // 2
        movementTrajectory.add(new Position(x + 1, y - 1));
        // 3
        movementTrajectory.add(new Position(x + 1, y));
        // 4
        movementTrajectory.add(new Position(x + 1, y + 1));
        // 5
        movementTrajectory.add(new Position(x + 1, y));
        // 6
        movementTrajectory.add(new Position(x + 1, y - 1));
        // 7
        movementTrajectory.add(new Position(x, y - 1));
        // 8
        movementTrajectory.add(new Position(x - 1, y - 1));
        // 9
        movementTrajectory.add(new Position(x - 1, y));
        // 10
        movementTrajectory.add(new Position(x - 1, y + 1));
        // 11
        movementTrajectory.add(new Position(x - 1, y));
        // 12
        movementTrajectory.add(new Position(x - 1, y - 1));
        // Assert the spider follows expected path

        for (int i = 0; i < 5; i++) {
            Position position = movementTrajectory.remove(0);
            pos = getEntities(res, "spider").get(0).getPosition();
            assertEquals(position, getEntities(res, "spider").get(0).getPosition());
        }

        dmc.saveGame("spider1");
        dmc.newGame("d_spiderTest_Boulder", "c_spiderTest_basicMovement");
        dmc.loadGame("spider1");

        for (Position p : movementTrajectory) {
            res = dmc.tick(Direction.UP);
            // For debugging purposes
            pos = getEntities(res, "spider").get(0).getPosition();
            assertEquals(p, getEntities(res, "spider").get(0).getPosition());
        }
    }

    @DisplayName("Persistence: Mercenary Status Retained")
    public void testPersistenceMercenaryBribedStatus() {

    }

    @DisplayName("Persistence: Invincibility Potion Status Retained")
    public void testPersistenceInvincibility() {

    }

    @DisplayName("Persistence: Invisibility Potion Status Retained")
    public void testPersistenceInvisibility() {

    }

    @DisplayName("Persistence: Spider + Zombie Toast Spawn Rate Retained")
    public void testPersistenceSpawner() {
    }

    @DisplayName("Persistence: Goals Retained")
    public void testPersistenceGoals() {

    }
}
