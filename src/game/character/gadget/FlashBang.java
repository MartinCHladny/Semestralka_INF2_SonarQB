package game.character.gadget;

import game.character.GameCharacter;
import game.R6S;
import game.Tile;

public class FlashBang implements SecondaryGadget {
    /**
     * Gadget má za úlohu oslepiť nepriateľa na jedno kolo a zabrániť mu tak v konaní
     * Dosah flashbangu sú 2 políčka všetkými smermi od miesta dopadu
     * @param tile
     * @param user
     * @param r6S
     */
    @Override
    public void useGadget(Tile tile, GameCharacter user, R6S r6S) {

        var indexI = r6S.getIndexI(tile);
        var indexJ = r6S.getIndexJ(tile);

        for (int k = indexI - 2; k <= indexI + 2; k++) {
            for (int l = indexJ - 2; l <= indexJ + 2; l++) {
                if (k >= 0 && l >= 0 && k < r6S.getMapSyze() && l < r6S.getMapSyze()) {
                    if (r6S.getPolicko(k, l).hasCharacter()) {
                        var targetHitByNade = r6S.getPolicko(k, l).getCharacterOnTile();
                        targetHitByNade.setBlinded(true);
                        System.out.println(String.format("%s blinded %s with a Flash Bang", user.getName(), targetHitByNade.getName()));
                        user.getR6S().setCombatLogMessage(String.format("%s blinded %s with a Flash Bang", user.getName(), targetHitByNade.getName()), "red");
                    }
                }
            }
        }

    }

    @Override
    public void useGadget(Tile tile, GameCharacter user) {

    }
}
