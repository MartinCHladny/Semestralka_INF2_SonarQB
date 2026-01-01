package game;
//aj po osobitnom imnportovaní mi to intelij premení sám na * keďže importujem naozaj všetko takže sa tomu moc nemám ako vyhnúť
import game.character.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;

public class R6S {

    private int operatorCount;
    private int numberOfEnemy;
    private int lightTerroristCount;
    private int heavyTesrroristCount;
    private HashMap<String, String[]> characterPosition;
    private Situation situation;
    private ArrayList<Operator> operatorList;
    private ArrayList<GameCharacter> terroristList;
    private Random rand;
    private GameCharacter actingOperator;
    private CommandExecution commandExecution;
    private boolean opTurn;

    /**
     * Hlavná trieda ktorá je zodpovedná za priebeh celej hry, konštruktor vytvorí postavy a mapu
     */
    public R6S() {
        this.rand = new Random();
        this.commandExecution = new CommandExecution(this);
        this.characterPosition = new HashMap<>();
        this.operatorList = new ArrayList<>();
        this.terroristList = new ArrayList<>();
        this.opTurn = false;
        this.situation = new Situation(rand.nextInt(3) + 1, this);

        // testovanie
        //this.situation = new Situation(1, this);

        this.operatorCount = 3;
        this.numberOfEnemy = this.rand.nextInt(3) + 3;
        this.heavyTesrroristCount = 1;
        this.lightTerroristCount = this.numberOfEnemy - this.heavyTesrroristCount;
        var alreadySpawnedOP = new ArrayList<Integer>();
        var numberOfOp = 0;

        for (int i = 0; i < 3; i++) {
            Tile opSpawn = this.situation.getOpratorSpawnPointsIndex(i);
            do {
                numberOfOp = this.rand.nextInt(4);
            } while (alreadySpawnedOP.contains(numberOfOp));
            alreadySpawnedOP.add(numberOfOp);
            var opToSpawn = this.vyberOperatorov(numberOfOp, opSpawn.getPoziciaX(), opSpawn.getPoziciaY());
            opSpawn.setCharacterOnTile(opToSpawn);
            this.operatorList.add(opToSpawn);
        }

        var terAlreadySpawned = 0;

        for (int i = 0; i < this.lightTerroristCount; i++) {
            Tile terSpawn = this.situation.getTerroristSpawnPointsIndex(i);
            var terToSpawn = new LightTerrorist(this, terSpawn.getPoziciaX(), terSpawn.getPoziciaY(), "Light Terrorist " + (i + 1));
            this.terroristList.add(terToSpawn);
            terSpawn.setCharacterOnTile(terToSpawn);
            terAlreadySpawned++;
        }

        for (int i = 0; i < this.heavyTesrroristCount; i++) {
            Tile terSpawn = this.situation.getTerroristSpawnPointsIndex(terAlreadySpawned);
            var terToSpawn = new HeavyTerrorits(this, terSpawn.getPoziciaX(), terSpawn.getPoziciaY(), "Heavy Terrorist " + (terAlreadySpawned + 1));
            this.terroristList.add(terToSpawn);
            terSpawn.setCharacterOnTile(terToSpawn);
            terAlreadySpawned++;
        }



    }

    /**
     * vráti veľkosť mapy
     * @return
     */
    public int getMapSyze() {
        return this.situation.getMapSyze();
    }

    /**
     * vráti políčko na vstupnej pozícii
     * @param x
     * @param y
     * @return
     */
    public Tile getPolicko(int x, int y) {
        return this.situation.getPolicko(x, y);
    }

