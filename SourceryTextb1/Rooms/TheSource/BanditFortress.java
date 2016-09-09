package SourceryTextb1.Rooms.TheSource;

import SourceryTextb1.GameObjects.DroppedItem;
import SourceryTextb1.GameObjects.Item;
import SourceryTextb1.GameObjects.Player;
import SourceryTextb1.GameObjects.TheSource.Bandit;
import SourceryTextb1.GameObjects.TheSource.Spider;
import SourceryTextb1.GameObjects.TheSource.WeakTower;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.art;

/**
 * A Fortress!  Exciting!
 * Normal entrance:
 * Player X: 67
 * Player Y: 103
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
                if (getPlayer().getY() >= 104){
                    setNewRoom("Cliffside",5,5);
                }
                if (getPlayer().getX() >= 135){
                    setNewRoom("SourceCaves",1,231);
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
    public void startup(){
        ititHitMeshes();
        super.playo.roomName = "BanditFortress";

        String[] doorLocked = {"The door seems to have locked behind you.","The owner must have installed\n an auto-lock on the door."};
        plantText(new Room.FlavorText(109, 9, doorLocked , ""));

        art arty = new art();
        String[][] base = art.strToArray(arty.banditFortress);
        String[] solids = {":", "w","m","#"};
        addToBaseHitMesh(base, solids);
        Layer lay1 = new Layer(base, "Base");
        org.addLayer(lay1);

        Spider itsyBitsy = new Spider(this, 39, 39);
        addMortal(itsyBitsy);

        int[][] banditStations = {{67,95},{30,71},{35,72},{34,70},{18,64}};
        for (int[] station : banditStations) {
            Bandit roughBill = new Bandit(org, this, station[0], station[1]);
            addMortal(roughBill);
        }

        int[][] towerLocs = {{18,46},{29,46},{81,45},{64,44},{72,88},{62,88},{76,15},{90,17},{48,6},{54,6},{76,32},{84,32}};
        for (int[] towerLoc : towerLocs) {
            WeakTower t = new WeakTower(org, this, towerLoc[0], towerLoc[1]);
            addMortal(t);
        }

        Item magicTater = new Item("Illicit Magic Potato","How lucky! This eccentric\n potato can permanently\n " +
                "increase either your\n Max HP or Max Mana.\n\nNOTE: it's permanent.\nYou got this illicitly.", playo, "item");
        DroppedItem gTater =  new DroppedItem(this, org, "You found a magic potato.  Cheater.", magicTater, 51, 19);
        super.addObject(gTater);

        genericRoomInitialize();
    }

    public BanditFortress(Player player){
        constructor(player);
        super.roomHeight = 105;
        super.roomWidth = 137;
        org = player.orgo;
        super.index = 1;
    }
}