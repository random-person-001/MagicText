package SourceryTextb1;

import SourceryTextb1.GameObjects.Player;
import SourceryTextb1.GameObjects.PlayerKeyPressListener;
import SourceryTextb1.Rooms.Room;

import java.awt.*;
import java.io.IOException;
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
        protaganist.orgo.setOwningPlayerUsername(protaganist.getUsername());
        protaganist.orgo.getWindow().txtArea.addKeyListener(kl);
        protaganist.orgo.terminateClock();
    }

    /**
     * Run this in a new thread for each player.
     * @param p
     */
    public void runGame(Player p){
        System.out.println("[GameInstance] beginning game running for " + p.getUsername());
        while (!p.roomName.equals("die")) {
            System.out.println("Entering the room '" + p.roomName + "'");
            Room r = zone1Rooms.get(p.roomName);
            if (r != null) {
                r.enter(p);
            } else {
                System.out.println("Unregistered room name! " + p.roomName);
                p.roomName = "die";
            }
        }
        System.out.println(p.getUsername() + " has DIED.  Oh, no, not again.");
    }

    Player requestNewPlayer(){
        Player noob = new Player(playerList.get(0).orgo, playerList.size());
        noob.frozen = false;
        noob.roomName = "TutorialBasement";
        playerList.add(noob);
        return noob;
    }
}
