package dungeonmania;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/*
This test is for the usage of any item
 */
public class ItemTests {
    /*
    Tests with inventory
     */
    @Test
    @DisplayName("Test player can pick up item")
    public void testPlayerPickUpItem() {

    }
    /*
    Movement with Doors and Keys
     */
    @Test
    @DisplayName("Test player cannot walk through a door without key")
    public void testPlayerCannotWalkThroughClosedDoor () {

    }

    @Test
    @DisplayName("Test player cannot walk through a door if keys do not match")
    public void testPlayerCannotWalkThroughClosedDoorMismatchKeys() {

    }

    /*
    Tests with Crafting System
     */

    @Test
    @DisplayName("Test player cannot craft shield or bow without resources")
    public void testPlayerCannotCraftWithoutMaterials() {

    }

    @Test
    @DisplayName("Test player can craft bow")
    public void testPlayerCanCraftBow() {

    }

    @Test
    @DisplayName("Test player can craft shield (2 wood + 1 treasure)")
    public void testPlayerCanCraftShieldWithTreasure() {

    }

    @Test
    @DisplayName("Test player can craft shield (2 wood + 1 key)")
    public void testPlayerCanCraftShieldWithKey() {

    }
}
