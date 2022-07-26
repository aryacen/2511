package dungeonmania;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getPlayer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SwampTests {
    @Test
    @DisplayName("Swamp Tile does not slow player down")
    public void testSwampPlayer() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_swampTest_player",
                "c_movementTest_testMovementDown");
        Position initPos = getPlayer(initDungonRes).get().getPosition();
        dmc.tick(Direction.RIGHT);
        DungeonResponse res = dmc.tick(Direction.RIGHT);
        Position finPos = getPlayer(res).get().getPosition();
        // The player should not be slowed down by the swamp tile
        assertEquals(initPos.translateBy(Direction.RIGHT).translateBy(Direction.RIGHT), finPos);
    }
    @Test
    @DisplayName("Swamp Tile slows spider down")
    public void testSwampSpider() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_swampTest_spider", "c_spiderTest_basicMovement");

        // There is a swamp tile directly above the spider
        // Spider reaches swamp tile
        res = dmc.tick(Direction.RIGHT);
        Position Pos1Tick = getEntities(res, "spider").get(0).getPosition();

        // Stuck
        res = dmc.tick(Direction.RIGHT);
        Position Pos2Tick = getEntities(res, "spider").get(0).getPosition();

        // Stuck
        res = dmc.tick(Direction.RIGHT);
        Position Pos3Tick = getEntities(res, "spider").get(0).getPosition();

        // Move off the tile
        res = dmc.tick(Direction.RIGHT);
        Position Pos4Tick = getEntities(res, "spider").get(0).getPosition();

        assertEquals(Pos1Tick, Pos2Tick);
        assertEquals(Pos2Tick, Pos3Tick);
        assertNotEquals(Pos3Tick, Pos4Tick);
    }

    @Test
    @DisplayName("Swamp Tile slows zombie down")
    public void testSwampZombie() {
        // Swamp tile all around the zombie
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swampTest_zombie", "c_spiderTest_basicMovement");
        res = dmc.tick(Direction.RIGHT);
        // Zombie reaches swamp tile
        Position Pos1Tick = getEntities(res, "zombie_toast").get(0).getPosition();

        // Stuck
        res = dmc.tick(Direction.RIGHT);
        Position Pos2Tick = getEntities(res, "zombie_toast").get(0).getPosition();

        // Stuck
        res = dmc.tick(Direction.RIGHT);
        Position Pos3Tick = getEntities(res, "zombie_toast").get(0).getPosition();

        // Move off the tile
        res = dmc.tick(Direction.RIGHT);
        Position Pos4Tick = getEntities(res, "zombie_toast").get(0).getPosition();
        assertEquals(Pos1Tick, Pos2Tick);
        assertEquals(Pos2Tick, Pos3Tick);
        assertNotEquals(Pos3Tick, Pos4Tick);
    }
    // TODO: MAKE TEST FOR WHEN THE OTHER ENTITY MOVEMENTS HAVE BEEN MADE
}
