package SourceryTextb1;

import SourceryTextb1.GameObjects.Player;
import SourceryTextb1.GameObjects.PlayerKeyPressListener;
import SourceryTextb1.Rooms.Room;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * An instance of the game, so that multiple can be happening across different players doing multiplayer.  When that
 * is happening, the other instances are SlaveGameInstances.
 * Created by riley on 02-Oct-2016.
 */
public class GameInstance {
    private Player protaganist;
    List<Player> playerList;
    private String roomID;
    private HashMap<String, Room> zone1Rooms;
    private ImageOrg org;

    public GameInstance(HashMap<String, Room> roomsSet, Player protaganistSet){
        zone1Rooms = roomsSet;
        protaganist = protaganistSet;
        roomID = protaganist.roomName;
        playerList = new ArrayList<>();
        playerList.add(protaganist);
        org = protaganistSet.orgo;
        PlayerKeyPressListener kl = new PlayerKeyPressListener(protaganist);
        org.getWindow().setOwningPlayerUsername(protaganist.getUsername());
        org.getWindow().txtArea.addKeyListener(kl);
    }

    public void runGame(){
        while (!roomID.equals("die")) {

            // Do this in a better place later
            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() throws StringIndexOutOfBoundsException, NullPointerException {
                    org.setCam(protaganist.getX()-22, protaganist.getY()-11);
                }
            }, 20, 20);

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

    ArrayList<Layer> getLayers (){
        return org.getLayers();
    }

    // A method of masters
    PlayerKeyPressListener requestNewPlayer(){
        Player noob = new Player(playerList.get(0).orgo,playerList.size());
        noob.frozen = false;
        noob.roomName = "Tutorial";
        PlayerKeyPressListener kl = new PlayerKeyPressListener(noob);
        playerList.add(noob);
        return kl;
    }

    private void doLevel(Room r){
        prepLevel(protaganist.orgo, protaganist.orgo.getWindow(), r);
        r.startup();
        r.setObjsPause(false);
        roomID = r.enter();
    }

    private void prepLevel(ImageOrg org, Window game, Room newRoom){
        org.removeAllButPlayer();
        game.clearImage();
        newRoom.ownID = roomID;
        newRoom.setPlayers(playerList);
        for (Player p : playerList) {
            p.setRoom(newRoom);
            p.frozen = false;
        }
    }

}
