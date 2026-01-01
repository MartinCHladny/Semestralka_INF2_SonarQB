package game.character.gadget;

import game.character.GameCharacter;
import game.character.Terrorist;
import game.Tile;

public class SledgeHammer implements PrimaryGadget {
    /**
     * Gadget je v podstate kladivo, ktoré sa dá použiť na priľahlé políčko a ak sa na danom políčku nachádza cover,
     * tak ho zničí inak ak sa na ňom nachádza nepriateľská postava zabije ju.
     * @param tile
     * @param user
     */
    @Override
    public void useGadget(Tile tile, GameCharacter user) {
        if (!tile.isAccessableTile()) {
            if (user.isAdjacent(tile)) {
                tile.destroyCover();
                System.out.println(String.format("%s destroyed cover using a Sladge Hammer", user.getName()));
                user.getR6S().setCombatLogMessage(String.format("%s destroyed cover using a Sladge Hammer", user.getName()), "blue");
                return;
            } else {
                System.out.println("Sladge hammer nie je na bluetooth");
                user.getR6S().setCombatLogMessage("Sladge hammer nie je na bluetooth", "black");
                return;
            }
        }

        if (tile.hasCharacter()) {
            var target = tile.getCharacterOnTile();

            if (target instanceof Terrorist) {
                if (user.isAdjacent(tile)) {
                    target.killCharacter(user);
                    return;
                } else {
                    System.out.println("Sladge hammer nie je na bluetooth");
                    user.getR6S().setCombatLogMessage("Sladge hammer nie je na bluetooth", "black");
                    return;
                }
            }
        }

        System.out.println("Sledge Hammer is usable only on cover or enemy characters");
        user.getR6S().setCombatLogMessage("Sledge Hammer is usable only on cover or enemy characters", "black");
    }
}
