package SourceryText.Rooms.ForestOfFondant;

import SourceryText.Art;
import SourceryText.GameObjects.Player;
import SourceryText.Layer;
import SourceryText.Rooms.Room;
import SourceryText.SpecialText;

import java.awt.*;

/**
 * A secret tunnel going to the shop, and your first opportunity to do some dirty work for the VC (the version control)
 * Created by Jared on 18-Jan-17.
 */
public class ShopTunnel extends Room {
    public ShopTunnel (Player player){
        super(player);
        strRoomName = "ShopTunnel";
    }

    @Override
    protected String playerLoop (Player play){
        while (exitCode.equals("")){
            try {

                Thread.sleep(50);

            } catch (InterruptedException ignore){}
        }
        return exitCode;
    }

    @Override
    public void uniqueResponse(int qID, Player respondTo){
        if (qID == 1){
            setNewRoom("FondantVillage", respondTo, 11, 112);
        }
    }

    @Override
    public void startup(){
        Art arty = new Art();
        String[][] base = Art.strToArray(arty.shopTunnel);
        Layer roomLayer = new Layer(base, "RoomLayer", 0, 0, true, false, false);

        Color caveFloor = new Color (26, 14, 6);
        Color caveBg = new Color (74, 40, 18);
        Color caveFg = new Color (94, 46, 20);
        roomLayer.findAndReplace(new SpecialText("."), new SpecialText(".", caveFg, caveBg), 40);
        roomLayer.findAndReplace(new SpecialText("."), new SpecialText(" ", caveFg, caveBg));

        roomLayer.findAndReplace(new SpecialText(" "), new SpecialText(" ", null, caveFloor));

        Color bricksFg =  new Color(102, 102, 102);
        Color bricksBkg = new Color(38, 38, 38);
        Color bricksFloor = new Color(26, 26, 26);

        roomLayer.findAndReplace(new SpecialText("#"), new SpecialText("#", bricksFg, bricksBkg));
        roomLayer.findAndReplace(new SpecialText("+"), new SpecialText("+", bricksBkg, bricksFloor));

        plantText(new FlavorText("Initiate, do you wish to return to\n the surface?", true, 1), 36, 11);

        highlightFlavorText(roomLayer);

        org.addLayer(roomLayer);

        Layer veilLayer = new Layer(Art.strToArray(arty.shopStorgeVeil), "StorageCover", 28, 26);
        veilLayer.findAndReplace(new SpecialText("#"), new SpecialText("#", bricksFg, bricksBkg));

        org.addLayer(veilLayer);

        initHitMeshes(roomLayer);
        String[] solids = {".","%","#","W","G"};
        addToBaseHitMesh(base, solids);

        addItems();

        org.roomBackground = caveBg;

        genericRoomInitialize();
    }

    public void addItems(){
    }
}
