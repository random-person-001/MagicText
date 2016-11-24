package SourceryTextb1.GameObjects;


import SourceryTextb1.SpecialText;

/**
 * Catch-all class that can go into your inventory.  Includes equipment, spells, and more!
 */
public class Item implements java.io.Serializable {

    /**
     * A string telling the world the type of spell it is.
     * Options include: damage, arcane, dark, ice, fire, buff, heal, blank
     */
    private String displayMode = "blank";
    /**
     * The full, unabridged, readable, whitespace-including name for the item.
     */
    private String name = "";
    /**
     * A five character representation of the spell name, for use in the HUD when the spell is equipped
     */
    private String icon = "";
    /**
     * A bunch of text describing this item.  Generally will not include things like discussing the data pertaining to it.
     * Newlines put at most 26 characters apart.
     */
    private String description = "";

    /**
     * A magic number, specifying the type of Item this is
     * 1: spell
     * 2: normal item
     * 3: equipment
     */
    public int itemType;

    public SpecialText animation1 = new SpecialText("%");
    public SpecialText animation2 = new SpecialText("Q");

    public int damage, range, healing, overheal, duration, cost = 0;
    private int armor, hpBoost, allBoost, arcBoost, fireBoost, iceBoost, darkBoost = 0;
    private boolean alting;
    private boolean pathfinds = false;

    public boolean isDmgSpell = false;

    public Item(String theName, String theDesc) {
        name = theName;
        description = theDesc;
    }

    public Item(String theName, String theDesc, String type) {
        this(theName, theDesc);
        switch (type) {
            case "spell":
                itemType = 1;
                break;
            case "item":
                itemType = 2;
                break;
            case "equipment":
                itemType = 3;
                break;
            case "equip":
                itemType = 3;
                break;
        }
    }

    public Item(String theName, String theDesc, String theIcon, String type) {
        this(theName, theDesc, type);
        icon = theIcon;
    }

    public Item(String theName, String theDesc, String theIcon, String type, boolean isAlting) {
        this(theName, theDesc, theIcon, type);
        alting = isAlting;
    }

    public void setIcon(String set) {
        icon = set;
    }

    public String getIcon() {
        return icon;
    }

    /**
     * Define a whole bunch of parameters for a damage spell
     *
     * @param toD   damage
     * @param toR   range
     * @param toC   cost
     * @param dMode display mode
     * @param s1    state one (animation)
     * @param s2    state two (animation)
     */
    public void dmgSpellDefine(int toD, int toR, int toC, String dMode, SpecialText s1, SpecialText s2, boolean pathfinding) {
        setDmgRngCost(toD, toR, toC);
        setDescMode(dMode);
        setAnim(s1, s2);
        pathfinds = pathfinding;
        System.out.println("Am I a damage spell?: " + isDmgSpell);
    }

    /**
     * Alternate version of dmgSpellDefine with SpecialText instead of strings
     * @param toD   damage
     * @param toR   range
     * @param toC   cost
     * @param dMode display mode
     * @param s1    state one (animation)
     * @param s2    state two (animation)
     */
    public void dmgSpellDefine(int toD, int toR, int toC, String dMode, SpecialText s1, SpecialText s2) {
        setDmgRngCost(toD, toR, toC);
        setDescMode(dMode);
        setAnim(s1, s2);
        System.out.println("Am I a damage spell?: " + isDmgSpell);
    }

    /**
     * Define a whole bunch of parameters for an alternating? spell
     *
     * @param toCost cost
     * @param dMode  display mode
     */
    public void altSpellDefine(int toCost, String dMode) {
        setDescMode(dMode);
        cost = toCost;
    }



    public boolean getAlting() {
        return alting;
    }

    public boolean getPathfinding() { return pathfinds; }

    public void setAnim(String s1, String s2) {
        animation1 = new SpecialText(s1);
        animation2 = new SpecialText(s2);
    }

    public void setAnim(SpecialText s1, SpecialText s2) {
        animation1 = s1;
        animation2 = s2;
    }

    public void setDescMode(String mode) {
        displayMode = mode;
    }

    public String getDescMode() {
        return displayMode;
    }

    public void setDmg(int to) {
        damage = to;
    }

    public void setRng(int to) {
        range = to;
    }

    public void setCost(int to) {
        cost = to;
    }

    /**
     * Set the damage range cost.  Also makes sure it's a damage spell.
     *
     * @param toD new damage
     * @param toR new range
     * @param toC new cost
     */
    public void setDmgRngCost(int toD, int toR, int toC) {
        damage = toD;
        range = toR;
        cost = toC;
        isDmgSpell = true;
    }

