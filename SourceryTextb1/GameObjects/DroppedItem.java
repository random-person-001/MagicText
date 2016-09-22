package SourceryTextb1.GameObjects;

import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * A dropped item that will put itself in the player's inventory when stepped on.
 * Created by riley on 13-Jun-2016.
 */
public class DroppedItem extends GameObject{
    //private Player player;
    private List<String> playersWhoPickedMeUp;
    private Item me;
    private String pickUpMessage;
    private String layerName;
    private boolean pickedUp = false;

    private boolean recordTaken = true;

    public DroppedItem(Room roomy, ImageOrg org, String messageOnPickup, Item dropped, int setx, int sety){
        strClass = "DroppedItem";
        room = roomy;
        playersWhoPickedMeUp = new ArrayList<>(); // Add THINGS HERE todo make player here
        orgo = org;
        pickUpMessage = messageOnPickup;
        me = dropped;
        x = setx;
        y = sety; // todo: room.playo won't work with multiplayer
        if (me.getName().length() > 0 && !room.playo.tracker.alreadyTaken(getX(),getY(),room.ownID)) { // Don't even set up timer if item name is an empty string (for dummy item)
            layerName = room.makeUniqueLayerName(super.strClass);
            Layer thisLayer = new Layer(new String[1][1], layerName, y, x, true, true, false);
            thisLayer.setStr(0, 0, "!");
            orgo.addLayer(thisLayer);
            setupTimer(100);
        }
    }

    public void updateShouldRecord (boolean newBoolean) {recordTaken = newBoolean;}

    @Override
    public void update(){
        //orgo.editLayer("!", layerName, 0, 0);
        for (Player player : room.players) {
            if (x == player.getX() && y == player.getY() && !pickedUp) {
                pickedUp = true;
                room.removeObject(this);
                if (recordTaken) room.playo.tracker.addLoc(getX(), getY(), room.ownID);
                player.addItem(me);
                if (!pickUpMessage.equals("None") || pickUpMessage.equals("")) {
                    System.out.println("Picking up: " + me.getName());
                    room.compactTextBox(orgo, pickUpMessage, "", false);
                }
            }
        }
    }

    @Override
    public void selfCleanup(){
        try {
            System.out.println("DroppedItem self clean!");
            orgo.editLayer(" ", layerName, 0, 0);
            orgo.removeLayer(layerName);
        }
        catch (NullPointerException ignore){}
    }
}
