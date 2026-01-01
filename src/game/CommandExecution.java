package game;

import game.character.GameCharacter;
import fri.shapesge.Manazer;

public class CommandExecution {

    private R6S r6S;
    private String commandToExecute;
    private Manazer manazer;

    /**
     * Trieda je zodpovedná za nastavovanie a potvrdzovanie príkazov
     * @param r6S
     */
    public CommandExecution(R6S r6S) {
        this.r6S = r6S;
        this.commandToExecute = "";
        this.manazer = new Manazer();
        this.manazer.spravujObjekt(this);
    }

    /**
     * nastaví útok ako príkaz na spracovanie po dodaní cieľa
     */
    public void executeAttack() {
        System.out.println("executeAttack");
        this.commandToExecute = "attack";
    }

    /**
     * nastaví použitie sekundárneho gadgetu ako príkaz na spracovanie po dodaní cieľa
     */
    public void executeSecondaryGadget() {
        System.out.println("executeSecondaryGadget");
        this.commandToExecute = "second";
    }

    /**
     * nastaví použitie primárneho gadgetu ako príkaz na spracovanie po dodaní cieľa
     */
    public void executePrimaryGadget() {
        System.out.println("executePrimaryGadget");
        this.commandToExecute = "primary";
    }

    /**
     * nastaví pohyb ako príkaz na spracovanie po dodaní cieľa
     */
    public void executeMove() {
        System.out.println("executeMove");
        this.commandToExecute = "move";
    }

    /**
     * vykoná akciu prebytia
     */
    public void executeReload() {
        System.out.println("executeReload");
        this.r6S.getActingOperator().reload();
        this.r6S.setOpTurn(false);
    }

    /**
     * vykoná akciu ukončenia svojho ťahu
     */
    public void executeEndTurn() {
        System.out.println("executeEndTurn");
        this.r6S.getActingOperator().setBlinded(false);
        this.r6S.setOpTurn(false);
    }

    /**
     * dodá cieľ na použitie pre vykonanie metódy podľa toho aká je nastavená
     * @param x
     * @param y
     */
    public void targetSelection(int x, int y) {
        if (!this.commandToExecute.equals("")) {
            Tile najdeny = null;

            for (int i = 0; i < this.r6S.getMapSyze(); i++) {
                for (int j = 0; j < this.r6S.getMapSyze(); j++) {
                    if (this.r6S.getPolicko(i, j).obsahujeBod(x, y)) {
                        najdeny = this.r6S.getPolicko(i, j);
                        break;
                    }
                }
            }

            if (najdeny != null) {
                GameCharacter opToAct = this.r6S.getActingOperator();
                switch (this.commandToExecute) {
                    case "attack": opToAct.attackCommand(najdeny);
                    break;
                    case "second": opToAct.useSecondaryGadgetCommand(najdeny);
                    break;
                    case "primary": opToAct.usePrimaryGadgetCommand(najdeny);
                    break;
                    case "move": opToAct.moveCommand(najdeny.getPoziciaX(), najdeny.getPoziciaY());
                    break;
                }
                this.r6S.setOpTurn(false);
            }
        }
    }
}
