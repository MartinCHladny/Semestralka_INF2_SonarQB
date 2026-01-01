package game.character.gadget.ammo;

import game.character.GameCharacter;

public class Incendiary implements Ammunition {
    /**
     * Ak je target Armored, metóda vráti 10 ak nie vráti 50
     * @param target
     * @return
     */
    @Override
    public int calculateDamage(GameCharacter target) {
        if (target.isArmored()) {
            return 10;
        } else {
            return 50;
        }
    }
}
