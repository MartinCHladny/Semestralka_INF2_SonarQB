package game.character.gadget.ammo;

import game.character.GameCharacter;

public interface Ammunition {
    /**
     * Vráti čislo poškodenia podľa typu munície a cieľa
     * @param target
     * @return
     */
    int calculateDamage(GameCharacter target);
}
