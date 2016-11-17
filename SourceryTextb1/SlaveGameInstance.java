package SourceryTextb1;

import SourceryTextb1.GameObjects.Player;

import java.io.IOException;

/**
 * A slave instance of the multiplayer game.  Exists in own window somewhere else.
 * Created by riley on 09-Oct-2016.
 */
public class SlaveGameInstance {
    private GameInstance masterInstance;

    public SlaveGameInstance(GameInstance master){
        masterInstance = master;
    }

    public void runGameAsSlave() {
        Player me = masterInstance.requestNewPlayer();
        System.out.println(me.getUsername());
        try {
            NetworkServer networkServer = new NetworkServer(me);
            networkServer.doTimerSend();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
