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

    public DroppedItem(Room roomy, ImageOrg org, String messageOnPickup, String callMyself, String layername, String displayChar, int setx, int sety){
        strClass = "DroppedItem";
        room = roomy;
        player = room.playo;
        orgo = org;
        pickUpMessage = messageOnPickup;
        itemName = callMyself;
        char1 = displayChar;
        layerName = layername;
        x = setx;
        y = sety;
        Layer thisLayer = new Layer(new String[1][1], layerName, y, x);
        orgo.addLayer(thisLayer);
    }

    @Override
    public void update(){
        orgo.editLayer(char1, layerName, 0, 0);
        if (x == player.getX() && y == player.getY()){
            System.out.println("You found a " + itemName);
            room.removeObject(this);
            orgo.editLayer(" ", layerName, 0, 0);
            orgo.removeLayer(layerName);
            player.addItem(itemName);
            room.compactTextBox(orgo, pickUpMessage,"",false);
        }
    }
}
