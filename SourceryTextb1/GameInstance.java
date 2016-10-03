package SourceryTextb1;

import SourceryTextb1.GameObjects.GameObject;
import SourceryTextb1.GameObjects.Mortal;
import SourceryTextb1.GameObjects.Player;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.UserScreens.Start;
import com.sun.istack.internal.Nullable;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

/**
 * An instance of the game, so that multiple can be happening across different players doing multiplayer.
 * Created by riley on 02-Oct-2016.
 */
public class GameInstance {
    GameInstance masterInstance;
    Player protaganist;
    Room currentRoom;
    String roomID;
    HashMap<String, Room> zone1Rooms;

    // BEGIN INTERNAL WORKINGS
    public GameInstance(HashMap<String, Room> roomsSet, Player protaganistSet){
        zone1Rooms = roomsSet;
        protaganist = protaganistSet;
        roomID = protaganist.roomName;
    }

    public void runGame(){ // Defaults to running game with itself as master.
        runGame(null);
    }

    @Nullable
    public void runGame(GameInstance master){
        masterInstance = master;
        while (!roomID.equals("die")) {
            System.out.println("Entering the room '" + roomID + "'");
            doLevel(zone1Rooms.get(roomID));  // This is so slick!
        }
        System.out.println("\nBetter luck next time!");
        // Cleanup
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignore) {}
        protaganist.orgo.removeAllButPlayer();
        protaganist.orgo.removeLayer("playerLayer");
        protaganist.orgo.getWindow().txtArea.setForeground(Color.WHITE);
        protaganist.orgo.setCam(0,0);
    }

    private void doLevel(Room r){
        prepLevel(protaganist.orgo, protaganist.orgo.getWindow(), r);
        r.startup();
        currentRoom = r;
        r.setObjsPause(false);
        roomID = r.enter(masterInstance);
    }

    private void prepLevel(ImageOrg org, Window game, Room newRoom){
        org.removeAllButPlayer();
        game.clearImage();
        newRoom.ownID = roomID;
        protaganist.setRoom(newRoom);
    }

    // END INTERNAL WORKINGS


    // METHODS FOR MULTIPLAYER USE

    public List<GameObject> getObjs(){
        if (masterInstance != null && !masterInstance.equals(this)){
            System.out.println("BRO!  Your 'master' game instance has its own master.  Not supposed to be that way.");
            return masterInstance.getObjs(); // This really shouldn't happen, but...
        }
        System.out.println("Master doling out objects to subjects.");
        return currentRoom.objs;
    }

    public List<Mortal> getMortals(){
        if (masterInstance != null && !masterInstance.equals(this)){
            System.out.println("BRO!  Your 'master' game instance has its own master.  Not supposed to be that way.");
            return masterInstance.getMortals(); // This really shouldn't happen, but...
        }
        System.out.println("Master doling out objects to subjects.");
        return currentRoom.enemies;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public String getRoomID(){
        return roomID;
    }
}
