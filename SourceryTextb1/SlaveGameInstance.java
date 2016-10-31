package SourceryTextb1;

import SourceryTextb1.GameObjects.PlayerKeyPressListener;

import java.util.ArrayList;

/**
 * A slave instance of the multiplayer game.  Exists in own window.
 * Created by riley on 09-Oct-2016.
 */
public class SlaveGameInstance {
    private GameInstance masterInstance;
    private ImageOrg org;

    public SlaveGameInstance(GameInstance master){
        masterInstance = master;
        Window w = new Window();
        org = new ImageOrg(w);
        org.getWindow().txtArea.fabulousMode = true; // YEAH!
    }

    public void runGameAsSlaveTo(GameInstance master){
        masterInstance = master;
        PlayerKeyPressListener kl = masterInstance.requestNewPlayer();
        org.getWindow().txtArea.addKeyListener(kl);
        kl.player.setRealOrg(org);
        System.out.println(kl.getPlayerUsername());
        org.getWindow().setOwningPlayerUsername(kl.getPlayerUsername());
        // Run game
        while (true){
            try {
                ArrayList<Layer> l = master.getLayersOf(kl.getPlayerUsername());
                org.setLayers(l);
                Thread.sleep(10);
            }
            catch (NullPointerException ignore){
                System.out.println("Null pointer exception fetching layers from master");
            } catch (InterruptedException ignore) {}
            org.setCam(master.playerList.get(1).getX()-22,master.playerList.get(1).getY()-11);
        }

    }

}
