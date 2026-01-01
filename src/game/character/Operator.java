package game.character;

import game.R6S;


public abstract class Operator extends GameCharacter {

    /**
     * Operátor, ktorý slúži ako hracia postava, ktorú budeme ovládať ako hráč
     * @param r6S
     * @param pozitionX
     * @param pozitionY
     * @param name
     * @param characterHP
     * @param armored
     * @param weaponMagazineSize
     * @param availableAmmunition
     */
    public Operator(R6S r6S, int pozitionX, int pozitionY, String name, int characterHP, boolean armored, int weaponMagazineSize, String availableAmmunition) {
        super(r6S, pozitionX, pozitionY, name, characterHP, armored, CharacterType.OPERATOR, weaponMagazineSize, availableAmmunition);
        super.setPixelCharacterIcon("images/OperatorIcon30.png");
    }

}
