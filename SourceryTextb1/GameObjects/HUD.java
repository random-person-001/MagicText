/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1.GameObjects;

import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;

import static java.lang.Math.abs;

/**
 * A nice heads-up display for stats on the Player
 * @author 119184
 */
public class HUD extends GameObject{
    String layerName;
    private String[] spell1Name = new String[6];
    private String[] spell2Name = new String[6];
    private int loc;

    public HUD (ImageOrg org, Room theRoom, Layer place){
        super.strClass = "HUD";
        orgo = org;
        room = theRoom;
        layerName = place.getName();

        setupTimer(100);
    }

    /** Take a string, and convert the first six characters in it to an array, filling with spaces when necessary
     * @param s string to arrayize
     * @return a String[][] with spaces at the end if needed
     */
    private String [] bufferWithSpaces(String s){
        String[] returny = new String[6];
        for (int i=0; i<6; i++){
            char c;
            try {
                c = s.charAt(i);
            }
            catch (StringIndexOutOfBoundsException e){
                c = ' ';
            }
            returny[i] = String.valueOf(c);
        }
        return returny;
    }

    private String [] convertIcon(String icon){
        String[] result = new String[6];
        int cut = icon.length();
        if (cut > 5) cut = 5;
        for (int ic = 0; ic < cut; ic++){
            char c = icon.charAt(ic);
            result[ic] = String.valueOf(c);
        }
        return result;
    }
    
    @Override
    public void update(){  // Edit layer acts after stuff.
        /*
        spell1Name = bufferWithSpaces(room.getPlayer().getPrimarySpell());
        spell2Name = bufferWithSpaces(room.getPlayer().getSecondarySpell());
        */

        spell1Name = convertIcon(room.getPlayer().getPrimarySpell());
        spell2Name = convertIcon(room.getPlayer().getSecondarySpell());

        loc = orgo.getPosLayer(layerName);
        orgo.getLayer(loc).clear();
        x = 0;
        drawLayer();
    }

    /**
     * Place a character on the layer, one over from where the last one was placed. (a fairly specialized fn)
     * @param newChar a single-char long String to place in the Layer at the next spot.
     */
    private void putChar(String newChar){
        orgo.editLayer(newChar, loc, 0, x);
        x++;
    }

    /**
     * Edit the layer to put all the stats and stuff on
     */
    private void drawLayer(){
        loc = orgo.getPosLayer(layerName);

        putChar("[");

        // Your health
        String healthValue = String.valueOf(room.playo.getHealth());
        putChar(String.valueOf(healthValue.charAt(0)));
        putChar(String.valueOf(healthValue.charAt(1)));
        for (int ii = 0; ii < 10 ; ii++){
            int fillPoint = (int)Math.ceil(((float)room.playo.getHealth() / (float)room.playo.maxHP) * 10);
            if (fillPoint > 10 && ii < fillPoint - 10){
                putChar("#");
            } else if (ii < fillPoint){
                putChar("+");
            } else {
                putChar("_");
            }
        }
        putChar("]");
        x++;

        // Spell 1
        putChar("(");
        for (int ii = 0 ; ii < 5; ii++){
            putChar(spell1Name[ii]);
        }
        putChar(")");
        x++;

        // Spell 2
        putChar("(");
        for (int ii = 0 ; ii < 5; ii++){
            putChar(spell2Name[ii]);
        }
        putChar(")");
        x++;

        // Mana count
        putChar("{");
        putChar(Integer.toString(abs(room.playo.mana / 10)));
        putChar(Integer.toString(abs(room.playo.mana / 1 - 10*(room.playo.mana / 10))));

        // Mana bar
        for (int ii = 0; ii < 10 ; ii++){
            int fillPoint = (int)Math.ceil(((float)room.playo.mana / (float)room.playo.maxMana) * 10);
            if (ii < fillPoint){
                putChar("=");
            } else if (ii < (int)Math.ceil(((float)(2000 - room.playo.manaWait) / 2000.0f) * 10)){
                putChar("_");
            } else {
                putChar(" ");
            }
        }
        putChar("}");

        /*
        for (int ii = 0 ; ii < 45; ii++){
            if (ii%5 == 0){
                putChar("|", ii);
            } else {
                putChar(" ", ii);
            }
        }
        */
    }
    
}
