package dungeonmania.Entities.Item.CollectableEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Item.Item;

public class InvisibilityPotionEntity extends Entity implements Item {

    public InvisibilityPotionEntity() {
        super("InvisibilityPotion", "CollectableEntity");
    }

    // When a player picks up an invisibility potion, they may consume it at any time and
    // they immeidately become invisible and can move past all other entities undetected.
    
    // Battles do not occur when a player is under the influence of an invisibility potion.
    // Since mercenaries typically follow the player, their movement becomes the same as 
    // a Zombie when the player is invisible.

    @Override
    public void collectItem(Entity entity) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void buildItem(Entity entity) {
        // TODO Auto-generated method stub
        
    }
    
}
