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
    private List<String> playersWhoPickedMeUp;
    private Item me;
    private String pickUpMessage;
    private String layerName = "Riley says that empty droppedItems should not be a thing";

    public DroppedItem(Room roomy, ImageOrg org, String messageOnPickup, Item dropped, int setx, int sety){
        strClass = "DroppedItem";
        room = roomy;
        playersWhoPickedMeUp = new ArrayList<>(); // Add THINGS HERE todo make player here
        orgo = org;
        pickUpMessage = messageOnPickup;
        me = dropped;
        x = setx;
        y = sety;
        if (me.getName().length() > 0 && !room.playo.tracker.alreadyTaken(getX(),getY(),room.ownID)) { // Don't even set up timer if item name is an empty string (for dummy item)
            layerName = room.makeUniqueLayerName(super.strClass) + "-";
            setupTimer(100);
        }
    }

    /**
     * Updates the layers for new players.  Only necessary to call every less often, probably.
     */
    private void longUpdate (){
        System.out.println("Long update");
        // Ensure that we have layers for everyone
        for (Player p : room.players){
            // players who haven't picked me up AND we don't have a layer for them already
            if (!playersWhoPickedMeUp.contains(p.getUsername()) && orgo.getPosLayer(layerName+p.getUsername()) == -1){
                // should make a new layer for that specific player to see
                Layer newLayer = new Layer(new String[1][1], layerName+p.getUsername(), y, x, true, true, false);
                newLayer.setStr(0, 0, "!");
                newLayer.setOwningPlayerUsername(p.getUsername());
                orgo.addLayer(newLayer);
            }
        }
    }

    @Override
    public void update(){
        for (Player player : room.players) {
            if (x == player.getX() && y == player.getY() && !playersWhoPickedMeUp.contains(player.getUsername())) {
                player.addItem(me);
                playersWhoPickedMeUp.add(player.getUsername());
                orgo.removeLayer(layerName+player.getUsername());
                if (!pickUpMessage.equals("None") || pickUpMessage.equals("")) {
                    System.out.println("Picking up: " + me.getName());
                    room.compactTextBox(orgo, pickUpMessage, "", false);
                }
            }
        }

        if (time % 2000 == 0){ // time stayed the same for a bit sometimes.
            System.out.println(time);
            longUpdate();
        }
    }

    @Override
    public void selfCleanup(){
        try {
            System.out.println("DroppedItem self clean!");
            orgo.editLayer(" ", layerName, 0, 0);
            orgo.getLayers().stream().filter(l -> l.name.contains(layerName)).forEach(l -> orgo.removeLayer(layerName));
        }
        catch (NullPointerException ignore){}
    }
}