    /**
     * An array of equipment datas.
     *
     * @return {armor, hpBoost, allBoost, arcBoost, fireBoost, iceBoost, darkBoost};
     */
    public int[] getEquipVals() {
        int[] result = {armor, hpBoost, allBoost, arcBoost, fireBoost, iceBoost, darkBoost};
        return result;
    }

    String equipType = "";

    public void setEquipType(String to) {
        equipType = to;
    }

    public String getEquipType() {
        return equipType;
    }

    public void setEquipvals(int armorTo, int hpBoostTo, int allBoostTo, int arcBoostTo, int fireBoostTo, int iceBoostTo, int darkBoostTo, String equipTypeTo) {
        armor = armorTo;
        hpBoost = hpBoostTo;
        allBoost = allBoostTo;
        arcBoost = arcBoostTo;
        fireBoost = fireBoostTo;
        iceBoost = iceBoostTo;
        darkBoost = darkBoostTo;
        setEquipType(equipTypeTo);
        displayMode = "equipment";
    }

    public void setHeal(int to) {
        healing = to;
    }

    public void healItemDefine(int to, int over){
        healing = to;
        overheal = over;
        displayMode = "healitem";
    }

    public void setDuration(int to) {
        duration = to;
    }

    public int getDuration() { return duration; }

    public String getName() {
        return name;
    }

    /**
     * @return the items's long description.  Includes the exclusive description as well as cost, range, damage, etc
     * if applicable.
     */
    public String getDesc(Player player) {
        switch (displayMode.toLowerCase()) {
            case "blank":
                return description;
            case "damage":
                return description + "\n\nDamage: " + String.valueOf(damage) + "\nRange : " + String.valueOf(range) + "\nCost  : " + String.valueOf(cost);
            case "arcane":
                return description + "\n\nDamage: " + String.valueOf(damage) + " (+" + (player.allSpellBoost + player.arcSpellBoost)
                        + ")\nRange : " + String.valueOf(range) + "\nCost  : " + String.valueOf(cost);
            case "fire":
                return description + "\n\nDamage: " + String.valueOf(damage) + " (+" + (player.allSpellBoost + player.fireSpellBoost)
                        + ")\nRange : " + String.valueOf(range) + "\nCost  : " + String.valueOf(cost);
            case "ice":
                return description + "\n\nDamage: " + String.valueOf(damage) + " (+" + (player.allSpellBoost + player.iceSpellBoost)
                        + ")\nRange : " + String.valueOf(range) + "\nCost  : " + String.valueOf(cost);
            case "dark":
                return description + "\n\nDamage: " + String.valueOf(damage) + " (+" + (player.allSpellBoost + player.darkSpellBoost)
                        + ")\nRange : " + String.valueOf(range) + "\nCost  : " + String.valueOf(cost);
            case "healing":
                return description + "\n\nRestores " + String.valueOf(healing) + " Health\nOverheal: " + String.valueOf(overheal) + "\nCost: " + String.valueOf(cost);
            case "healitem":
                return description + "\n\nRestores " + String.valueOf(healing) + " Health\nOverheal: " + String.valueOf(overheal);
            case "potion":
                return description + "\n\nOne time use: press A or\nENTER to consume\n\nDuration: " + String.valueOf(duration / 1000) + "s";
            case "buff":
                return description + "\n\nDuration: " + String.valueOf(duration) + " Seconds\nCost: " + String.valueOf(cost);
            case "equipment":
                String finalDesc = description + "\n\n";
                if (armor > 0) finalDesc += (String.format("+%1$d Defense\n", armor));
                if (hpBoost > 0) finalDesc += (String.format("+%1$d Max Health\n", hpBoost));
                if (allBoost > 0) finalDesc += (String.format("+%1$d (All) Spell Damage\n", allBoost));
                if (arcBoost > 0) finalDesc += (String.format("+%1$d Arcane Spell Damage\n", arcBoost));
                if (fireBoost > 0) finalDesc += (String.format("+%1$d Fire Spell Damage\n", fireBoost));
                if (iceBoost > 0) finalDesc += (String.format("+%1$d Ice Spell Damage\n", iceBoost));
                if (darkBoost > 0) finalDesc += (String.format("+%1$d Dark Spell Damage\n", darkBoost));
                return finalDesc;
            default:
                return description + "\n\nINVALID DISPLAY MODE: \n  \"" + displayMode + "\"";
        }
    }
}
