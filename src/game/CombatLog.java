package game;

import fri.shapesge.BlokTextu;
import fri.shapesge.StylFontu;

public class CombatLog {

    private final int combatLogLength;
    private final int x;
    private final int y;
    private BlokTextu[] combatLog;

    /**
     * vytvorí kombat log ktorý má za úlohu vypisovať dianie v hre
     * @param combatLogLength
     * @param x
     * @param y
     */
    public CombatLog(int combatLogLength, int x, int y) {
        this.combatLogLength = combatLogLength;
        this.x = x;
        this.y = y;
        this.combatLog = new BlokTextu[this.combatLogLength];
        system.out.println()
    }

    /**
     * vloží sa správa do combat logu
     * @param message
     * @param color
     */
    public void setMessage(String message, String color) {
        if (this.combatLog[0] != null) {
            this.combatLog[0].skry();
        }

        for (int i = 0; i < this.combatLogLength - 1; i++) {

            if (this.combatLog[i + 1] != null) {
                this.combatLog[i] = this.combatLog[i + 1];
                this.combatLog[i].posunZvisle(-15);
            }
        }
        this.combatLog[this.combatLogLength - 1] = new BlokTextu(message, this.x, this.y);
        this.combatLog[this.combatLogLength - 1].zmenFarbu(color);
        this.combatLog[this.combatLogLength - 1].zmenFont("arial", StylFontu.PLAIN, 15);
        this.combatLog[this.combatLogLength - 1].zobraz();
    }

}
