package SourceryText.GameSettings;

import SourceryText.SpecialText;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Zach on 2/20/2017.
 */
public class DisplaySettings {
    private static final transient String savePath = "Settings/KeyMaps/";
    private static final transient String extension = ".STKM";

    public String getSavePath()     {return savePath;}
    public String getExtension()    {return extension;}

    private static final transient String fontPath = "Settings/Display/Font";
    private static final transient String[] fontExtensions = new String[]{".ttf", ".otf"};
    public String fontName = "Monospaced";

    public float fontSizeMultiplier = 1;

    public int fontStyle = Font.PLAIN;

    public static final transient int FULL_COLOR = 0;
    public static final transient int BLACK_AND_WHITE = 1;
    public int color = FULL_COLOR;

    public int brightnessDelta = 0;
    public static final transient int NORMALBRIGHTNESS = 0;
    public static final transient int INVERTEDBRIGHTNESS = 1;
    public int invertedBrightness = NORMALBRIGHTNESS;

    private static final transient String framePath = "Settings/Display/Frame";
    public transient SpecialText[] frameChars = new SpecialText[]{};
    public String frameName;

    public transient JPanel windowBorder = null;
    public String windowBordername;

    public int[] windowSize = new int[]{-1,-1};

    public boolean alwaysOnTop = false;
}
