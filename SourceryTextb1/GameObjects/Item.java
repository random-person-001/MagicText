package SourceryTextb1.GameObjects;

public class Item{

    private String displayMode = "blank";

    private String name = "";
    private String icon = "";
    private String description = "";

    public String animation1 = "%";
    public String animation2 = "Q";

    public int damage, range, healing, duration, cost = 0;
    private Player player;

    public boolean isDmgSpell = false;

    public Item(String theName, String theDesc, Player play){
        name = theName;
        description = theDesc;
        player = play;
    }
    public Item(String theName, String theDesc, String theIcon, Player play){
        name = theName;
        description = theDesc;
        player = play;
        icon = theIcon;
    }


    public void setIcon(String set){
        icon = set;
    }

    public String getIcon() { return icon; }

    public void setAnim(String s1, String s2){
        animation1 = s1;
        animation2 = s2;
    }

    public void setDescMode(String mode){
        displayMode = mode;
    }

    public void setDmg(int to) { damage = to; }
    public void setRng(int to) { range = to; }
    public void setCost(int to) { cost = to; }
    public void setDmgRngCost (int toD, int toR, int toC) {
        damage = toD;
        range = toR;
        cost = toC;
        isDmgSpell = true;
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
                return description + "\n\nRestores " + String.valueOf(healing) + " Health";
            case "buff":
                return description + "\n\nDuration: " + String.valueOf(duration);
            default:
                return description + "\n\nINVALID DISPLAY MODE: \n  \"" + displayMode + "\"";
        }
    }
}
