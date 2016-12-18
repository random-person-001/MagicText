package SourceryText.GameObjects.TheSource;

import SourceryText.GameObjects.Mortal;
import SourceryText.ImageOrg;
import SourceryText.Layer;
import SourceryText.Rooms.Room;
import SourceryText.SpecialText;

import java.awt.*;


/**
 * A dangerous bandit!
 * Created by riley on 31-Aug-2016
 */
public class Bandit extends Mortal {
    private int followDist = 8;
    private boolean isInTraining = false;
    private SpecialText icon;

    public Bandit(ImageOrg orga, Room theRoom, int xStart, int yStart) {
        this(orga, theRoom, xStart, yStart, false);
    }
    public Bandit(ImageOrg orga, Room theRoom, int xStart, int yStart, boolean isInTraining) {
        super.strClass = "Bandit";
        org = orga;
        room = theRoom;
        layerName = room.makeUniqueLayerName(super.strClass);

        x = xStart;
        y = yStart;
        org.addLayer(new Layer(new String[1][1], layerName, x, y));

        this.isInTraining = isInTraining;
        if (!isInTraining){
            icon = new SpecialText("B", new Color(255, 160, 160));
            setHealth(15);
            setupTimer(600);
        } else {
            icon = new SpecialText("b", new Color(255, 180, 180));
            setHealth(4);
            setupTimer(700);
        }
    }


    @Override
    public void update() {
        Mortal closestGoodGuy = getClosestGoodGuy();
        if (closestGoodGuy != null) {
            if (Math.abs(x - closestGoodGuy.getX()) <= 2 && Math.abs(y - closestGoodGuy.getY()) <= 2) {
                closestGoodGuy.subtractHealth(2);
            }
            if (!isInTraining && (closestGoodGuy.getX() == getX() || closestGoodGuy.getY() == getY())) { // sprint when straight line to player
                pathToPos(followDist, closestGoodGuy.getX(), closestGoodGuy.getY());
            }
            pathToPos(followDist, closestGoodGuy.getX(), closestGoodGuy.getY());
        } else {
            System.out.println("Bandit could not find a nearest good guy :(");
        }
        setDispIcon(icon);
    }

    public void setIcon(SpecialText newIcon){
        icon = newIcon;
    }

    @Override
    public void onDeath() {
        org.removeLayer(layerName);
    }
}
