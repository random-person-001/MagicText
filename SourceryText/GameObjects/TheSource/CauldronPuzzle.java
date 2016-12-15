package SourceryText.GameObjects.TheSource;

import SourceryText.Art;
import SourceryText.GameObjects.Player;
import SourceryText.ImageOrg;
import SourceryText.Layer;
import SourceryText.Rooms.Room;

/**
 * Created by Jared on 12-Dec-16.
 */
public class CauldronPuzzle {

    private Room container;
    private ImageOrg org;
    private Player player;

    private Layer selectorLayer = new Layer(new String[1][1], "selector", 2, 6, false, true, true);
    private Layer menuLayer;

    private Layer entryLayer;

    private int selectorY = 5;
    private String[] ingredientsAdded = new String[6];
    private int addingPos = 0;

    private boolean inMenu = false;

    public CauldronPuzzle (Room creator, Player operator){
        container = creator;
        org = creator.org;
        player = operator;

        Art artImport = new Art();
        menuLayer = new Layer(Art.strToArray(artImport.witchHutCauldronUI), "menuLayer", 0, 4, false, true, true);

        entryLayer = new Layer(new String[6][18], "entryLayer", 27, 6, false, true, true);
        entryLayer.clear();

        selectorLayer.setStr(0,0,">");
    }

    public void setOperator(Player operator){
        player = operator;
    }

    public void menuStartup(){
        if (!inMenu) {
            org.addLayer(menuLayer);
            org.addLayer(selectorLayer);
            org.addLayer(entryLayer);

            player.frozen = true;
            inMenu = true;
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
                    if (selectorY < 6) selectorY = 14;
                    org.getLayer("selector").setPos(2, selectorY);
                    break;
                case "down":
                    selectorY++;
                    if (selectorY > 14) selectorY = 6;
                    org.getLayer("selector").setPos(2, selectorY);
                    break;
            }
            //System.out.printf("[Cauldron] SelectorY: %1$d\n", selectorY);
        }
    }

    private void enterCheck(){
        switch (selectorY){
            case 6:
                addIngredient("Grape of Good Hope");
                break;
            case 7:
                addIngredient("Chichen Pizza");
                break;
            case 8:
                addIngredient("Gordian Nut");
                break;
            case 9:
                addIngredient("Sistine Apple");
                break;
            case 10:
                addIngredient("Mesopotato");
                break;
            case 11:
                addIngredient("Bering Date");
                break;
            case 12:
                addingPos = 0;
                ingredientsAdded = new String[6];
                org.clearLayer("entryLayer");
                break;
            case 14:
                Layer grabEntries = org.getLayer("entryLayer");
                if (grabEntries != null) entryLayer = grabEntries;
                org.removeLayer("selector");
                org.removeLayer("menuLayer");
                org.removeLayer("entryLayer");
                player.frozen = false;
                inMenu = false;
                break;
        }
    }

    private void addIngredient(String name){
        if (addingPos < 6) {
            ingredientsAdded[addingPos] = name; //Add entry to data

            char[] nameElements = name.toCharArray();
            int charX = 0;
            for (char c : nameElements) { //Display ingredient in list
                org.editLayer(String.valueOf(c), "entryLayer", addingPos, charX);
                charX++;
            }

            addingPos++; //Add position counter for next ingredient
        }
    }
}
