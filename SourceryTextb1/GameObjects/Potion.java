package SourceryTextb1.GameObjects;

import SourceryTextb1.Rooms.Room;

import static java.lang.Math.abs;

/**
 * Similar to a spell, but gets used up.  Can have a lasting effect, thus the update method
 * Created by Riley on 17-Nov-16.
 */
class Potion extends GameObject {
    //private Random rand = new Random();
    private String name = "Generic";
    private Player caster;

    Potion(Room roomy, Player setPlayer, String namey){
        strClass = "Potion";
        caster = setPlayer;
        room = roomy;
        name = namey;
        setupTimer(50);
        setPlayer.fabulousMode = true;
    }

    public void update(){
        x = caster.getX();
        y = caster.getY();
        System.out.println("potion updating");
        switch (name) {
            // Garlic potato chip hurts everything near you.
            case "SC&OPotatoChip":
                int r = 4; // Range (radius)
                int d = 4; // Damage (max, at center)
                for (int xi = -r; xi <= r; xi++) {
                    for (int yi = -r; yi <= r; yi++) {
                        float xDamageMult = abs(abs(xi) - r) / (float) r; // 0 to 1, peaking when xi=0 (center)
                        float yDamageMult = abs(abs(yi) - r) / (float) r;
                        int totalDamage = (int) (d * .5 * (xDamageMult + yDamageMult));
                        room.hurtSomethingAt(xi + x, yi + y, totalDamage, "Jeez, killed yourself with an Potion!", true);
                        //System.out.println(x + xi + " " + (yi + y) + " given " + totalDamage + " damage");
                    }
                    //System.out.println();
                }
                if (getTime() > 20*1000){ // burnout time
                    cancelTimer();
                    caster.fabulousMode = false;
                }
                break;
        }
    }
}
