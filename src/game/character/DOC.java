package game.character;

import game.character.gadget.DOCStimPistol;
import game.character.gadget.HEGrenade;
import game.R6S;

public class DOC extends Operator {
    /**
     * Operátor pre hráča menom DOC.
     * @param r6S
     * @param pozitionX
     * @param pozitionY
     */
    public DOC(R6S r6S, int pozitionX, int pozitionY) {
        super(r6S, pozitionX, pozitionY, "Gustave Doc Kateb", 250, true, 3, "incendiary");
        super.setPixelRepresentation("images/DOC42.png");
        super.getListOfPrimaryGadgets().add(new DOCStimPistol());
        super.getListOfPrimaryGadgets().add(new DOCStimPistol());
        super.getListOfPrimaryGadgets().add(new DOCStimPistol());
        super.getListOfSecondaryGadgets().add(new HEGrenade());
        super.getListOfSecondaryGadgets().add(new HEGrenade());
    }


}
