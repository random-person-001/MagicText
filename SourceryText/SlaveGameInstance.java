package SourceryText;

import SourceryText.GameObjects.Player;
import SourceryText.Network.NetworkServerWorker;

import java.io.IOException;

/**
 * A slave instance of the multiplayer game.  This is the local, serverside code that takes care of clients that are connected.
 * Created by riley on 09-Oct-2016.
 */
public class SlaveGameInstance {
    private GameInstance masterInstance;
    private Player me;

    public SlaveGameInstance(GameInstance master) {
        masterInstance = master;
    }

    /**
     * Set up server and call run game appropriately.
     */
    public void runGameAsSlave() {
        me = masterInstance.requestNewPlayer();
        System.out.println("[SlaveGameInstance] player " + (me == null ? "null" : "nonnull"));
        //new Thread(this::runGameAsSlave).start();
        (new Thread(() -> masterInstance.runGame(me))).start();
    }

    public Player getCorrespondingPlayer(){
        return me;
    }
}
