package SourceryText.GameObjects.TheSource;

import SourceryText.GameObjects.Mortal;
import SourceryText.GameObjects.Spell;
import SourceryText.ImageOrg;
import SourceryText.Layer;
import SourceryText.Rooms.Room;
import SourceryText.SpecialText;

import java.awt.*;


/**
 * A dangerous bandit!
 * Created by riley on 01-Sept-2016
 */
public class WeakTower extends Mortal {

    public WeakTower(ImageOrg orga, Room theRoom, int xStart, int yStart) {
        super.strClass = "WeakTower";
        org = orga;
        room = theRoom;
        layerName = room.makeUniqueLayerName(super.strClass);

        x = xStart;
        y = yStart;
        setHealth(7);
        org.addLayer(new Layer(new String[1][1], layerName, x, y));

        setupTimer(666);
    }

    @Override
    public void update() {
        // spell stuff
        int dmg = 2;
        int rng = 7;
        SpecialText anim1 = new SpecialText("|");
        SpecialText anim2 = new SpecialText("-");
        boolean alt = false;
        Mortal m = getClosestGoodGuy();
        if (m != null) {
            if (Math.abs(x - m.getX()) <= 2 && Math.abs(y - m.getY()) <= 2) {
                m.subtractHealth(2);
            }
            if (Math.abs(m.getX() - getX()) <= 2) {
                int orientation = (m.getY() - getY() > 0) ? 1 : 0;
                room.addObject(new Spell(room, x, y, orientation, dmg, rng, anim1, anim2, alt, "arcane"));
            }
            if (Math.abs(m.getY() - getY()) <= 2) {
                int orientation = (m.getX() - getX() > 0) ? 3 : 2;
                room.addObject(new Spell(room, x, y, orientation, dmg, rng, anim1, anim2, alt, "arcane"));
            }
        } else {
            System.out.println("WeakTower could not find a nearest good guy :(");
        }
        setDispIcon(new SpecialText("T", new Color(255, 200, 160)));
    }

    @Override
    public void onDeath() {
        org.removeLayer(layerName);
    }
}
