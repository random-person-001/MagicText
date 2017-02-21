package SourceryText.GameObjects;

import SourceryText.Layer;
import SourceryText.Rooms.Room;
import SourceryText.SpecialText;

import java.awt.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Random;

/**
 * Created by Zach on 2/18/2017.
 */
public class MagicHole extends GameObject implements java.io.Serializable{
    private String layerName = "";
    private static final Random rand = new Random();
    private static final int PAINT_DELAY = 10;
    private int paintDelay = PAINT_DELAY;
    private int speed;
    private static final int DEFAULT_SPEED = 10;
    private Color color;
    public static final Color DEFAULT_COLOR = Color.darkGray;
    public static final Color TRANS = new Color(0,0,0,0);
    private boolean spitter = false;
    private int crunch;
    private MagicHole suckTo;
    private String[] suckWhom;
    private String[] dontSuckWhom;
    private SuckFormula suckFormula;
    public interface SuckFormula {
        public float[] suckStrength(int xD, int yD, GameObject toSuck);
    }
    public static final SuckFormula DEFAULT_SUCKFORMULA = new SuckFormula() {
        @Override
        public float[] suckStrength(int xD, int yD, GameObject toSuck) {
            double D = Math.sqrt(xD*xD+yD*yD);
            if(D==0)    { return new float[] {0,0};}
            double xPortion = xD/D;
            double yPortion = yD/D;
            return new float[]{(float) (xPortion*(2/Math.pow(2,(D+5)/6))/6), (float) (float) (yPortion*(2/Math.pow(2,(D+5)/6))/6)};
        }
    };

    private int animeCounter = 0;
    private final SpecialText[] assets = {new SpecialText("O", TRANS, TRANS), new SpecialText("", TRANS, TRANS),
                                          new SpecialText("|", TRANS, TRANS), new SpecialText("-", TRANS, TRANS), new SpecialText("\\", TRANS, TRANS), new SpecialText("/", TRANS, TRANS)};
        private final SpecialText[][][] animation =
                                                    {{{assets[1],assets[1],assets[1],assets[1],assets[1],assets[1],assets[1]},
                                                      {assets[1],assets[1],assets[1],assets[1],assets[1],assets[1],assets[1]},
                                                      {assets[1],assets[1],assets[4],assets[2],assets[5],assets[1],assets[1]},
                                                      {assets[1],assets[1],assets[3],assets[0],assets[3],assets[1],assets[1]},
                                                      {assets[1],assets[1],assets[5],assets[2],assets[4],assets[1],assets[1]},
                                                      {assets[1],assets[1],assets[1],assets[1],assets[1],assets[1],assets[1]},
                                                      {assets[1],assets[1],assets[1],assets[1],assets[1],assets[1],assets[1]}},

                                                     {{assets[1],assets[1],assets[1],assets[1],assets[1],assets[1],assets[1]},
                                                      {assets[1],assets[4],assets[1],assets[2],assets[1],assets[5],assets[1]},
                                                      {assets[1],assets[1],assets[1],assets[1],assets[1],assets[1],assets[1]},
                                                      {assets[1],assets[3],assets[1],assets[0],assets[1],assets[3],assets[1]},
                                                      {assets[1],assets[1],assets[1],assets[1],assets[1],assets[1],assets[1]},
                                                      {assets[1],assets[5],assets[1],assets[2],assets[1],assets[4],assets[1]},
                                                      {assets[1],assets[1],assets[1],assets[1],assets[1],assets[1],assets[1]}},

                                                     {{assets[4],assets[1],assets[1],assets[2],assets[1],assets[1],assets[5]},
                                                      {assets[1],assets[1],assets[1],assets[1],assets[1],assets[1],assets[1]},
                                                      {assets[1],assets[1],assets[1],assets[1],assets[1],assets[1],assets[1]},
                                                      {assets[3],assets[1],assets[1],assets[0],assets[1],assets[1],assets[3]},
                                                      {assets[1],assets[1],assets[1],assets[1],assets[1],assets[1],assets[1]},
                                                      {assets[1],assets[1],assets[1],assets[1],assets[1],assets[1],assets[1]},
                                                      {assets[5],assets[1],assets[1],assets[2],assets[1],assets[1],assets[4]}}};

