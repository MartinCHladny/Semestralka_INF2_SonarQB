package game.character.gadget;

import game.character.GameCharacter;
import game.R6S;
import game.Tile;

public interface SecondaryGadget extends Gadget {
    /**
     * odvetvie gadgetov
     * @param tile
     * @param user
     * @param r6S
     */
    void useGadget(Tile tile, GameCharacter user, R6S r6S);
}

