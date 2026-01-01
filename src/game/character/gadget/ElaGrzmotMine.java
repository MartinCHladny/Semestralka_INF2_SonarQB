package game.character.gadget;

import game.character.GameCharacter;
import game.character.Terrorist;
import game.Tile;

public class ElaGrzmotMine implements PrimaryGadget {
    /**
     * Tento gadget má za úlohu permanentne dezorientovať cieľ a zhoršiť mu tak jeho presnosť strelby
     * za predpokladu že target je terrorista
     * @param tile
     * @param user
     */

    @Override
    public void useGadget(Tile tile, GameCharacter user) {
        if (tile.hasCharacter()) {
            var target = tile.getCharacterOnTile();

            if (target instanceof Terrorist) {

                target.setConcussed(true);
                System.out.println(String.format("%s successfully used Grzmot mine on %s", user.getName(), target.getName()));
                user.getR6S().setCombatLogMessage(String.format("%s successfully used Grzmot mine on %s", user.getName(), target.getName()), "blue");
                return;
            }
        }

        System.out.println("Grzmot mine is usable only on enemy targets");
        user.getR6S().setCombatLogMessage("Grzmot mine is usable only on enemy targets", "black");
    }
}

