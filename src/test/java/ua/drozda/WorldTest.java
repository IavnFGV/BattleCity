package ua.drozda;

import org.junit.Before;
import ua.drozda.battlecity.core.World;

/**
 * Unit test for simple App.
 */
public class WorldTest {

    private World world;

    @Before
    public void prepare() {
        System.out.println(">>>>WorldTest Starting");
        world = new World();
        System.out.println("world created");
    }



}
