/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MagicTextb2.Rooms;

import MagicTextb2.GameObjects.Food;
import MagicTextb2.GameObjects.HUD;
import MagicTextb2.ImageOrg;
import MagicTextb2.Layer;
import MagicTextb2.GameObjects.Player;
import MagicTextb2.art;

/**
 *
 * @author 119184
 */
public class RoomTwo extends Room {
    
    ImageOrg org;
    int maxH;
    int maxW;
    
    private void loop(Player play){
        for (int ii = 0 ; ii < 1000 ; ii++){
            try {
                Thread.sleep(75);
                //System.out.println("I'm not dead yet! " + ii);
                updateObjs(75);
                play.update();//Update player
                //play.reportPos();
                org.compileImage();
                if (getFoodCount() == 0){
                    return;
                }
            } catch (InterruptedException ex) {}
        }
    }
    
    public void startup(ImageOrg org, Player player){
        super.playo = player;
        super.baseHitMesh = new boolean[super.roomHeight][super.roomWidth];
        super.objHitMesh = new boolean[super.roomHeight][super.roomWidth];
        emptyHitMesh();
        art arty = new art();
        String[][] roomArr = art.strToArray(arty.dualRoom);
        addToBaseHitMesh(roomArr, "#");
        addToBaseHitMesh(roomArr, "<");
        addToBaseHitMesh(roomArr, ">");
        addToBaseHitMesh(roomArr, "V");
        addToBaseHitMesh(roomArr, "Λ");
        Layer lay1 = new Layer(roomArr, "base");
        org.addLayer(lay1);

        String foodLayerName = "foooooooooood";
        Layer foodstuffs = new Layer(new String[maxH][maxW], foodLayerName);
        org.addLayer(foodstuffs);
        //ArrayList<Food> worldFoodSupply = new ArrayList<>();
        for (int i = 0 ; i < totalFood ; i++){
            Food aFood = new Food(org, foodLayerName, (Room)this);
            addObject(aFood);
        }
        super.playo = player;
        
        Layer playerLayer = new Layer(new String[maxH][maxW], player.layerName);
        org.addLayer(playerLayer);
        
        Layer HUDd = new Layer(new String[maxH][maxW], "HUD", false);
        org.addLayer(HUDd);
        HUD hud = new HUD(org, (Room)this, HUDd);
        addObject(hud);
    }
    
    /** 
     * Enter the room. IE, start loops and stuff now.
     */
    public void enter(Player player){
        org.compileImage();
        player.frozen = false;
        loop(player);
        player.frozen = true;
        org.removeAllButPlayer(); //Cleanup, happens when loop is done.
        org.compileImage();
    }
    
    public RoomTwo(ImageOrg orgo){
        org = orgo;
        maxH = 40;
        maxW = 70; //69
        super.totalFood = 99;
        super.roomHeight = maxH;
        super.roomWidth = maxW;
        super.index = 2;
    }

    private String[][] makeABox(int width, int height){
        String[][] output = new String[height][width];
        for(int ii = 0; ii < height; ii++){
            for (int iii = 0 ; iii < width; iii++){
                if (ii == 0 || ii == height - 1){
                    if (iii == 0 || iii == width - 1){
                        output[ii][iii] = "#"; //Corner
                    } else {
                        if (iii%2 == 0){
                            output[ii][iii] = "<"; //edging 1, horizontal
                        }else{
                            output[ii][iii] = ">"; //edging 2, horizontal
                        }
                    }
                } else {
                    if (iii == 0 || iii == width - 1){
                        if (ii%2 == 0){
                            output[ii][iii] = "V"; //edging 1, vertical
                        }else{
                            output[ii][iii] = "Λ"; //edging 2, vertical
                        }
                    } else if (ii % 4 == 0 && iii % 8 == 0){
                        output[ii][iii] = "`"; // tiling floor
                    } else {
                        output[ii][iii] = " "; // blank space
                    }
                }
            }
        }
        return output;
    }

}
