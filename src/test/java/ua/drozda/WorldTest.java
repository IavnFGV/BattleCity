package ua.drozda;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ua.drozda.battlecity.core.World;
import ua.drozda.battlecity.core.actors.*;
import ua.drozda.battlecity.core.collisions.CollisionManager;
import ua.drozda.battlecity.core.tiles.Tile;
import ua.drozda.battlecity.core.tiles.Water;
import ua.drozda.battlecity.core.world.cells.GameCell;
import ua.drozda.battlecity.io.LevelLoader;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
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
        Tile testTile = Tile.getTile(Water.class);
        assertNotNull(testTile);
        System.out.println("testTile = " + testTile);
        GameCell gameCell = new GameCell(0, 0, testTile);
        assertNotNull(gameCell);
        System.out.println("gameCell = " + gameCell);
        testComplete();
    }

    @Ignore
    @Test
    public void testUpdatableInterfaceForWater() throws Exception {
        testMethodStarted("testUpdatableInterfaceForWater");
        Tile testTile = Tile.getTile(Water.class);
        assertNotNull(testTile);
        System.out.println("testTile = " + testTile);
        GameCell gameCell = new GameCell(0, 0, testTile);
        assertNotNull(gameCell);
        System.out.println("gameCell = " + gameCell);
        System.out.println("Making toggle");
        gameCell.toggle(null);
        System.out.println("gameCell = " + gameCell);
        //    assertTrue("Now waterState MUST be 1", gameCell.getTile().getTileState() == 1);
        System.out.println("Making toggle");
        gameCell.toggle(null);
        System.out.println("gameCell = " + gameCell);
        //   assertTrue("Now waterState MUST be 0", gameCell.getTile().getTileState() == 0);
        testComplete();
    }

    @Test
    public void testGameCellObserver() throws Exception {
        testMethodStarted("testGameCellObserver");
        class TileObserver implements Observer {
            private Tile curTile;

            public void update(Observable o, Object arg) {
                if (o instanceof GameCell) {

                    curTile = ((GameCell) o).getTile();
                }
            }

            @Override
            public String toString() {
                return "TileObserver{" +
                        "curTile=" + curTile +
                        '}';
            }

        }

        Tile testTile = Tile.getTile(Water.class);
        assertNotNull(testTile);
        System.out.println("testTile = " + testTile);
        GameCell gameCell = new GameCell(0, 0, testTile);
        assertNotNull(gameCell);
        System.out.println("gameCell = " + gameCell);
        TileObserver tileObserver = new TileObserver();
        System.out.println("tileObserver = " + tileObserver);
        gameCell.addObserver(tileObserver);
        System.out.println("Making toggle");
        gameCell.toggle(null);
        //       assertTrue("tileObserver.curTile.getTileState MUST be 1", tileObserver.curTile.getTileState() == 1);
        System.out.println("tileObserver = " + tileObserver);

    }


    @Ignore // correct mistake))
    @Test
    public void testActorMoving() {
        testMethodStarted("testActorMoving");
        CollisionManager collisionManager = new CollisionManager();
        PlayerTank tank = new PlayerTank(collisionManager);
        MoveStrategy<Tank> tankMoveStrategy = new MoveStrategy<Tank>(tank);
        tank.setMoveStrategy(tankMoveStrategy);

        class ActorObserver implements Observer {
            Actor actor;

            public void update(Observable o, Object arg) {
                if (o instanceof Actor.ObservableProxy) {
                    actor = ((Actor.ObservableProxy) o).getActor();
                }
            }
        }
        class TestWorld extends Observable {
            public void testObserver(ActorCommand command) {
                setChanged();
                notifyObservers(command);
            }
        }
        ActorObserver actorObserver = new ActorObserver();
        tank.addObserver(actorObserver);
        TestWorld testWorld = new TestWorld();
        testWorld.addObserver(tank);
        ActorCommand command = new ActorCommand();
        System.out.println("actorObserver.actor = " + actorObserver.actor);
        assertNull(actorObserver.actor);
        command.setDirection(Direction.UP);
        command.setNanoseconds(1000000000L);
        command.setVelocity(12E-8);
        testWorld.testObserver(command);
        assertNotNull("actorObserver.actor MUST be setted(not equalnull)", actorObserver.actor);
        System.out.println("actorObserver.actor = " + actorObserver.actor);
        testComplete();
    }

    @Test
    public void levelLoaderTest() throws Exception {
        testMethodStarted("levelLoaderTest");
        LevelLoader.loadlevel("5", world);
        System.out.println("world" + world);
        testComplete();
    }

    @Test
    public void test() {
        List<Object> objects = new LinkedList<Object>();
        objects.add(0, null);
        objects.add(1, null);

        System.out.println(objects);
    }

}
