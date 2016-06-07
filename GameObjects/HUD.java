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

        /*
        String[] testHUD = {"[","a","b","c","d","]"," ","[","e","f","g","h","]","","{","0","0","","_","_","_","_","_","_","_","_","_","_","}"};
        for (int it = 0 ; it < testHUD.length ; it ++) {
            orgo.editLayer(testHUD[it], loc, y, x + 8 + it);
        }
        */
    }
    
}
