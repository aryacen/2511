package dungeonmania.Entities.MovingEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Item.Item;
import dungeonmania.Entities.StaticEntities.StaticEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.EntityConstants;
import dungeonmania.util.Position;

import java.util.ArrayList;

public class MercenaryEntity extends MovingEntity {
    public MercenaryEntity(String id, String type, Position position) {
        super(id, type, position);
        this.hp = EntityConstants.mercenary_health;
        this.attack = EntityConstants.mercenary_attack;
        this.isInteractable = true;
        // TODO: GIVE MERCENARY A MOVEMENT FUNCTION
    }

    @Override
    public void move(Direction direction, ArrayList<Item> items, ArrayList<StaticEntity> staticEntities, ArrayList<MovingEntity> movingEntities) {

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
