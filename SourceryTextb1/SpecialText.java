package SourceryTextb1;

import java.awt.*;

public class SpecialText{
    String self = "";
    Color foregroundColor = new Color (255,255,255);
    Color backgroundColor = new Color (0  ,0  ,0);

    public SpecialText( String assign){
        this (assign, null, null);
    }

    public SpecialText (String assign, Color setForeGround){
        this (assign, setForeGround, null);
    }

    public SpecialText (String assign, Color setForeGround, Color setBackGround){
        self = assign;
        if (setForeGround != null) foregroundColor = setForeGround;
        if (setBackGround != null) backgroundColor = setBackGround;
    }

    public String getStr(){ return self; }

    public Color getForegroundColor(){ return foregroundColor; }

    public Color getBackgroundColor(){ return backgroundColor; }

    /**
     * Returns the foreground color accented by another one.
     * @param modifier The color that is influencing the base foreground color.
     */
    public Color makeInfluencedForegroundColor (Color modifier){
        float redModifier = modifier.getRed() / 255;
        float greenModifier = modifier.getGreen() / 255;
        float blueModifier = modifier.getBlue() / 255;
        return new Color(
                (float)foregroundColor.getRed() * redModifier,
                (float)foregroundColor.getGreen() * greenModifier,
                (float)foregroundColor.getBlue() * blueModifier);
    }
}
