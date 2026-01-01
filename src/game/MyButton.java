package game;

import fri.shapesge.DataObrazku;
import fri.shapesge.Manazer;
import fri.shapesge.Obrazok;

import java.awt.event.ActionListener;

public class MyButton {
    private final int x;
    private final int y;
    private final ActionListener actionListener;
    private final DataObrazku popisObrazku;
    private Obrazok obrazok;

    /**
     * Vytvorenie tlačidieľ na ovládanie operátorov ktorý sú na ťahu
     * @param obrazok
     * @param x
     * @param y
     * @param actionListener
     */
    public MyButton(String obrazok, int x, int y, ActionListener actionListener) {

        this.x = x;
        this.y = y;
        Manazer manazer = new Manazer();
        manazer.spravujObjekt(this);
        this.actionListener = actionListener;
        this.popisObrazku = new DataObrazku(obrazok);
        this.obrazok = new Obrazok(this.popisObrazku, x, y);
        this.obrazok.zobraz();
    }

    /**
     * metóda ktorá zisťuje či sa stlačilo práve toto tlačidlo ak áno vykoná sa pridelená akcia
     * @param x
     * @param y
     */
    public void vyberSuradnice(int x, int y) {
        if (x < this.x || x > this.x + this.popisObrazku.getSirka()) {
            return;
        }

        if (y < this.y || y > this.y + this.popisObrazku.getVyska()) {
            return;
        }

        this.actionListener.actionPerformed(null);
    }
}