    public MagicHole(Room room, int x, int y, boolean spitter, String[] suckWhom, String[] dontSuckWhom, SuckFormula suckFormula, Color color, int speed, int crunch) {
        this(room, x, y, null, suckWhom, dontSuckWhom, suckFormula, color, speed, crunch);
        this.spitter = spitter;
    }
    public MagicHole(Room room, int x, int y, MagicHole suckTo, String[] suckWhom, String[] dontSuckWhom, SuckFormula suckFormula, Color color, int speed, int crunch) {
        this.suckTo = suckTo!=null ? suckTo : this;
        this.suckWhom = suckWhom!=null ? suckWhom : new String[]{};
        this.dontSuckWhom = dontSuckWhom!=null ? dontSuckWhom : new String[]{};
        this.suckFormula = suckFormula!=null ? suckFormula :DEFAULT_SUCKFORMULA;
        this.color = color!=null ? color : DEFAULT_COLOR;
        this.speed = speed>=0 ? speed : DEFAULT_SPEED;
        this.crunch = crunch;
        super.strClass = "MagicHole";

        this.x = x;
        this.y = y;

        this.room = room;
        org = room.org;

        layerName = room.makeUniqueLayerName(strClass);
        Layer selfIcon = new Layer(animation[0], layerName, x - animation[0][0].length/2, y - animation[0].length/2, true, false, false);

        org.addLayer(selfIcon);

        setColor(this.color);

        setupTimer(this.speed);
    }

    public void update() {
        for(GameObject suckable : getSuckables()) {
            applySpitage(suckable);
            applySuckage(suckable);
        }
        paintSelf();
    }

    private GameObject[] getSuckables() {
        while(true) {
            try {
                ArrayList<GameObject> suckablesList = new ArrayList<>();
                for (GameObject potentialSuckable : room.objs) {
                    if (isSuckable(potentialSuckable)) {
                        suckablesList.add(potentialSuckable);
                    }
                }
                for (GameObject potentialSuckable : room.enemies) {
                    if (isSuckable(potentialSuckable)) {
                        suckablesList.add(potentialSuckable);
                    }
                }
                GameObject[] suckables = new GameObject[suckablesList.size()];
                suckablesList.toArray(suckables);
                return suckables;
            } catch (ConcurrentModificationException e) {e.printStackTrace();}
        }
    }
    private boolean isSuckable(GameObject potentialSuckable) {
        boolean suckable = false;
        for(String suckType: suckWhom) {
            for(Class potentialSuckablesClass = potentialSuckable.getClass(); potentialSuckablesClass!=GameObject.class.getSuperclass(); potentialSuckablesClass = potentialSuckablesClass.getSuperclass()) {
                if (potentialSuckablesClass.getSimpleName().equals(suckType)) {
                    suckable = true;
                    break;
                }
            }
            if(suckable)    { break; }
        }
        for(String suckType: dontSuckWhom) {
            if(!suckable)    { break; }
            for(Class potentialSuckablesClass = potentialSuckable.getClass(); potentialSuckablesClass!=GameObject.class.getSuperclass(); potentialSuckablesClass = potentialSuckablesClass.getSuperclass()) {
                if (potentialSuckablesClass.getSimpleName().equals(suckType)) {
                    suckable = false;
                    break;
                }
            }
        }
        return suckable;
    }

    private void applySuckage(GameObject toSuck) {
        float[] suckStrength = suckFormula.suckStrength(x - toSuck.getX(), y - toSuck.getY(), toSuck);
        if(toSuck.isAt(x,y) && spitter)  {
            while(suckStrength[0]==0 && suckStrength[1]==0) {
                suckStrength[0]=rand.nextInt(3)-1;
                suckStrength[1]=rand.nextInt(3)-1;
            }
        }
        toSuck.suckage[0] += spitter ? -suckStrength[0] : suckStrength[0];
        toSuck.suckage[1] += spitter ? -suckStrength[1] : suckStrength[1];
        int suckX = (int) toSuck.suckage[0];
        int suckY = (int) toSuck.suckage[1];
        toSuck.suckage[0]-=suckX;
        toSuck.suckage[1]-=suckY;
        if(!room.isPlaceSolid(toSuck.getX()+suckX,toSuck.getY()+suckY) || toSuck.getClass()==Spell.class) {
            toSuck.translate(suckX, suckY);
        }
    }

    private void applySpitage(GameObject toSpit) {
        if(toSpit.isAt(x,y)) {
            toSpit.setX(suckTo.getX());
            toSpit.setY(suckTo.getY());
            if(toSpit.getClass()==Mortal.class || toSpit.getClass().getSuperclass()==Mortal.class) {
                if(crunch>0)        ((Mortal) toSpit).subtractHealth(crunch, "You where crunched by a magic hole", "Crunch");
                else if(crunch<0)   ((Mortal) toSpit).restoreHealth(-crunch);
            }
        }
    }

    private void paintSelf() {
        if(paintDelay==0) {
            animeCounter += spitter ? 1 : -1;
            animeCounter = (animeCounter % animation.length + animation.length) % animation.length;
            org.getLayer(layerName).setSelf(animation[animeCounter]);
            paintDelay = PAINT_DELAY;
        } else {
            paintDelay--;
        }
    }

    public void setColor(Color color) {
        for(SpecialText asset: assets) {
            asset.setForeground(color);
        }
    }
}
