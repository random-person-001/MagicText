package SourceryText.GameSettings;

import java.io.Serializable;

/**
 * Created by Zach on 1/22/2017.
 */
public interface Settings extends Serializable{
    public String getSavePath();
    public String getExtension();
}
