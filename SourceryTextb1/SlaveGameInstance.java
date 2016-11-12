package SourceryTextb1;

import SourceryTextb1.GameObjects.Player;
import SourceryTextb1.GameObjects.PlayerKeyPressListener;

import java.io.IOException;

/**
 * A slave instance of the multiplayer game.  Exists in own window somewhere else.
 * Created by riley on 09-Oct-2016.
 */
public class SlaveGameInstance {
    private GameInstance masterInstance;
    private ImageOrg org;

    public SlaveGameInstance(GameInstance master){
        masterInstance = master;
        Window w = new Window();
        org = new ImageOrg(w);
    }

    public void runGameAsSlaveTo(GameInstance master) {
        masterInstance = master;
        Player me = masterInstance.requestNewPlayer();
        //org.getWindow().txtArea.addKeyListener(kl);
        System.out.println(me.getUsername());
        try {
            NetworkerServer networkerServer = new NetworkerServer(me);
            networkerServer.doTimerSend();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
