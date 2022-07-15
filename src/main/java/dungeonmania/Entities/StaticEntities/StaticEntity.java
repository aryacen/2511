package dungeonmania.Entities.StaticEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.util.Position;

public abstract class StaticEntity extends Entity {
    public StaticEntity(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
    }

    // Tells the movement interface whether the StaticEntity can be walked pass or not
    // by a moving entity
    // E.g. if the player checks if a boulder is passable, then this returns false
    // however if a spider
    // TODO: MAKE THIS FUNCTION WORK FOR ALL THE STATIC ENTITIES
    public abstract boolean canPass(String type);
}
