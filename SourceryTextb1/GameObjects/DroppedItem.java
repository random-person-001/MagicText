package SourceryTextb1.GameObjects;

import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;

/**
 * A dropped item that will put itself in the player's inventory when stepped on.
 * Created by riley on 13-Jun-2016.
 */
public class DroppedItem extends GameObject{
    private Player player;
    private String pickUpMessage;
    private String itemName;
    private String char1;
    private String layerName;
    private Layer thisLayer;

    public DroppedItem(Player playo, ImageOrg org, String messageOnPickup, String callMyself, String layername, String displayChar, int setx,int sety){
        strClass = "DroppedItem";
        player = playo;
        orgo = org;
        pickUpMessage = messageOnPickup;
        itemName = callMyself;
        char1 = displayChar;
        layerName = layername;
        x = setx;
        y = sety;
        thisLayer = new Layer(new String[y+2][x+2], layerName, x, y);
    }

    void pickup(){
    }

    @Override
    public void update(){
        orgo.editLayer(char1, layerName, y, x);
        if (x == player.getX() && y == player.getY()){
            if (player.inventory.containsKey(itemName)){
                int n = player.inventory.get(itemName);
                player.inventory.put(itemName, n+1);
            }else{
                player.inventory.put(itemName,1);
            }
            orgo.editLayer(" ", layerName, y, x);
        }
    }
}
