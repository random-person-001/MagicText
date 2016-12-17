package SourceryText.GameObjects.TheSource;

import SourceryText.Art;
import SourceryText.GameObjects.Item;
import SourceryText.GameObjects.Player;
import SourceryText.ImageOrg;
import SourceryText.Layer;
import SourceryText.Rooms.Room;
import SourceryText.SpecialText;

import java.awt.*;

/**
 * Created by Jared on 12-Dec-16.
 */
public class CauldronPuzzle {

    private ImageOrg org;
    private Player player;

    private final int CORNER_X = 7;
    private final int CORNER_Y = 5;

    private Layer selectorLayer = new Layer(new String[1][1], "selector", CORNER_X + 2, CORNER_Y + 2, false, true, true);
    private Layer menuLayer;

    private int selectorY = 5;

    private boolean inMenu = false;

    private boolean puzzleSolved = false;
    private int numberOfFailures = 0;

    private Layer grapeLayer = new Layer(new String[1][18], "Grape of Good Hope", CORNER_X + 7, CORNER_Y + 2, false, true, true);
    private Layer nutLayer   = new Layer(new String[1][17], "Gordian Nut", CORNER_X + 7, CORNER_Y + 3, false, true, true);
    private Layer appleLayer = new Layer(new String[1][17], "Sistine Apple", CORNER_X + 7, CORNER_Y + 4, false, true, true);
    private Layer taterLayer = new Layer(new String[1][17], "Mesopotato", CORNER_X + 7, CORNER_Y + 5, false, true, true);
    private Layer dateLayer  = new Layer(new String[1][17], "Bering Date", CORNER_X + 7, CORNER_Y + 6, false, true, true);
    private Layer pizzaLayer = new Layer(new String[1][17], "Chichen Pizza", CORNER_X + 7, CORNER_Y + 7, false, true, true);

    private Layer[] layerStack = {grapeLayer, nutLayer, appleLayer, taterLayer, dateLayer, pizzaLayer};
    private Layer pickedLayer;
    private int pickedLoc;

    public CauldronPuzzle (Room creator, Player operator){
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

            /* //This is cursed; it doesn't work for whatever reason
            System.out.print("[Cauldron] Layers in layerStack");
            for (Layer item : layerStack){
                System.out.printf(": %1$s ", item.getName());
                org.addLayer(item);
            }
            System.out.print("\n");
            */

            org.addLayer(grapeLayer);
            org.addLayer(appleLayer);
            org.addLayer(pizzaLayer);
            org.addLayer(taterLayer);
            org.addLayer(nutLayer);
            org.addLayer(dateLayer);

            player.frozen = true;
            inMenu = true;

            if (!puzzleSolved) {
                clearOutput();
                numberOfFailures = 0;
            }

            selectorY = 2;
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
                pickLayerAt(0); //pickLayer ran according to cursor position
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
            case 8:
                brewPotion();
                break;
            case 9:  //EXIT
                org.removeLayer("selector");
                org.removeLayer("menuLayer");

                if (pickedLayer != null){
                    pickedLayer.setAllFg(Color.WHITE);
                    pickedLayer = null;
                }

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
        if (!puzzleSolved) {
            if (pickedLayer == null) {
                pickedLayer = org.getLayer(layerStack[loc].getName());
                pickedLoc = loc;
                pickedLayer.setAllFg(new Color(200, 200, 255));
            } else {
                Layer justPicked = org.getLayer(layerStack[loc].getName());
                Layer temp = justPicked.createDuplicate(); //Layer picked just now cloned

                justPicked.setPos(justPicked.getX(), pickedLayer.getY()); //Switch layer y positions
                pickedLayer.setPos(pickedLayer.getX(), temp.getY());

                layerStack[loc] = pickedLayer; //Switch locations in layerStack
                layerStack[pickedLoc] = temp;

                justPicked.setAllFg(Color.WHITE); //Undo coloration
                pickedLayer.setAllFg(Color.WHITE);

                pickedLayer = null;
            }
        }
    }
    
    private Layer retrieveLayerFromOrg (Layer localLayer){
        Layer outsideLayer = org.getLayer(localLayer.getName());
        if (outsideLayer != null) {
            org.removeLayer(localLayer);
            return outsideLayer.createDuplicate();
        }
        else
            System.out.println("[Cauldron] Null layer retrieved for " + localLayer.getName());
        return localLayer;
    }

    private void brewPotion(){
        if (player != null && !puzzleSolved) {
            if (layerStack[0].getName() == "Chichen Pizza" && layerStack[1].getName() == "Bering Date" && layerStack[2].getName() == "Mesopotato" &&
            layerStack[3].getName() == "Grape of Good Hope" && layerStack[4].getName() == "Gordian Nut" && layerStack[5].getName() == "Sistine Apple") {

                Item solutionPotion = new Item("Clone Buster", "The perfect solution to a\n clone problem.\n\nHowever, it can be used" +
                        "\n improperly, so you should\n give this to someone who\n can use it responsibly.", "item");
                player.addItem(solutionPotion);
                postSolve();
            } else {
                Item failurePotion = new Item("Pointless Brew", "A textbook example of\n what not do when\n brewing potions.\n\nYou should feel somewhat\n ashamed of this potion.", "item");
                failurePotion.healItemDefine(0, 9999);
                player.addItem(failurePotion);
                numberOfFailures++;
                String toWrite = "Brewing failed!";
                if (numberOfFailures > 1){
                    toWrite += "(" + numberOfFailures + ")";
                }
                writeToOutput(toWrite, new Color(225, 125, 125));
            }
        }
    }

    private void postSolve(){
        puzzleSolved = true;
        grapeLayer.setAllFg(new Color(100, 100, 100));
        appleLayer.setAllFg(new Color(100, 100, 100));
        taterLayer.setAllFg(new Color(100, 100, 100));
        nutLayer.setAllFg(new Color(100, 100, 100));
        pizzaLayer.setAllFg(new Color(100, 100, 100));
        dateLayer.setAllFg(new Color(100, 100, 100));
        writeToOutput("Clone Buster created!", Color.green);
    }

    private void writeToOutput(String message, Color messageColor){
        for (int ii = 0; ii < 29; ii++){
            if (ii < message.length()){
                org.editLayer(new SpecialText(message.substring(ii, ii+1), messageColor), "menuLayer", 11, 2 + ii);
            } else {
                org.editLayer(" ", "menuLayer", 11, 2 + ii);
            }
        }
    }

    private void clearOutput(){
        for (int ii = 0; ii < 29; ii++){
            menuLayer.setStr(11, ii+2, " ");
        }
    }
}
