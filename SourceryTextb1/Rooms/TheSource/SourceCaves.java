package SourceryTextb1.Rooms.TheSource;

import SourceryTextb1.GameObjects.Player;
import SourceryTextb1.GameObjects.TheSource.Bandit;
import SourceryTextb1.GameObjects.TheSource.Spider;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.art;

/**
 * Caves!  Exciting!
 * Created by riley on 01-Sep-2016.
 */
public class SourceCaves extends Room{
    private ImageOrg org;

    @Override
    protected String loop(){
        int count = 0;

        while (exitCode.equals("")){
            try {
                Thread.sleep(20);
                if (getPlayer().getY() > 4300){
                    setNewRoom("SourcePit",11,1);
                }
                if (count == 0){
                    if (playo.getX() == 109 && playo.getY() == 10) {
                        queueMessage(new Room.FlavorText("Most capital letters (ex: A, B, C..) out in\n the world have flavor text \n accessible through the 'F' key", ""));
                    }
                    count++;
                }

            } catch (InterruptedException ignored) {}
        }
        return exitCode;
    }

    public void startup(){
        ititHitMeshes();
        super.playo.roomName = "SourceCaves";

        String[] doorLocked = {"The door seems to have locked behind you.","The owner must have installed\n an auto-lock on the door."};
        plantText(new Room.FlavorText(109, 9, doorLocked , ""));

        art arty = new art();
        String[][] base = art.strToArray(arty.sourceCaves);
        String[] solids = {"#"};
        addToBaseHitMesh(base, solids);
        Layer lay1 = new Layer(base, "Base");
        org.addLayer(lay1);

        Spider itsyBitsy = new Spider(this, 31, 11);
        addMortal(itsyBitsy);

        int[][] banditStations = {{13,105},{13, 117},{1,137},{5,144},{3,185},{5, 232},{5, 233},{2,227}};
        for (int[] station : banditStations) {
            Bandit roughBill = new Bandit(org, this, station[1], station[0]);
            addMortal(roughBill);
        }

        genericRoomInitialize();
    }

    public SourceCaves(Player player){
        constructor(player);
        super.roomHeight = 63;
        super.roomWidth = 420;
        org = player.orgo;
        super.index = 1;
    }
}
