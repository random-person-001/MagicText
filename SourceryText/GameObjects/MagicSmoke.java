package SourceryText.GameObjects;

import SourceryText.Layer;
import SourceryText.Rooms.Room;
import SourceryText.SpecialText;

import java.awt.*;

/**
 * Created by Zach on 2/1/2017.
 */
public class MagicSmoke extends GameObject implements java.io.Serializable{

    private String layerName = "";
    private Color color;
    private int radius;
    private int[] blockedTypes;
    public static final int MANAREGEN = -1;
    public static final int HEALTYPE = 0;
    public static final int ARCANETYPE = 1;
    public static final int FIRETYPE = 2;
    public static final int ICETYPE = 3;
    public static final int DARKTYPE = 4;

    public MagicSmoke (Room room, GameObject follow, int radius, int[] blockedTypes) {
        this(room, follow.getX(), follow.getY(), radius, blockedTypes);
        follow.addMagicSmoke(this);
    }

    public MagicSmoke (Room room, GameObject follow, int radius, int[] blockedTypes, Color color) {
        this(room, follow.getX(), follow.getY(), radius, blockedTypes, color);
        follow.addMagicSmoke(this);
    }

    public MagicSmoke (Room room, int x, int y, int radius, int[] blockedTypes) {this(room, x, y, radius, blockedTypes, new Color(50, 255, 100, 100));}

    public MagicSmoke (Room room, int x, int y, int radius, int[] blockedTypes, Color color) {
        this.color = color;
        this.radius = radius;
        this.blockedTypes = blockedTypes;
        super.strClass = "MagicSmoke";

        this.x = x;
        this.y = y;

        this.room = room;
        org = room.org;

        layerName = room.makeUniqueLayerName(strClass);
        Layer selfIcon = new Layer(new String[radius*2+1][radius*2+1], layerName, x - radius, y - radius);

        org.addLayer(selfIcon);

        paintSelf();

        setupTimer(100);
    }

    private void paintSelf() {
        SpecialText paint = new SpecialText("", new Color(0, 0, 255, 0), color);
        for (int x=0;x<=radius*2+1;x++) {
            for (int y=0;y<=radius*2+1;y++) {
                if( ((x-radius)*(x-radius))*4+(y-radius)*(y-radius) <= radius*radius){
                    org.editLayer(paint, layerName, x, y);
                }
            }
        }
    }

    public boolean isBlockSpell(Item spell, int x, int y) {
        boolean blockedType = false;
        for(int blocktype: blockedTypes) {
            if(blocktype==spell.getSpellType()) {
                blockedType = true;
                break;
            }
        }
        boolean blockedSpot = ((x-this.x)*(x-this.x))+(y-this.y)*(y-this.y) <= radius*radius;
        return blockedType && blockedSpot;
    }

    public boolean isBlockManaRegen(int x, int y) {
        boolean manaRegenBlocked = false;
        for(int blocktype: blockedTypes) {
            if(blocktype==MANAREGEN) {
                manaRegenBlocked = true;
                break;
            }
        }
        boolean blockedSpot = ((x-this.x)*(x-this.x))+(y-this.y)*(y-this.y) <= radius*radius;
        return manaRegenBlocked && blockedSpot;
    }

    public void move (int x, int y) {
        this.x = x;
        this.y = y;
        org.getLayer(layerName).setX(x - radius);
        org.getLayer(layerName).setY(y - radius);
    }

    public void setColor(Color color) {
        this.color = color;
        update();
    }

    public void update()
    {
        paintSelf();
    }
}
