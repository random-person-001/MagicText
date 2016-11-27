package SourceryTextb1.Rooms.TheSource;

import SourceryTextb1.Art;
import SourceryTextb1.GameObjects.DroppedItem;
import SourceryTextb1.GameObjects.Item;
import SourceryTextb1.GameObjects.OneWayDoor;
import SourceryTextb1.GameObjects.Player;
import SourceryTextb1.GameObjects.TheSource.Bandit;
import SourceryTextb1.GameObjects.TheSource.RangedBandit;
import SourceryTextb1.GameObjects.TheSource.WeakTower;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.SpecialText;

import java.awt.*;

/**
 * A Fortress!  Exciting!
 * Normal entrance:
 * Player X: 67
 * Player Y: 103
 * Created by riley on 01-Sep-2016.
 */
public class BanditFortress extends Room{

    public BanditFortress(Player player){
        super(player);
        strRoomName = "BanditFortress";
    }

    @Override
    protected String loop(Player play){
        int count = 0;

        while (exitCode.equals("")){
            try {
                Thread.sleep(20);
                if (play.getY() >= 104){
                    setNewRoom("InnerMountains", play, 1, 24);
                }
                if (play.getX() >= 135){
                    setNewRoom("HiddenBunker", play, 1, 128);
                }
                if (count == 0){
                    if (play.getX() == 109 && play.getY() == 10) {
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

        int[][] banditStations = {{35,74},{33,75},{38,75},{36,76},{61,87},{73,87},{67,94},{19,46},
                {27,46},{42,56},{48,61},{56,56},{60,63},{74,32},{86,32},{51,25},{59,24},{62,19},{39,15}};
        for (int[] station : banditStations) {
            Bandit roughBill = new Bandit(org, this, station[0], station[1]);
            addMortal(roughBill);
        }

        int[][] towerLocs = {{76,15},{84,17},{76,32},{84,32},{78,43},{67,46},{23,41},{23,50},{63,94},
                             {71,94},{71,97},{63,97}};
        for (int[] towerLoc : towerLocs) {
            WeakTower t = new WeakTower(org, this, towerLoc[0], towerLoc[1]);
            addMortal(t);
        }

        int[][] rangedLocs = {{58,52},{80,58},{27,61},{69,65}};
        for (int[] rangedLoc : rangedLocs) {
            RangedBandit ranger = new RangedBandit (this, rangedLoc[0], rangedLoc[1]);
            addMortal(ranger);
        }

        Item magicTater = new Item("Magic Potato","How lucky! This eccentric\n potato can permanently\n " +
                "increase either your\n Max HP or Max Mana.\n\nNOTE: it's permanent.\nYou got this illicitly.", "item");
        DroppedItem gTater =  new DroppedItem(this, "You found a magic potato.  Cheater.", magicTater, 51, 19);
        super.addObject(gTater);

        OneWayDoor bunkerEntrance = new OneWayDoor(true, 94, 60, this, org);
        addObject(bunkerEntrance);
    }

    @Override
    public void startup(){

        String[] rockText1 = {"You've passed safely through the walls of \n the fortress!  Unfortunately, many more\n bandits lie inside.  Be careful."};
        plantText(new Room.FlavorText(59, 82, rockText1, ""));

        String[] rockText2 = {"The immBanense, strong walls of the bandit   \n fortress tower before you.  This may be\n a long shot, whatever you're to do here."};
        plantText(new Room.FlavorText(67, 94, rockText2, ""));

        String[] byFountain = {"Phew!  So many bandits!  Thankfully, it  \n only gets worse from here on out.  The \n Bandit King lies ahead, ugly and brutal.",
                "Meanwhile, you can enjoy the beautiful\n fountain.  There's an inaccessible item\n in the middle of it.  Pretty, huh?"};
        plantText(new Room.FlavorText(51, 25, byFountain, "Talking Gargoyle"));

        Art arty = new Art();
        String[][] base = Art.strToArray(arty.banditFortress);

        Layer lay1 = new Layer(base, "Base");
        highlightFlavorText(lay1);
        lay1.findAndReplace(new SpecialText("C"), new SpecialText(" ", null, new Color(43, 38, 33)));
        org.addLayer(lay1);

        initHitMeshes(lay1);

        addItems();
        String[] solids = {":","w","m","#","/","C"};
        addToBaseHitMesh(base, solids);

        genericRoomInitialize();
    }
}
