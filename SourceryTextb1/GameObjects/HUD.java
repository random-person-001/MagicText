/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1.GameObjects;

import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.net.URI;
import java.awt.Desktop;

import static java.lang.Math.abs;

/**
 * A nice heads-up display for stats on the Player
 *
 * @author 119184
 */
public class HUD extends GameObject {
    String layerName;
    private String[] spell1Name = new String[6];
    private String[] spell2Name = new String[6];
    private int loc;
    private int cursorBlinkTimer = 0;
    private String command = "";
    private String nextCommand = "";
    private int consoleEntryProg = 0;
    private String promptChar = "»";
    private String responseMessage = null;
    private int responseDuration = 2;
    private boolean authing = false;
    private KeyListener playerKeyListener;

    public HUD(ImageOrg org, Room theRoom, Layer place) {
        super.strClass = "HUD";
        orgo = org;
        room = theRoom;
        layerName = place.getName();

        ConsoleKeyListener listener = new ConsoleKeyListener(this);
        orgo.getWindow().txtArea.addKeyListener(listener);

        setupTimer(100);

        for (KeyListener kl : orgo.getWindow().txtArea.getKeyListeners()) {
            if (kl.toString().contains("PlayerKeypressListener")) {
                playerKeyListener = kl;
            }
        }
    }

    private String[] convertIcon(String icon) {
        String[] result = new String[6];
        int cut = icon.length();
        if (cut > 5) cut = 5;
        for (int ic = 0; ic < cut; ic++) {
            char c = icon.charAt(ic);
            result[ic] = String.valueOf(c);
        }
        return result;
    }

    @Override
    public void update() {  // Edit layer acts after stuff.
        spell1Name = convertIcon(room.getPlayer().getPrimarySpell());
        spell2Name = convertIcon(room.getPlayer().getSecondarySpell());

        loc = orgo.getPosLayer(layerName);
        orgo.getLayer(loc).clear();
        x = 0;
        drawLayer();
    }

    /**
     * Place a character on the layer, one over from where the last one was placed. (a fairly specialized fn)
     *
     * @param newChar a single-char long String to place in the Layer at the next spot.
     */
    private void putChar(String newChar) {
        orgo.editLayer(newChar, loc, 0, x);
        x++;
    }

    /**
     * Edit the layer to put all the stats and stuff on
     */
    private void drawLayer() {
        if (responseMessage != null) {
            for (int ii = 0; ii < responseMessage.length(); ii++) {
                putChar(responseMessage.substring(ii, ii + 1));
            }
        } else if (consoleEntryProg < 3) {
            loc = orgo.getPosLayer(layerName);

            putChar("[");

            // Your health
            String healthValue = String.valueOf(room.playo.getHealth());
            putChar(String.valueOf(healthValue.charAt(0)));
            try {
                putChar(String.valueOf(healthValue.charAt(1)));
            } catch (StringIndexOutOfBoundsException e) {
                putChar(" ");
            }
            for (int ii = 0; ii < 10; ii++) {
                int fillPoint = (int) Math.ceil(((float) room.playo.getHealth() / (float) room.playo.maxHP) * 10);
                if (fillPoint > 10 && ii < fillPoint - 10) {
                    putChar("#");
                } else if (ii < fillPoint) {
                    putChar("+");
                } else {
                    putChar("_");
                }
            }
            putChar("]");
            x++;

            // Spell 1
            putChar("(");
            for (int ii = 0; ii < 5; ii++) {
                putChar(spell1Name[ii]);
            }
            putChar(")");
            x++;

            // Spell 2
            putChar("(");
            for (int ii = 0; ii < 5; ii++) {
                putChar(spell2Name[ii]);
            }
            putChar(")");
            x++;

            // Mana count
            putChar("{");
            putChar(Integer.toString(abs(room.playo.mana / 10)));
            putChar(Integer.toString(abs(room.playo.mana /* / 1 */ - 10 * (room.playo.mana / 10))));

            // Mana bar
            for (int ii = 0; ii < 10; ii++) {
                int fillPoint = (int) Math.ceil(((float) room.playo.mana / (float) room.playo.maxMana) * 10);
                if (ii < fillPoint) {
                    putChar("=");
                } else if (ii < (int) Math.ceil(((float) (2000 - room.playo.manaWait) / 2000.0f) * 10)) {
                    putChar("_");
                } else {
                    putChar(" ");
                }
            }
            putChar("}");
        } else {
            putChar(promptChar);
            putChar(" ");
            for (int ii = 0; ii < command.length(); ii++) {
                putChar(command.substring(ii, ii + 1));
            }
            if (cursorBlinkTimer % 10 < 6) {
                putChar("|");
            }
            if (cursorBlinkTimer == 10 * 50) {
                cursorBlinkTimer = 0;
            }
            cursorBlinkTimer++;
        }
    }

