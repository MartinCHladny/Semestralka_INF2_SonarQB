package game.character;

import game.character.gadget.AshBreachingRound;
import game.character.gadget.FlashBang;
import game.R6S;

public class Ash extends Operator {
    /**
     * Operátor pre hráča menom ASH.
     * @param r6S
     * @param pozitionX
     * @param pozitionY
     */
    public Ash(R6S r6S, int pozitionX, int pozitionY) {
        super(r6S, pozitionX, pozitionY, "Eliza Ash Cohen", 150, false, 3, "incendiary");
        super.setPixelRepresentation("images/ASH42.png");
        super.getListOfPrimaryGadgets().add(new AshBreachingRound());
        super.getListOfPrimaryGadgets().add(new AshBreachingRound());
        super.getListOfSecondaryGadgets().add(new FlashBang());
        super.getListOfSecondaryGadgets().add(new FlashBang());
        super.getListOfSecondaryGadgets().add(new FlashBang());

    }
}
