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
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class NewGameTests {

    @Test
    @DisplayName("Test if the newGame function properly constructs a DungeonResponse")
    public void testNewGameBasic() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("zombies", "simple");
        Position position1 = new Position(1, 1);
        Position position2 = new Position(1, 2);
        Position position3 = new Position(2, 1);
        Position position4 = new Position(8, 8);
        EntityResponse entity1 = new EntityResponse(initDungeonRes.getEntities().get(0).getId(), "player", position1,
                false);
        EntityResponse entity2 = new EntityResponse(initDungeonRes.getEntities().get(1).getId(), "sword", position2,
                false);
        EntityResponse entity3 = new EntityResponse(initDungeonRes.getEntities().get(2).getId(), "zombie_toast_spawner",
                position3, true);
        EntityResponse entity4 = new EntityResponse(initDungeonRes.getEntities().get(3).getId(), "exit", position4,
                false);
        List<EntityResponse> entities = new ArrayList<EntityResponse>();
        List<ItemResponse> inventory = new ArrayList<ItemResponse>();
        List<BattleResponse> battles = new ArrayList<BattleResponse>();
        List<String> buildables = new ArrayList<String>();
        entities.add(entity1);
        entities.add(entity2);
        entities.add(entity3);
        entities.add(entity4);
        String goal = ":exit";
        DungeonResponse expectedOutput = new DungeonResponse("zombies1", "zombies", entities, inventory, battles,
                buildables, goal);
        assertEquals(expectedOutput.getDungeonId(), initDungeonRes.getDungeonId());
        assertEquals(expectedOutput.getDungeonName(), initDungeonRes.getDungeonName());
        assertTrue(expectedOutput.getEntities().equals(initDungeonRes.getEntities()));
        assertTrue(expectedOutput.getInventory().equals(initDungeonRes.getInventory()));
        assertTrue(expectedOutput.getBattles().equals(initDungeonRes.getBattles()));
        assertTrue(expectedOutput.getBuildables().equals(initDungeonRes.getBuildables()));
        assertEquals(expectedOutput.getGoals(), initDungeonRes.getGoals());
    }

    @Test
    @DisplayName("Test if the newGame function properly constructs the Goal String")
    public void testNewGameGoals() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes2 = dmc.newGame("zombies", "simple");
        String expectedGoal2 = ":exit";
        assertEquals(expectedGoal2, initDungeonRes2.getGoals());

        DungeonManiaController dmc2 = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc2.newGame("advanced", "simple");
        String expectedGoal = ":enemies AND :treasure";
        assertEquals(expectedGoal, initDungeonRes.getGoals());

        DungeonManiaController dmc3 = new DungeonManiaController();
        DungeonResponse initDungeonRes1 = dmc3.newGame("exit_goal_order", "simple");
        String expectedGoal1 = ":boulders AND (:exit AND :treasure)";
        assertEquals(expectedGoal1, initDungeonRes1.getGoals());

        DungeonManiaController dmc4 = new DungeonManiaController();
        DungeonResponse initDungeonRes3 = dmc4.newGame("d_complexGoalsTest_andAll", "simple");
        String expectedGoal3 = ":exit AND (:treasure AND (:boulders AND :enemies))";
        assertEquals(expectedGoal3, initDungeonRes3.getGoals());
    }

    @Test
    @DisplayName("Test if getDungeonResponseModel function works")
    public void testGetDungeonResponseModel() {
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("zombies", "simple");

        DungeonResponse initDungeonRes = dmc.getDungeonResponseModel();

        Position position1 = new Position(1, 1);
        Position position2 = new Position(1, 2);
        Position position3 = new Position(2, 1);
        Position position4 = new Position(8, 8);
        EntityResponse entity1 = new EntityResponse(initDungeonRes.getEntities().get(0).getId(), "player", position1,
                false);
        EntityResponse entity2 = new EntityResponse(initDungeonRes.getEntities().get(1).getId(), "sword", position2,
                false);
        EntityResponse entity3 = new EntityResponse(initDungeonRes.getEntities().get(2).getId(), "zombie_toast_spawner",
                position3, true);
        EntityResponse entity4 = new EntityResponse(initDungeonRes.getEntities().get(3).getId(), "exit", position4,
                false);
        List<EntityResponse> entities = new ArrayList<EntityResponse>();
        List<ItemResponse> inventory = new ArrayList<ItemResponse>();
        List<BattleResponse> battles = new ArrayList<BattleResponse>();
        List<String> buildables = new ArrayList<String>();
        entities.add(entity1);
        entities.add(entity2);
        entities.add(entity3);
        entities.add(entity4);
        String goal = ":exit";
        DungeonResponse expectedOutput = new DungeonResponse("zombies1", "zombies", entities, inventory, battles,
                buildables, goal);
        assertEquals(expectedOutput.getDungeonId(), initDungeonRes.getDungeonId());
        assertEquals(expectedOutput.getDungeonName(), initDungeonRes.getDungeonName());
        assertTrue(expectedOutput.getEntities().equals(initDungeonRes.getEntities()));
        assertTrue(expectedOutput.getInventory().equals(initDungeonRes.getInventory()));
        assertTrue(expectedOutput.getBattles().equals(initDungeonRes.getBattles()));
        assertTrue(expectedOutput.getBuildables().equals(initDungeonRes.getBuildables()));
        assertEquals(expectedOutput.getGoals(), initDungeonRes.getGoals());
    }
}