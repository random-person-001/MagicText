package SourceryTextb1.GameObjects;

import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;

/** A staff such that you can perform magic.
 * Created by riley on 11-Jun-2016.
 */
public class Staff extends GameObject{
    private String layerName = "staff";
    /**
     * You should be able to reuse this class later for a better staff.  0:wood 1:engraved 2:jewel-encrusted
     */
    private int upgrades = 0;
    private ImageOrg orgo;

    public Staff (ImageOrg org, Room theRoom, int setX, int setY){
        super.strClass = "WoodStaff";
        orgo = org;
        room = theRoom;
        Layer staffLay = new Layer(new String[room.roomHeight][room.roomWidth], layerName);
        org.addLayer(staffLay);

        super.x = setX;
        super.y = setY;
    }

    public void update(){
        orgo.editLayer("i", layerName, y, x);
        if (room.playo.x == super.x && room.playo.y == super.y){
            room.infoMessage(orgo, "It would probably behoove you to place that into your inventory, such that you are more civilized than tossing "
                            +       "blunt objects, and can cast spells like a decent member of our society.  Press enter to learn how.");
            orgo.editLayer(" ", layerName, y, x);
            room.playo.inventory.put(super.strClass, 1);
            orgo.removeLayer(layerName);
            room.removeObject(this);
        }
    }

    /**
     * Set the awesomeness of the staff
     * @param newCoolness a number for which upgrade of staff this is; 0:wood 1:engraved 2:jewel-encrusted
     */
    public void setUpgrades(int newCoolness){
        upgrades = newCoolness;
        if (upgrades == 0){
            super.strClass = "WoodStaff";
        }
        else if (upgrades == 1){
            super.strClass = "EngravedStaff";
        }
        else if (upgrades == 2){
            super.strClass = "JewelEncrustedStaff";
        }
    }

    /**
     * @return the awesomeness of the staff.  0:wood 1:engraved 2:jewel-encrusted
     */
    public int getUpgrades(){
        return upgrades;
    }
}

