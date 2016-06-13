package SourceryTextb1.GameObjects;

import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Window;
import SourceryTextb1.art;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;


/**
 * A class to select something in your inventory
 * Created by riley on 12-Jun-2016.
 */
class Inventory{
    private Player player;
    private ImageOrg org;
    private Layer invBkgdLayer;
    private Layer itemsLayer;
    private art arty = new art();
    private String layerName = "Inventory";
    private int selectX = 1;
    private int selectY = 1;
    private int[][] arrayOfStuff; // TODO make the array of things in inventory

    public Inventory(ImageOrg orgo, Player p){
        org = orgo;
        player = p;
    }

    public void show() {
        player.frozen = true;
        invBkgdLayer = new Layer(art.strToArray(new art().inventoryBkgd), layerName, false, true);
        org.addLayer(invBkgdLayer); // Background grid
        itemsLayer = populateItems();
        org.addLayer(itemsLayer);
        Layer selector = new Layer(new String[22][46], "selector", false, false);
        org.addLayer(selector); // Foreground stuff

        Window window = org.getWindow();
        Navigator keyListener = new Navigator(this);
        window.txtArea.addKeyListener(keyListener); // Add key listeners.
        while (!keyListener.resume){
            try {
                updateSelector();
                org.compileImage();
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.exit(0);
            }
        }
        org.removeLayer("selector");
        org.removeLayer("invItems");
        org.removeLayer(layerName);
        window.txtArea.removeKeyListener(keyListener);
        player.frozen = false;
    }

    private Layer populateItems(){
        Layer itemsLayer = new Layer(new String[22][47], "invItems", false, false);
        if (player.inventory.containsKey("Book") && player.inventory.get("Book")>0){
            putItem(itemsLayer, arty.oldBook, 1, 1);
            putItem(itemsLayer, arty.oldBook, 4, 3);
            putItem(itemsLayer, arty.oldBook, 2, 5);
        }
        if (player.inventory.containsKey("Spark") && player.inventory.get("Spark")>0){
            putItem(itemsLayer, arty.spark, 1, 2);
        }
        return itemsLayer;
    }
    private void updateSelector(){
        int indexY = 4*(selectY-1) + 1;
        int indexX = 8*(selectX-1);
        System.out.println("X: " + indexX + "     Y:" + indexY);
        org.clearLayer("selector");
        org.editLayer("|", "selector", indexY, indexX);
        org.editLayer("|", "selector", indexY+1, indexX);
        org.editLayer("|", "selector", indexY+2, indexX);
        org.editLayer("|", "selector", indexY, indexX+7);
        org.editLayer("|", "selector", indexY+1, indexX+7);
        org.editLayer("|", "selector", indexY+2, indexX+7);
        org.compileImage();
    }

    private void putItem (Layer lay, String thing, int indexX, int indexY){
        indexY = 4*(indexY-1) + 1;
        indexX = 8*(indexX-1) + 1;
        String[][] arrThing = art.strToArray(thing);
        System.out.println(Arrays.deepToString(arrThing));
        for (int i = 0; i<arrThing.length; i++){
            for (int j = 0; j<arrThing[0].length; j++){
                lay.setStr(i+indexY, j+indexX, arrThing[i][j]);
            }
        }
    }
    private void putPrimary (String thing){
        int y = 19;
        int x = 30;
        String[][] arrThing = art.strToArray(thing);
        for (int i = 0; i<arrThing.length; i++){
            for (int j = 0; j<arrThing[0].length; j++){
                org.editLayer(arrThing[i][j], "invItems", i+y, j+x);//4,4);//i+y, j+x);
            }
        }
    }
    private void putSecondary (String thing){
        int y = 19;
        int x = 38;
        String[][] arrThing = art.strToArray(thing);
        for (int i = 0; i<arrThing.length; i++){
            for (int j = 0; j<arrThing[0].length; j++){
                org.editLayer(arrThing[i][j], "invItems", i+y, j+x);
            }
        }
    }
    String inveuntoryBkgd =
                    "///.........  I N V E N T O R Y  ..........\\\\\\\n" +
                    ".      ..      ..      ..      ..      ..    .\n" +
                    ".      ..      ..      ..      ..      ..    .\n" +
                    ".      ..      ..      ..      ..      ..    .\n" +
                    ".........................................    .\n" +
                    ".      ..      ..      ..      ..      ..    .\n" +
                    ".      ..      ..      ..      ..      ..    .\n" +
                    ".      ..      ..      ..      ..      ..    .\n" +
                    ".........................................    .\n" +
                    ".      ..      ..      ..      ..      ..    .\n" +
                    ".      ..      ..      ..      ..      ..    .\n" +
                    ".      ..      ..      ..      ..      ..    .\n" +
                    ".........................................    .\n" +
                    ".      ..      ..      ..      ..      ..    .\n" +
                    ".      ..      ..      ..      ..      ..    .\n" +
                    ".      ..      ..      ..      ..      ..    .\n" +
                    "..............................................\n" +
                    ".      ..      ..      ...|..EQUIPPED SPELLS..\n" +
                    ".      ..      ..      ...|....1st......2nd...\n" +
                    ".      ..      ..      ...|...      ..      ..\n" +
                    "..........................|...      ..      ..\n" +
                    "..........................|...      ..      ..\n" +
                    "\\\\\\........................................///\n";


    private int getObject(int x,int y){
        return arrayOfStuff[x][y];
    }

    void keyPressed(char c){
        System.out.println(c);
        switch (c){
            case '©': // Up
                if (selectY > 1)
                    selectY--;
                break;
            case '®': // Down
                if (selectY < 5 && !(selectX >= 4 && selectY == 4 ))
                    selectY++;
                break;
            case 'µ': // Left
                if (selectX > 1)
                    selectX--;
                break;
            case 'æ': // Right
                if (selectX < 5 && !(selectY == 5 && selectX == 3 ))
                    selectX++;
                break;
            case '1': // Set primary
                putPrimary(arty.spark);
                player.setPrimarySpell("Spark");
                break;
            case '2': // Set secondary
                putSecondary(arty.oldBook);
                player.setSecondarySpell("Book");
                break;
            default:
                break;
        }
    }

}


class Navigator extends KeyAdapter {
    private Inventory inv;
    boolean resume = false;

    Navigator(Inventory inventory) {
        inv = inventory;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        int key = event.getKeyCode();
        if (key == KeyEvent.VK_UP) {
            inv.keyPressed('©');
        }
        if (key == KeyEvent.VK_DOWN) {
            inv.keyPressed('®');
        }
        if (key == KeyEvent.VK_LEFT) {
            inv.keyPressed('µ');
        }
        if (key == KeyEvent.VK_RIGHT) {
            inv.keyPressed('æ');
        }
        if (key == '1') {
            inv.keyPressed('1');
        }
        if (key == '2') {
            inv.keyPressed('2');
        }
        if (key == KeyEvent.VK_ESCAPE || key == KeyEvent.VK_ENTER || event.getKeyChar() == 'w') {
            resume = true;
        }
    }
}