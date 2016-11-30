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

/**
 * Main class of MagicText, where everything starts.
 *
 * @author 119184, and a bit of 104410
 */
public class Start {
    private static boolean doDemo = false;

    public static void main(String[] args) throws InterruptedException {
        Window game = new Window();
        ImageOrg org = new ImageOrg(game);

        if (doDemo) {
            Player player = new Player(null, org,0);
            player.goTo(4,4);
            NewTestRoom rooma = new NewTestRoom(player);
            org.removeAllButPlayer();
            player.setRoom(rooma);
            rooma.startup();
            rooma.enter(player);
            while (rooma.enemies.size() > 0){}
        } else {
            while (true) {
                // Cleanup, possibly from last game
                if (!game.isVisible()) {
                    game.build(org.topDownBuild());
                    game.setVisible(true);
                    org.setDefaultPlayer(null);
                    org.setCam(0, 0);
                    org.resetClock();
                }

                // Show the menu and navigate it.
                new MainMenu(org);
                // Once the window is hidden (upon your death), we cycle back to the beginning
                while (game.isVisible()) {
                    Thread.sleep(5000);
                }
            }
        }
    }
}

