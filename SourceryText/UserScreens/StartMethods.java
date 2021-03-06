package SourceryText.UserScreens;

import SourceryText.*;
import SourceryText.GameObjects.Player;
import SourceryText.GameObjects.PlayerKeyPressListener;
import SourceryText.GameSettings.KeyMap;
import SourceryText.Network.NetworkClient;
import SourceryText.Network.NetworkServerBoss;
import SourceryText.Window;

import java.awt.*;
import java.io.IOException;

/**
 * I don't know if making this collection of methods is supposed to be a class, per OOP best practices, but I feel like
 * this makes it more organized.
 * Created by riley on 29-Nov-2016.
 */
public class StartMethods {
    private ImageOrg org;

    public StartMethods(ImageOrg org) {
        this.org = org;
    }

    void newGame(KeyMap keymap) {
        System.out.println("[-]\n\\/\\/\\/\\/\\/\\/\\\n  CREATING NEW GAME\n\\/\\/\\/\\/\\/\\/\\\n");
        Player player = new Player(null, org, 0, keymap);
        player.roomName = "TutorialBasement";
        GameInstance master = new GameInstance(player);
        player.setGameInstance(master);
        new Thread(() -> master.runGame(player)).start();
    }

    void buildGame(GameInstance instance) {
        Player imported = instance.getProtaganist();
        org.terminateClock();
        String roomName = imported.roomName;
        imported.getGameInstance().setWindow(org.getWindow());
        imported.room.makePlayerExitRoom(imported, roomName); // hopefully doesn't kill anything

        org = imported.org; // Now we switch orgs out.
        org.resetClock();
        for (Player p : instance.getPlayers()){ // there will be no non-braindead client players on save resume.
            if (!p.getHasLocalWindow()){
                p.braindead = true;
            }
        }
        imported.resumeFromSave();
        instance.resetNSB();
        if (imported.getHasLocalWindow()) {
            System.out.println("Has local window, adding listener");
            PlayerKeyPressListener kl = new PlayerKeyPressListener(imported);
            org.getWindow().txtArea.addKeyListener(kl);
        }
        new Thread(() -> instance.runGame(imported)).start();
    }

    void doIntro() {
        System.out.println("Starting intro...");
        (new Thread(() -> {
            try {
                intro();
                System.out.println("Intro finished.");
                new MainMenu(org);
            } catch (InterruptedException e) {
                System.out.println("Intro interrupted.");
            }
        })).start(); // I think this should be the only thread in the program (save for the dying one of the very
        // beginning?) at this point, all others should have fallen through
    }

    void doNetworkClient(String serverName, KeyMap keymap) {
        NetworkClient networker = new NetworkClient();
        try {
            networker.main(serverName, keymap);
        } catch (IOException e) {
            e.printStackTrace();
            networker.attemptCancel();
        }
    }

