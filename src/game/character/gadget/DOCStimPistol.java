package game.character.gadget;

import game.character.GameCharacter;
import game.character.Operator;
import game.Tile;

public class DOCStimPistol implements PrimaryGadget {
    /**
     * Stim pistol je gadget pre operátora DOC skratka pre doktora a má za úlohu liečiť kohokoľvek zasiahne
     * Stim lieči 100 hp a vypíše sa číslo koľko hp vyliečila. Samozrejme je to obmedzené iba pre spojenecké charakteri
     * alebo inak povedané operátorov
     * @param tile
     * @param user
     */
    @Override
    public void useGadget(Tile tile, GameCharacter user) {
        if (tile.hasCharacter()) {
            if (tile.getCharacterOnTile() instanceof Operator) {
                var target = tile.getCharacterOnTile();
                if (target.getHP() < target.getMaxHP()) {
                    var healed = 100;

                    if (target.getHP() + 100 > target.getMaxHP()) {
                        healed =  target.getMaxHP() - target.getHP();
                    }

                    target.adjustHP(-100, user);
                    System.out.println(String.format("%s healed %s using stim pistol for %d healts points", user.getName(), target.getName(), healed));
                    user.getR6S().setCombatLogMessage(String.format("%s healed %s using stim pistol for %d healts points", user.getName(), target.getName(), healed), "blue");
                    return;
                } else {
                    System.out.println("Operator is already at full hp");
                    user.getR6S().setCombatLogMessage("Operator is already at full hp", "black");
                    return;
                }
            }
        }

        System.out.println("Stim pistol is usable only on living allied targets");
        user.getR6S().setCombatLogMessage("Stim pistol is usable only on living allied targets", "black");

    }
}
