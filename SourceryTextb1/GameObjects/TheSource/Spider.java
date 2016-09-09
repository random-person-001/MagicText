package SourceryTextb1.GameObjects.TheSource;

import SourceryTextb1.GameObjects.DroppedItem;
import SourceryTextb1.GameObjects.Item;
import SourceryTextb1.GameObjects.Mortal;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;


/**
 * A dangerous troll (not the internet kind)
 * Created by riley on 16-Jun-2016.
 */
public class Spider extends Mortal {
    private int followDist = 20;
    private DroppedItem droppedItem;

    public Spider(Room theRoom, int xStart, int yStart) {
        this(theRoom, xStart, yStart,
                new DroppedItem(theRoom, theRoom.org, "", new Item("","", theRoom.getPlayer()), 0, 0)); // blank item
    }
    public Spider(Room theRoom, int xStart, int yStart, DroppedItem itemToDrop) {
        super.strClass = "Spider";
        room = theRoom;
        layerName = room.makeUniqueLayerName(super.strClass);
        droppedItem = itemToDrop;
        orgo = room.org;
        x = xStart;
        y = yStart;
        setHealth(8);
        orgo.addLayer(new Layer(new String[1][1], layerName, y, x));

        setupTimer(750);
    }

    @Override
    public void update() {
        if (Math.abs(x - room.playo.getX()) <= 1 && Math.abs(y - room.playo.getY()) <= 1){
            room.playo.subtractHealth(2);
        }
        pathToPos(followDist, room.playo.getX(), room.playo.getY());
        setDispIcon("S");
    }

    @Override
    protected void onDeath(){
        orgo.removeLayer(layerName);
        room.addObject(droppedItem);
    }
}
