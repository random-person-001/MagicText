package SourceryTextb1.Rooms.TheSource;

import SourceryTextb1.Art;
import SourceryTextb1.GameObjects.DroppedItem;
import SourceryTextb1.GameObjects.Item;
import SourceryTextb1.GameObjects.Player;
import SourceryTextb1.GameObjects.TheSource.*;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.SpecialText;

/**
 * Some mountains, where you fight some enemies
 * Created by riley on 15-Jun-2016.
 */
public class Cliffside extends Room {

    public Cliffside(Player player) {
        super(player);
        strRoomName = "Cliffside";
    }

    protected String loop(Player play) {
        int count = 0;
        while (exitCode.equals("") && play.roomName == strRoomName) {
            try {
                Thread.sleep(20);

                if (count == 0) {
                    queueMessage(new FlavorText("Ah, the air is nice and fresh\n outside.", "").setViewerUsername(play.getUsername()));
                    queueMessage(new FlavorText("Endless rows of trees covered in sunset\n sitting beyond your view distance....", "").setViewerUsername(play.getUsername()));
                    queueMessage(new FlavorText("A picture taken here on this cliff\n would definitely make a great postcard.", "").setViewerUsername(play.getUsername()));
                    // ^ This should be an item in The Mines of Mementos
                    count++;
                }
                if (play.getY() < 1 && play.getX() < 20) {
                    setNewRoom("SourcePit", play, 42, 57);
                }
                if (play.getY() < 1 && play.getX() > 20) {
                    setNewRoom("InnerMountains", play, 44, 11);
                }
                if (play.getY() >= 32) {
                    setNewRoom("SourceCaves", play, 1, 62);
                }
                if (play.getX() >= 146) {
                    setNewRoom("BanditFortress", play, 103, 67);
                }
            } catch (InterruptedException ignored) {
            }
        }
        return exitCode;
    }

    /**
     * Everything that self-updates and can be paused (and acts nonexistent during paused) should go here.
     */
    @Override
    public void addItems() {

        int[][] locs = {{106, 115}, {14, 17}};
        for (int ii = 0; ii < locs[0].length; ii++) {
            Wolf wolf = new Wolf(org, this, locs[0][ii], locs[1][ii]);
            addMortal(wolf);
        }

        Spider hiddenMenace = new Spider(this, 56, 7);
        addMortal(hiddenMenace);

        Bandit john = new Bandit(org, this, 132, 11);
        addMortal(john);
        Bandit jack = new Bandit(org, this, 135, 13);
        addMortal(jack);

        Thief johnathan = new Thief(org, this, 21, 10);
        addMortal(johnathan);

        int[][] towerLocs = {{124, 14}, {130, 18}, {130, 8}, {140, 16}};
        for (int[] towerLoc : towerLocs) {
            WeakTower t = new WeakTower(org, this, towerLoc[0], towerLoc[1]);
            addMortal(t);
        }

        Item potion = new Item("SC&OPotatoChip", "This variety of magic\n potato chip is flavored\n with sour cream and onions\n\nIt makes your breath so bad\n it hurts nearby enemies.", "item");
        potion.setDescMode("potion");
        potion.setDuration(20 * 1000);
        DroppedItem dPotion = new DroppedItem(this, "You found a potato chip!", potion, 58, 7);
        super.addObject(dPotion);
    }

    @Override
    public void startup() {

        String[][] base = Art.strToArray(arty.mountainPlace);

        Layer baseLayer = new Layer(base, "backgronud");
        Art coloring = new Art();
        baseLayer.influenceAll(coloring.mountainPallette1);
        baseLayer.findAndReplace(new SpecialText("0", coloring.mountainPallette1), new SpecialText("O", coloring.mountainPallette2));
        baseLayer.findAndReplace(new SpecialText("o", coloring.mountainPallette1), new SpecialText("o", coloring.mountainPallette2));
        baseLayer.findAndReplace(new SpecialText(",", coloring.mountainPallette1), new SpecialText(",", coloring.mountainPallette2));
        org.addLayer(baseLayer);

        initHitMeshes(baseLayer);

        addItems();
        String[] solids = {":", "^", "#", ".", "0", "o"};
        addToBaseHitMesh(base, solids);

        genericRoomInitialize();
    }
}
