package dungeonmania.Entities.StaticEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.util.Position;

public class ExitEntity extends StaticEntity {
    public ExitEntity(String id, String type, Position position) {
        super(id, type, position);
    }

    @Override
    public boolean canPass(String type) {
        return true;
    }

    // If the Player goes through it, the puzzle is complete.

    @Override
    public boolean isSpawner() {
        return false;
    }
}
