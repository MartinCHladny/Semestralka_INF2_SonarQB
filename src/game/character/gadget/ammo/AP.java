package game.character.gadget.ammo;

import game.character.GameCharacter;

public class AP implements Ammunition {
    /**
     * Ak je target Armored, metóda vráti 50 ak nie vráti 20
     * @param target
     * @return
     */
    @Override
    public int calculateDamage(GameCharacter target) {
        if (target.isArmored()) {
            return 50;
        } else {
            return 20;
        }
    }
}
