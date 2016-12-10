package SourceryTextb1.GameObjects;

import SourceryTextb1.Rooms.Room;

import java.util.List;

import static java.lang.Math.abs;

/**
 * Similar to a spell, but gets used up.  Can have a lasting effect, thus the update method
 * Created by Riley on 17-Nov-16.
 */
class Potion extends GameObject {
    //private Random rand = new Random();
    private String name = "Generic";
    private boolean firstUpdate = true;
    private Player caster;
    private int duration = 0;

    Potion(Room roomy, Player setPlayer, String namey, int effectTime) {
        strClass = "Potion";
        caster = setPlayer;
        room = roomy;
        name = namey;
        duration = effectTime;
        setupTimer(50);
        setPlayer.fabulousMode = true;
    }

    ///@Override
    public void update() {
        if (getTime() >= duration) {
            onCancel();
        }
        x = caster.getX();
        y = caster.getY();
        //System.out.println("potion updating");
        switch (name) {
            case "SC&OPotatoChip": // Sour Cream & Onion potato chip hurts everything near you.
                int r = 4; // Range (radius)
                int d = 4; // Damage (max, at center)
                for (int xi = -r; xi <= r; xi++) {
                    for (int yi = -r; yi <= r; yi++) {
                        float xDamageMult = abs(abs(xi) - r) / (float) r; // 0 to 1, peaking when xi=0 (center)
                        float yDamageMult = abs(abs(yi) - r) / (float) r;
                        int totalDamage = (int) (d * .5 * (xDamageMult + yDamageMult));
                        room.hurtSomethingAt(xi + x, yi + y, totalDamage, "Jeez, killed yourself with an Potion!", true, "arcane");
                        //System.out.println(x + xi + " " + (yi + y) + " given " + totalDamage + " damage");
                    }
                    //System.out.println();
                }
                break;
            case "MagicTaterChip": // Keeps your health up
                if (getTime() % 250 == 0) {
                    caster.restoreHealth(1, 10);
                } else {
                    System.out.println(getTime());
                }
                break;
            case "dillTaterChip": // Slows everything down
                if (firstUpdate) {
                    while (caster.frozen){
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ignore) {}
                    }
                    room.changeTimerSpeedsBy(10f);
                }
                break;
            case "fondantChunk": // Speeds everything up
                if (firstUpdate) {
                    while (caster.frozen){
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ignore) {}
                    }
                    room.changeTimerSpeedsBy(.3f);
                }
                break;
            case "fondantHunk": // Like fondantChunk, but even worse.  Probably fatal.
                if (firstUpdate) {
                    while (caster.frozen){
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ignore) {}
                    }
                    room.changeTimerSpeedsBy(.05f);
                }
                break;
            case "wrinkleInTime": // Slows down time for things near you
                if (firstUpdate) {
                    while (caster.frozen) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ignore) {
                        }
                    }
                }
                int range = 6; // Range (radius) (actual effective radius is one less)
                float max = 4f; // Time dilation (max, at center)
                for (int xi = -range; xi <= range; xi++) {
                    for (int yi = -range; yi <= range; yi++) {
                        float coeff = max;
                        if (abs(xi) >= range-1 || abs(yi) >= range-1) coeff = 1; // restore normalicy at fringes
                        List<GameObject> objectsHere = room.getObjectsAt(xi + x, yi + y);
                        for (GameObject object : objectsHere) {
                            if (!object.strClass.equals("Player")) {
                                object.setTimerToWeirdFrequency(coeff);
                            }
                        }
                    }
                }
                break;
        }
        firstUpdate = false;
    }

    private void onCancel(){
        cancelTimer();
        caster.fabulousMode = false;
        switch (name){
            case "wrinkleInTime": // Fall through
            case "fondantChunk": // fall through
            case "dillTaterChip":
                room.changeTimerSpeedsBy(1f);
        }
    }
}
