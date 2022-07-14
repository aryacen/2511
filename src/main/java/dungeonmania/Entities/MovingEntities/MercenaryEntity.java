package dungeonmania.Entities.MovingEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.util.Position;

public class MercenaryEntity extends Entity {
    public MercenaryEntity(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
    }

    // part of the dungeon.

    // They constantly mvoe towards the Player, stopping only if they
    // cannot move any closer (they are able to move around walls).

    // Mercenaries are limited by the same movement constraints as the Player.

    // All mercenaries are considered hostile, unless the Player can bribe them
    // with a certain amount of gold; in which case they become allies.

    // Mercenaries must be within a certain radius of the Player to be bribed,
    // which is formed by the diagonally and cardinally adjacent cells in
    // a "square" fashion, akin to the blast radius for bombs.

    // As an ally, once it reaches the Player it simply follows the Player around,
    // occupying the square the player was previously in.
    
}
