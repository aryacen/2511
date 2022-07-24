package dungeonmania;

import dungeonmania.Entities.Entity;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static dungeonmania.TestUtils.getEntities;
import static org.junit.jupiter.api.Assertions.assertEquals;

/*
This test is for enemies of the player and how they interact
 */
public class EnemyTest {
    /*
    Spiders
    Most of the circular path has been tested by the example test so they are not required
     */
    @Test
    @DisplayName("Spider will switch direction when reaching with a boulder")
    public void testSpiderBoulderSwitchDirection() {
        /**
         * This is example given in the README
         */

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
        movementTrajectory.add(new Position(x , y - 1));
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
        for (Position p: movementTrajectory) {
            res = dmc.tick(Direction.UP);
            // For debugging purposes
            pos = getEntities(res, "spider").get(0).getPosition();
            assertEquals(p, getEntities(res, "spider").get(0).getPosition());
        }
    }

    @Test
    @DisplayName("Spider will switch direction when reaching with a boulder (this case tests that it flips even on first step")
    public void testSpiderFlipsFirstStep() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_BoulderInitial", "c_spiderTest_basicMovement");
        Position pos = getEntities(res, "spider").get(0).getPosition();

        List<Position> movementTrajectory = new ArrayList<Position>();
        int x = pos.getX();
        int y = pos.getY();
        // 1
            movementTrajectory.add(new Position(x, y + 1));
        // 2
            movementTrajectory.add(new Position(x - 1, y + 1));
        // 3
            movementTrajectory.add(new Position(x - 1, y));
        // 4
            movementTrajectory.add(new Position(x - 1, y - 1));
        // 5
            movementTrajectory.add(new Position(x - 1, y));
        // Assert the spider follows expected path
            for (Position p: movementTrajectory) {
            res = dmc.tick(Direction.UP);
            // For debugging purposes
            pos = getEntities(res, "spider").get(0).getPosition();
            assertEquals(p, getEntities(res, "spider").get(0).getPosition());
        }
    }

    @Test
    @DisplayName("Spider will not move if it is enclosed by two boulders")
    public void testSpiderStopByTwoBoulders() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_stuckBetweenBoulder", "c_spiderTest_basicMovement");
        Position posInitial = getEntities(res, "spider").get(0).getPosition();
        res = dmc.tick(Direction.UP);
        Position pos = getEntities(res, "spider").get(0).getPosition();
        assertEquals(pos, posInitial);
    }


    /*
    Mercenary
     */
//    @Test
    @DisplayName("Test Mercenary moves towards Player")
    public void testMercenaryMovementPreBribe() {

    }

//    @Test
    @DisplayName("Test Mercenary can be bribed and will follow player")
    public void testMercenaryMovementPostBribe() {

    }

    /*
    Zombie Toast
     */

    @Test
    @DisplayName("Test Zombie Toast movement (can only move 1 square)")
    public void testZombieToastMovement() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_enemyTest_zombieMovementBasic",
                "c_movementTest_testCardinalMovement");
        DungeonResponse actualDungonRes = dmc.tick(Direction.DOWN);
        List<EntityResponse> entityList = getEntities(actualDungonRes, "zombie_toast");
        assert(entityList.size() == 1);
        EntityResponse zombie = entityList.get(0);
        ArrayList<Position> possiblePositions = new ArrayList<>(Arrays.asList(new Position(51, 50), new Position(50, 51), new Position(49, 50), new Position(50, 49)));
        assert(possiblePositions.contains(zombie.getPosition()));
    }

    @Test
    @DisplayName("Test Zombie Toast movement (no movement if surrounded by boulders)")
    public void testZombieToastMovementSurroundedByBoulders() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_enemyTest_zombieMovementBoulder",
                "c_movementTest_testCardinalMovement");
        List<EntityResponse> entityList = getEntities(initDungonRes, "zombie_toast");
        assert(entityList.size() == 1);
        EntityResponse zombieInit = entityList.get(0);

        DungeonResponse actualDungonRes = dmc.tick(Direction.DOWN);
        entityList = getEntities(actualDungonRes, "zombie_toast");
        assert(entityList.size() == 1);

        EntityResponse zombie = entityList.get(0);
        assert(zombie.getPosition().equals(zombieInit.getPosition()));
    }

    @Test
    @DisplayName("Test Zombie Toast movement (no movement if surrounded by walls)")
    public void testZombieToastMovementSurroundedByWalls() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_enemyTest_zombieMovementWall",
                "c_movementTest_testCardinalMovement");
        List<EntityResponse> entityList = getEntities(initDungonRes, "zombie_toast");
        assert(entityList.size() == 1);
        EntityResponse zombieInit = entityList.get(0);

        DungeonResponse actualDungonRes = dmc.tick(Direction.DOWN);
        entityList = getEntities(actualDungonRes, "zombie_toast");
        assert(entityList.size() == 1);

        EntityResponse zombie = entityList.get(0);
        assert(zombie.getPosition().equals(zombieInit.getPosition()));

    }

    @Test
    @DisplayName("Test Zombie Toast movement (can be teleported)")
    public void testZombieToastMovementTeleport() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_enemyTest_zombieMovementPortal",
                "c_movementTest_testCardinalMovement");
        List<EntityResponse> entityList = getEntities(initDungonRes, "zombie_toast");
        assert(entityList.size() == 1);
        EntityResponse zombieInit = entityList.get(0);

        DungeonResponse actualDungonRes = dmc.tick(Direction.DOWN);
        entityList = getEntities(actualDungonRes, "zombie_toast");
        assert(entityList.size() == 1);

        EntityResponse zombie = entityList.get(0);
        /*
         Assert that the zombie has teleported and hasn't just walked onto one of the portals
         Don't know which one he is teleported via but the teleporter functionality is already tested with the player
        */
        ArrayList<Position> nonValidPosition = new ArrayList<>(Arrays.asList(
            zombie.getPosition(),
            zombie.getPosition().translateBy(Direction.UP),
            zombie.getPosition().translateBy(Direction.DOWN),
            zombie.getPosition().translateBy(Direction.RIGHT),
            zombie.getPosition().translateBy(Direction.LEFT)
        ));
        assert(nonValidPosition.contains(zombie.getPosition()));
    }

}
