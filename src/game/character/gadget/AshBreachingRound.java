package game.character.gadget;

import game.character.GameCharacter;
import game.Tile;

public class AshBreachingRound implements PrimaryGadget {
    /**
     * Gadget je vyhradený pre Operátora Ash a jeho úlohou je z ďiaľky zničiť zvolený cover
     * Jednoducho sa pošle správa destroyCover a vypíše sa dianie akcie
     * @param tile
     * @param user
     */
    @Override
    public void useGadget(Tile tile, GameCharacter user) {
        if (!tile.isAccessableTile()) {
            tile.destroyCover();
            System.out.println(String.format("%s destroyed cover using her gadget breaching round", user.getName()));
            user.getR6S().setCombatLogMessage(String.format("%s destroyed cover using her gadget breaching round", user.getName()), "blue");
            return;
        }

        System.out.println("Breaching round is usable only on destructible surface");
        user.getR6S().setCombatLogMessage("Breaching round is usable only on destructible surface", "black");

    }
}
