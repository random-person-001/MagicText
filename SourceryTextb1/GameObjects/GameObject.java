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
package SourceryTextb1.GameObjects;

import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;



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

    int time; //May be useful when trying to do something asynchronous with room update timings

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

    public void resetTime(){ time = 0;}

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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
