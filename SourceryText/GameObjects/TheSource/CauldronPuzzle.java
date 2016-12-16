package SourceryText.GameObjects.TheSource;

import SourceryText.Art;
import SourceryText.GameObjects.Player;
import SourceryText.ImageOrg;
import SourceryText.Layer;
import SourceryText.Rooms.Room;

import java.awt.*;

/**
 * Created by Jared on 12-Dec-16.
 */
public class CauldronPuzzle {

    private Room container;
    private ImageOrg org;
    private Player player;

    private final int CORNER_X = 7;
    private final int CORNER_Y = 5;

    private Layer selectorLayer = new Layer(new String[1][1], "selector", CORNER_X + 2, CORNER_Y + 2, false, true, true);
    private Layer menuLayer;

    private int selectorY = 5;

    private boolean inMenu = false;

    private Layer grapeLayer = new Layer(new String[1][18], "Grape of Good Hope", CORNER_X + 7, CORNER_Y + 2, false, true, true);
    private Layer pizzaLayer = new Layer(new String[1][17], "Chichen Pizza", CORNER_X + 7, CORNER_Y + 3, false, true, true);
    private Layer appleLayer = new Layer(new String[1][17], "Sistine Apple", CORNER_X + 7, CORNER_Y + 4, false, true, true);
    private Layer taterLayer = new Layer(new String[1][17], "Mesopotato", CORNER_X + 7, CORNER_Y + 5, false, true, true);
    private Layer nutLayer   = new Layer(new String[1][17], "Gordian Nut", CORNER_X + 7, CORNER_Y + 6, false, true, true);
    private Layer dateLayer  = new Layer(new String[1][17], "Bering Date", CORNER_X + 7, CORNER_Y + 7, false, true, true);

    private Layer[] layerStack = {grapeLayer, pizzaLayer, appleLayer, taterLayer, nutLayer, dateLayer};
    private Layer pickedLayer;
    private int pickedLoc;

    public CauldronPuzzle (Room creator, Player operator){
        container = creator;
        org = creator.org;
        player = operator;

        Art artImport = new Art();
        menuLayer = new Layer(Art.strToArray(artImport.witchHutCauldronUI), "menuLayer", CORNER_X, CORNER_Y, false, true, true);

        layerInitName(grapeLayer);
        layerInitName(pizzaLayer);
        layerInitName(taterLayer);
        layerInitName(appleLayer);
        layerInitName(nutLayer);
        layerInitName(dateLayer);

        selectorLayer.setStr(0,0,">");
    }

    public void setOperator(Player operator){
        player = operator;
    }

    public void menuStartup(){
        if (!inMenu) {
            org.addLayer(menuLayer);
            org.addLayer(selectorLayer);

            org.addLayer(grapeLayer);
            org.addLayer(appleLayer);
            org.addLayer(pizzaLayer);
            org.addLayer(taterLayer);
            org.addLayer(nutLayer);
            org.addLayer(dateLayer);

            player.frozen = true;
            inMenu = true;
        }
    }

    private void layerInitName(Layer layer){
        String writing = layer.getName();
        char[] writingElements = writing.toCharArray();
        int charX = 0;
        for (char c : writingElements){
            layer.setStr(0, charX, String.valueOf(c));
            charX++;
        }
    }

    public void getInput(String keyAct){
        if (inMenu) {
            switch (keyAct) {
                case "enter":
                    enterCheck();
                    break;
                case "up":
                    selectorY--;
                    if (selectorY < 2) selectorY = 9;
                    org.getLayer("selector").setPos(CORNER_X + 2, CORNER_Y + selectorY);
                    break;
                case "down":
                    selectorY++;
                    if (selectorY > 9) selectorY = 2;
                    org.getLayer("selector").setPos(CORNER_X + 2, CORNER_Y + selectorY);
                    break;
            }
            //System.out.printf("[Cauldron] SelectorY: %1$d\n", selectorY);
        }
    }

    private void enterCheck(){
        switch (selectorY){
            case 2:
                pickLayerAt(0);
                break;
            case 3:
                pickLayerAt(1);
                break;
            case 4:
                pickLayerAt(2);
                break;
            case 5:
                pickLayerAt(3);
                break;
            case 6:
                pickLayerAt(4);
                break;
            case 7:
                pickLayerAt(5);
                break;
            case 9:
                org.removeLayer("selector");
                org.removeLayer("menuLayer");

                grapeLayer = retrieveLayerFromOrg(grapeLayer);
                appleLayer = retrieveLayerFromOrg(appleLayer);
                pizzaLayer = retrieveLayerFromOrg(pizzaLayer);
                taterLayer = retrieveLayerFromOrg(taterLayer);
                nutLayer   = retrieveLayerFromOrg(nutLayer);
                dateLayer  = retrieveLayerFromOrg(dateLayer);
                
                player.frozen = false;
                inMenu = false;
                break;
        }
    }

    private void pickLayerAt (int loc){
        if (pickedLayer == null){
            pickedLayer = org.getLayer(layerStack[loc].getName());
            pickedLoc = loc;
            pickedLayer.setAllFg(new Color(200, 200, 255));
        } else {
            Layer justPicked = org.getLayer(layerStack[loc].getName());
            Layer temp = justPicked.createDuplicate(); //Layer picked just now cloned

            justPicked.setPos(justPicked.getX(), pickedLayer.getY()); //Switch layer y positions
            pickedLayer.setPos(pickedLayer.getX(), temp.getY());

            layerStack[loc] = pickedLayer;
            layerStack[pickedLoc] = temp;

            justPicked.setAllFg(Color.WHITE);
            pickedLayer.setAllFg(Color.WHITE);

            pickedLayer = null;
        }
    }
    
    private Layer retrieveLayerFromOrg (Layer localLayer){
        Layer outsideLayer = org.getLayer(localLayer.getName());
        if (outsideLayer != null) {
            org.removeLayer(localLayer);
            return outsideLayer;
        }
        else
            System.out.println("[Cauldron] Null layer retrieved for " + localLayer.getName());
        return localLayer;
    }
}
