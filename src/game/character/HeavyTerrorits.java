package game.character;

import game.character.gadget.HEGrenade;
import game.R6S;
import java.util.Random;

/**
 * Počítačom ovládaná heavy verzia terroristu
 */
public class HeavyTerrorits extends GameCharacter implements Terrorist {


    private Random rand;
    private R6S r6S;

    /**
     * vytvorí teroristu
     * @param r6S
     * @param pozitionX
     * @param pozitionY
     * @param name
     */
    public HeavyTerrorits(R6S r6S, int pozitionX, int pozitionY, String name) {
        super(r6S, pozitionX, pozitionY , name, 150, true, CharacterType.TERRORIST, 6, "AP");
        super.setPixelRepresentation("images/HeavyT42.png");
        super.setPixelCharacterIcon("images/TerroristIcon30.jpg");
        this.rand = new Random();
        this.r6S = r6S;
        super.getListOfSecondaryGadgets().add(new HEGrenade());
        super.getListOfSecondaryGadgets().add(new HEGrenade());
    }

    /**
     * pri zavolaní náhodne zvolí čo má terorista urobiť
     */
    @Override
    public void doAction() {
        var akcia = this.rand.nextInt(10);

        if (super.getAmmoInMagazine() <= 0) {
            super.reload();
            return;
        }

        if (akcia == 1) {
            this.moveByIndex();
            return;
        }
        Operator target = this.r6S.getTargetForTerrorist();

        if (akcia > 1 && akcia < 7) {
            super.attackCommand(this.r6S.getTileByTarget(target));
            return;
        }

        if (!super.getListOfSecondaryGadgets().isEmpty()) {
            super.useSecondaryGadgetCommand(this.r6S.getTileByTarget(target));
        } else {
            super.attackCommand(this.r6S.getTileByTarget(target));
        }
    }

    /**
     * zvolí políško na ktoré by sa mal terrorista pohnúť a vykoná pohyb v rámci jeho možného pohybu
     */
    @Override
    public void moveByIndex() {
        var myTile = this.r6S.getTileByTarget(this);
        var indexI = 0;
        var indexJ = 0;

        for (int i = 0; i < this.r6S.getMapSyze(); i++) {
            for (int j = 0; j < this.r6S.getMapSyze(); j++) {
                if (this.r6S.getPolicko(i, j) == myTile) {
                    indexI = i;
                    indexJ = j;
                    break;
                }
            }
        }

        var moovementLeft = 0;
        var noveX = 0;
        var noveY = 0;
        var finalX = 0;
        var finalY = 0;

        do {
            moovementLeft = super.getMovementSpeed();
            noveX = this.rand.nextInt(super.getMovementSpeed() * 2) - super.getMovementSpeed();
            moovementLeft -= Math.abs(noveX);
            if (moovementLeft == 0) {
                noveY = 0;
            } else {
                noveY = this.rand.nextInt(moovementLeft * 2) - moovementLeft;
            }
            finalX = indexI + noveX;
            finalY = indexJ + noveY;

        } while (finalX < 0 || finalY < 0 || finalX > (this.r6S.getMapSyze() - 1) || finalY > (this.r6S.getMapSyze() - 1 ));

        var moveToTile = this.r6S.getPolicko(indexI + noveX, indexJ + noveY);
        super.moveCommand(moveToTile.getPoziciaX(), moveToTile.getPoziciaY());

    }
}
