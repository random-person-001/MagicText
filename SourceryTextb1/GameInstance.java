package SourceryTextb1;

import SourceryTextb1.GameObjects.Player;
import SourceryTextb1.GameObjects.PlayerKeyPressListener;
import SourceryTextb1.Rooms.Room;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadFactory;

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
        for (Room r : zone1Rooms.values()){
            r.startup();
//            try {
//                Thread.sleep(700);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            r.setObjsPause(true);
        }
        org.terminateClock();
        for (Player p : playerList) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    while (!p.roomName.equals("die")) {
                        System.out.println("Entering the room '" + p.roomName + "'");
                        doLevel(zone1Rooms.get(p.roomName), p);  // This is so slick!
                    }
                }
            }, 10);
        }
        protaganist.orgo.removeAllButPlayer();
        protaganist.orgo.removeLayer("playerLayer");
        protaganist.orgo.getWindow().txtArea.setForeground(Color.WHITE);
        protaganist.orgo.setCam(0,0);
    }

    private void doLevel(Room r, Player p){
        org.getWindow().clearImage();
        r.ownID = r.strRoomName;
        r.setObjsPause(false);
        p.restartTimer(); // I'm really not sure why, but the player didn't move until I reset its timer after switching levels.
        p.setRoom(r);
        r.enter(p);
        System.out.println(p.roomName);
        System.out.println(r.strRoomName);
        try {
        while (p.roomName.equals(r.strRoomName)) {
            Thread.sleep(20);
        }
        Thread.sleep(100);
    } catch (InterruptedException ignore) {}
    }


    /**
     * @param username which player
     * @return a list of layers in the room which that player is in
     */
    ArrayList<Layer> getLayersOf (String username){
        for (Player p : playerList){
            if (p.getUsername().equals(username)){
                return p.orgo.getLayers();
            }
        }
        return null;
    }

    PlayerKeyPressListener requestNewPlayer(){
        Player noob = new Player(playerList.get(0).orgo,playerList.size());
        noob.frozen = false;
        noob.roomName = "TutorialBasement";
        PlayerKeyPressListener kl = new PlayerKeyPressListener(noob);
        playerList.add(noob);
        return kl;
    }
}
