package SourceryTextb1;

import java.util.ArrayList;

/**
 * Created by Jared on 06-Sep-16.
 */

public class ItemTracker {
    private ArrayList<ItemTakenFlag> itemLocs = new ArrayList<>();

    public void addLoc(int x, int y, String ID){
        ItemTakenFlag taken = new ItemTakenFlag(x,y,ID);
        System.out.printf("[TRACKER] Item marked @ (%1$d,%2$d,%3$s)\n", x,y,ID);
        itemLocs.add(taken);
    }

    /**
     * Returns true when the item @ that location and room is already taken.
     *      False if it hasn't
     */
    public boolean alreadyTaken (int x, int y, String rmID){
        System.out.printf("Running a check @ %1$d,%2$d (%3$d)\n", x, y, itemLocs.size());
        if (itemLocs.size() == 0){
            return false;
        }
        for (ItemTakenFlag flag : itemLocs){
            System.out.printf("\tIT: Parsing: p(%1$d,%2$d,%3$s) vs. l(%4$d,%5$d,%6$s)\n",x,y,rmID,flag.xLoc,flag.yLoc,flag.roomID);
            if (flag.xLoc == x && flag.yLoc == y && flag.roomID.equals(rmID)){
                return true;
            }
        }
        return false;
    }

    protected class ItemTakenFlag{
        protected int xLoc;
        protected int yLoc;
        protected String roomID = "";

        protected ItemTakenFlag(int xSet, int ySet, String roomSet){
            xLoc = xSet;
            yLoc = ySet;
            roomID = roomSet;
        }
    }
}
