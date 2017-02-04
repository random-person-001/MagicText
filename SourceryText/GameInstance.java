package SourceryText;

import SourceryText.GameObjects.Player;
import SourceryText.GameObjects.PlayerKeyPressListener;
import SourceryText.Network.NetworkServerBoss;
import SourceryText.Rooms.ForestOfFondant.Cliffbottom;
import SourceryText.Rooms.ForestOfFondant.FondantVillage;
import SourceryText.Rooms.ForestOfFondant.ShopTunnel;
import SourceryText.Rooms.ForestOfFondant.VillageInterior;
import SourceryText.Rooms.Room;
import SourceryText.Rooms.SeaOfSurprise.DockAndShip;
import SourceryText.Rooms.TheSource.*;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * A main instance of the game, so that players can be happening with multiplayer.  When that
 * is happening, the other instances are SlaveGameInstances.
 * Created by riley on 02-Oct-2016, expanded significantly to accommodate zone switching on 22-Nov-2016
 */
public class GameInstance implements java.io.Serializable {
    private transient NetworkServerBoss nsb = new NetworkServerBoss(this);
    private Player protaganist;
    private List<Player> playerList;
    private int zoneNumber;
    private HashMap<String, Room> thisZoneRooms;
    private boolean inMiddleOfSwitchingEveryonesZones = false;
    private String startingRoomName = "TutorialBasement";

    public GameInstance(Player protaganistSet) {
        protaganist = protaganistSet;
        zoneNumber = protaganist.getZoneNumber();
        playerList = new ArrayList<>();
        playerList.add(protaganist);
        PlayerKeyPressListener kl = new PlayerKeyPressListener(protaganist);
        System.out.println("[GameInstance] Protaganist Org: " + protaganist.org.toString());
        protaganist.org.setDefaultPlayer(protaganist);
        protaganist.org.getWindow().txtArea.addKeyListener(kl);
        protaganist.org.terminateClock();
        switchZones(); // Initialize zone one to start
        //resetNSB();
    }

    /**
     * Run this in a new thread for each player.
     *
     * @param p zees player
     */
    public void runGame(Player p) {
        System.out.println("[GameInstance] beginning game running for " + p.getUsername());
        while (!p.roomName.equals("die")) {
            System.out.println(p.getUsername() + " entering the room '" + p.roomName + "'" + " in Zone " + zoneNumber);
            if (thisZoneRooms.containsKey(p.roomName)) {
                Room r = thisZoneRooms.get(p.roomName);
                // Normal entering of a room without zone changes
                r.enter(p);
            } else if (p.roomName.contains("switch to zone") && !inMiddleOfSwitchingEveryonesZones) {
                // The first person to switch zones executes this; other Players won't but rather execute the next block.
                // This does all the fancy zone switching.
                inMiddleOfSwitchingEveryonesZones = true;
                zoneNumber = Integer.valueOf(Character.toString(p.roomName.charAt(p.roomName.length() - 1)));
                System.out.println("Player " + p.getUsername() + " initializing a zone change to Zone " + zoneNumber);
                switchZones();
                try {
                    Thread.sleep(70); // Way enough time for all the rooms to cycle through and finish up.
                } catch (InterruptedException ignore) {
                }
                inMiddleOfSwitchingEveryonesZones = false;
            } else if (inMiddleOfSwitchingEveryonesZones) {
                // Somebody else is taking care of making our roomName and everything change, so we can sit back and relax.
                System.out.println("[GameInstance.runGame() for + " + p.getUsername() + "] Somebody else is doing zone changes");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                // Not a normal room within this zone or a zone-changing room name
                System.out.println("[GameInstance.runGame() for + " + p.getUsername() + "] Unregistered room name! (zone " + zoneNumber + ")");
                p.roomName = "die";
            }
        }
        p.dead = true;
        System.out.println(p.getUsername() + " has DIED.  Oh, no, not again.");
        Layer lastSight = p.org.topDownBuild(p);
        lastSight.influenceAll(Color.RED);
        p.org.getWindow().build(lastSight);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        p.org.getWindow().dispose(); // Kill this window
    }

