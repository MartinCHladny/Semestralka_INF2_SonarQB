package game.character;

import game.character.gadget.HEGrenade;
import game.character.gadget.SledgeHammer;
import game.R6S;

public class Sledge extends Operator {
    /**
     * Operátor pre hráča menom Sledge.
     * @param r6S
     * @param pozitionX
     * @param pozitionY
     */
    public Sledge(R6S r6S, int pozitionX, int pozitionY) {
        super(r6S, pozitionX, pozitionY, "Seamus Sledge Cowden", 250, true, 6, "AP");
        super.setPixelRepresentation("images/Sledge42.png");
        super.getListOfPrimaryGadgets().add(new SledgeHammer());
        super.getListOfSecondaryGadgets().add(new HEGrenade());
        super.getListOfSecondaryGadgets().add(new HEGrenade());
    }
}
