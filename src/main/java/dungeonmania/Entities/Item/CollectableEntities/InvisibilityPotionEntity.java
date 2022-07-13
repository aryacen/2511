package dungeonmania.Entities.Item.CollectableEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Item.Item;

public class InvisibilityPotionEntity extends Item {
    public InvisibilityPotionEntity(String id) {
        super(id, "invisibility_potion");
    }

    // When a player picks up an invisibility potion, they may consume it at any time and
    // they immeidately become invisible and can move past all other entities undetected.
    
    // Battles do not occur when a player is under the influence of an invisibility potion.
    // Since mercenaries typically follow the player, their movement becomes the same as 
    // a Zombie when the player is invisible.

}
