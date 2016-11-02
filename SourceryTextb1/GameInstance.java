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
    private HashMap<String, Room> zone1Rooms;

    public GameInstance(HashMap<String, Room> roomsSet, Player protaganistSet){
        zone1Rooms = roomsSet;
        protaganist = protaganistSet;
        playerList = new ArrayList<>();
        playerList.add(protaganist);
        PlayerKeyPressListener kl = new PlayerKeyPressListener(protaganist);
        protaganist.orgo.getWindow().setOwningPlayerUsername(protaganist.getUsername());
        protaganist.orgo.getWindow().txtArea.addKeyListener(kl);
    }

    public void runGame(){
        protaganist.orgo.terminateClock();
        for (Player p : playerList) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    while (!p.roomName.equals("die")) {
                        System.out.println("Entering the room '" + p.roomName + "'");
                        doLevel(zone1Rooms.get(p.roomName), p);
                    }
                    System.out.println(p.getUsername() + " has DIED.  Oh, no, not again.");
                }
            }, 10);
        }
        protaganist.orgo.removeAllButPlayer();
        protaganist.orgo.getWindow().txtArea.setForeground(Color.WHITE);
        protaganist.orgo.setCam(0,0);
    }

    private void doLevel(Room r, Player p){
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
        Player noob = new Player(playerList.get(0).orgo, playerList.size());
        noob.frozen = false;
        noob.roomName = "TutorialBasement";
        PlayerKeyPressListener kl = new PlayerKeyPressListener(noob);
        playerList.add(noob);
        return kl;
    }
}
