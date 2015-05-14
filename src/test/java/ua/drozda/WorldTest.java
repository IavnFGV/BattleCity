package ua.drozda;

import org.junit.Before;
import org.junit.Test;
import ua.drozda.battlecity.core.World;
import ua.drozda.battlecity.core.tiles.Tile;
import ua.drozda.battlecity.core.tiles.Water;
import ua.drozda.battlecity.core.world.GameCell;

import java.util.Observable;
import java.util.Observer;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static ua.drozda.TestConstants.testComplete;
import static ua.drozda.TestConstants.testMethodStarted;

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

    @Test
    public void testGameCellCreating() throws Exception {
        testMethodStarted("testGameCellCreating");
        Tile testTile = Tile.getTile(Water.class, 0);
        assertNotNull(testTile);
        System.out.println("testTile = " + testTile);
        GameCell gameCell = new GameCell(0, 0, testTile);
        assertNotNull(gameCell);
        System.out.println("gameCell = " + gameCell);
        testComplete();
    }

    @Test
    public void testUpdatableInterfaceForWater() throws Exception {
        testMethodStarted("testUpdatableInterfaceForWater");
        Tile testTile = Tile.getTile(Water.class, 0);
        assertNotNull(testTile);
        System.out.println("testTile = " + testTile);
        GameCell gameCell = new GameCell(0, 0, testTile);
        assertNotNull(gameCell);
        System.out.println("gameCell = " + gameCell);
        System.out.println("Making Update 1");
        gameCell.update(1L);
        System.out.println("gameCell = " + gameCell);
        assertTrue("Now waterState MUST be 1", gameCell.getTile().getTileState() == 1);
        System.out.println("Making Update 2");
        gameCell.update(2L);
        System.out.println("gameCell = " + gameCell);
        assertTrue("Now waterState MUST be 0", gameCell.getTile().getTileState() == 0);
        testComplete();
    }


    @Test
    public void testGameCellObserver() throws Exception {
        testMethodStarted("testGameCellObserver");

        Tile testTile = Tile.getTile(Water.class, 0);
        assertNotNull(testTile);
        System.out.println("testTile = " + testTile);
        GameCell gameCell = new GameCell(0, 0, testTile);
        assertNotNull(gameCell);
        System.out.println("gameCell = " + gameCell);
        TileObserver tileObserver = new TileObserver();
        System.out.println("tileObserver = " + tileObserver);
        gameCell.addObserver(tileObserver);
        System.out.println("Making Update 4567L");
        gameCell.update(4567L);
        assertTrue("tileObserver.lastUpdate MUST be 4567L", tileObserver.lastUpdate == 4567L);
        System.out.println("tileObserver = " + tileObserver);

    }

    public static class TileObserver implements Observer {
        private long lastUpdate;

        public void update(Observable o, Object arg) {
            if (o instanceof GameCell) {
                lastUpdate = ((GameCell) o).getLastUpdate();
            }
        }

        @Override
        public String toString() {
            return "TileObserver{" +
                    "lastUpdate=" + lastUpdate +
                    '}';
        }
    }

}
