package SourceryTextb1.GameObjects;

import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Window;
import SourceryTextb1.art;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 * A class to select something in your inventory
 * Created by riley on 12-Jun-2016.
 */
class Inventory{
    private Player player;
    private ImageOrg org;
    private Layer invLayer;
    private String layerName = "Inventory";
    private int selectX = 0;
    private int selectY = 0;
    private int[][] arrayOfStuff; // TODO make the array of things in inventory

    public Inventory(ImageOrg orgo, Player p){
        org = orgo;
        player = p;
        art arty = new art();
        invLayer = new Layer(art.strToArray(arty.inventoryBkgd), layerName, false, true);
    }

    public void show() {
        org.addLayer(invLayer); // Background grid
        Layer selector = new Layer(new String[22][46], "selector", false, false);
        org.addLayer(selector); // Foreground stuff

        Window window = org.getWindow();
        Navigator keyListener = new Navigator(this);
        window.txtArea.addKeyListener(keyListener); // Add key listeners.
        while (!keyListener.resume){
            try {
                org.compileImage();
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.exit(0);
            }
        }
        org.removeLayer("selector");
        org.removeLayer(layerName);
        window.txtArea.removeKeyListener(keyListener);
    }

    private int getObject(int x,int y){
        return arrayOfStuff[x][y];
    }

    void keyPressed(char c){
        System.out.println(c);
        switch (c){
            case '©': // Up
                selectY++;
                break;
            case '®': // Down
                selectY--;
                break;
            case 'µ': // Left
                selectX--;
                break;
            case 'æ': // Right
                selectX++;
                break;
            case '1': // Set primary
                player.setPrimarySpell("Test");
                break;
            case '2': // Set secondary
                player.setSecondarySpell("Second");
                break;
            default:
                break;
        }
        org.compileImage();
    }

}


class Navigator extends KeyAdapter {
    private Inventory inv;
    public boolean resume = false;

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