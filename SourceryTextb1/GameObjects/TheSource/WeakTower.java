package SourceryTextb1.GameObjects.TheSource;

import SourceryTextb1.GameObjects.Mortal;
import SourceryTextb1.GameObjects.Spell;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;


/**
 * A dangerous bandit!
 * Created by riley on 01-Sept-2016
 */
public class WeakTower extends Mortal {

    public WeakTower(ImageOrg orga, Room theRoom, int xStart, int yStart) {
        super.strClass = "WeakTower";
        orgo = orga;
        room = theRoom;
        layerName = room.makeUniqueLayerName(super.strClass);

        x = xStart;
        y = yStart;
        setHealth(7);
        orgo.addLayer(new Layer(new String[1][1], layerName, y, x));

        setupTimer(666);
    }

    @Override
    public void update() {
        // spell stuff
        int dmg = 2;
        int rng = 7;
        String anim1 = "|";
        String anim2 = "-";
        boolean alt = false;
        if (Math.abs(x - room.playo.getX()) <= 2 && Math.abs(y - room.playo.getY()) <= 2){
            room.playo.subtractHealth(2);
        }
        if (Math.abs(room.playo.getX() - getX()) <= 2){
            int orientation = (room.playo.getY() - getY() > 0) ? 1 : 0;
            room.addObject(new Spell(orgo, room, x, y, orientation, dmg, rng, anim1, anim2, alt));
        }
        if (Math.abs(room.playo.getY() - getY()) <= 2){
            int orientation = (room.playo.getX() - getX() > 0) ? 3 : 2;
            room.addObject(new Spell(orgo, room, x, y, orientation, dmg, rng, anim1, anim2, alt));
        }
        orgo.editLayer("T", layerName, 0, 0);
    }

    @Override
    protected void onDeath(){
        orgo.removeLayer(layerName);
    }
}
