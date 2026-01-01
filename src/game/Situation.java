package game;

import java.util.ArrayList;

public class Situation {

    private int mapSyze;
    private Tile mapBase;
    private MyButton[] poleTlacidiel;
    private Tile[][] playingField;
    private ArrayList<Tile> opratorSpawnPoints;
    private ArrayList<Tile> terroristSpawnPoints;
    private R6S r6S;
    private CombatLog combatLog;

    /**
     * situácia je špecifické scenário ktoré mení veľkosť mapy miesta pre spawn charakterov pozicuje combat log a tlačidlá
     * samotná mapa sa skladá z objektov Tile ktoré majú svoju obrázkovú reprezentáciu podľa toho či sú cover alebo nie
     * @param scenario
     * @param r6S
     */
    public Situation(int scenario, R6S r6S) {

        this.r6S = r6S;
        var buttonX = 0;
        var combatLogY = 0;

        if (scenario == 1) {
            this.mapSyze = 8;
            buttonX = 365;
            combatLogY = 530;
        } else if (scenario == 2) {
            this.mapSyze = 10;
            buttonX = 450;
            combatLogY = 550;
        } else {
            this.mapSyze = 14;
            buttonX = 620;
            combatLogY = 700;
        }

        this.mapBase = new Tile(false);
        this.mapBase.getStvorec().zmenStranu((this.mapBase.getRozmerPolicka() * this.mapSyze) + (this.mapSyze) + 11);
        this.mapBase.getStvorec().posunVodorovne(-55);
        this.mapBase.getStvorec().posunZvisle(-35);
        this.mapBase.getStvorec().zmenFarbu("black");
        this.mapBase.getStvorec().zobraz();

        this.playingField = new Tile[this.mapSyze][this.mapSyze];
        for (int i = 0; i < this.mapSyze; i++) {
            for (int j = 0; j < this.mapSyze; j++) {
                if (i == this.mapSyze - 1 || (i + j) % 3 != 0 || i % 2 == 0) {
                    this.playingField[i][j] = new Tile(true);
                } else {
                    this.playingField[i][j] = new Tile(false);
                }
                this.playingField[i][j].getStvorec().posunVodorovne(1 + (j * (this.mapBase.getRozmerPolicka() + 1)) - 50);
                this.playingField[i][j].getStvorec().posunZvisle(1 + (i * (this.mapBase.getRozmerPolicka() + 1)) - 30);
                this.playingField[i][j].setPoziciaX(1 + (j * (this.mapBase.getRozmerPolicka() + 1)) + 10);
                this.playingField[i][j].setPoziciaY(1 + (i * (this.mapBase.getRozmerPolicka() + 1)) + 20);
                this.playingField[i][j].getStvorec().zmenStranu(this.mapBase.getRozmerPolicka());
                this.playingField[i][j].getStvorec().zmenFarbu("white");
                this.playingField[i][j].getStvorec().zobraz();
                this.playingField[i][j].setTileRepresentation("images/image of floor42.jpg");

                if (!this.playingField[i][j].isAccessableTile()) {
                    this.playingField[i][j].setCoverTile("images/cover42.png");
                }
            }
        }

        this.opratorSpawnPoints = new ArrayList<>();
        this.terroristSpawnPoints = new ArrayList<>();
        if (scenario == 1) {
            this.opratorSpawnPoints.add(this.playingField[6][1]);
            this.opratorSpawnPoints.add(this.playingField[6][4]);
            this.opratorSpawnPoints.add(this.playingField[6][7]);

            this.terroristSpawnPoints.add(this.playingField[0][2]);
            this.terroristSpawnPoints.add(this.playingField[0][5]);
            this.terroristSpawnPoints.add(this.playingField[3][4]);
            this.terroristSpawnPoints.add(this.playingField[2][0]);
            this.terroristSpawnPoints.add(this.playingField[0][7]);

        } else if (scenario == 2) {
            this.opratorSpawnPoints.add(this.playingField[8][2]);
            this.opratorSpawnPoints.add(this.playingField[8][5]);
            this.opratorSpawnPoints.add(this.playingField[8][8]);

            this.terroristSpawnPoints.add(this.playingField[0][2]);
            this.terroristSpawnPoints.add(this.playingField[0][5]);
            this.terroristSpawnPoints.add(this.playingField[4][4]);
            this.terroristSpawnPoints.add(this.playingField[2][9]);
            this.terroristSpawnPoints.add(this.playingField[5][0]);

        } else {
            this.opratorSpawnPoints.add(this.playingField[12][1]);
            this.opratorSpawnPoints.add(this.playingField[12][7]);
            this.opratorSpawnPoints.add(this.playingField[12][13]);

            this.terroristSpawnPoints.add(this.playingField[0][2]);
            this.terroristSpawnPoints.add(this.playingField[2][3]);
            this.terroristSpawnPoints.add(this.playingField[0][8]);
            this.terroristSpawnPoints.add(this.playingField[3][7]);
            this.terroristSpawnPoints.add(this.playingField[1][12]);
        }

        this.poleTlacidiel = new MyButton[6];
        this.poleTlacidiel[0] = new MyButton("images/attackCommand75.png", buttonX, 0, e -> this.r6S.getCommandExecution().executeAttack());
        this.poleTlacidiel[1] = new MyButton("images/secondaryGadget75.png", buttonX, 75, e -> this.r6S.getCommandExecution().executeSecondaryGadget());
        this.poleTlacidiel[2] = new MyButton("images/primaryGadget75.jpg", buttonX, 150, e -> this.r6S.getCommandExecution().executePrimaryGadget());
        this.poleTlacidiel[3] = new MyButton("images/moveCommand75.jpg", buttonX, 225, e -> this.r6S.getCommandExecution().executeMove());
        this.poleTlacidiel[4] = new MyButton("images/reloadCommand75.png", buttonX, 300, e -> this.r6S.getCommandExecution().executeReload());
        this.poleTlacidiel[5] = new MyButton("images/endTurn75.jpg", buttonX, 375, e -> this.r6S.getCommandExecution().executeEndTurn());

        this.combatLog = new CombatLog(5, 10, combatLogY);
    }

    /**
     * vráti veľkosť mapy
     * @return
     */
    public int getMapSyze() {
        return this.mapSyze;
    }

    /**
     * vráti políčko podľa vstupných parametrov
     * @param x
     * @param y
     * @return
     */
    public Tile getPolicko(int x, int y) {
        return this.playingField[x][y];
    }

    /**
     * vráti spawn point pre operátora na intexe I
     * @param i
     * @return
     */
    public Tile getOpratorSpawnPointsIndex(int i) {
        return this.opratorSpawnPoints.get(i);
    }

    /**
     * vráti spawn point pre terroristu na intexe I
     * @param i
     * @return
     */
    public Tile getTerroristSpawnPointsIndex(int i) {
        return this.terroristSpawnPoints.get(i);
    }

    /**
     * pošle správu combat logu aby pridal správu do výpisu
     * @param combatLogMessage
     * @param color
     */
    public void setCombatLogMessage(String combatLogMessage, String color) {
        this.combatLog.setMessage(combatLogMessage, color);
    }
}