    private void intro() throws InterruptedException {
        Window window = org.getWindow();
        window.txtArea.setBackground(Color.BLACK);
        Layer lay1 = new Layer(new String[23][46], "Intro1");
        lay1.setStr(10, 23, "@");
        org.addLayer(lay1);
        Thread.sleep(750);
        org.printLayers();

        for (int eff = 0; eff < 40; eff++) {
            window.txtArea.setBackground(new Color(eff, eff, eff, 255));
            Thread.sleep(50);
        }
        Thread.sleep(2000);

        String thisIsYou = "This is you.";
        for (int ii = 0; ii < thisIsYou.length(); ii++) {
            org.editLayer(String.valueOf(thisIsYou.charAt(ii)), "Intro1", 12, 18 + ii);
        }
        Thread.sleep(5000);

        org.removeLayer("Intro1");
        Art arty = new Art();
        String[][] town = Art.strToArray(arty.intro1);
        Layer lay = new Layer(town, "Intro2");
        org.addLayer(lay);
        Layer lay2Title = new Layer(new String[3][46], "Intro2Title", 0, 30);
        org.addLayer(lay2Title);
        org.setCam(0, 10);
        Thread.sleep(500);

        String explain = "Times were simple a couple weeks ago.";
        for (int ii = 0; ii < explain.length(); ii++) {
            org.editLayer(String.valueOf(explain.charAt(ii)), "Intro2Title", 1, 3 + ii);
        }
        Thread.sleep(5000);

        String explainMore = "@'s were people, #'s were walls & terrain";
        for (int ii = 0; ii < explainMore.length(); ii++) {
            org.editLayer(String.valueOf(explainMore.charAt(ii)), "Intro2Title", 2, 3 + ii);
        }
        Thread.sleep(4000);

        for (int pan = 0; pan < 13; pan++) {
            org.moveCam(0, -1);
            Thread.sleep(250);
        }
        Thread.sleep(2000);

        org.removeLayer("Intro2");
        org.removeLayer("Intro2Title");
        Layer lay3 = new Layer(new String[23][46], "Intro3");
        lay3.setStr(10, 22, "@");
        org.addLayer(lay3);
        org.setCam(0, 0);
        Thread.sleep(1500);

        window.txtArea.setForeground(new Color(189, 83, 89, 255));
        String villainIntro = "This is someone else.";
        for (int ii = 0; ii < villainIntro.length(); ii++) {
            org.editLayer(String.valueOf(villainIntro.charAt(ii)), "Intro3", 7, 12 + ii);
        }
        Thread.sleep(4500);

        String goodExcuse = "(To be fair, everyone DOES look identical)";
        for (int ii = 0; ii < goodExcuse.length(); ii++) {
            org.editLayer(String.valueOf(goodExcuse.charAt(ii)), "Intro3", 8, 2 + ii);
        }
        Thread.sleep(5000);

        String goodFollowUp = "He despised that fact. He desired complexity";
        for (int ii = 0; ii < goodFollowUp.length(); ii++) {
            org.editLayer(String.valueOf(goodFollowUp.charAt(ii)), "Intro3", 12, 1 + ii);
        }
        Thread.sleep(3000);

        String resolution = "And so, he journeyed to The Source";
        for (int ii = 0; ii < resolution.length(); ii++) {
            org.editLayer(String.valueOf(resolution.charAt(ii)), "Intro3", 13, 6 + ii);
        }
        Thread.sleep(1500);

        org.clearLayer("Intro3");
        Thread.sleep(1500);

        window.txtArea.setForeground(Color.white);
        int line = 4;
        introText("The Source is a gaping hole in the world.", 1, line);
        Thread.sleep(3000);

        introText("It leads out of the universe,", 1, line + 1);
        introText(" and into its source code", 1, line + 2);
        Thread.sleep(4000);

        introText("Most worlds are closed-source, but", 1, line + 4);
        introText(" this one is special. It's open-source.", 1, line + 5);
        Thread.sleep(4000);

        introText("That person found a rope strong enough", 1, line + 7);
        introText(" to survive exiting the universe", 1, line + 8);
        Thread.sleep(4000);

        introText("He traveled down into The Source", 1, line + 10);
        introText(" ..and found the world's settings...", 1, line + 11);
        Thread.sleep(4000);

        lay3.clear();
        org.removeLayer("Intro3");
        String[][] code = Art.strToArray(arty.intro2);
        lay3 = new Layer(code, "Intro4");
        org.addLayer(lay3);
        org.setCam(0, 18);
        Thread.sleep(5000);

        for (int ii = 0; ii < 18; ii++) {
            org.moveCam(0, -1);
            Thread.sleep(100);
        }
        Thread.sleep(1500);

        for (int ii = 0; ii < 6; ii++) {
            org.editLayer(" ", "Intro4", 18, 35 - ii);
            Thread.sleep(100);
        }
        Thread.sleep(500);

        String TRUE = "TRUE;";
        for (int ii = 0; ii < TRUE.length(); ii++) {
            org.editLayer(String.valueOf(TRUE.charAt(ii)), "Intro4", 18, 30 + ii);
            Thread.sleep(400);
        }
        Thread.sleep(5000);

        lay3.clear();
        String result = "...And that changed EVERYTHING";
        for (int ii = 0; ii < result.length(); ii++) {
            org.editLayer(String.valueOf(result.charAt(ii)), "Intro4", 11, 7 + ii);
        }
        Thread.sleep(3000);

        for (int eff = 39; eff < 255; eff += 2) {
            window.txtArea.setBackground(new Color(eff, eff, eff, 255));
            Thread.sleep(50);
        }
        lay3.clear();


                /*
                for (int eff = 255; eff >= 0; eff -= 5) {
                    game.txtArea.setBackground(new Color(eff, eff, eff, 255));
                    Thread.sleep(50);
                }
                */

        window.txtArea.setBackground(Color.BLACK);
    }

    private void introText(String text, int offset, int line) {
        System.out.println("Writing: " + text);
        for (int ii = 0; ii < text.length(); ii++) {
            org.getLayer("Intro3").setStr(line, ii + offset, String.valueOf(text.charAt(ii)));
        }
    }
}
