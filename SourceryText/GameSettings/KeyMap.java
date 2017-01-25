package SourceryText.GameSettings;

import java.awt.event.KeyEvent;

/**
 * Created by Zach on 1/21/2017.
 */
public class KeyMap implements Settings{
    private static final transient String savePath = "Settings/KeyMaps/";
    private static final transient String extension = ".STKM";

    public String getSavePath()     {return savePath;}
    public String getExtension()    {return extension;}

    public int UP_PRIMARY = -1;
    public int LEFT_PRIMARY = -1;
    public int DOWN_PRIMARY = -1;
    public int RIGHT_PRIMARY = -1;
    public int CLOCKWISE_PRIMARY = -1;
    public int ANTICLOCKWISE_PRIMARY = -1;
    public int TURN_TYPE_TOGGLE_PRIMARY = -1;
    public int TURN_TYPE_HOLD_PRIMARY = -1;
    public int DISPLAY_DIRECTION_PRIMARY = -1;
    public int RUN_PRIMARY = -1;
    public int SPELL1_PRIMARY = -1;
    public int SPELL2_PRIMARY = -1;
    public int INTERACT_PRIMARY = -1;
    public int MENU_PRIMARY = -1;
    public int CONFIRM_PRIMARY = -1;
    public int BACK_PRIMARY = -1;

    public int UP_SECONDARY = -1;
    public int LEFT_SECONDARY = -1;
    public int DOWN_SECONDARY = -1;
    public int RIGHT_SECONDARY = -1;
    public int CLOCKWISE_SECONDARY = -1;
    public int ANTICLOCKWISE_SECONDARY = -1;
    public int TURN_TYPE_TOGGLE_SECONDARY = -1;
    public int TURN_TYPE_HOLD_SECONDARY = -1;
    public int DISPLAY_DIRECTION_SECONDARY = -1;
    public int RUN_SECONDARY = -1;
    public int SPELL1_SECONDARY = -1;
    public int SPELL2_SECONDARY = -1;
    public int INTERACT_SECONDARY = -1;
    public int MENU_SECONDARY = -1;
    public int CONFIRM_SECONDARY = -1;
    public int BACK_SECONDARY = -1;

    public void setMap(int action, int ordinal, int key) {
        switch(ordinal) {
            case 1:     switch (action) {
                            case 0:     UP_PRIMARY=key;     break;
                            case 1:     LEFT_PRIMARY=key;     break;
                            case 2:     DOWN_PRIMARY=key;     break;
                            case 3:     RIGHT_PRIMARY=key;     break;
                            case 4:     CLOCKWISE_PRIMARY=key;     break;
                            case 5:     ANTICLOCKWISE_PRIMARY=key;     break;
                            case 6:     TURN_TYPE_TOGGLE_PRIMARY=key;     break;
                            case 7:     TURN_TYPE_HOLD_PRIMARY=key;     break;
                            case 8:     DISPLAY_DIRECTION_PRIMARY=key;     break;
                            case 9:     RUN_PRIMARY=key;     break;
                            case 10:    SPELL1_PRIMARY=key;     break;
                            case 11:    SPELL2_PRIMARY=key;     break;
                            case 12:    INTERACT_PRIMARY=key;     break;
                            case 13:    MENU_PRIMARY=key;     break;
                            case 14:    CONFIRM_PRIMARY=key;     break;
                            case 15:    BACK_PRIMARY=key;     break;
                        }
                        break;
            case 2:     switch (action) {
                            case 0:     UP_SECONDARY=key;     break;
                            case 1:     LEFT_SECONDARY=key;     break;
                            case 2:     DOWN_SECONDARY=key;     break;
                            case 3:     RIGHT_SECONDARY=key;     break;
                            case 4:     CLOCKWISE_SECONDARY=key;     break;
                            case 5:     ANTICLOCKWISE_SECONDARY=key;     break;
                            case 6:     TURN_TYPE_TOGGLE_SECONDARY=key;     break;
                            case 7:     TURN_TYPE_HOLD_SECONDARY=key;     break;
                            case 8:     DISPLAY_DIRECTION_SECONDARY=key;     break;
                            case 9:     RUN_SECONDARY=key;     break;
                            case 10:    SPELL1_SECONDARY=key;     break;
                            case 11:    SPELL2_SECONDARY=key;     break;
                            case 12:    INTERACT_SECONDARY=key;     break;
                            case 13:    MENU_SECONDARY=key;     break;
                            case 14:    CONFIRM_SECONDARY=key;     break;
                            case 15:    BACK_SECONDARY=key;     break;
                        }
                break;
        }
    }