    private void keyPressed(char key) {
        if (consoleEntryProg < 3) {
            if (key == 'c') {
                consoleEntryProg++;
                if (consoleEntryProg >= 3) {
                    orgo.getWindow().txtArea.removeKeyListener(playerKeyListener);
                }
            } else {
                consoleEntryProg = 0;
            }
        } else if (Character.isLetterOrDigit(key) || key == ' ' || key == '-' || key == '&') {
            command += key;
        }
    }

    /**
     * Open a given url in the default web browser.
     *
     * @param url the url
     * @return whether it ran without throwing errors
     */
    private boolean openURL(String url) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url));
                return true;
            }
        } catch (URISyntaxException e) {
            System.out.println("Awwwwww! (URI)");
        } catch (IOException e) {
            System.out.println("Awwwwww! (IO)");
        }
        return false;
    }

    /**
     * Process what's stored as the <code>command</code>, and do appropriate actions.
     * <p>
     * List of commands:
     * >unfreeze : sets frozen to off for player (in the case it is forgotten somehow)
     * >ghost : Disables the player checking for walls before moving into one (also called 'noclip')
     * >unghost : Re-enables players checking for walls
     * >addhp (amount) : Restores (amount) HP w/ 50 Overheal.
     * >addtater (amount) : Puts (amount) potato(es) in the player's inventory
     * >echo (message) : print message out to standard out
     * >die : subtract a fatal amount of health
     * >make me a sandwich : evoke snarky response
     * >sudo make me a sandwich : evoke submissive response from computer.  Also enter sudo mode
     * >sudo : enter sudo mode
     * >exit : exit sudo mode, or the game if you aren't in it.
     * >jumpto (level name) : set the Room's exitCode string to what follows.
     * >ls | pwd : currently not developed.  Later will tell the name of current room.
     * >import antigravity : open default web browser and navigate to relevant xkcd (353)
     * >help : get a snarky response telling the user to look here in the source code
     * >setResponseTime (time) : set a new duration for the response message on the console to be shown (seconds)
     * >goto (x) (y) : teleport player to location
     * >getpos : print out current player coords in response
     * >blue rinse : murder every living thing. (except you)
     * >lightning (x) (y) : strike a bunch of damage to a specified location.  Leaves hot ashes behind.
     * >icbm [?] (x) (y) [d] [r] : splash damage about x, y. Use -r before params if relative location. d=damage, r=radius
     * >pointer | compiling | wifi | random : all relevant xkcd comics.
     * >gotta catch em all : rapidly teleport to each dropped item on the level to collect them all, then return to start.
     * </p>
     *     Tip: to execute a bunch of commands after each other, use '&&' in between them.  Also, you can type a command
     *     after 'sudo' to execute that command with root privileges.
     */

    private void processCommand() {
        boolean executeNextCommand = false;
        System.out.println("Nxt cmd: " + nextCommand);
        Player player = room.playo;
        if (command.contains("&& ")) {
            nextCommand = command.substring(command.indexOf("&& ") + 3);
            command = command.substring(0, command.indexOf("&& "));
            executeNextCommand = true;
        }
        if (authing) {
            authing = false;
            if (command.trim().length() == 2 && command.contains("42")) {
                promptChar = "#";
                showResponse("#Authentication successful!#");
                if (nextCommand.length() > 0 && !nextCommand.startsWith("su") && !nextCommand.contains("sudo")){ // avoid too much recursion
                    command = nextCommand;
                    executeNextCommand = true;
                }
            } else {
                showResponse("#Authentication failed!#");
            }
        } else if (command.startsWith("sudo")) {
            if (command.length() >= 5) {
                nextCommand = command.substring(5);
            }
            command = "";
            showResponse("Authentication: what's six by nine?");
            authing = true;
            return;
        }else if (command.contains("unfreeze")) {
            player.frozen = false;
            showResponse("Player unfrozen!");
        } else if (command.contains("unghost")) {
            player.isGhost = false;
            showResponse("Player now obeys walls again!");
        } else if (command.contains("ghost")) {
            player.isGhost = true;
            showResponse("Player is now a ghost.");
        } else if (command.contains("addhp ") && command.length() > 6) {
            int amountToHeal = Integer.valueOf(command.substring(6));
            room.playo.restoreHealth(amountToHeal, 50);
            showResponse(String.format("Restoring %1$d health to player", amountToHeal));
        } else if (command.contains("addtater ") && command.length() > 9) {
            int amountToGive = Integer.valueOf(command.substring(9));
            player.addPotato(amountToGive);
            showResponse(String.format("Giving the player %1$d potato(es)", amountToGive));
        } else if (command.contains("setResponseTime ")) {
            System.out.println(command.substring(16));
            int newTime = Integer.valueOf(command.substring(16));
            if (newTime < 15) { // that's a reasonable upper limit, right?
                responseDuration = newTime;
                showResponse("New response duration: " + newTime + " s");
            } else {
                showResponse("Dude, give something reasonable");
            }
        } else if (command.contains("addhp") || command.contains("addtater") || command.contains("setResponseTime")) { //no spaces
            showResponse("No quantity specified.");
        } else if (command.contains("echo ")) {
            showResponse(command.substring(5));
        } else if (command.startsWith("die") || command.contains("suicide")) {
            player.subtractHealth(1000000000, "Write with caution, for words are mightier\n than the sword");
            showResponse("Ok, then.  May you pass well into the next world!");
        } else if (command.contains("make") && command.contains("sandwich")) {
            if (command.contains("sudo") || promptChar.equals("#")) {
                showResponse("Ok, fine, I'll make you a sandwich.");
                Item sandwichItem = new Item("Sandwich", "Using your awesome\n knowledge of commandline-\n fu, you convinced the\n computer to make you this.", "~", player, "item");
                DroppedItem sandwichDrop = new DroppedItem(room, orgo, "Here's your sandwich", sandwichItem, "drops", player.getX(), player.getY());
                room.addObject(sandwichDrop);
            } else {
                showResponse("No!  Make your own sandwich!");
            }
        } else if (command.contains("exit")) {
            if (promptChar.equals("#")) {  //Currently in sudo mode; return to normalicy
                showResponse("Exited sudo mode.");
                promptChar = "»";
            } else {  // Open the quit menu
                Inventory inv = player.getInventory();
                inv.newShow();
                inv.keyPressed('®');
                inv.keyPressed('®');
                inv.keyPressed('®');
                inv.pressedA = true; // navigate to the quit game menu programmatically
            }
        } else if (command.startsWith("jumpto ")) { // Jump to a new level (defined in the switch statement in Start.java)
            if (promptChar.equals("#")) {
                showResponse("Going to level " + command.substring(7));
                room.exitCode = command.substring(7);
            } else {
                showResponse("You have insufficient privileges to perform this action.");
            }
        } else if (command.startsWith("ls") || command.startsWith("pwd")) {
            showResponse("Command currently under development.  Check back later!");
        } else if (command.contains("import antigravity")) {
            showResponse("Opening relevant XKCD (353) in default web browser ");
            openURL("http://xkcd.com/353/"); //NOTICE: this appears not to work on chrome-based browsers.
        } else if (command.contains("pointers")) {
            showResponse("Opening relevant XKCD (138) in default web browser ");
            openURL("http://xkcd.com/138/"); //NOTICE: this appears not to work on chrome-based browsers.
        } else if (command.contains("wifi") || command.contains("wi-fi")) {
            showResponse("Opening relevant XKCD (416) in default web browser ");
            openURL("http://xkcd.com/416/"); //NOTICE: this appears not to work on chrome-based browsers.
        } else if (command.contains("random")) {
            showResponse("Opening relevant XKCD (221) in default web browser ");
            openURL("http://xkcd.com/221/"); //NOTICE: this appears not to work on chrome-based browsers.
        } else if (command.contains("compil")) {
            showResponse("Opening relevant XKCD (303) in default web browser ");
            openURL("http://xkcd.com/303/"); //NOTICE: this appears not to work on chrome-based browsers.
        } else if (command.contains("help")) {
            showResponse("Check the source code for help: GameObjects/HUD.java, method processCommand()");
        } else if (command.contains("getpos")) {
            showResponse("Currently at x=" + player.getX() + " y=" + player.getY());
        } else if (command.contains("blue") && command.contains("rinse")) {
            if (promptChar.equals("#")) {
                int smoteMortals = 0;
                for (Mortal e : room.enemies) {
                    if (!e.strClass.equals("Player")) {
                        e.subtractHealth(e.getHealth() + 1);
                        smoteMortals++;
                    }
                }
                showResponse("Blue rinse smote " + smoteMortals + " rivals.");
            } else {
                showResponse("You have insufficient privileges to perform this action.");
            }
        } else if (command.contains("goto ")) {
            command = command.substring(5);
            boolean relative = false;
            if (command.contains("r")){
                command = command.substring(2+command.indexOf("r"));
                relative = true;
            }
            int[] p = getNParameters(command, 2);
            if (p != null) {
                int x = (relative) ? player.getX() + p[0] : p[0];
                int y = (relative) ? player.getY() + p[1] : p[1];
                player.goTo(x, y);
                showResponse("TP to x=" + x + " y=" + y);
            }
        } else if (command.contains("lightning ")) {
            command = command.substring(10);
            int[] p = getNParameters(command, 2);
            if (p != null) {
                int x = p[0];
                int y = p[1];
                System.out.println("X: " + x);
                System.out.println("Y: " + y);
                room.hurtSomethingAt(x, y, 1000, "You were zapped by your own lightning!\nNext time, be more careful" +
                        "with \nthe command line.");

                Item ashItem = new Item("Ash", "You struck down a \n bolt of lightning, \n which left only this\n behind.", "~", player, "item");
                DroppedItem ashDrop = new DroppedItem(room, orgo, "Ow!  The ashes of your smote\n enemies are still hot!", ashItem, "drops", x, y);
                room.addObject(ashDrop);
                showResponse("1000 damage zapped at x=" + x + " y=" + y);
            }
        } else if (command.contains("lightning") || command.contains("goto")) { // (no spaces)
            showResponse("No coordinates specified");
        }
        // note: damage pattern is that of a square pyramid.
        else if (command.contains("icbm") || command.contains("nuke")) {
            if (!(promptChar.equals("#"))) {
                showResponse("You have insufficient privileges to perform this action.");
            } else if (occurrencesOf(command, " ") < 2) {
                showResponse("Please specify more numbers.");
            } else {
                command = command.substring(4);
                boolean relative = false;
                if (command.substring(0, 3).contains("r")) {
                    relative = true;
                    command = command.substring(2 + command.indexOf("r"));
                }
                System.out.println(command);
                int[] p = getParameters(command);
                if (p != null && p.length >= 2) {
                    int x = p[0];
                    int y = p[1];
                    if (relative) {
                        x += player.getX();
                        y += player.getY();
                    }
                    int d = (p.length >= 3) ? p[2] : 100;
                    int r = (p.length >= 4) ? p[3] : 2;

                    for (int xi = -r; xi <= r; xi++) {
                        for (int yi = -r; yi <= r; yi++) {
                            float xDamageMult = abs(abs(xi) - r) / (float) r; // 0 to 1, peaking when xi=0 (center)
                            float yDamageMult = abs(abs(yi) - r) / (float) r;
                            int totalDamage = (int) (d * .5 * (xDamageMult + yDamageMult));
                            room.hurtSomethingAt(xi + x, yi + y, totalDamage, "Jeez, killed yourself with an ICBM!\n Careful!");
                            System.out.println(x + xi + " " + (yi + y) + " given " + totalDamage + " damage");
                        }
                        System.out.println();
                    }
                    showResponse("BOOM! " + (r * r) + " locations affected by ICBM (" + d + "mT tnt)");
                }
            }
        } else if (command.contains("catch") && command.contains("all")) { // Good ol' Pokemon ref
            int startX = player.getX();
            int startY = player.getY();
            int n = 0; // counter of collected items, to show off.
            for (int i=0; i < room.objs.size(); i++){
                System.out.println(i);
                GameObject o = room.objs.get(i);
                // TP to dropped object position, wait for it to be picked up.
                if (o.strClass.equals("DroppedItem")){
                    i--;  // cuz the list will shift down when it's gone.
                    n++;  // we found another item!
                    System.out.println("Found dropped object");
                    player.goTo(o.getX(), o.getY());
                    while (room.objs.contains(o)){
                        // wait
                        try {
                            o.update();
                            Thread.sleep(40);
                        } catch (InterruptedException ignore) {}
                    }
                }
            }
            player.goTo(startX, startY);
            showResponse("Collected " + n + " items around the level!");
        }




        else if (command.length() > 0){
            showResponse("Command " + command + " not recognised.  Check your spelling or " +
                    "request it as a new feature from the developers.");
        }
        if (!executeNextCommand){//(nextCommand.equals(command) || nextCommand.length() == 0){
            exitCommandLine();
        }
        else {
            command = nextCommand;
            nextCommand = "";
            processCommand();
        }
    }

    private void exitCommandLine() {
        command = "";
        nextCommand = "";
        consoleEntryProg = 0;
        // Only add back the player key listener if it isn't already there, avoiding adding it multiple times.
        KeyListener[] allKeyListeners = orgo.getWindow().txtArea.getKeyListeners();
        boolean alreadyAdded = false;
        for (KeyListener kl : allKeyListeners) {
            if (kl.equals(playerKeyListener)){
                alreadyAdded = true;
            }
        }
        if (!alreadyAdded){
            orgo.getWindow().txtArea.addKeyListener(playerKeyListener);
        }
    }

    /**
     * @param input a string with parameters in it
     * @param count desired number of parameters
     * @return an array of the integer parameters if the number's right, else null
     */
    private int[] getNParameters(String input, int count) {
        int[] toReturn;
        try {
            toReturn = getParameters(input);
        } catch (NumberFormatException e) {
            showResponse("Bad parameters.  Use only numbers.");
            return null;
        }
        if (toReturn.length == count) {
            return toReturn;
        }
        if (toReturn.length > count) {
            showResponse("Too many (" + toReturn.length + ") parameters specified.  Need " + count);
        } else if (toReturn.length < count) {
            showResponse("Not enough (" + toReturn.length + ") parameters specified.  Need " + count);
        }
        return null;
    }

    /**
     * Parse a string with spaces in it to return a list of integers after the spaces
     *
     * @param input input string
     * @return array of integers that came in the string
     */
    private int[] getParameters(String input) throws NumberFormatException {
        String[] stuff = input.split(" ");
        System.out.println(input);
        System.out.println(Arrays.toString(stuff));
        int actualParams = 0;
        for (String aStuff : stuff) {
            if (aStuff.length() > 0) {
                actualParams++;
            }
        }
        int[] parameters = new int[actualParams];
        int ii = 0;
        for (String aStuff : stuff) {
            if (aStuff.length() > 0) {
                parameters[ii] = Integer.parseInt(aStuff);
                ii++;
            }
        }
        System.out.println(Arrays.toString(parameters));
        return parameters;
    }

    private int occurrencesOf(String input, String ofWhat) {
        return input.length() - input.replace(ofWhat, "").length();
    }

    /**
     * @param message a string to show in the HUD's place, for <code>responseDuration</code> seconds,
     *                whereupon it resets <code>responseMessage</code> to null so that normal stuff
     *                gets displayed there.
     *                Also will send it to standard output, with nice formatting and all that.
     */
    private void showResponse(String message) {
        responseMessage = message;
        int each = 0;
        Timer sidescrollTimer = new Timer();
        if (responseMessage.length() > 46) {
            int leftover = responseMessage.length() - 46 + 1; // pretend it's two longer so neither end gets 0 time to show
            responseMessage = " " + responseMessage;
            each = (int) (1000 * (float) responseDuration / (float) leftover); // each is in seconds per character to be moved
            System.out.println(leftover);
            System.out.println(each);
        }
        System.out.println("##[] Console []##");
        System.out.println(responseMessage + "\n"); // newline for spacing
        try {
            if (each > 0) {
                sidescrollTimer.scheduleAtFixedRate(new TimerTask() { // sidescroll the message if needed.
                    @Override
                    public void run() throws StringIndexOutOfBoundsException, NullPointerException {
                        //System.out.println("HUD message moving.");
                        responseMessage = responseMessage.substring(1);
                    }
                }, 0, each);
            }
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    //System.out.println("HUD message done.");
                    responseMessage = null;
                    sidescrollTimer.cancel();
                }
            }, responseDuration * 1000);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (NullPointerException ignore) {
        } // happens when timer is late
    }

    private class ConsoleKeyListener extends KeyAdapter {
        private HUD sendTo;

        ConsoleKeyListener(HUD theHUD) {
            sendTo = theHUD;
        }

        @Override
        public void keyPressed(KeyEvent event) {
            int key = event.getKeyCode();
            char ch = event.getKeyChar();
            if (sendTo.consoleEntryProg >= 3 && key == KeyEvent.VK_ENTER) {
                sendTo.processCommand();
            } else if (key == KeyEvent.VK_ESCAPE){
                sendTo.exitCommandLine();
            } else if (sendTo.consoleEntryProg >= 3 && key == KeyEvent.VK_BACK_SPACE) {
                sendTo.command = (sendTo.command.length() > 0) ? sendTo.command.substring(0, sendTo.command.length() - 1) : "";
            } else {
                sendTo.keyPressed(ch);
            }
        }
    }

}
