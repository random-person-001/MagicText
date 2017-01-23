package SourceryText.GameSettings;

import java.io.*;
import java.io.FileInputStream;

/**
 * Created by Zach on 1/22/2017.
 */
public class SettingsIO {
    private FileOutputStream fileOut;
    private FileInputStream fileIn;
    private ObjectOutputStream objOut;
    private ObjectInputStream objIn;

    public void saveSettings(Settings settings, String fileName) {
        try {
            fileOut = new FileOutputStream(System.getProperty("user.dir") + "/" + settings.getSavePath() + fileName + settings.getExtension());
            objOut = new ObjectOutputStream(fileOut);
            objOut.writeObject(settings);
            objOut.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in " + fileName);
        }
        catch(IOException i) {
            i.printStackTrace();
        }
    }

    public Object loadSettings(Settings settings, String fileName) {
        try {
            fileIn = new FileInputStream(System.getProperty("user.dir") + "/" + settings.getSavePath() + fileName + settings.getExtension());
            objIn = new ObjectInputStream(fileIn);
            settings = (Settings) objIn.readObject();
            objIn.close();
            fileIn.close();
        }catch(IOException i) {
            i.printStackTrace();
            return null;
        }catch(ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return null;
        }
        return settings;
    }

    public String[] getFileNames(Settings settings) {
        int num = 0;
        File[] files = new File(settings.getSavePath()).listFiles();
        for (File file : files) {
            if (file.isFile()) {
                num++;
            }
        }
        String[] fileNames = new String[num];
        for (int i=0;i<files.length;i++) {
            if (files[i].isFile()) {
                fileNames[i] = files[i].getName().substring(0,files[i].getName().lastIndexOf("."));
            }
        }
        return fileNames;
    }
}
