import me.jesensky.dan.playertracker.Configuration;
import me.jesensky.dan.playertracker.exceptions.NoSuchKeyException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.IOException;
import java.util.*;

@RunWith(JUnit4.class)
public class TestConfiguration {
    @Test
    public void loadInts() throws IOException, NoSuchKeyException {
        Configuration configuration = new Configuration();
        configuration.setValue("toads", 51252);
        configuration.setValue("1", 9999);
        Configuration.save("test", configuration);
        configuration = Configuration.load("test");
        Assert.assertEquals(51252, (int) configuration.getValue("toads"));
        Assert.assertEquals(9999, (int) configuration.getValue("1"));
        new File("toads").delete();
    }

    @Test
    public void loadStrings() throws IOException, NoSuchKeyException {
        Configuration configuration = new Configuration();
        configuration.<String>setValue("toads%", "how many toads?");
        configuration.<String>setValue("toads", "so many toads");
        Configuration.save("test", configuration);
        configuration = Configuration.load("test");
        Assert.assertEquals("how many toads?", configuration.<String>getValue("toads%"));
        Assert.assertEquals("so many toads", configuration.<String>getValue("toads"));
        new File("toads").delete();
    }

    @Test
    public void loadCollections() throws IOException, NoSuchKeyException {
        Configuration configuration = new Configuration();
        List<Object> l1 = new ArrayList<Object>(), l2 = new LinkedList<Object>();

        //add items to lists
        l1.add("toads");
        l1.add(35);
        l1.add(36.33);

        l2.add(353535);
        l2.add(233.2212);
        l2.add("frogs");

        //set collections
        configuration.<List>setValue("list1", l1);
        configuration.<List>setValue("list2", l2);
        //save
        Configuration.save("test", configuration);
        //load
        configuration = Configuration.load("test");
        //assert
        Assert.assertArrayEquals(l1.toArray(), ((List<Object>) configuration.getValue("list1")).toArray());
        Assert.assertArrayEquals(l2.toArray(), ((List<Object>) configuration.getValue("list2")).toArray());

        new File("toads").delete();
    }

    @Test
    public void loadDoubles() throws IOException, NoSuchKeyException {
        Configuration configuration = new Configuration();
        configuration.setValue("toads", 84510.2152);
        configuration.setValue("1", 83.3336);
        Configuration.save("test", configuration);
        configuration = Configuration.load("test");
        Assert.assertEquals(84510.2152, configuration.<Double>getValue("toads"), 1);
        Assert.assertEquals(83.3336, configuration.<Double>getValue("1"), 1);
        new File("toads").delete();
    }

    @Test
    public void n4() throws IOException, NoSuchKeyException {
        Configuration configuration = new Configuration();
        configuration.setValue("toads", 51252);
        Configuration.save("test", configuration);
        configuration = Configuration.load("test");
        Assert.assertEquals(51252, (int) configuration.getValue("toads"));
        new File("toads").delete();
    }

    @Test
    public void streamsClosed() throws IOException, NoSuchKeyException {
        Configuration configuration = new Configuration();
        Configuration.save("test", configuration);
        new File("toads").delete();
        Configuration.save("test", configuration);
        configuration = Configuration.load("test");
        new File("toads").delete();
    }
}