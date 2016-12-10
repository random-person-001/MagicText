package SourceryTextb1.Rooms.SeaOfSurprise;

import SourceryTextb1.Art;
import SourceryTextb1.GameObjects.Mortal;
import SourceryTextb1.GameObjects.Player;
import SourceryTextb1.GameObjects.TheSource.Spider;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;

import java.util.ConcurrentModificationException;

/**
 * A boat that moves away
 * Created by riley on 14-Jun-2016.
 */
public class DockAndShip extends Room {
    private String[][] dock;
    private Layer docky;

    public DockAndShip(Player player) {
        super(player);
        strRoomName = "DockAndShip";
    }

    @Override
    protected String loop(Player play) {
        if (play.getX() == 0 && play.getY() == 0) {
            play.goTo(66, 50);
        }
        int count = 0;
        int timer = 0;
        while (exitCode.equals("")) {
            try {
                Thread.sleep(20);
                if (count == 0) {
                    compactTextBox("You should probs get on the ship!", "A Passerby", false);
                    count++;
                }
                if (count == 1 && playo.getX() == 23 && playo.getY() == 1) {
                    compactTextBox("Ahoy!  This is the front of the ship. \n ", "Seagull", false);
                    count++;
                }
                timer++;
                if (timer > 1000 && timer < 1800 && timer % 10 == 0) {
                    //System.out.println("Moving one");
                    Layer docky = org.getLayer("Dock");
                    docky.setY(docky.getY() + 1);
                    docky.setX(docky.getX() + 1);
                    //System.out.println(docky.getY());
                    dockUpdate();
                    try {
                        for (Mortal m : enemies) {
                            if (m.getX() > 42) {
                                m.goTo(m.getX() + 1, m.getY() + 1);
                                if (m.getX() > roomWidth || m.getY() > roomHeight) { //Later, this might be good to put in Enemy.checkDead()
                                    m.subtractHealth(30, "You were not long for this world. \n You *Were* told to get on the ship.", "arcane");
                                }
                                if (count == 1 && m.strClass.equals("Player")) {
                                    compactTextBox("You missed the ship!  I told you not to.", "THE PASSERBY", false);
                                    count++;
                                }
                            }
                        }
                    } catch (ConcurrentModificationException ignored) {
                    }
                    for (int i = 43; i < 46; i++) { // Just needed the first time, but whatevs.
                        addToBaseHitMesh(42, i);
                        addToBaseHitMesh(43, i); // So you can't walk off the side
                        org.editLayer("|", "Boat", i, 42);
                    }
                }
                if (timer > 2000) {
                    //System.out.println("It's been a long while.");
                }

            } catch (InterruptedException ignored) {
            }
        }
        return exitCode;
    }

    private void dockUpdate() {
        clearObjHitMesh();
        String[] docksolids = {"|", "-", "_"};
        addToObjHitMesh(dock, docksolids, docky.getX(), docky.getY());
    }

    @Override
    public void startup() {

        String[][] base = Art.strToArray(arty.largeBoat);
        String[] solids = {"|", "-", "\\", "/", "_", "="};
        addToBaseHitMesh(base, solids);
        Layer lay1 = new Layer(base, "Boat");
        org.addLayer(lay1);

        initHitMeshes(lay1);
        playo.goTo(72, 51);

        dock = Art.strToArray(arty.dock);
        String[] docksolids = {"|", "-", "_"};
        addToObjHitMesh(dock, docksolids, 40, 27);
        docky = new Layer(dock, "Dock", 40, 27);
        org.addLayer(docky);

        for (int i = 43; i < 46; i++) {
            removeFromObjHitMesh(42, i);
            org.editLayer("X", "Boat", i, 42);
        }

        makeSpiderAt(26, 20); // Around front of boat
        makeSpiderAt(36, 25);

        makeSpiderAt(51, 43);
        makeSpiderAt(52, 45);

        makePiranahAt(20, 22);
        makePiranahAt(24, 23);
        makePiranahAt(28, 24);

        genericRoomInitialize();
    }

    private void makeSpiderAt(int x, int y) {
        Spider spidey = new Spider(this, x, y);
        addMortal(spidey);
    }

    private void makePiranahAt(int x, int y) {
        makeSpiderAt(x, y);
    }
}
