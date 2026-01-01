package game.character;

import game.character.gadget.ElaGrzmotMine;
import game.character.gadget.FlashBang;
import game.R6S;

public class Ela extends Operator {
    /**
     * Operátor pre hráča menom Ela.
     * @param r6S
     * @param pozitionX
     * @param pozitionY
     */
    public Ela(R6S r6S, int pozitionX, int pozitionY) {
        super(r6S, pozitionX, pozitionY, "Ela Bosak", 150, false, 6, "AP");
        super.setPixelRepresentation("images/ELA42.png");
        super.getListOfPrimaryGadgets().add(new ElaGrzmotMine());
        super.getListOfPrimaryGadgets().add(new ElaGrzmotMine());
        super.getListOfPrimaryGadgets().add(new ElaGrzmotMine());
        super.getListOfSecondaryGadgets().add(new FlashBang());
        super.getListOfSecondaryGadgets().add(new FlashBang());
        super.getListOfSecondaryGadgets().add(new FlashBang());
    }
}
