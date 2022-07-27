package dungeonmania;

import dungeonmania.response.models.DungeonResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PersistenceTests {
//    @Test
    @DisplayName("Basic test for persistence for position")
    public void testPersistencePosition() {
        /*
         Load into a game
         Save it
         creates a new game
         Load the old game
         Check that all the entities are in the same position by equating the dungeon response
        */
        DungeonManiaController dmc = new DungeonManiaController();
        // TODO:
        DungeonResponse res = dmc.newGame("d_persistenceTest_position", "");
    }

    @DisplayName("Persistence: spider retains movement pattern")
    public void testPersistenceSpiderMovement() {

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
}
