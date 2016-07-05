package SourceryTextb1.Rooms;

import SourceryTextb1.GameObjects.Food;
import SourceryTextb1.GameObjects.HUD;
import SourceryTextb1.GameObjects.Player;
import SourceryTextb1.GameObjects.Spike;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.art;

/**
 * @author riley-ubuntu
 * created on 4/6/16
 */
public class RoomThree extends Room {

    ImageOrg org;
    int maxH;
    int maxW;

    private void loop(Player play){
        int exitCode = 0;
        while(exitCode == 0){
            try {
                Thread.sleep(75);
                //System.out.println("I'm not dead yet! " + ii);
                updateObjs(75);
                play.update();//Update player
                //play.reportPos();
                org.compileImage();
                if (getCountOf("Food") == 0){
                    exitCode = 1;
                }
            } catch (InterruptedException ex) {}
        }
    }

    public void startup(ImageOrg org, Player player){
        Layer spells = new Layer(new String[maxH][maxW], "Spellz", true);
        org.addLayer(spells);

        super.playo = player;
        player.castingLayer = spells;
        super.baseHitMesh = new boolean[super.roomHeight][super.roomWidth];
        super.objHitMesh = new boolean[super.roomHeight][super.roomWidth];
        //emptyAllHitMeshes();
        String[][] test = makeABox(maxW,maxH);
        Layer lay1 = new Layer(test, "Test");
        org.addLayer(lay1);

        art arty = new art();
        String[][] castle = arty.strToArray(arty.castle1);
        //emptyAllHitMeshes();
        addToBaseHitMesh(castle, "#");
        Layer castleLayer = new Layer(castle, "castle");
        org.addLayer(castleLayer);

        String foodLayerName = "foooooooooood";
        Layer foodstuffs = new Layer(new String[maxH][maxW], foodLayerName);
        org.addLayer(foodstuffs);
        //ArrayList<Food> worldFoodSupply = new ArrayList<>();
        for (int i = 0 ; i < totalFood ; i++){
            Food aFood = new Food(org, foodLayerName, (Room)this);
            addObject(aFood);
        }
        super.playo = player;

        Layer playerLayer = new Layer(new String[maxH][maxW], player.getLayerName());
        org.addLayer(playerLayer);

        Layer HUDd = new Layer(new String[maxH][maxW], "HUD", false);
        org.addLayer(HUDd);
        HUD hud = new HUD(org, (Room)this, HUDd);
        addObject(hud);

        Layer spikeBed = new Layer(new String[maxH][maxW], "spikebed");
        org.addLayer(spikeBed);
        for (int i=0; i<20; i++){
            addObject(new Spike(org, this, 2*i, 8));
        }
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

    public RoomThree(ImageOrg orgo){
        org = orgo;
        maxH = org.getWindow().maxH();
        maxW = org.getWindow().maxW();
        super.totalFood = 99;
        super.roomHeight = maxH;
        super.roomWidth = maxW;
        super.index = 3;
    }

    private String[][] makeABox(int width, int height){
        String[][] output = new String[height][width];
        for(int ii = 0; ii < height; ii++){
            for (int iii = 0 ; iii < width; iii++){
                if (ii == 0 || ii == height - 1){
                    if (iii == 0 || iii == width - 1){
                        output[ii][iii] = "+"; //Corner
                    } else {
                        if (iii%2 == 0){
                            output[ii][iii] = "-"; //edging 1, horizontal
                        }else{
                            output[ii][iii] = "~"; //edging 2, horizontal
                        }
                    }
                } else {
                    if (iii == 0 || iii == width - 1){
                        if (ii%2 == 0){
                            output[ii][iii] = ";"; //edging 1, vertical
                        }else{
                            output[ii][iii] = ":"; //edging 2, vertical
                        }
                    } else if ((ii % 5 == 0  || ii % 5 ==1) && iii % 10 == 1){
                        output[ii][iii] = "-"; // tiling floor, centers
                    } else if (1 >= ii%5  && 1 >= -1 + iii%10){
                        output[ii][iii] = "_"; // stuff around center floortiles
                    } else {
                        output[ii][iii] = " "; // blank space
                    }
                }
            }
        }
        return output;
    }

}
