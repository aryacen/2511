package dungeonmania.Entities.Item.CollectableEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Item.Item;

public class InvincibilityPotion extends Entity implements Item {

    public InvincibilityPotion() {
        super("InvincibilityPotion", "CollectableEntity");
    }

    // When a Player picks up an Invincibility potion, they may consume it at any time.
    // Any battles that occur when the Player has the effects of the potion end immediately
    // after the first round, with the Player immediately winning.
    // Because of this, Mercenaries and Zombies will run away from the Player when they are invinciple.
    // Movement of spiders and bribed mercenaries remains unaffected.
    // The effects of the potion only last for a limited time.

    @Override
    public void collectItem(Entity entity) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void buildItem(Entity entity) {
        // TODO Auto-generated method stub
        
    }
    
}
