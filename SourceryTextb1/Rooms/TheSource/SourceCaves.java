package SourceryTextb1.Rooms.TheSource;

import SourceryTextb1.Art;
import SourceryTextb1.GameObjects.DroppedItem;
import SourceryTextb1.GameObjects.Item;
import SourceryTextb1.GameObjects.Player;
import SourceryTextb1.GameObjects.TheSource.Bandit;
import SourceryTextb1.GameObjects.TheSource.Spider;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;

/**
 * Caves!  Exciting!
 * Created by riley on 01-Sep-2016.
 */
public class SourceCaves extends Room{

    public SourceCaves(Player player){
        super(player);
    }

    @Override
    protected String loop(){
        int count = 0;

        while (exitCode.equals("")){
            try {
                Thread.sleep(20);
                if (getPlayer().getY() <= 0){
                    setNewRoom("Cliffside",134,31);
                }
                if (getPlayer().getY() == 1 && getPlayer().getX() == 230) {
                    setNewRoom("BanditFortress",134,64);
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

    @Override
    public void addItems(){
        ititHitMeshes();

        Spider itsyBitsy = new Spider(this, 31, 11);
        addMortal(itsyBitsy);

        int[][] banditStations = {{13,105},{13, 117},{1,137},{5,144},{3,185},{5, 232},{5, 233},{2,227}}; // X and Y are switched.
        for (int[] station : banditStations) {
            Bandit roughBill = new Bandit(org, this, station[1], station[0]); // X and Y switched.  I'm really sorry.
            addMortal(roughBill);
        }

        Item magicTater = new Item("Magic Potato","How lucky! This eccentric\n potato can permanently\n increase either your\n Max HP or Max Mana.\n\nNOTE: it's permanent!", playo, "item");
        DroppedItem gTater =  new DroppedItem(this, org, "You found a hidden magic potato!", magicTater, 5, 40);
        super.addObject(gTater);
    }

    @Override
    public void startup(){
        addItems();

        String[] doorLocked = {"The door seems to have locked behind you.","The owner must have installed\n an auto-lock on the door."};
        plantText(new Room.FlavorText(109, 9, doorLocked , ""));

        Art arty = new Art();
        String[][] base = Art.strToArray(arty.sourceCaves);
        String[] solids = {"#"};
        addToBaseHitMesh(base, solids);
        Layer lay1 = new Layer(base, "Base");
        org.addLayer(lay1);


        genericRoomInitialize();
    }
}
