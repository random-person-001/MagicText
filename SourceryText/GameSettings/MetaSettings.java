package SourceryText.GameSettings;

/**
 * Created by Zach on 1/22/2017.
 */
public class MetaSettings implements Settings{
    private static final transient String savePath = "Settings/Meta/";
    private static final transient String extension = ".STMETA";

    public String getSavePath()     {return savePath;}
    public String getExtension()    {return extension;}

    public static final transient String FILE_NAME = "MetaSettings";

    public static final transient String[] OVERITE_PROTECTED_KEYMAPS = {} ;
    public static final transient String DEFAULT_KEYMAP = "DefaultKeyMap";
    public String USER_KEYMAP = "";
}
