package SourceryTextb1;

import java.awt.*;

public class SpecialText implements java.io.Serializable {
    String self = "";
    Color foregroundColor = new Color(255, 255, 255);
    Color backgroundColor = new Color(0, 0, 0);

    public SpecialText(String assign) {
        this(assign, null, null);
    }

    public SpecialText(String assign, Color setForeGround) {
        this(assign, setForeGround, null);
    }

    public SpecialText(String assign, Color setForeGround, Color setBackGround) {
        self = assign;
        if (setForeGround != null) foregroundColor = setForeGround;
        if (setBackGround != null) backgroundColor = setBackGround;
    }

    public String getStr() {
        return self;
    }

    public Color getForegroundColor() {
        return foregroundColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void becomeOpaqueSpace() {
        self = "ñ";
    }

    public String toString() {
        return getStr();
    }

    public boolean isSignificant() {
        return !("".equals(self) || " ".equals(self) || self == null) || !backgroundColor.equals(Color.BLACK);
    }

    /**
     * Returns the foreground color accented by another one.
     *
     * @param modifier The color that is influencing the base foreground color. (can be null)
     */
    public Color makeInfluencedForegroundColor(Color modifier) {
        return makeInfluencedForegroundColor(modifier, false);
    }

    public void setInfluencedForegroundColor(Color modifier) {
        foregroundColor = makeInfluencedForegroundColor(modifier, false);
    }

    public Color makeInfluencedForegroundColor(Color modifier, boolean debug) {
        if (modifier == null) {
            return foregroundColor;
        }
        float redModifier = (float) modifier.getRed() / 255;
        float greenModifier = (float) modifier.getGreen() / 255;
        float blueModifier = (float) modifier.getBlue() / 255;
        if (debug)
            System.out.printf("factors:\nR: %1$f\nG: %2$f\nB: %3$f\n", (float) foregroundColor.getRed() * redModifier, (float) foregroundColor.getGreen() * greenModifier, (float) foregroundColor.getBlue() * blueModifier);
        return new Color(
                (int) ((float) foregroundColor.getRed() * redModifier),
                (int) ((float) foregroundColor.getGreen() * greenModifier),
                (int) ((float) foregroundColor.getBlue() * blueModifier));
        //return Color.WHITE;
    }

    public boolean equals(SpecialText other) {
        return self.equals(other.getStr()) && foregroundColor.equals(other.getForegroundColor()) && backgroundColor.equals(other.getBackgroundColor());
    }
}
