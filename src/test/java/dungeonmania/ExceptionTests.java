package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;

/*
Tests associated with any exceptions
 */
public class ExceptionTests {
    // TO DO: FINISH THIS
    @Test
    public void testUnknownDungeon() {
        DungeonManiaController dmc = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () -> dmc.newGame("unknownDungeon", "simple"));
    }

    @Test
    public void testUnknownConfig() {
        DungeonManiaController dmc = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () -> dmc.newGame("zombies", "unknownConfig"));
    }

    @Test
    public void testUsingStaticItem() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("zombies", "simple");
        EntityResponse tempEntity = initDungeonRes.getEntities().stream().filter(e -> e.getType().equals("sword"))
                .findFirst().get();

        // PICK UP SWORD
        dmc.tick(Direction.DOWN);
        assertThrows(IllegalArgumentException.class, () -> dmc.tick(tempEntity.getId()));
    }

    @Test
    public void testItemNotInInventory() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("advanced", "simple");
        EntityResponse tempEntity = initDungeonRes.getEntities().stream()
                .filter(e -> e.getType().equals("invisibility_potion"))
                .findFirst().get();
        assertThrows(InvalidActionException.class, () -> dmc.tick(tempEntity.getId()));
    }

    @Test
    public void testInvalidBuildable() {
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("advanced", "simple");
        assertThrows(IllegalArgumentException.class, () -> dmc.build("sword"));
    }

    @Test
    public void testInsufficientMaterial() {
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("build_bow", "simple");
        assertThrows(InvalidActionException.class, () -> dmc.build("bow"));
    }
}
