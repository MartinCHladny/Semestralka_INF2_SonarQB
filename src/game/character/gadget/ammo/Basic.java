package game.character.gadget.ammo;

import game.character.GameCharacter;

public class Basic implements Ammunition {
    /**
     * Ak je target Armored, metóda vráti 20 ak nie vráti 30
     * @param target
     * @return
     */
    @Override
    public int calculateDamage(GameCharacter target) {
        if (target.isArmored()) {
            return 20;
        } else {
            return 30;
        }
    }
}
