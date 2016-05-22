package ch.fhnw.oop2.departure_app.Service;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the file helper service
 */
public class FileHelperTest
{
    @Test
    public void testGetAbsolutePath()
    {
        String path = "resources/assets/css/main.css";

        String absolutePath = FileHelper.getAbsolutePath(path);

        Assert.assertTrue(absolutePath.contains("main.css"));
    }
}
