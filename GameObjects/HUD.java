/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MagicTextb2.GameObjects;

import MagicTextb2.ImageOrg;
import MagicTextb2.Layer;
import MagicTextb2.Rooms.Room;

import static java.lang.Math.abs;

/**
 *
 * @author 119184
 */
public class HUD extends GameObject{
    String layerName;
    String spell1Name = "     ";
    String spell2Name = "     ";

    public HUD (ImageOrg org, Room theRoom, Layer place){
        super.strClass = "HUD";
        orgo = org;
        room = theRoom;
        layerName = place.getName();
    }
    
    @Override
    public void update(){  // Edit layer acts after stuff.
        //y = orgo.getCamX();
        //x = orgo.getCamY();
        y = (y<0)? 0 : y;
        x = (x<0)? 0 : x;
        
        int pos = (orgo.getPosLayer(layerName));
        orgo.getLayer(pos).clear();
        createLayer(pos);
    }
    
    private void createLayer(int loc){
        if (room.foodEaten < 0) {
            orgo.editLayer("[", loc, y, x + 0);
            orgo.editLayer("-", loc, y, x + 1);
        }else{
            orgo.editLayer(" ", loc, y, x + 0);
            orgo.editLayer("[", loc, y, x + 1);
        }

        orgo.editLayer(Integer.toString(abs(room.foodEaten / 10)), loc, y , x+2);
        orgo.editLayer(Integer.toString(abs(room.foodEaten / 1 - 10*(int)(room.foodEaten / 10))), loc, y , x+3);
        orgo.editLayer("]", loc, y , x+4);

        orgo.editLayer("(", loc, y, x + 6);
        for (int ii = 0 ; ii < 5; ii++){
            orgo.editLayer(String.valueOf(spell1Name.charAt(ii)), loc, y, x+7+ii);
        }

        orgo.editLayer(")", loc, y, x + 12);
        orgo.editLayer("", loc, y, x + 13);
        orgo.editLayer("(", loc, y, x + 14);
        for (int ii = 0 ; ii < 5; ii++){
            orgo.editLayer(String.valueOf(spell1Name.charAt(ii)), loc, y, x+15+ii);
        }
        orgo.editLayer(")", loc, y, x+21);

        orgo.editLayer("{", loc, y, x+23);
        orgo.editLayer(Integer.toString(abs(room.playo.mana / 10)), loc, y , x+24);
        orgo.editLayer(Integer.toString(abs(room.playo.mana / 1 - 10*(int)(room.playo.mana / 10))), loc, y , x+25);

        for (int ii = 0; ii < 10 ; ii++){
            if (ii < (((float)room.playo.mana / (float)room.playo.maxMana) * 10)){
                orgo.editLayer("=", loc, y, x+26+ii);
            } else {
                orgo.editLayer("_", loc, y, x+26+ii);
            }
        }

        orgo.editLayer("}", loc, y, x+36);
    }
    
}
