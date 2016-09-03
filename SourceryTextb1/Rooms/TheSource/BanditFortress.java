package SourceryTextb1.Rooms.TheSource;

import SourceryTextb1.GameObjects.Player;
import SourceryTextb1.GameObjects.TheSource.Spider;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.art;

/**
 * A fortress!  Exciting!
 * Created by riley on 01-Sep-2016.
 */
public class BanditFortress extends Room{
    private ImageOrg org;

    @Override
    protected String loop(){
        int count = 0;

        while (exitCode.equals("")){
            try {
                Thread.sleep(20);
                if (getPlayer().getY() > 43){
                    setNewRoom("SourcePit",11,1);
                }
                if (count == 0){
                    if (playo.getX() == 109 && playo.getY() == 10) {
                        textBox(new Room.FlavorText("Most capital letters (ex: A, B, C..) out in\n the world have flavor text \n accessible through the 'F' key", ""));
                    }
                    count++;
                }

            } catch (InterruptedException ignored) {}
        }
        return exitCode;
    }

    public void startup(){
        ititHitMeshes();
        super.playo.roomName = "ThePit";

        String[] doorLocked = {"The door seems to have locked behind you.","The owner must have installed\n an auto-lock on the door."};
        addMessage(new Room.FlavorText(109, 9, doorLocked , ""));

        art arty = new art();
        String[][] base = art.strToArray(arty.sourcePit);
        String[] solids = {".",",",":",";","^","_","#","'","D","X"};
        addToBaseHitMesh(base, solids);
        Layer lay1 = new Layer(base, "Base");
        org.addLayer(lay1);

        Spider itsyBitsy = new Spider(this, 57, 42);
        addMortal(itsyBitsy);

        genericRoomInitialize();
    }

    public BanditFortress(Player player){
        constructor(player);
        org = player.orgo;
        super.index = 1;
    }
}
