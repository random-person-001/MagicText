package MagicTextb2.Rooms;

import MagicTextb2.GameObjects.Food;
import MagicTextb2.GameObjects.HUD;
import MagicTextb2.GameObjects.Player;
import MagicTextb2.ImageOrg;
import MagicTextb2.Layer;
import MagicTextb2.art;

/**
 * Created by riley-ubuntu on 09-Jun-2016.
 */

public class BeginningRoom extends Room {
    ImageOrg org;
    int maxH;
    int maxW;

    private void loop(){
        for (int ii = 0 ; ii < 1000 ; ii++){
            try {
                Thread.sleep(75);
                //System.out.println("I'm not dead yet! " + ii);
                updateObjs();
                super.playo.update();//Update player
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
        String[] solids = {"╔","╗","═","╚","╝","║"};
        String[][] roomArr = art.strToArray(arty.smallRoom);
        addToBaseHitMesh(roomArr, solids);
        Layer lay1 = new Layer(roomArr, "base");
        org.addLayer(lay1);

        String foodLayerName = "foooooooooood";
        Layer foodstuffs = new Layer(new String[maxH][maxW], foodLayerName);
        org.addLayer(foodstuffs);
        Food aFood = new Food(org, foodLayerName, this);
        addObject(aFood);

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
    public void enter(){
        org.compileImage();
        super.playo.frozen = false;
        loop();
        super.playo.frozen = true;
        org.removeAllButPlayer(); //Cleanup, happens when loop is done.
        org.compileImage();
    }

    public BeginningRoom(ImageOrg orgo){
        org = orgo;
        maxH = 7;
        maxW = 10;
        super.roomHeight = maxH;
        super.roomWidth = maxW;
        super.index = 1;
    }

}

