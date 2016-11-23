package SourceryTextb1;

import SourceryTextb1.GameObjects.Player;
import SourceryTextb1.GameObjects.PlayerKeyPressListener;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.Rooms.TheSource.*;
import SourceryTextb1.Rooms.SeaOfSurprise.*;
import SourceryTextb1.Rooms.MinesOfMementos.*;
import SourceryTextb1.Rooms.ForestOfFondant.*;
import SourceryTextb1.Rooms.CavernsOfCliffhangers.*;

import java.util.*;
import java.util.List;

/**
 * A main instance of the game, so that players can be happening with multiplayer.  When that
 * is happening, the other instances are SlaveGameInstances.
 * Created by riley on 02-Oct-2016, expanded significantly to accommodate zone switching on 22-Nov-2016
 */
public class GameInstance {
    private Player protaganist;
    private List<Player> playerList;
    private int zoneNumber;
    private HashMap<String, Room> zone1Rooms;
    private HashMap<String, Room> zone2Rooms;
    private HashMap<String, Room> zone3Rooms;
    private HashMap<String, Room> zone4Rooms;
    private HashMap<String, Room> zone5Rooms;
    private boolean inMiddleOfSwitchingEveryonesZones = false;

    public GameInstance(Player protaganistSet){
        protaganist = protaganistSet;
        zoneNumber = protaganist.getZoneNumber();
        playerList = new ArrayList<>();
        playerList.add(protaganist);
        PlayerKeyPressListener kl = new PlayerKeyPressListener(protaganist);
        protaganist.orgo.setOwningPlayerUsername(protaganist.getUsername());
        protaganist.orgo.getWindow().txtArea.addKeyListener(kl);
        protaganist.orgo.terminateClock();
        switchZones(); // Initialize zone one to start
    }

    /**
     * Run this in a new thread for each player.
     * @param p
     */
    public void runGame(Player p){
        System.out.println("[GameInstance] beginning game running for " + p.getUsername());
        while (!p.roomName.equals("die")) {
            System.out.println("Entering the room '" + p.roomName + "'");
            Room r;
            switch (zoneNumber){
                case 1:
                    r = zone1Rooms.get(p.roomName);
                    break;
                case 2:
                    r = zone2Rooms.get(p.roomName);
                    break;
                case 3:
                    r = zone3Rooms.get(p.roomName);
                    break;
                case 4:
                    r = zone4Rooms.get(p.roomName);
                    break;
                case 5:
                    r = zone5Rooms.get(p.roomName);
                    break;
                default:
                    System.out.println("[GameInstance] Somebody set an invalid zoneNumber!  Irrecoverable, dieing!");
                    p.dead = true;
                    return;
            }
            if (r != null) {
                // Normal entering of a room without zone changes
                r.enter(p);
            }
            else if (p.roomName.contains("switch to zone") || !inMiddleOfSwitchingEveryonesZones){
                // The first person to switch zones executes this; other Players won't but rather execute the next block.
                // This does all the fancy zone switching.
                inMiddleOfSwitchingEveryonesZones = true;
                        zoneNumber = Integer.valueOf(Character.toString(p.roomName.charAt(p.roomName.length()-1)));
                for (Player eachPlayer: playerList){
                    eachPlayer.room.exitCode = "switch to zone " + zoneNumber;
                    eachPlayer.roomName = "switch to zone " + zoneNumber; // This will trigger an exit of their current room.
                }
                switchZones();
                try {
                    Thread.sleep(70); // Way enough time for all the rooms to cycle through and finish up.
                } catch (InterruptedException ignore) {}
                inMiddleOfSwitchingEveryonesZones = false;
            }
            else if (inMiddleOfSwitchingEveryonesZones){
                // Somebody else is taking care of making our roomName and everything change, so we can sit back and relax.
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else {
                // Not a normal room within this zone or a zone-change-room-name
                System.out.println("Unregistered room name! " + p.roomName + " (zone "+zoneNumber+")");
                p.roomName = "die";
            }
        }
        p.dead = true;
        System.out.println(p.getUsername() + " has DIED.  Oh, no, not again.");
    }

    /**
     * Initialize the rooms in the new zone and kill the old to free memory.  Also set all players to be in the new zone.
     * Assumes that the new zone number has been set as zoneNumber.
     */
    private void switchZones(){
        int to = zoneNumber;
        String startingRoomName = "Define me in GameInstance.switchZones()";
        switch (to){
            case 1:
                zone1Rooms = initializeZone1Rooms(protaganist);
                startingRoomName = "TutorialBasement";
                break;
            case 2:
                zone1Rooms = null; // Is there a better way to free up memory?
                zone2Rooms = initializeZone2Rooms(protaganist);
                startingRoomName = "ForestPath";
                break;
            case 3:
                zone2Rooms = null;
                zone3Rooms = initializeZone3Rooms(protaganist);
                break;
            case 4:
                zone3Rooms = null;
                zone4Rooms = initializeZone4Rooms(protaganist);
                break;
            case 5:
                zone4Rooms = null;
                zone5Rooms = initializeZone5Rooms(protaganist);
                break;
            default:
                System.out.println("[GameInstance] zoneNumber read in switchZones()! ("+to+")");
        }
        for (Player p : playerList){
            p.roomName = startingRoomName;
            p.setZoneNumber(to);
        }
    }

    /**
     * @return a new instance of Player, all set up to go!
     */
    Player requestNewPlayer(){
        Player noob = new Player(playerList.get(0).orgo, playerList.size());
        noob.frozen = false;
        noob.roomName = "TutorialBasement";
        playerList.add(noob);
        return noob;
    }

    /**
     * @return a hashmap of all the levels in Zone 1, initialized, startuped, paused, and paired with their string.
     * representation.
     */
    // If we really felt like it, we would use code introspection for this, but that just seems like a lot of unneccessary work.
    private HashMap<String, Room> initializeZone1Rooms(Player player) {
        HashMap<String, Room> rooms = new HashMap<>();
        rooms.put("TutorialBasement", new TutorialBasement(player));
        rooms.put("SourcePit", new SourcePit(player));
        rooms.put("Cliffside", new Cliffside(player));
        rooms.put("SourceCaves", new SourceCaves(player));
        rooms.put("BanditFortress", new BanditFortress(player));
        rooms.put("InnerMountains", new InnerMountains(player));
        rooms.put("SnowyPeak", new SnowyPeak(player));
        rooms.put("IceCaves", new IceCaves(player));
        rooms.put("HiddenBunker", new HiddenBunker(player));
        rooms.forEach((s, room) -> room.startup());
        rooms.forEach((s, room) -> room.setObjsPause(true));
        return rooms;
    }

    private HashMap<String, Room> initializeZone2Rooms(Player player) {
        System.out.println("[GameInstance] no classes are specified yet in initializeZone2Rooms(): Bad things will happen");
        return null;
    }

    private HashMap<String, Room> initializeZone3Rooms(Player player) {
        System.out.println("[GameInstance] no classes are specified yet in initializeZone3Rooms(): Bad things will happen");
        return null;
    }

    private HashMap<String, Room> initializeZone4Rooms(Player player) {
        System.out.println("[GameInstance] no classes are specified yet in initializeZone4Rooms(): Bad things will happen");
        return null;
    }

    private HashMap<String, Room> initializeZone5Rooms(Player player) {
        System.out.println("[GameInstance] no classes are specified yet in initializeZone5Rooms(): Bad things will happen");
        return null;
    }
}
