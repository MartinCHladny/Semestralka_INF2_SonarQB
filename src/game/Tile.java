package game;

import game.character.GameCharacter;
import fri.shapesge.Obrazok;
import fri.shapesge.Stvorec;

public class Tile {

    private Stvorec stvorec;
    private int poziciaX;
    private int poziciaY;
    private final int velkostPolicka;
    private boolean accessableTile;
    private GameCharacter characterOnTile;
    private Obrazok tileRepresentation;
    private Obrazok cover;

    /**
     * samotné pole v hracej ploche ktoré môže ale nemusí byť prístupné
     * ak je neprístupné znamená to že je to cover
     * @param accessableTile
     */
    public Tile(boolean accessableTile) {
        this.accessableTile = accessableTile;
        this.stvorec = new Stvorec();
        this.velkostPolicka = 42;
        this.poziciaX = 60;
        this.poziciaY = 40;
    }


    public int getRozmerPolicka() {
        return this.velkostPolicka;
    }

    public Stvorec getStvorec() {
        return this.stvorec;
    }

    public void setPoziciaX(int novaPoziciaX) {
        this.poziciaX = novaPoziciaX;
    }

    public void setPoziciaY(int novaPoziciaY) {
        this.poziciaY = novaPoziciaY;
    }

    public int getPoziciaX() {
        return this.poziciaX;
    }

    public int getPoziciaY() {
        return this.poziciaY;
    }

    /**
     * metóda zistí či naše pole obsahuje dané súradnice
     * @param x
     * @param y
     * @return
     */
    public boolean obsahujeBod(int x, int y) {
        if (x < this.poziciaX || x > this.poziciaX + this.velkostPolicka) {
            return false;
        }

        if (y < this.poziciaY || y > this.poziciaY + this.velkostPolicka) {
            return false;
        }

        return true;
    }

    public boolean isAccessableTile() {
        return this.accessableTile;
    }

    public void setCharacterOnTile(GameCharacter character) {
        this.characterOnTile = character;
    }

    public GameCharacter getCharacterOnTile() {
        return this.characterOnTile;
    }

    public boolean hasCharacter() {
        return this.characterOnTile != null;
    }

    /**
     * nastaví reprezentačný obrázok pre naše pole a posunie ho na miesto
     * @param directory
     */
    public void setTileRepresentation(String directory) {
        this.tileRepresentation = new Obrazok(directory);
        this.tileRepresentation.zmenPolohu(this.poziciaX, this.poziciaY);
        this.tileRepresentation.zobraz();
    }

    /**
     * správa zničí cover a tile sa stane prístupným a už sa neráta ako cover pri strelbu
     */
    public void destroyCover() {
        this.accessableTile = true;
        this.cover.skry();
    }

    /**
     * nastaví tile ako cover
     * @param directory
     */
    public void setCoverTile(String directory) {
        this.cover = new Obrazok(directory);
        this.cover.zmenPolohu(this.poziciaX, this.poziciaY);
        this.cover.zobraz();
    }
}
