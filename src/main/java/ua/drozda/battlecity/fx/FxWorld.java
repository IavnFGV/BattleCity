package ua.drozda.battlecity.fx;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import sun.misc.Launcher;
import ua.drozda.battlecity.core.BulletUnit;
import ua.drozda.battlecity.core.GameUnit;
import ua.drozda.battlecity.core.World;
import ua.drozda.battlecity.core.interfaces.Togglable;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * Created by GFH on 18.05.2015.
 */
public class FxWorld implements Togglable {

    public static final Integer tileSize = 16;
    public static final Integer tankSize = 32;

    public static final Integer bulletExplosion = 32;
    public static final Integer tankExplosion = 64;
    public static final Integer baseExplosion = 64;


    public static final Integer tileZoneX = 28 * 32;
    public static final Integer firstPlayerZoneX = 20 * tankSize;
    public static final Integer firstPlayerZoneY = 0 * tankSize;
    public static final Integer secondPlayerZoneX = 24 * tankSize;
    public static final Integer secondPlayerZoneY = 0 * tankSize;

    public static final Integer borderZoneX = 15 * tankSize;
    public static final Integer borderZoneY = 8 * tankSize;

    public static final Integer brickZoneY = 0 * tileSize;
    public static final Integer steelZoneY = 1 * tileSize;
    public static final Integer waterZoneY = 2 * tileSize;
    public static final Integer forestZoneY = 3 * tileSize;
    public static final Integer iceZoneY = 4 * tileSize;

    public static final Integer emptyZoneY = 11 * tileSize;
    public static final Integer explozionZoneY = 17 * tileSize;


    //     private static InputStream spritesStream = FxWorld.class.getResourceAsStream("../images/sprites.png");
//    private static InputStream spritesStream = FxWorld.class.getResourceAsStream("../images/graphics_debug.png");
    public static Map<String, Image> imageMap = new HashMap<>();
    private static InputStream spritesStream = FxWorld.class.getResourceAsStream("../images/graphics.png");
    public static volatile Image sprites = new Image(spritesStream);
    ObservableList<FxGameUnit> fxGameUnitsList = FXCollections.observableArrayList(new ArrayList<>());
    // private FxTank firstPlayerTank;
    private World world;

    {

        final String path = "ua/drozda/battlecity/images";
        final File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        try {
            if (jarFile.isFile()) {  // Run with JAR file
                final JarFile jar = new JarFile(jarFile);
                final Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
                while (entries.hasMoreElements()) {
                    final String name = entries.nextElement().getName();
                    if (name.startsWith(path + "/")) { //filter according to the path
                        System.out.println(name);
                    }
                }
                jar.close();
            } else { // Run with IDE
                final URL url = Launcher.class.getResource("/" + path);
                if (url != null) {
                    try {
                        final File apps = new File(url.toURI());
                        for (File app : apps.listFiles()) {
                            System.out.println(app);
                        }
                    } catch (URISyntaxException ex) {
                        // never happens
                    }
                }
            }
        } catch (Exception e) {

        }
    }

    public FxWorld() {
    }


    //   public FxTank getFirstPlayerTank() {
//        return firstPlayerTank;
//    }

    public void handleCollisions() {

    }

    @Override
    public void doToggle(Long now) {
        fxGameUnitsList.forEach(u -> u.toggle(now));
    }

    public World getWorld() {
        return world;
    }


    public void setWorld(World world) {
        this.world = world;

        getWorld().getUnitList().forEach(u -> {
            fxGameUnitsList.add(FxGameUnit.createFxGameUnit(u));
        });
        getWorld().getObservableUnitList().
                addListener(new ListChangeListener<GameUnit>() {
                                @Override
                                public void onChanged(Change<? extends GameUnit> c) {
                                    System.out.println("Detected a change! ");
                                    while (c.next()) {
                                        if (c.wasAdded()) {
                                            List<BulletUnit> addedBullets = c.getAddedSubList().stream()
                                                    .filter(gameUnit -> gameUnit instanceof BulletUnit)
                                                    .map(gameUnit -> (BulletUnit) gameUnit)
                                                    .collect(Collectors.toList());
                                            addedBullets.forEach(bulletUnit -> fxGameUnitsList.add(FxGameUnit
                                                    .createFxGameUnit(bulletUnit)));
                                        }
                                    }
                                }
                            }

                );
    }


}
