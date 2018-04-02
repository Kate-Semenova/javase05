package test.t01.model;

import main.t01.model.TxtChanger;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Ekaterina Semenova on 19.03.2018.
 */
public class TxtChangerTest {
    @Test
    public void addLineShouldAddNewLineToFile() {
        File file = new File("src\\file\\fileTest.txt");
        if (file.exists()) {
            TxtChanger txtChanger = new TxtChanger(file);
            assertTrue(txtChanger.addLine("Hello"));
            assertEquals("Hello\n", txtChanger.getText());
        }
    }
}
