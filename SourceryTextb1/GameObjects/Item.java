package SourceryTextb1.GameObjects;

public class Item{

    private String displayMode = "blank";

    private String name = "";
    private String icon = "";
    private String description = "";

    public String animation1 = "%";
    public String animation2 = "Q";

    public int damage, range, healing, duration, cost = 0;
    private int armor, hpBoost, allBoost, arcBoost, fireBoost, iceBoost, darkBoost = 0;
    private boolean alting;
    private Player player;

    public boolean isDmgSpell = false;

    public Item(String theName, String theDesc, Player play){
        name = theName;
        description = theDesc;
        player = play;
    }

    public Item(String theName, String theDesc, Player play, String type){
        name = theName;
        description = theDesc;
        player = play;
        switch(type){
            case "spell": itemType = 1;
                break;
            case "item": itemType = 2;
                break;
            case "equipment": itemType = 3;
                break;
        }
    }

    public Item(String theName, String theDesc, String theIcon, Player play, String type){
        name = theName;
        description = theDesc;
        player = play;
        icon = theIcon;
        switch(type.toLowerCase()){
            case "spell": itemType = 1;
                break;
            case "item": itemType = 2;
                break;
            case "equipment": itemType = 3;
                break;
        }
    }

    public Item(String theName, String theDesc, String theIcon, Player play, String type, boolean isAlting){
        name = theName;
        description = theDesc;
        player = play;
        icon = theIcon;
        switch(type.toLowerCase()){
            case "spell": itemType = 1;
                break;
            case "item": itemType = 2;
                break;
            case "equipment": itemType = 3;
                break;
        }
        alting = isAlting;
    }

    public int itemType;

    public void setIcon(String set){
        icon = set;
    }

    public String getIcon() { return icon; }

    public void dmgSpellDefine(int toD, int toR, int toC, String dMode, String s1, String s2){
        setDmgRngCost(toD, toR, toC);
        setDescMode(dMode);
        setAnim(s1, s2);
        System.out.println("Am I a damage spell?: " + isDmgSpell);
    }

    public void altSpellDefine(int toCost, String dMode){
        setDescMode(dMode);
        cost = toCost;
    }

    public boolean getAlting(){
        return alting;
    }

    public void setAnim(String s1, String s2){
        animation1 = s1;
        animation2 = s2;
    }

    public void setDescMode(String mode){
        displayMode = mode;
    }

    public String getDescMode () { return displayMode; }

    public void setDmg(int to) { damage = to; }
    public void setRng(int to) { range = to; }
    public void setCost(int to) { cost = to; }
    public void setDmgRngCost (int toD, int toR, int toC) {
        damage = toD;
        range = toR;
        cost = toC;
        isDmgSpell = true;
    }

    public int[] getEquipVals(){
        int[] result = {armor, hpBoost, allBoost, arcBoost, fireBoost, iceBoost, darkBoost};
        return result;
    }

    String equipType = "";

    public void setEquipType(String to){ equipType = to; }

    public String getEquipType(){ return equipType; }

    public void setEquipvals(int armorTo, int hpBoostTo, int allBoostTo, int arcBoostTo, int fireBoostTo, int iceBoostTo, int darkBoostTo, String equipTypeTo){
        armor = armorTo;
        hpBoost = hpBoostTo;
        allBoost = allBoostTo;
        arcBoost = arcBoostTo;
        fireBoost = fireBoostTo;
        iceBoost = iceBoostTo;
        darkBoost = darkBoostTo;
        setEquipType(equipTypeTo);
    }

    public void setHeal(int to) { healing = to; }
    public void setDur(int to) { duration = to; }

    public String getName() { return name; }

    public String getDesc() {
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
                return description + "\n\nRestores " + String.valueOf(healing) + " Health\nCost: " + String.valueOf(cost);
            case "buff":
                return description + "\n\nDuration: " + String.valueOf(duration) + " Seconds\nCost: " + String.valueOf(cost);
            default:
                return description + "\n\nINVALID DISPLAY MODE: \n  \"" + displayMode + "\"";
        }
    }
}
