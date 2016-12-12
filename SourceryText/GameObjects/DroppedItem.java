package SourceryText.GameObjects;

import SourceryText.Layer;
import SourceryText.Rooms.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * A dropped item that will put itself in the player's inventory when stepped on.
 * Created by riley on 13-Jun-2016.
 */
public class DroppedItem extends GameObject {
    private List<String> playersWhoPickedMeUp; // their usernames
    private Item me;
    private String pickUpMessage;
    private String layerName = "Riley says that empty droppedItems should not be a thing";

    public DroppedItem(Room roomy, String messageOnPickup, Item dropped, int setx, int sety) {
        strClass = "DroppedItem";
        room = roomy;
        playersWhoPickedMeUp = new ArrayList<>();
        org = roomy.org;
        pickUpMessage = messageOnPickup;
        me = dropped;
        x = setx;
        y = sety;
        if (me.getName().length() > 0) { // Don't even set up timer if item name is an empty string (for dummy item)
            layerName = room.makeUniqueLayerName(super.strClass) + "-";
            setupTimer(100);
        }
        time = 3000; // Do a long update immediately
    }

    /**
     * Updates the layers for new players.  Only necessary to call every less often, probably.
     */
    private void longUpdate() {
        // Ensure that we have layers for everyone
        for (Player p : room.players) {
            // players who haven't picked me up AND we don't have a layer for them already
            if (!(playersWhoPickedMeUp.contains(p.getUsername()) || org.layerExists(layerName + p.getUsername()))) {
                // should make a new layer for that specific player to see
                Layer newLayer = new Layer(new String[1][1], layerName + p.getUsername(), x, y, true, true, false);
                newLayer.setStr(0, 0, "!");
                newLayer.setOwningPlayerUsername(p.getUsername());
                org.addLayer(newLayer);
            }
        }
    }

    @Override
    public void update() {
        for (Player player : room.players) {
            if (x == player.getX() && y == player.getY() && !playersWhoPickedMeUp.contains(player.getUsername())) {
                player.addItem(me);
                playersWhoPickedMeUp.add(player.getUsername());
                org.removeLayer(layerName + player.getUsername());
                if (!pickUpMessage.equals("None") || pickUpMessage.equals("")) {
                    System.out.println("Picking up: " + me.getName());
                    room.splashMessage(pickUpMessage, "", player);
                }
            }
        }
        if (time >= 3000) {
            longUpdate();
            resetTime();
        }
    }

    @Override
    public void selfCleanup() {
        try {
            System.out.println("DroppedItem self clean!");
            org.editLayer(" ", layerName, 0, 0);
            org.getLayers().stream().filter(l -> l.name.contains(layerName)).forEach(l -> org.removeLayer(layerName));
        } catch (NullPointerException ignore) {
        }
    }
}
