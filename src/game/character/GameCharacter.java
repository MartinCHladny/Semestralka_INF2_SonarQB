package game.character;

import game.character.gadget.ammo.AP;
import game.character.gadget.ammo.Ammunition;
import game.character.gadget.ammo.Basic;
import game.character.gadget.ammo.Incendiary;
import game.character.gadget.PrimaryGadget;
import game.character.gadget.SecondaryGadget;
import game.character.gadget.SledgeHammer;
import game.R6S;
import game.Tile;
import fri.shapesge.Obrazok;
import fri.shapesge.Manazer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public abstract class GameCharacter {

    private final String name;
    private int characterHP;
    private final int characterMaxHP;
    private final boolean armored;
    private boolean isConcussed;
    private boolean isBlinded;
    private final CharacterType characterType;
    private int weaponMagazineSize;
    private int ammoInMagazine;
    private String availableAmmunition;
    private Ammunition ammoInUse;
    private HashMap<String, Ammunition> ammoTypes;
    private ArrayList<PrimaryGadget> listOfPrimaryGadgets;
    private ArrayList<SecondaryGadget> listOfSecondaryGadgets;
    private int pozitionX;
    private int pozitionY;
    private int movementSpeed;
    private Manazer manazer;
    private Random rand;
    private R6S r6S;
    private Obrazok pixelRepresentation;
    private Obrazok pixelCharacterIcon;

    /**
     * Základ charakterov ktoré budú figurovať v našej hre
     * @param r6S
     * @param pozitionX
     * @param pozitionY
     * @param name
     * @param characterHP
     * @param armored
     * @param characterType
     * @param weaponMagazineSize
     * @param availableAmmunition
     */
    public GameCharacter(R6S r6S, int pozitionX, int pozitionY, String name, int characterHP, boolean armored, CharacterType characterType, int weaponMagazineSize, String availableAmmunition) {
        this.r6S = r6S;
        this.pozitionX = pozitionX;
        this.pozitionY = pozitionY;
        this.name = name;
        this.characterHP = characterHP;
        this.characterMaxHP = characterHP;
        this.armored = armored;
        this.isConcussed = false;
        this.isBlinded = false;
        this.characterType = characterType;
        this.manazer = new Manazer();
        this.rand = new Random();
        this.listOfPrimaryGadgets = new ArrayList<>();
        this.listOfSecondaryGadgets = new ArrayList<>();
        this.weaponMagazineSize = weaponMagazineSize;
        this.ammoInMagazine = weaponMagazineSize;
        this.ammoTypes = new HashMap<>();
        this.ammoTypes.put("basic", new Basic());
        this.ammoTypes.put("AP", new AP());
        this.ammoTypes.put("incendiary", new Incendiary());
        this.ammoInUse = this.ammoTypes.get(availableAmmunition);
        this.availableAmmunition = availableAmmunition;
        if (this.armored) {
            this.movementSpeed = 2;
        } else {
            this.movementSpeed = 4;
        }


    }

    /**
     * Slúži na posunutie postavy v rámci hracieho poľa tým že sa nájde políčkoktoré obsahuje vstupové súradnice
     * a prebehne kontrola či je pre postavu možné sa na políčko dostať
     * @param x
     * @param y
     */
    public void moveCommand(int x, int y) {

        if (this.isBlinded) {
            System.out.println(String.format("%s failed to move due to being blinded which resulted in lost turn", this.name));
            this.r6S.setCombatLogMessage(String.format("%s failed to move due to being blinded which resulted in lost turn", this.name), "red");
            this.isBlinded = false;
            return;
        }

        Tile najdeny = null;
        var najdeneI = 0;
        var najdeneJ = 0;

        for (int i = 0; i < this.r6S.getMapSyze(); i++) {
            for (int j = 0; j < this.r6S.getMapSyze(); j++) {
                if (this.r6S.getPolicko(i, j).obsahujeBod(x, y)) {
                    najdeny = this.r6S.getPolicko(i, j);
                    najdeneI = i;
                    najdeneJ = j;
                    break;
                }
            }
        }

        var myTileI = 0;
        var myTileJ = 0;

        for (int i = 0; i < this.r6S.getMapSyze(); i++) {
            for (int j = 0; j < this.r6S.getMapSyze(); j++) {
                if (this.r6S.getPolicko(i, j) == this.r6S.getTileByTarget(this)) {
                    myTileI = i;
                    myTileJ = j;
                    break;
                }
            }
        }

        int smerX = Math.abs(myTileI - najdeneI);
        int smerY = Math.abs(myTileJ - najdeneJ);

        if (smerX > this.movementSpeed || smerY > this.movementSpeed) {
            System.out.println("Not enough movement points to move on that tile");
            this.r6S.setCombatLogMessage("Not enough movement points to move on that tile", "black");
            return;
        }


        if (najdeny != null && najdeny.isAccessableTile() && !najdeny.hasCharacter()) {

            this.r6S.getTileByTarget(this).setCharacterOnTile(null);
            najdeny.setCharacterOnTile(this);
            this.pixelRepresentation.zmenPolohu(najdeny.getPoziciaX(), najdeny.getPoziciaY());
            System.out.println(String.format("%s moved to another location", this.name));
            this.r6S.setCombatLogMessage(String.format("%s moved to another location", this.name), "blue");

        } else {
            System.out.println("You can't move there the tile is already occupied");
            this.r6S.setCombatLogMessage("You can't move there the tile is already occupied", "orange");
        }
    }

    /**
     * Slúži na zaútočenie proti nejakému inému charakteru a po kým sú splnené podmienky tak cieľ obdrží poškodenie podľa toho akú muníciu má a na koho strielal
     * @param tile
     */
    public void attackCommand(Tile tile) {
        var attackFailure = 0;

        if (!tile.isAccessableTile()) {
            System.out.println("You can attack only targets not environment");
            this.r6S.setCombatLogMessage("You can attack only targets not environment", "black");
            return;
        }

        if (!tile.hasCharacter()) {
            System.out.println("Tile must have a target");
            this.r6S.setCombatLogMessage("Tile must have a target", "orange");
            return;
        }

        var target = tile.getCharacterOnTile();

        if (this.isBlinded) {
            System.out.println(String.format("%s failed to perform an attack due to being blinded which resulted in lost turn", this.name));
            this.r6S.setCombatLogMessage(String.format("%s failed to perform an attack due to being blinded which resulted in lost turn", this.name), "red");
            this.isBlinded = false;
            return;
        }

        if (this.ammoInMagazine <= 0) {
            System.out.println("Magazine is empty. Attack impossible");
            this.r6S.setCombatLogMessage("Magazine is empty. Attack impossible", "black");
            return;
        }

        if (this.isTargetCovered(target)) {
            //System.out.println("Roll for shot against cover");
            attackFailure = this.rand.nextInt(3);
            if (attackFailure == 1) {
                System.out.println(String.format("%s failed to hit the target %s due the targets cover", this.getName(), target.name));
                this.r6S.setCombatLogMessage(String.format("%s failed to hit the target %s due the targets cover", this.getName(), target.name), "red");
                this.ammoInMagazine--;
                return;
            }
        }

        if (this.isConcussed) {
            attackFailure = this.rand.nextInt(2);
            if (attackFailure == 0) {
                System.out.println(String.format("%s 's attack missed due the attacker being concussed", this.name));
                this.r6S.setCombatLogMessage(String.format("%s 's attack missed due the attacker being concussed", this.name), "red");
                this.ammoInMagazine--;
                return;
            }
        }

        System.out.println(String.format("%s successfully hit %s for %d damage.", this.name, target.name, this.calculateDamage(target)));
        this.r6S.setCombatLogMessage(String.format("%s successfully hit %s for %d damage.", this.name, target.name, this.calculateDamage(target)), "red");
        target.adjustHP(this.calculateDamage(target), this);
        this.ammoInMagazine--;

    }

    /**
     * Použije sekundárny gadget ak nejaký samozrejme máme na daný tile
     * @param tile
     */
    public void useSecondaryGadgetCommand(Tile tile) {
        if (!this.isBlinded) {
            for (int i = 0; i < this.listOfSecondaryGadgets.size(); i++) {
                if  (this.listOfSecondaryGadgets.get(i) != null) {
                    this.listOfSecondaryGadgets.get(i).useGadget(tile, this, this.r6S);
                    this.listOfSecondaryGadgets.remove(i);
                    return;
                }
            }
            System.out.println(String.format("%s has no more Secondary gadgets", this.name));
            this.r6S.setCombatLogMessage(String.format("%s has no more Secondary gadgets", this.name), "black");
        } else {
            System.out.println(String.format("%s failed to use gadget due to being blinded which resulted in lost turn", this.name));
            this.r6S.setCombatLogMessage(String.format("%s failed to use gadget due to being blinded which resulted in lost turn", this.name), "red");
            this.isBlinded = false;
        }
    }

    /**
     * Použije primárny gadget ak nejaký samozrejme máme na daný tile
     * @param tile
     */
    public void usePrimaryGadgetCommand(Tile tile) {
        if (!this.isBlinded) {
            for (int i = 0; i < this.listOfPrimaryGadgets.size(); i++) {
                if  (this.listOfPrimaryGadgets.get(i) != null) {
                    this.listOfPrimaryGadgets.get(i).useGadget(tile, this);
                    if (!(this.listOfPrimaryGadgets.get(i) instanceof SledgeHammer)) {
                        this.listOfPrimaryGadgets.remove(i);
                    }
                    return;
                }
            }
            System.out.println(String.format("%s has no more Primary gadgets", this.name));
            this.r6S.setCombatLogMessage(String.format("%s has no more Primary gadgets", this.name), "black");
        } else {
            System.out.println(String.format("%s failed to use gadget due to being blinded which resulted in lost turn", this.name));
            this.r6S.setCombatLogMessage(String.format("%s failed to use gadget due to being blinded which resulted in lost turn", this.name), "red");
            this.isBlinded = false;
        }
    }

    /**
     * prebije zbraň podľa toho akú má charakter miníciu k dispozícií
     */
    public void reload() {
        if (this.isBlinded) {
            System.out.println(String.format("%s failed to reload you weapon due to being blinded which resulted in lost turn", this.name));
            this.r6S.setCombatLogMessage(String.format("%s failed to reload you weapon due to being blinded which resulted in lost turn", this.name), "red");
            this.isBlinded = false;
            return;
        }

        this.ammoInMagazine = this.weaponMagazineSize;
        this.ammoInUse = this.ammoTypes.get(this.availableAmmunition);
        System.out.println(String.format("%s reloaded weapon with %s type of ammunition", this.getName(), this.availableAmmunition));
        this.r6S.setCombatLogMessage(String.format("%s reloaded weapon with %s type of ammunition", this.getName(), this.availableAmmunition), "blue");
    }

    /**
     * Manažér začne spravovať objekt podľa boolean parametra
     * @param mode
     */
    public void manazerSpravujCharakter(boolean mode) {
        if (mode) {
            this.manazer.spravujObjekt(this);
        } else {
            this.manazer.prestanSpravovatObjekt(this);
        }
    }

    /**
     * pošle správu triede náboj aby vrátila poškodenie, ktoré by mal target dostať
     * @param target
     * @return
     */
    public int calculateDamage(GameCharacter target) {
        return this.ammoInUse.calculateDamage(target);
    }

    /**
     * getter na to či je charakter armored alebo nie
     * @return
     */
    public boolean isArmored() {
        return this.armored;
    }

    /**
     * getter mena charakteru
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * getter zoznamu dostupných primárnych gadgetov
     * @return
     */
    public ArrayList getListOfPrimaryGadgets() {
        return this.listOfPrimaryGadgets;
    }

    /**
     * getter zoznamu dostupných sekundárnych gadgetov
     * @return
     */
    public ArrayList getListOfSecondaryGadgets() {
        return this.listOfSecondaryGadgets;
    }

    /**
     * getter aktuálnych hp charakteru
     * @return
     */
    public int getHP() {
        return this.characterHP;
    }

    /**
     * getter macimálnych hp charakteru
     * @return
     */
    public int getMaxHP() {
        return this.characterMaxHP;
    }

    /**
     * upraví životy charakteru podľa parametra a skonroluje sa čo charakter zomrel
     * @param damage
     * @param attacker
     */
    public void adjustHP(int damage, GameCharacter attacker) {
        this.characterHP -= damage;
        if (this.characterHP > this.characterMaxHP) {
            this.characterHP = this.characterMaxHP;
        }
        this.didCharacterDied(attacker);
    }

    /**
     * nastavý reprezentačný obrázok charaktreru a postaví ho na miesto
     * @param directory
     */
    public void setPixelRepresentation(String directory) {
        this.pixelRepresentation = new Obrazok(directory);
        this.pixelRepresentation.zmenPolohu(this.pozitionX, this.pozitionY);
        this.pixelRepresentation.zobraz();
    }

    /**
     * nastaví ikonku, ktorá sa zobrazí keď je charakter na ťahu
     * @param directory
     */
    public void setPixelCharacterIcon(String directory) {
        this.pixelCharacterIcon = new Obrazok(directory);
    }

    /**
     * getter pre veˇkosť možnosti pohybu
     * @return
     */
    public int getMovementSpeed() {
        return this.movementSpeed;
    }

    /**
     * vráti boolean či je charakter na živu podľa jeho hp
     * @return
     */
    public boolean isAlive() {
        if (this.getHP() <= 0) {
            return false;
        }
        return true;
    }

    /**
     * nastaví charakteru stav concussed na zhoršenie presnosti strelby
     * @param state
     */
    public void setConcussed(boolean state) {
        this.isConcussed = state;
    }

    /**
     * vráti boolean podľa toho či charakter je alebo nie je oslepený
     * @param state
     */
    public void setBlinded(boolean state) {
        this.isBlinded = state;
    }

    /**
     * metóda má za úlohu prejsť LOS (line of sight) a zistiť či sa v ňom nachádza cover, ktorý nie je bližšie ako 1 pole od strelca
     * @param target
     * @return
     */
    public boolean isTargetCovered(GameCharacter target) {

        if (this.isAdjacent(this.r6S.getTileByTarget(target))) {
            return false;
        }

        var attackerIndexI = this.r6S.getIndexI(this.r6S.getTileByTarget(this));
        var attackerIndexJ = this.r6S.getIndexJ(this.r6S.getTileByTarget(this));
        var targetIndexI = this.r6S.getIndexI(this.r6S.getTileByTarget(target));
        var targetIndexJ = this.r6S.getIndexJ(this.r6S.getTileByTarget(target));
        var pathX = 0;
        var pathY = 0;

        if (attackerIndexI < targetIndexI) {
            attackerIndexI++;
        } else {
            attackerIndexI--;
        }

        if (attackerIndexJ < targetIndexJ) {
            attackerIndexJ++;
        } else {
            attackerIndexJ--;
        }


        while (attackerIndexI != targetIndexI || attackerIndexJ != targetIndexJ) {
            pathX = 0;
            pathY = 0;
            if (attackerIndexI < targetIndexI) {
                pathX = 1;
            } else if (attackerIndexI == targetIndexI) {
                pathX = 0;
            } else {
                pathX = -1;
            }

            if (attackerIndexJ < targetIndexJ) {
                pathY = 1;
            } else if (attackerIndexJ == targetIndexJ) {
                pathY = 0;
            } else {
                pathY = -1;
            }
            attackerIndexI += pathX;
            if (!this.r6S.getPolicko(attackerIndexI, attackerIndexJ).isAccessableTile()) {
                return true;
            }

            attackerIndexJ += pathY;

            if (!this.r6S.getPolicko(attackerIndexI, attackerIndexJ).isAccessableTile()) {
                return true;
            }
        }

        return false;
    }

    /**
     * metóda zistí či sa tile nachádza vedľa nášho
     * @param tile
     * @return
     */
    public boolean isAdjacent(Tile tile) {
        var indexI = this.r6S.getIndexI(this.r6S.getTileByTarget(this));
        var indexJ = this.r6S.getIndexJ(this.r6S.getTileByTarget(this));

        for (int i = indexI - 1; i <= indexI + 1; i++) {
            for (int j = indexJ - 1; j <= indexJ + 1; j++) {
                if (i >= 0 && j >= 0 && i < this.r6S.getMapSyze() && j < this.r6S.getMapSyze()) {
                    if (this.r6S.getPolicko(i, j) == tile) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * vykoná kontrolu ći charakter zomrel a ak áno vypíše hlášku úmrtia
     * @param attacker
     */
    public void didCharacterDied(GameCharacter attacker) {
        if (this.characterHP <= 0) {
            System.out.println(String.format("%s was killed in action by %s", this.name, attacker.getName()));
            this.r6S.setCombatLogMessage(String.format("%s was killed in action by %s", this.name, attacker.getName()), "green");
            this.pixelRepresentation.skry();
            this.r6S.getTileByTarget(this).setCharacterOnTile(null);
            this.r6S.characterDied(this);
        }
    }

    /**
     * metóda zabije charakter
     * @param attacker
     */
    public void killCharacter(GameCharacter attacker) {
        this.characterHP = 0;
        this.didCharacterDied(attacker);
    }

    /**
     * vráti počet zostávajúcich nábojov v zásobníku
     * @return
     */
    public int getAmmoInMagazine() {
        return this.ammoInMagazine;
    }

    /**
     * Nastaví ktorý charakter má pri sebe ikonku indukujúcu jeho ťah
     * @param character
     * @param mode
     */
    public void setModeOfCharacterIcon(GameCharacter character, boolean mode) {
        if (mode) {
            Tile tile = this.r6S.getTileByTarget(character);
            this.pixelCharacterIcon.zmenPolohu(tile.getPoziciaX() - 20, tile.getPoziciaY() - 20);
            this.pixelCharacterIcon.zobraz();
        } else {
            this.pixelCharacterIcon.skry();
        }
    }

    /**
     * vráti r6S
     * @return
     */
    public R6S getR6S() {
        return this.r6S;
    }
}