    public int getMap(int action, int ordinal) {
        switch(ordinal) {
            case 1:     switch (action) {
                case 0:     return UP_PRIMARY;
                case 1:     return LEFT_PRIMARY;
                case 2:     return DOWN_PRIMARY;
                case 3:     return RIGHT_PRIMARY;
                case 4:     return CLOCKWISE_PRIMARY;
                case 5:     return ANTICLOCKWISE_PRIMARY;
                case 6:     return TURN_TYPE_TOGGLE_PRIMARY;
                case 7:     return TURN_TYPE_HOLD_PRIMARY;
                case 8:     return DISPLAY_DIRECTION_PRIMARY;
                case 9:     return RUN_PRIMARY;
                case 10:    return SPELL1_PRIMARY;
                case 11:    return SPELL2_PRIMARY;
                case 12:    return INTERACT_PRIMARY;
                case 13:    return MENU_PRIMARY;
                case 14:    return CONFIRM_PRIMARY;
                case 15:    return BACK_PRIMARY;
                }
            case 2:     switch (action) {
                case 0:     return UP_SECONDARY;
                case 1:     return LEFT_SECONDARY;
                case 2:     return DOWN_SECONDARY;
                case 3:     return RIGHT_SECONDARY;
                case 4:     return CLOCKWISE_SECONDARY;
                case 5:     return ANTICLOCKWISE_SECONDARY;
                case 6:     return TURN_TYPE_TOGGLE_SECONDARY;
                case 7:     return TURN_TYPE_HOLD_SECONDARY;
                case 8:     return DISPLAY_DIRECTION_SECONDARY;
                case 9:     return RUN_SECONDARY;
                case 10:    return SPELL1_SECONDARY;
                case 11:    return SPELL2_SECONDARY;
                case 12:    return INTERACT_SECONDARY;
                case 13:    return MENU_SECONDARY;
                case 14:    return CONFIRM_SECONDARY;
                case 15:    return BACK_SECONDARY;
                }
        }
        return -1;
    }

    public String[] getActionDescription(int action) {
        switch (action) {
            case 0: return new String[]
                    {"moves the character 1 space up (north)",
                    "",
                    "Press (del) or enter & key to (un)map"};
            case 1: return new String[]
                    {"moves the character 1 space left",
                    "(west)",
                    "Press (del) or enter & key to (un)map"};
            case 2: return new String[]
                    {"moves the character 1 space down",
                    "(south)",
                    "Press (del) or enter & key to (un)map"};
            case 3: return new String[]
                    {"moves the character 1 space right",
                    "(east)",
                    "Press (del) or enter & key to (un)map"};
            case 4: return new String[]
                    {"turns the character 90 degrees",
                    "clockwise",
                    "Press (del) or enter & key to (un)map"};
            case 5: return new String[]
                    {"turns the character 90 degrees",
                    "anticlockwise",
                    "Press (del) or enter & key to (un)map"};
            case 6: return new String[]
                    {"toggles between auto-turning(direction",
                    "follows movement) and manual-turning",
                    "Press (del) or enter & key to (un)map"};
            case 7: return new String[]
                    {"swaps the turning mode while held down",
                    "",
                    "Press (del) or enter & key to (un)map"};
            case 8: return new String[]
                    {"toggles the display of the direction",
                    "indicator",
                    "Press (del) or enter & key to (un)map"};
            case 9: return new String[]
                    {"makes the character move faster",
                    "while held down (uses mana)",
                    "Press (del) or enter & key to (un)map"};
            case 10: return new String[]
                    {"casts the spell currently equipped to",
                    "spell slot #1",
                    "Press (del) or enter & key to (un)map"};
            case 11: return new String[]
                    {"casts the spell currently equipped to)",
                    "spell slot #2",
                    "Press (del) or enter & key to (un)map"};
            case 12: return new String[]
                    {"interacts with people or things",
                    "",
                    "Press (del) or enter & key to (un)map"};
            case 13: return new String[]
                    {"opens or closes the menu",
                    "",
                    "Press (del) or enter & key to (un)map"};
            case 14: return new String[]
                    {"enters sub-menus, continues dialogs,",
                    "and makes selections",
                    "Press (del) or enter & key to (un)map"};
            case 15: return new String[]
                    {"exits sub-menus, cancels dialogs,",
                    "and deselects",
                    "Press (del) or enter & key to (un)map"};
        }
        return new String[]{"","",""};
    }

