package SourceryText.GameObjects.TheSource;

import SourceryText.GameObjects.GameObject;
import SourceryText.GameObjects.Player;
import SourceryText.Layer;
import SourceryText.SpecialText;

import java.util.List;

/**
 * Created by riley on 11-Jan-2017.
 * Catch snowflakes in the Snowy Peak!
 *
 * General concepts around this detail/mechanic:
 * > strafe to catch snowflakes
 * > equip bucket as weapon, aimlock is changed to an 'o' or 'u'
 * > snowflakes tp to top when caught, like when they get too low
 * > throw bucket of snow into fireplace, get items

 */
public class SnowflakeBucket extends GameObject {
    private Player holder;
    private String layerName;
    private Layer bucketLayer;
    private int snowflakesCaught = 0;
    private boolean wasCatchingSnowflakesLastTime = false;
    private int ticksLeftBeforeShowingBucket = 0;

    //Convenience variables
    private final int UP = 0;
    private final int DOWN = 1;
    private final int LEFT = 2;
    private final int RIGHT = 3;

    public SnowflakeBucket (Player holder){
        this.strClass = "SnowflakeBucket";
        org = holder.org;
        room = holder.room;
        this.holder = holder;

        x = holder.getX() + 1;
        y = holder.getY();
        layerName = room.makeUniqueLayerName(strClass);
        bucketLayer = new Layer(new String[1][1], layerName, x, y);
        bucketLayer.setImportance(true);
        bucketLayer.setSpecTxt(0, 0, new SpecialText("u"));
        setupTimer(20);
    }

    @Override
    public void update(){
        if (!holder.roomName.equals("SnowyPeak")){
            return;
        }
        if (holder.getOrientationLocked() && holder.getPrimarySpell().equals("Buckt") && room != null && room.strRoomName.equals("SnowyPeak")) {
            //System.out.println("Bucket active!");
            if (!wasCatchingSnowflakesLastTime){
                System.out.println("Yahoo! This'll be fun!");
                org.addLayer(bucketLayer);
            }
            switch (holder.getOrientation()) {
                case UP:
                    x = holder.getX();
                    y = holder.getY() - 1;
                    break;
                case DOWN:
                    x = holder.getX();
                    y = holder.getY() + 1;
                    break;
                case LEFT:
                    x = holder.getX() - 1;
                    y = holder.getY();
                    break;
                case RIGHT:
                    x = holder.getX() + 1;
                    y = holder.getY();
                    break;
            }

            Layer orgsLayerCopy = org.getLayer(layerName);
            if (orgsLayerCopy != null) {
                orgsLayerCopy.setPos(x, y);
            }

            if (ticksLeftBeforeShowingBucket == 0) {
                org.editLayer("u", layerName, 0,0);
            } else {
                ticksLeftBeforeShowingBucket--;
            }
            wasCatchingSnowflakesLastTime = true;

            List<GameObject> objsHere = room.getObjectsAt(x,y);
            if (objsHere.size() > 0) {
                for (GameObject o : objsHere){
                    if (o.strClass == "Snowflake"){
                        ((Snowflake)o).respawn();
                        holder.addCurrency("Snowflake", 1);
                        snowflakesCaught = holder.getCurrency("Snowflake");
                        org.editLayer(String.valueOf(snowflakesCaught), layerName, 0, 0);
                        ticksLeftBeforeShowingBucket = 10;

                        String description = "A bucket for holding\n snowflakes that you\n found in the Witch's " +
                                "\n Hut.\n\n You currently have \n" + snowflakesCaught + " snowflake"+
                                (snowflakesCaught==1 ? "" : "s") +" in it!";
                        holder.getItem("Bucket", "spells").setDescription(description);
                    }
                }
            }

        }
        else {
            if (wasCatchingSnowflakesLastTime) {
                System.out.println("Ending snowflake bucketing. Hope it was fun!");
                org.removeLayer(layerName);
            }
            wasCatchingSnowflakesLastTime = false;
        }
    }

    @Override
    public void selfCleanup(){
        org.removeLayer(layerName);
    }
}
