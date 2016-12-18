package SourceryText.GameObjects.TheSource;

import SourceryText.GameObjects.Mortal;
import SourceryText.GameObjects.Player;
import SourceryText.GameObjects.Spell;
import SourceryText.Layer;
import SourceryText.Rooms.Room;
import SourceryText.SpecialText;

import java.awt.*;

/**
 * Created by Jared on 24-Nov-16.
 */
public class BanditKing extends Mortal {

    private int chargeTimer = 0;
    private final int CHARGE_TIME = 40;
    private final int CHARGE_END = 80;

    public BanditKing(Room theRoom, int startX, int startY) {
        super.strClass = "BanditKing";


        room = theRoom;
        org = room.org;
        x = startX;
        y = startY;
        setHealth(75);
        layerName = room.makeUniqueLayerName(strClass);
        org.addLayer(new Layer(new String[1][1], layerName, x, y));

        setupTimer(150);

        Spell attack = new Spell(room, 0, 0, 0, 5, 10, new SpecialText("d"), new SpecialText("P"), true, "arcane");
        attack.setHostility(true);
        rangedInit(1, 2, 8, 1, attack);
    }

    @Override
    public void update() {

        chargeTimer++;
        if (chargeTimer >= CHARGE_TIME){
            isRanged = false;
            Mortal target = getClosestGoodGuy();
            pathToPos(followingDist, target.getX(), target.getY());
            if (Math.abs(target.getX() - x) + Math.abs(target.getY() - y) <= 1){
                room.hurtSomethingAt(target.getX(), target.getY(), 8, "You got sliced by the Bandit King's axe!", "");
                isRanged = true;
                chargeTimer = 0;
            }
        }
        if (chargeTimer == CHARGE_END) {
            chargeTimer = 0;
            isRanged = true;
        }

        Layer iconLayer = org.getLayer(layerName);
        if (iconLayer != null) iconLayer.setPos(x, y);

        setDispIcon(new SpecialText("K", new Color(255, 28, 41)));

        System.out.println(followingDist);
    }
}
