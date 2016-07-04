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

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;


 /**
 *
 * @author 119184
 */
public class GameObject {
    public String strClass = "None";
    protected ImageOrg orgo;
    protected Room room;
    
    protected int x;
    protected int y;

    protected AtomicBoolean paused = new AtomicBoolean(false);

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

    public void setupTimer(int frequency){
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new updateTimer(frequency), frequency, frequency);
    }

    public void setPause(boolean set){
        paused.set(set);
    }

    class updateTimer extends TimerTask {
        int freq;

        public updateTimer(int frequency){
            freq = frequency;
        }

        public void run(){
            if (!paused.get()) {
                update();
                addTime(freq);
            }
        }
    }
}
