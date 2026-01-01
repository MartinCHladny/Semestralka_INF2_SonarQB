package game.character.gadget;

import game.character.GameCharacter;
import game.Tile;

public interface Gadget {
    /**
     * metóda na používanie gadgetov
     * @param tile
     * @param user
     */
    void useGadget(Tile tile, GameCharacter user);
}
