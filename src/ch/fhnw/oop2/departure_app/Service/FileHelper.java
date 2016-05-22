package ch.fhnw.oop2.departure_app.Service;

import java.io.File;

/**
 * File helper class that provides some functionality to deal with files
 */
public class FileHelper
{
    /**
     * Builds an absolute path for a given file
     * @param filePath String The file path
     * @return String The absolute path
     */
    public static String getAbsolutePath(String filePath)
    {
        File f = new File(filePath);

        return "file:///" + f.getAbsolutePath().replace("\\", "/");
    }
}