    /**
     * Initialize the rooms in the new zone and kill the old to free memory.  Also set all players to be in the new zone.
     * Assumes that the new zone number has been set as zoneNumber.
     */
    private void switchZones() {
        int to = zoneNumber;
        startingRoomName = "Define me in GameInstance.switchZones()";
        switch (to) {
            case 1:
                thisZoneRooms = initializeZone1Rooms(protaganist);
                startingRoomName = "TutorialBasement";
                break;
            case 2:
                thisZoneRooms = initializeZone2Rooms(protaganist);
                startingRoomName = "Cliffbottom";
                break;
            case 3:
                thisZoneRooms = initializeZone3Rooms(protaganist);
                break;
            case 4:
                thisZoneRooms = initializeZone4Rooms(protaganist);
                break;
            case 5:
                thisZoneRooms = initializeZone5Rooms(protaganist);
                break;
            default:
                System.out.println("[GameInstance] bad zoneNumber read in switchZones()! (" + to + ")");
        }
        for (Player p : playerList) {
            if (p.room != null) {
                p.room.exitCode = startingRoomName;
            }
            p.roomName = startingRoomName;
            p.setZoneNumber(to);
            p.goTo(0, 0);
            System.out.println("set " + p.getUsername() + "'s zone to " + zoneNumber + " and room name to " + startingRoomName); // does display
        }
    }

    /**
     * @return a new instance of Player, all set up to go!
     */
    Player requestNewPlayer() {
        Player noob = new Player(this, playerList.get(0).org, playerList.size(), null);
        noob.frozen = false;
        noob.roomName = startingRoomName;
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
        rooms.put("WitchHut", new WitchHut(player));
        rooms.put("IceCaves", new IceCaves(player));
        rooms.put("HiddenBunker", new HiddenBunker(player));
        rooms.put("HallOfBanditKing", new HallOfBanditKing(player));
        rooms.forEach((s, room) -> room.startup());
        rooms.forEach((s, room) -> room.setObjsPause(true));
        return rooms;
    }

    private HashMap<String, Room> initializeZone2Rooms(Player player) {
        HashMap<String, Room> rooms = new HashMap<>();

        // Add rooms here
        rooms.put("DockAndShip", new DockAndShip(player));
        rooms.put("Cliffbottom", new Cliffbottom(player));
        rooms.put("FondantVillage", new FondantVillage(player));
        rooms.put("VillageInterior", new VillageInterior(player));
        rooms.put("ShopTunnel", new ShopTunnel(player));

        rooms.forEach((s, room) -> room.startup());
        rooms.forEach((s, room) -> room.setObjsPause(true));
        return rooms;
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

    public Player getProtaganist() {
        return protaganist;
    }

    /**
     * Set the window for each of this zone's room's orgs, as well as the player.  When you go to a new zone, these will
     * be passed on to everything in the new zone.
     * @param window the new Window everything should be
     */
    public void setWindow(Window window) {
        thisZoneRooms.forEach((s, room) -> room.org.setWindow(window));
        protaganist.org.setWindow(window);
    }

    /**
     * Start accepting connections from other computers
     */
    public void openNetworking(){
        nsb.openNetworking();
    }

    /**
     * Stop accepting connections from other computers
     */
    public void closeNetworking(){
        nsb.closeNetworking();
    }

    /**
     * @return whether the game is accepting connections from other computers
     */
    public boolean getIsNetworkOpen(){
        return nsb.getIsNetworkOpen();
    }

    /**
     * Kick all players out who aren't the host.
     * Notifies all client players that they'll be kicked off a bit before the fact, to be nice.
     */
    public void bootClientPlayersOff(){
        (new Thread(() -> {
            for (Player p : playerList) {
                if (!p.getHasLocalWindow()){
                    p.room.splashMessage("You're being kicked out.\n The host of this world has \n kicked everyone out.", "* Game *", p);
                }
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ignore) {
            }
            nsb.bootOutClients();
        })).start();
    }

    /**
     * Only call this when you need it - like resuming from save (serialized) or on startup
     */
    public void resetNSB() {
        if (nsb != null){
            System.out.println("Network server boss was not null.  I hope you're resuming from save, or else you're doing it wrong");
            nsb.closeNetworking();
        }
        nsb = new NetworkServerBoss(this);
    }

    public List<Player> getPlayers(){
        return playerList;
    }
}
