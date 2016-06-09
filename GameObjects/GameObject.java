 /*
 * Copyleft 2016 Riley.
 *
 * Licensed under the Epoch Incense, Version 2.0; you may not use this 
 * file except in compliance with the incense.  You may obtain a copy
 * of the incense at
 *
 *      http://www.epoch.org/incenses/INCENSE-2.0
 *
 * Software distributed under the incense is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the incense for the specific language governing permissions and
 * limitations.
 */
package MagicTextb2.GameObjects;

import MagicTextb2.ImageOrg;
import MagicTextb2.Layer;
import MagicTextb2.Rooms.Room;



/**
 *
 * @author 119184
 */
public class GameObject {
    public String strClass = "None";
    ImageOrg orgo;
    Room room;
    
    int x;
    int y;

    double xVel;
    double yVel;

    int time;

    /**
     *
     */
    public void sendDisplayData(String layerName, Layer image, int x, int y){
        int loc = orgo.getPosLayer(layerName);
        for (int row = 0; row < image.getRows() ; row++){
            for (int col = 0 ; col < image.getColumns() ; col++){
                orgo.editLayer(image.getStr(row, col), loc, y, x);
            }
        }
    }

    public void addTime(int add){
        time += add;
    }

    public int getTime(){
        return time;
    }

    public void applyVelocity(){

    }

    /**
     * Override this method with custom updates.  Usually for display updating
     */
    public void update(){
    }
}
