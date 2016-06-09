/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



package MagicTextb2;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JFrame;
import javax.swing.JTextArea;



/**
 *
 * @author 119184
 */
public class Window extends JFrame{
    private static final String OPAQUE_SPACE = "\t";
    public JTextArea txtArea = new JTextArea();
    private Container c = getContentPane();
    
    private Layer fullImage = new Layer(new String[maxH()][maxW()]);

    /**
     * @return the maximum allowable height in the game
     */
    public int maxH(){
        return 62;
    } //Note that this must be >= any layer sizes, but other than that it's arbitrary

    /**
     * @return the maximum allowable width in the game
     */
    public int maxW(){ return 95; }

    /**
     * Directly set the textArea contents to just an empty string
     */
    public void clearText(){
        txtArea.setText("");
    }


    /**
     * Clear out the compiled layers to be spacey quotes.  Note that this will be overwritten/ignored next
     * time org.compileImage() is called
     */
    public void clearImage(){
        //fullImage = new String[50][20];
        for (int row = 0; row < fullImage.getRows() ; row++){
            for (int col = 0; col < fullImage.getColumns(); col++){
                fullImage.setStr(row,col," ");
            }
        }
    }

    /**
     * Clear out a rectangle in the compiled layers to be spacey quotes.  Note that this will be overwritten/ignored
     * next time org.compileImage() is called
     * @param rStart starting row
     * @param rEnd ending row
     * @param cStart starting column
     * @param cEnd ending column
     */
    public void clearArea(int rStart, int rEnd, int cStart, int cEnd){
        rEnd = (rEnd < fullImage.getRows()) ? fullImage.getRows() : rEnd;
        cEnd = (cEnd < fullImage.getColumns()) ? fullImage.getColumns() : cEnd;
        for (int row = rStart; row < rEnd ; row++){
            for (int col = cStart; col < cEnd; col++){
                fullImage.setStr(row,col," ");
            }
        }
    }

    /** Perform some sort of magic.   I quote from the function, "This stuff is complicated!!!!"
     * @param layer a new Layer to be placed
     * @param camX camera X coord
     * @param camY camera Y coord
     */
    public void placeLayer(Layer layer, int camX, int camY){ //Layers place on top of each other
        for (int row = 0; row < layer.getRows() ; row++){
            for (int col = 0; col < layer.getColumns(); col++){ //This stuff is complicated!!!!
                if (!" ".equals(layer.getStr(row,col))){ 
                    if ("".equals(layer.getStr(row, col)) || layer.getStr(row, col) == null || layer.getStr(row, col).equals(OPAQUE_SPACE)){
                        fullImage.placeStr(row + layer.getX() - camX,col + layer.getY() - camY," ");
                    } else {
                        fullImage.setStr(row + layer.getX() - camX, col + layer.getY() - camY, layer.getStr(row,col));
                    }
                }
            }
        }
    }

    /** Much like placeLayer, but ignores camera.
     * @param layer a layer to place on top of fullImage
     */
    public void setLayer(Layer layer){ //Much like placeLayer, but ignores camera
        for (int row = 0; row < fullImage.getRows() ; row++){
            for (int col = 0; col < fullImage.getColumns(); col++){ //This stuff is complicated!!!!
                if (!" ".equals(layer.getStr(row, col))){ 
                    if ("".equals(layer.getStr(row, col)) || layer.getStr(row, col) == null || layer.getStr(row, col).equals(OPAQUE_SPACE)){
                        fullImage.placeStr(row, col, " ");
                    } else {
                        fullImage.setStr(row, col, layer.getStr(row,col));
                    }
                }
            }
        }
    }

    /** Place the temporary idea of what should be on the screen (fullImage) onto the actual display
     * @param camX camera X coordinate
     * @param camY camera Y coordinate
     */
    public void build(int camX, int camY){
        //fullImage = new String[20][50];       
        String build = "";
        for (int row = 0; row < maxH(); row++){// Used to be 20
            for (int col = 0; col < maxW(); col++){ // Used to be 50
                if ("".equals(fullImage.getStr(row,col)) || fullImage.getStr(row,col) == null){
                    build += " ";
                } else {
                    build += fullImage.getStr(row,col);
                }
            }
            build += "\n";
        }
        clearText();
        txtArea.append(build);
    }

    /** Toss some string onto the end of the window's text area.  Note that in most cases, this will be off the screen.
     * @param str the String to append
     */
    public void append(String str){
        txtArea.setText(txtArea.getText() + str);
    }
    
    public Window() throws InterruptedException{
        setBounds(100, 100, 412, 412);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Magic Text!");
        setResizable(false);

        clearImage();
        build(0,0);
        txtArea.setBackground(Color.BLACK);
        txtArea.setForeground(Color.WHITE);
        //txtArea.setFont(new Font("Consolas", Font.PLAIN, 15));
        txtArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
        txtArea.setEditable(false);

        c.add(txtArea);
        c.validate();

        setVisible(true);
        txtArea.requestFocusInWindow();
    }
    
    private void test() throws InterruptedException{
        String[][] test = {{"H","e","l","l","o","."}};
        Layer lay1 = new Layer(test);
        boolean testBool = (txtArea != null);
        //System.out.println(testBool);
        boolean testBool2 = (c != null);
        //System.out.println(testBool2);
        //System.out.println(true);
        
        //c.remove(txtArea);
        //System.out.println(secureValidate());
        //c.validate();
        
        clearImage();
        //placeLayer(lay1, 0, 0);
        build(0,0);        
        
        c.add(txtArea);
        c.validate();
    }

    public void validateContainer(){
        c.validate();
    }

    /**
     * Perform some sort of magic, of questionable usefulness.
     */
    private void init(){
        clearImage();
        build(0,0);        
        
        c.add(txtArea);
        c.validate();
        txtArea.requestFocusInWindow();
    }
    

    private boolean secureValidate(){
        AtomicBoolean bool = new AtomicBoolean(false);
        c.validate();
        bool.lazySet(true);
        return bool.get();
    }
}
