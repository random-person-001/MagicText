package SourceryTextb1;

import java.awt.*;

/**
 * A test object to be serialized and sent over a network.
 * Created by riley on 03-Nov-2016.
 */
public class SendToWindow implements java.io.Serializable {
    public SpecialText[][] text = new SpecialText[46][28];
    public Color foreground = Color.WHITE;
    public Color background = Color.WHITE;
}