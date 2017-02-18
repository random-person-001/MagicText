package SourceryText;

import SourceryText.GameObjects.Player;

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
     * Call run game appropriately.
     */
    public void runGameAsSlave() {
        runGameAsSlave(masterInstance.requestNewPlayer());
    }

    /**
     * Call run game appropriately on the player
     */
    public void runGameAsSlave(Player clientControlledPlayer){
        me = clientControlledPlayer;
        (new Thread(() -> masterInstance.runGame(me))).start();
    }

    public Player getCorrespondingPlayer(){
        return me;
    }
}