    /**
     * spustí hru a bude sa hrať kým nepadnú všetci terroristi alebo operátori alebo všewtci naraz
     * po skončení hry sa výpíše kto vyhral
     */
    public void startGame() {
        while (this.operatorCount != 0 && this.numberOfEnemy != 0) {

            for (GameCharacter opToAct: this.operatorList) {
                if (opToAct.isAlive()) {
                    if (this.numberOfEnemy != 0) {
                        this.actingOperator = opToAct;
                        this.opTurn = true;
                        opToAct.setModeOfCharacterIcon(opToAct, true);

                        while (this.opTurn) {
                            opToAct.manazerSpravujCharakter(true);
                            try {
                                Thread.sleep(500); // Pauses for 1 second
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            opToAct.setModeOfCharacterIcon(opToAct, true);
                        }
                        opToAct.setModeOfCharacterIcon(opToAct, false);
                        opToAct.manazerSpravujCharakter(false);
                    }
                }
            }

            for (GameCharacter toAct: this.terroristList) {
                if (toAct.isAlive()) {
                    if (this.operatorCount != 0) {
                        toAct.setModeOfCharacterIcon(toAct, true);
                        toAct.manazerSpravujCharakter(true);
                        Terrorist terroristToAct = (Terrorist)toAct;
                        terroristToAct.doAction();
                        toAct.setModeOfCharacterIcon(toAct, true);

                        try {
                            Thread.sleep(3000); // Pauses for 1 second
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        toAct.manazerSpravujCharakter(false);
                        toAct.setModeOfCharacterIcon(toAct, false);
                    }
                }
            }

            System.out.println("Turn Line:\n-------------------------------------------------------------------------------");
        }

        if (this.operatorCount == 0 && this.numberOfEnemy == 0) {
            System.out.println("Match result: DRAW, both Terrorist and Rainbow Operators are KIA.");
            this.setCombatLogMessage("Match result: DRAW, both Terrorist and Rainbow Operators are KIA.", "yellow");
        }

        if (this.operatorCount == 0) {
            System.out.println("Operation failed. All Rainbow Operators were KIA.");
            this.setCombatLogMessage("Operation failed. All Rainbow Operators were KIA.", "yellow");
        }

        if (this.numberOfEnemy == 0) {
            System.out.println("Operation successful. All hostile targets have been neutralized.");
            this.setCombatLogMessage("Operation successful. All hostile targets have been neutralized.", "yellow");
        }

    }

    /**
     * vráti operátora podľa vstupného čísla
     * @param number
     * @param poziciaX
     * @param poziciaY
     * @return
     */
    public Operator vyberOperatorov(int number, int poziciaX, int poziciaY) {
        switch (number) {
            case 1: return new DOC(this, poziciaX, poziciaY);

            case 2: return new Ela(this, poziciaX, poziciaY);

            case 3: return new Sledge(this, poziciaX, poziciaY);

            default: return new Ash(this, poziciaX, poziciaY);
        }
    }

    /**
     * vráti náhodný cieľ pre terroristu
     * @return
     */
    public Operator getTargetForTerrorist() {
        var targetIndex = 0;
        Operator target = null;

        do {
            targetIndex = this.rand.nextInt(3);
            target = this.operatorList.get(targetIndex);
        } while (!target.isAlive());

        return target;
    }

    /**
     * vráti pole na ktorom stojí cieľ z parametra
     * @param target
     * @return
     */
    public Tile getTileByTarget(GameCharacter target) {
        Tile foundTile = null;

        for (int i = 0; i < this.getMapSyze(); i++) {
            for (int j = 0; j < this.getMapSyze(); j++) {
                if (this.getPolicko(i, j).hasCharacter()) {
                    if (this.getPolicko(i, j).getCharacterOnTile() == target) {
                        foundTile = this.getPolicko(i, j);
                        break;
                    }
                }
            }
        }

        return foundTile;
    }

    public GameCharacter getActingOperator() {
        return this.actingOperator;
    }

    public void characterDied(GameCharacter team) {
        if (team instanceof Operator) {
            this.operatorCount--;
            return;
        }
        this.numberOfEnemy--;
    }

    /**
     * vráti vykonávač príkazov
     * @return
     */
    public CommandExecution getCommandExecution() {
        return this.commandExecution;
    }

    /**
     * nastaví operátora ktorý je práve na ťahu
     * @param opTurn
     */
    public void setOpTurn(boolean opTurn) {
        this.opTurn = opTurn;
    }

    /**
     * vráti index I zo zoznamu polí pre políčko z parametra
     * @param tile
     * @return
     */
    public int getIndexI(Tile tile) {
        for (int i = 0; i < this.getMapSyze(); i++) {
            for (int j = 0; j < this.getMapSyze(); j++) {
                if (this.getPolicko(i, j) == tile) {
                    return i;
                }
            }
        }
        return 0;
    }

    /**
     * vráti index J zo zoznamu polí pre políčko z parametra
     * @param tile
     * @return
     */
    public int getIndexJ(Tile tile) {
        for (int i = 0; i < this.getMapSyze(); i++) {
            for (int j = 0; j < this.getMapSyze(); j++) {
                if (this.getPolicko(i, j) == tile) {
                    return j;
                }
            }
        }
        return 0;
    }

    /**
     * pošle správu situácii aby poslala správu combat logu aby pridal správu
     * @param combatLogMessage
     * @param color
     */
    public void setCombatLogMessage(String combatLogMessage, String color) {
        this.situation.setCombatLogMessage(combatLogMessage, color);
    }
}