    public String getKeyText(int keyCode){
        switch(keyCode){
            case KeyEvent.VK_0: return "0";
            case KeyEvent.VK_1: return "1";
            case KeyEvent.VK_2: return "2";
            case KeyEvent.VK_3: return "3";
            case KeyEvent.VK_4: return "4";
            case KeyEvent.VK_5: return "5";
            case KeyEvent.VK_6: return "6";
            case KeyEvent.VK_7: return "7";
            case KeyEvent.VK_8: return "8";
            case KeyEvent.VK_9: return "9";
            case KeyEvent.VK_A: return "A";
            case KeyEvent.VK_ACCEPT: return "ACCEPT";
            case KeyEvent.VK_ADD: return "NUM+";
            case KeyEvent.VK_AGAIN: return "AGAIN";
            case KeyEvent.VK_ALL_CANDIDATES: return "ALLCAN";
            case KeyEvent.VK_ALPHANUMERIC: return "ALPHNU";
            case KeyEvent.VK_ALT: return "ALT";
            case KeyEvent.VK_ALT_GRAPH: return "ALTGRA";
            case KeyEvent.VK_AMPERSAND: return "&";
            case KeyEvent.VK_ASTERISK: return "*";
            case KeyEvent.VK_AT: return "@";
            case KeyEvent.VK_B: return "B";
            case KeyEvent.VK_BACK_QUOTE: return "`";
            case KeyEvent.VK_BACK_SLASH: return "\\";
            case KeyEvent.VK_BACK_SPACE: return "BKSP";
            case KeyEvent.VK_BEGIN: return "BEGIN";
            case KeyEvent.VK_BRACELEFT: return "{";
            case KeyEvent.VK_BRACERIGHT: return "}";
            case KeyEvent.VK_C: return "C";
            case KeyEvent.VK_CANCEL: return "CANCEL";
            case KeyEvent.VK_CAPS_LOCK: return "CAPS";
            case KeyEvent.VK_CIRCUMFLEX: return "CIRCUM";
            case KeyEvent.VK_CLEAR: return "CLEAR";
            case KeyEvent.VK_CLOSE_BRACKET: return "]";
            case KeyEvent.VK_CODE_INPUT: return "CODEIN";
            case KeyEvent.VK_COLON: return ":";
            case KeyEvent.VK_COMMA: return ",";
            case KeyEvent.VK_COMPOSE: return "COMPOS";
            case KeyEvent.VK_CONTEXT_MENU: return "CNTXT";
            case KeyEvent.VK_CONTROL: return "CTRL";
            case KeyEvent.VK_CONVERT: return "CNVERT";
            case KeyEvent.VK_COPY: return "COPY";
            case KeyEvent.VK_CUT: return "CUT";
            case KeyEvent.VK_D: return "D";
            case KeyEvent.VK_DEAD_ABOVEDOT: return "DADOT";
            case KeyEvent.VK_DEAD_ABOVERING: return "DARING";
            case KeyEvent.VK_DEAD_ACUTE: return "DACUTE";
            case KeyEvent.VK_DEAD_BREVE: return "DBREVE";
            case KeyEvent.VK_DEAD_CARON: return "DCARON";
            case KeyEvent.VK_DEAD_CEDILLA: return "DCEDLA";
            case KeyEvent.VK_DEAD_CIRCUMFLEX: return "DCIRC";
            case KeyEvent.VK_DEAD_DIAERESIS: return "DDIAER";
            case KeyEvent.VK_DEAD_DOUBLEACUTE: return "DDACUT";
            case KeyEvent.VK_DEAD_GRAVE: return "DGRAVE";
            case KeyEvent.VK_DEAD_IOTA: return "DIOTA";
            case KeyEvent.VK_DEAD_MACRON: return "DMACRON";
            case KeyEvent.VK_DEAD_OGONEK: return "DOGNEK";
            case KeyEvent.VK_DEAD_SEMIVOICED_SOUND: return "DSEMIV";
            case KeyEvent.VK_DEAD_TILDE: return "D~";
            case KeyEvent.VK_DEAD_VOICED_SOUND: return "DVOICE";
            case KeyEvent.VK_DECIMAL: return "NUM.";
            case KeyEvent.VK_DELETE: return "DEL";
            case KeyEvent.VK_DIVIDE: return "NUM/";
            case KeyEvent.VK_DOLLAR: return "$";
            case KeyEvent.VK_DOWN: return "DOWN";
            case KeyEvent.VK_E: return "E";
            case KeyEvent.VK_END: return "END";
            case KeyEvent.VK_ENTER: return "ENTER";
            case KeyEvent.VK_EQUALS: return "=";
            case KeyEvent.VK_ESCAPE: return "ESC";
            case KeyEvent.VK_EURO_SIGN: return "EURO";
            case KeyEvent.VK_EXCLAMATION_MARK: return "!";
            case KeyEvent.VK_F: return "F";
            case KeyEvent.VK_F1: return "F1";
            case KeyEvent.VK_F10: return "F10";
            case KeyEvent.VK_F11: return "F11";
            case KeyEvent.VK_F12: return "F12";
            case KeyEvent.VK_F13: return "F13";
            case KeyEvent.VK_F14: return "F14";
            case KeyEvent.VK_F15: return "F15";
            case KeyEvent.VK_F16: return "F16";
            case KeyEvent.VK_F17: return "F17";
            case KeyEvent.VK_F18: return "F18";
            case KeyEvent.VK_F19: return "F19";
            case KeyEvent.VK_F2: return "F2";
            case KeyEvent.VK_F20: return "F20";
            case KeyEvent.VK_F21: return "F21";
            case KeyEvent.VK_F22: return "F22";
            case KeyEvent.VK_F23: return "F23";
            case KeyEvent.VK_F24: return "F24";
            case KeyEvent.VK_F3: return "F3";
            case KeyEvent.VK_F4: return "F4";
            case KeyEvent.VK_F5: return "F5";
            case KeyEvent.VK_F6: return "F6";
            case KeyEvent.VK_F7: return "F7";
            case KeyEvent.VK_F8: return "F8";
            case KeyEvent.VK_F9: return "F9";
            case KeyEvent.VK_FINAL: return "FINAL";
            case KeyEvent.VK_FIND: return "FIND";
            case KeyEvent.VK_FULL_WIDTH: return "FULWTH";
            case KeyEvent.VK_G: return "G";
            case KeyEvent.VK_GREATER: return ">";
            case KeyEvent.VK_H: return "H";
            case KeyEvent.VK_HALF_WIDTH: return "HAFWTH";
            case KeyEvent.VK_HELP: return "HELP";
            case KeyEvent.VK_HIRAGANA: return "HRGANA";
            case KeyEvent.VK_HOME: return "HOME";
            case KeyEvent.VK_I: return "I";
            case KeyEvent.VK_INPUT_METHOD_ON_OFF: return "IMOF";
            case KeyEvent.VK_INSERT: return "INS";
            case KeyEvent.VK_INVERTED_EXCLAMATION_MARK: return "¡";
            case KeyEvent.VK_J: return "J";
            case KeyEvent.VK_JAPANESE_HIRAGANA: return "JAPHGN";
            case KeyEvent.VK_JAPANESE_KATAKANA: return "JAPKKN";
            case KeyEvent.VK_JAPANESE_ROMAN: return "JAPRMN";
            case KeyEvent.VK_K: return "K";
            case KeyEvent.VK_KANA: return "KANA";
            case KeyEvent.VK_KANA_LOCK: return "KANLK";
            case KeyEvent.VK_KANJI: return "KANJI";
            case KeyEvent.VK_KATAKANA: return "KTKANA";
            case KeyEvent.VK_KP_DOWN: return "KPDOWN";
            case KeyEvent.VK_KP_LEFT: return "KPLEFT";
            case KeyEvent.VK_KP_RIGHT: return "KPRGHT";
            case KeyEvent.VK_KP_UP: return "KPUP";
            case KeyEvent.VK_L: return "L";
            case KeyEvent.VK_LEFT: return "LEFT";
            case KeyEvent.VK_LEFT_PARENTHESIS: return ")";
            case KeyEvent.VK_LESS: return "<";
            case KeyEvent.VK_M: return "M";
            case KeyEvent.VK_META: return "META";
            case KeyEvent.VK_MINUS: return "-";
            case KeyEvent.VK_MODECHANGE: return "MODECH";
            case KeyEvent.VK_MULTIPLY: return "NUM*";
            case KeyEvent.VK_N: return "N";
            case KeyEvent.VK_NONCONVERT: return "NONCNV";
            case KeyEvent.VK_NUM_LOCK: return "NUMLK";
            case KeyEvent.VK_NUMBER_SIGN: return "#";
            case KeyEvent.VK_NUMPAD0: return "NUM0";
            case KeyEvent.VK_NUMPAD1: return "NUM1";
            case KeyEvent.VK_NUMPAD2: return "NUM2";
            case KeyEvent.VK_NUMPAD3: return "NUM3";
            case KeyEvent.VK_NUMPAD4: return "NUM4";
            case KeyEvent.VK_NUMPAD5: return "NUM5";
            case KeyEvent.VK_NUMPAD6: return "NUM6";
            case KeyEvent.VK_NUMPAD7: return "NUM7";
            case KeyEvent.VK_NUMPAD8: return "NUM8";
            case KeyEvent.VK_NUMPAD9: return "NUM9";
            case KeyEvent.VK_O: return "O";
            case KeyEvent.VK_OPEN_BRACKET: return "]";
            case KeyEvent.VK_P: return "P";
            case KeyEvent.VK_PAGE_DOWN: return "PGDOWN";
            case KeyEvent.VK_PAGE_UP: return "PGUP";
            case KeyEvent.VK_PASTE: return "PASTE";
            case KeyEvent.VK_PAUSE: return "PAUSE";
            case KeyEvent.VK_PERIOD: return ".";
            case KeyEvent.VK_PLUS: return "+";
            case KeyEvent.VK_PREVIOUS_CANDIDATE: return "PRECAN";
            case KeyEvent.VK_PRINTSCREEN: return "PRINT";
            case KeyEvent.VK_PROPS: return "PROPS";
            case KeyEvent.VK_Q: return "Q";
            case KeyEvent.VK_QUOTE: return "'";
            case KeyEvent.VK_QUOTEDBL: return "\"";
            case KeyEvent.VK_R: return "R";
            case KeyEvent.VK_RIGHT: return "RIGHT";
            case KeyEvent.VK_RIGHT_PARENTHESIS: return ")";
            case KeyEvent.VK_ROMAN_CHARACTERS: return "ROMAN";
            case KeyEvent.VK_S: return "S";
            case KeyEvent.VK_SCROLL_LOCK: return "SCROLK";
            case KeyEvent.VK_SEMICOLON: return ";";
            case KeyEvent.VK_SEPARATOR: return "SEPRAT";
            case KeyEvent.VK_SHIFT: return "SHIFT";
            case KeyEvent.VK_SLASH: return "/";
            case KeyEvent.VK_SPACE: return "SPACE";
            case KeyEvent.VK_STOP: return "STOP";
            case KeyEvent.VK_SUBTRACT: return "NUM-";
            case KeyEvent.VK_T: return "T";
            case KeyEvent.VK_TAB: return "TAB";
            case KeyEvent.VK_U: return "U";
            case KeyEvent.VK_UNDEFINED: return "UNKNWN";
            case KeyEvent.VK_UNDERSCORE: return "_";
            case KeyEvent.VK_UNDO: return "UNDO";
            case KeyEvent.VK_UP: return "UP";
            case KeyEvent.VK_V: return "V";
            case KeyEvent.VK_W: return "W";
            case KeyEvent.VK_WINDOWS: return "WIN";
            case KeyEvent.VK_X: return "X";
            case KeyEvent.VK_Y: return "Y";
            case KeyEvent.VK_Z: return "Z";
        }
        return "none";
    }
}
