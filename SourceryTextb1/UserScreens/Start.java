/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1.UserScreens;

import SourceryTextb1.GameObjects.Player;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Rooms.NewTestRoom;
import SourceryTextb1.Window;

import java.util.*;
import java.awt.Color;

/**
 * Main class of MagicText, where everything starts.
 *
 * @author 119184, and a bit of 104410
 */
public class Start {
    private static boolean doDemo = false;
    private static Window game;
    private static ImageOrg org;
    private static List<Player> playerList; // for multiplayer!

    public static void main(String[] args) throws InterruptedException {
        game = new Window();
        org = new ImageOrg(game);

        if (doDemo) {
            Player player = new Player(null, org,0);
            player.goTo(4,4);
            playerList = new ArrayList<>();
            playerList.add(player);
            NewTestRoom rooma = new NewTestRoom(player);
            org.removeAllButPlayer();
            org.roomBackground = Color.BLACK;
            player.setRoom(rooma);
            rooma.startup();
            rooma.enter(player);
            while (rooma.enemies.size() > 0){}
        } else {
            new MainMenu(org);
        }
    }
}

