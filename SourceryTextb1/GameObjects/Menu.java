package SourceryTextb1.GameObjects;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import SourceryTextb1.GameObjects.Player;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Window;
import SourceryTextb1.art;

/**
 * Created by Jared on 6/20/2016.
 */
public class Menu {
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Item> spells = new ArrayList<>();
    private Player player;
    private ImageOrg org;
    private Window window;

    public Menu(Player play, ImageOrg orgo){
        player = play;
        org = orgo;
    }

    public void popUp (){
        art artsedo = new art();
        Layer txtBox = new Layer(art.strToArray(artsedo.topMenu), "Dialog", 13, 0, false, true);

        org.addLayer(txtBox);
        org.compileImage();

        Window window = org.getWindow();
        Dismissal keyListener = new Dismissal();
        window.txtArea.addKeyListener(keyListener); // Add key listeners.

        while (!keyListener.resume){
            try {
                Thread.sleep(300);
            } catch (InterruptedException ignored) {}
        }

        org.removeLayer("Dialog");
        window.txtArea.removeKeyListener(keyListener);
    }
    
}

class Item{
    public int amount = 0;
    public String name = "";
    public String description = "";
    
    public Item(int quantity, String theName, String itsDescription){
        amount = quantity;
        name = theName;
        description = itsDescription;
    }
    
    public int getQuant(){
        return amount;
    }

    public String getName(){
        return name;
    }
    public String getDesc(){
        return description;
    }
}

class Dismissal extends KeyAdapter {
    boolean resume = false;

    @Override
    public void keyPressed(KeyEvent event) {
        int key = event.getKeyCode();
        char ch = event.getKeyChar();
        if (key == KeyEvent.VK_ESCAPE || key == KeyEvent.VK_ENTER) {
            resume = true;
        }
    }
}