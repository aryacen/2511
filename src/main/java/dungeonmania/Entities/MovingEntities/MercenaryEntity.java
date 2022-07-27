package dungeonmania.Entities.MovingEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Item.Item;
import dungeonmania.Entities.StaticEntities.StaticEntity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.ArrayList;

import static dungeonmania.util.EntityConstants.*;

public class MercenaryEntity extends MovingEntity {
    private Integer bribeAmount;
    private Integer bribeRadius;
    public MercenaryEntity(String id, String type, Position position) {
        super(id, type, position);
        this.hp = getInstance("mercenary_health");
        this.attack = getInstance("mercenary_attack");
        this.bribeAmount = getInstance("bribe_amount").intValue();
        this.bribeRadius = getInstance("bribe_radius").intValue();
        this.isInteractable = true;
        // TODO: GIVE MERCENARY A MOVEMENT FUNCTION
    }

    @Override
    public void move(Direction direction,
                     ArrayList<Item> items,
                     ArrayList<StaticEntity> staticEntities,
                     ArrayList<MovingEntity> movingEntities) {

    }

    public void interact(Entity merc, PlayerEntity player, ArrayList<StaticEntity> staticEntities) throws InvalidActionException {
        Position playerPos = player.getPosition();
        Position mercPos = merc.getPosition();
        // for Milestone 3 add check for sceptre here before attempting to bribe

        double distanceBetween = Position.calculateDistanceBetween(playerPos, mercPos);
        if (distanceBetween > bribeRadius) {
            throw new InvalidActionException("Mercenary out of range");
        } else if (bribeAmount > player.getCountOfItems("treasure")) {
            throw new InvalidActionException("Not enough treasure to bribe");
        }
        // after bribed, merc becomes uninteractable
        this.isInteractable = false;
        // change mercenary movement to post-bribe

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
