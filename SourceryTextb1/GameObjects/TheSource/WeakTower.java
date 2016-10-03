package SourceryTextb1.GameObjects.TheSource;

import SourceryTextb1.GameObjects.Mortal;
import SourceryTextb1.GameObjects.Spell;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.SpecialText;


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
        SpecialText anim1 = new SpecialText("|");
        SpecialText anim2 = new SpecialText("|");
        boolean alt = false;
        Mortal m = getClosestGoodGuy();
        if (Math.abs(x - m.getX()) <= 2 && Math.abs(y - m.getY()) <= 2){
            m.subtractHealth(2);
        }
        if (Math.abs(m.getX() - getX()) <= 2){
            int orientation = (m.getY() - getY() > 0) ? 1 : 0;
            room.addObject(new Spell(orgo, room, x, y, orientation, dmg, rng, anim1, anim2, alt));
        }
        if (Math.abs(m.getY() - getY()) <= 2){
            int orientation = (m.getX() - getX() > 0) ? 3 : 2;
            room.addObject(new Spell(orgo, room, x, y, orientation, dmg, rng, anim1, anim2, alt));
        }
        orgo.editLayer("T", layerName, 0, 0);
    }

    @Override
    public void onDeath(){
        orgo.removeLayer(layerName);
    }
}
