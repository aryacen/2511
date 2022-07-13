package dungeonmania.Entities.Item;

/**
 * Figures out what can be crafted and interacts between inventory
 */
public class CraftingSystem {
    public CraftingSystem() {

    }

    /**
     * Check that an item can be crafted
     * @return
     */
    public boolean canCraft() {
        return false;
    }

    /**
     * Craft a specific item
     * Will remove the item from the inventory and add the crafted item in
     * @return
     */
    public void craft() {
        Item itemCrafted;
        // Remove item from inventory
        // Add item crafted ot inventory
    }
}
