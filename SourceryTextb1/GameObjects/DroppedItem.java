package SourceryTextb1.GameObjects;

import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;

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

    public DroppedItem(Room room, ImageOrg org, String messageOnPickup, String callMyself, String layername, String displayChar, int setx, int sety){
        strClass = "DroppedItem";
        player = room.playo;
        orgo = org;
        pickUpMessage = messageOnPickup;
        itemName = callMyself;
        char1 = displayChar;
        layerName = layername;
        x = setx;
        y = sety;
        Layer thisLayer = new Layer(new String[1][1], layerName, y, x);
        org.addLayer(thisLayer);
    }

    @Override
    public void update(){
        orgo.editLayer(char1, layerName, 0, 0);
        if (x == player.getX() && y == player.getY()){
            System.out.println("Hi!  It's a " + itemName);
            if (player.inventory.containsKey(itemName)){
                int n = player.inventory.get(itemName);
                player.inventory.put(itemName, n+1);
            }else{
                player.inventory.put(itemName,1);
            }
            orgo.editLayer(" ", layerName, 0, 0);
            orgo.removeLayer(layerName);
            room.removeObject(this);
            room.compactTextBox(orgo, pickUpMessage,"",false);
        }
    }
}
