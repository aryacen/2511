package dungeonmania;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

    }

    /*
    Movement with walls
     */
    @Test
    @DisplayName("Test player is blocked by wall")
    public void testWallBlock() {

    }
    /*
    Movement with Boulders
     */
    @Test
    @DisplayName("Test player can move boulder")
    public void testBoulderCanBePushed() {

    }

    @Test
    @DisplayName("Test player cannot move boulder into wall")
    public void testBoulderCannotBePushedIntoWalls() {

    }

    @Test
    @DisplayName("Test player cannot move two boulders")
    public void testCannotPushTwoBoulders() {

    }
    /*
    Movement with Teleporters
     */
    @Test
    @DisplayName("Test player can be teleported via a teleporter")
    public void testTeleportFunctions() {

    }
    @Test
    @DisplayName("Test player can't be teleported into a wall")
    public void testTeleportIntoWall() {

    }
    @Test
    @DisplayName("Test player can't be teleported into a spot with no movements")
    public void testTeleportIntoPositonWithNoValidMovement() {

    }

    @Test
    @DisplayName("Test teleporter correctly teleports to the correct corresponding teleporter")
    public void testTeleportCorrectColouredTeleporter() {

    }

    /*
    Interaction with Zombie Toaster Spawner
     */
    @Test
    @DisplayName("Test player can destroy zombie toast spawner")
    public void testCanDestroyZombieToastSpawner() {

    }

    @Test
    @DisplayName("Test player cannot destroy zombie toast spawner out of range")
    public void testCannotDestroyOutOfRangeZombieToastSpawner() {

    }

    @Test
    @DisplayName("Test player cannot destroy zombie toast spawner without weapon")
    public void testCannotDestroyZombieToastSpawnerWithoutWeapon() {
    }
}
