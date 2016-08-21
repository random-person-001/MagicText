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

    private boolean pickedUp = false;

    private Item me;

    public DroppedItem(Room roomy, ImageOrg org, String messageOnPickup, Item dropped, String layername, int setx, int sety){
        strClass = "DroppedItem";
        room = roomy;
        player = room.playo;
        orgo = org;
        pickUpMessage = messageOnPickup;
        me = dropped;
        char1 = "!";
        layerName = layername;
        x = setx;
        y = sety;
        Layer thisLayer = new Layer(new String[1][1], layerName, y, x, true, true, true);
        thisLayer.setStr(0,0, "!");
        orgo.addLayer(thisLayer);

        //System.out.println("\n\n" + me.getName() + " JUST DROPPED ON THE GROUND! (" + x + "," + y + ")\n\n");

        setupTimer(100);
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
