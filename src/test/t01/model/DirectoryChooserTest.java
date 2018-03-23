package test.t01.model;

import main.t01.model.DirectoryChooser;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.nio.file.NoSuchFileException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Ekaterina Semenova on 19.03.2018.
 */
public class DirectoryChooserTest {
    private String name;
    private String path;
    private DirectoryChooser directoryChooser;
    private String fileStr;
    private File file;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp(){
        name = "newFile";
        path = "src\\file";
        directoryChooser = new DirectoryChooser(path);
        fileStr = path +"\\"+ name + ".txt";
        file = new File(fileStr);
    }
    @Test
    public void createNewFileShouldReturnBoolean(){
        String name = "newFile";
        String path = "src\\file";
        DirectoryChooser directoryChooser = new DirectoryChooser(path);
        String fileStr = path +"\\"+ name + ".txt";
        File file = new File(fileStr);
        if(file.exists()){
            assertFalse(directoryChooser.createNewFile(name));
        } else{
            assertTrue(directoryChooser.createNewFile(name));
        }
    }
    @Test
    public void deleteFileShouldReturnBoolean(){

        if(file.exists()){
            assertTrue(directoryChooser.deleteFile(file));
        } else{
            assertFalse(directoryChooser.deleteFile(file));
        }
    }

    @Test

    public void changeDirectoryShouldThrowException() throws NoSuchFileException {
        File file = new File("src\\file\\file");
        thrown.expect(NoSuchFileException.class);
        thrown.expectMessage(file.getAbsolutePath());
        directoryChooser.changeDirectory("src\\file\\file");
    }
}
