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

    public CauldronPuzzle (Room creator, Player operator){
        container = creator;
        org = creator.org;
        player = operator;

        Art artImport = new Art();
        menuLayer = new Layer(Art.strToArray(artImport.witchHutCauldronUI), "menuLayer", 0, 4, false, true, true);

        entryLayer = new Layer(new String[5][15], "entryLayer", 27, 6, false, true, true);
        entryLayer.clear();

        selectorLayer.setStr(0,0,">");
    }

    public void menuStartup(){
        org.addLayer(menuLayer);
        org.addLayer(selectorLayer);
        org.addLayer(entryLayer);

        entryLayer.clear();

        player.frozen = true;
    }

    public void getInput(String keyAct){
        switch(keyAct){
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

    private void enterCheck(){
        switch (selectorY){
            case 14:
                org.removeLayer("selector");
                org.removeLayer("menuLayer");
                org.removeLayer("entryLayer");
                player.frozen = false;
                break;
        }
    }
}
