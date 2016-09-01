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
    private Item me;
    private String pickUpMessage;
    private String layerName;
    private boolean pickedUp = false;

    public DroppedItem(Room roomy, ImageOrg org, String messageOnPickup, Item dropped, int setx, int sety){
        strClass = "DroppedItem";
        room = roomy;
        player = room.playo;
        orgo = org;
        pickUpMessage = messageOnPickup;
        me = dropped;
        x = setx;
        y = sety;
        if (me.getName().length() > 0) { // Don't even set up timer if item name is an empty string (for dummy item)
            layerName = room.makeUniqueLayerName(super.strClass);
            Layer thisLayer = new Layer(new String[1][1], layerName, y, x, true, true, false);
            thisLayer.setStr(0, 0, "!");
            orgo.addLayer(thisLayer);
            setupTimer(100);
        }
    }

    @Override
    public void update(){
        orgo.editLayer("!", layerName, 0, 0);
        if (x == player.getX() && y == player.getY() && !pickedUp){
            pickedUp = true;
            room.removeObject(this);
            orgo.editLayer(" ", layerName, 0, 0);
            orgo.removeLayer(layerName);
            player.addItem(me);
            if (!pickUpMessage.equals("None") || pickUpMessage == "") {
                System.out.println("Picking up: " + me.getName());
                room.compactTextBox(orgo, pickUpMessage, "", false);
            }
        }
    }
}
