package dungeonmania.Entities.Item.CollectableEntities;

import dungeonmania.Entities.Item.Item;
import dungeonmania.util.Position;

public class InvincibilityPotion extends Item {
    public InvincibilityPotion(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, false);
    }

    // Any battles that occur when the Player has the effects of the potion end immediately
    // after the first round, with the Player immediately winning.
    // Because of this, Mercenaries and Zombies will run away from the Player when they are invinciple.
    // Movement of spiders and bribed mercenaries remains unaffected.
    // The effects of the potion only last for a limited time.
}
