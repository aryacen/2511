package dungeonmania.Entities.StaticEntities;

import dungeonmania.util.Position;

public class ExitEntity extends StaticEntity {
    public ExitEntity(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
    }

    @Override
    public boolean canPass(String type) {
        return true;
    }


    // If the Player goes through it, the puzzle is complete.
}
