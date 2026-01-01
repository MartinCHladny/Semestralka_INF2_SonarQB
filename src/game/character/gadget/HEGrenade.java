package game.character.gadget;

import game.character.GameCharacter;
import game.R6S;
import game.Tile;

public class HEGrenade implements SecondaryGadget {
    /**
     * kalsický granát ktorý v jeho dosahu zasiahne ciele
     * dosah granátu je 1 pole do každej strany od miesta dopadu
     * @param tile
     * @param user
     * @param r6S
     */
    @Override
    public void useGadget(Tile tile, GameCharacter user, R6S r6S) {

        var indexI = r6S.getIndexI(tile);
        var indexJ = r6S.getIndexJ(tile);

        for (int k = indexI - 1; k <= indexI + 1; k++) {
            for (int l = indexJ - 1; l <= indexJ + 1; l++) {
                if (k >= 0 && l >= 0 && k < r6S.getMapSyze() && l < r6S.getMapSyze()) {
                    if (r6S.getPolicko(k, l).hasCharacter()) {
                        var targetHitByNade = r6S.getPolicko(k, l).getCharacterOnTile();
                        targetHitByNade.adjustHP(40, user);
                        System.out.println(String.format("%s hit %s with a HE Grenade for 40 damage", user.getName(), targetHitByNade.getName()));
                        user.getR6S().setCombatLogMessage(String.format("%s hit %s with a HE Grenade for 40 damage", user.getName(), targetHitByNade.getName()), "red");
                    }
                }
            }
        }
    }

    @Override
    public void useGadget(Tile tile, GameCharacter user) {

    }
}
