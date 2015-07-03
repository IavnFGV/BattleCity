package ua.drozda.battlecity.fx;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ua.drozda.battlecity.core.ActiveUnit;
import ua.drozda.battlecity.core.TankUnit;
import ua.drozda.battlecity.core.World;
import ua.drozda.battlecity.io.LevelLoader;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * Created by GFH on 17.05.2015.
 */
public class MainFXApplication extends Application {

    World world = new World(2);
    FxWorld fxWorld = new FxWorld();
    Boolean sleepKeyPressHandle = true;
    Long toggleCount = 0l;
    KeyPressedEventHandler keyPressedEventHandler = new KeyPressedEventHandler();
    TankUnit firstPlayerTank;
    TankUnit secondPlayerTank;

    private Set<KeyCode> firstPlayerMovements = EnumSet.of(KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D);
    private Set<KeyCode> secondPlayerMovements = EnumSet.of(KeyCode.UP, KeyCode.LEFT, KeyCode.DOWN, KeyCode.RIGHT);


    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        LevelLoader.loadlevel("20", world);
        fxWorld.setWorld(world);
        Group root = new Group();
        Scene scene = new Scene(root, fxWorld.getWorld().getWorldWiddthPixel(), fxWorld.getWorld().getWorldHeightPixel(), Color
                .BLACK);
        scene.setOnKeyReleased(keyPressedEventHandler);
        scene.setOnKeyPressed(keyPressedEventHandler);
        AnimationTimer toggleAnimationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                fxWorld.toggle(now);
                toggleCount = now;
            }
        };

        AnimationTimer mainLoop = new AnimationTimer() {
            private Boolean init = false;

            @Override
            public void handle(long now) {
                if (!init) {

                    world.initializeWorld(now);
                    FxGameUnit tank = FxGameUnit.createFxGameUnit(world.getFirstPlayer());
                    fxWorld.fxGameUnitsList.add(tank);
                    firstPlayerTank = world.getFirstPlayer();
                    // root.getChildren().add(tank.getImageView());

                    FxGameUnit tank2 = FxGameUnit.createFxGameUnit(world.getSecondPlayer());
                    fxWorld.fxGameUnitsList.add(tank2);
                    secondPlayerTank = world.getSecondPlayer();
                    //root.getChildren().add(tank2.getImageView());
                    //fxWorld.ga
                    Collections.reverse(fxWorld.fxGameUnitsList);
                    for (FxGameUnit fxGameUnit : fxWorld.fxGameUnitsList) {
                        root.getChildren().add(fxGameUnit.getImageView());
                    }

                    init = true;
                }
                world.handleCollisions();
                world.heartBeat(now);
                handleCommands(); // TODO TEST required
                world.updatePositions(now);
                world.notifyObservers();
                handleSound(now);
            }
        };

        toggleAnimationTimer.start();
        mainLoop.start();
        primaryStage.setTitle("JavaFX Scene Graph Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void handleCommands() {
        if (!keyPressedEventHandler.isAnyKeyDown(firstPlayerMovements)) {
            firstPlayerTank.setEngineOn(false);
        } else {
            firstPlayerMovements();
        }
        if (!keyPressedEventHandler.isAnyKeyDown(secondPlayerMovements)) {
            secondPlayerTank.setEngineOn(false);
        } else {
            secondPlayerMovements();
        }

    }

    private void firstPlayerMovements() {
        if (keyPressedEventHandler.isKeyDown(KeyCode.W)) {
            firstPlayerTank.setDirection(ActiveUnit.Direction.UP);
            firstPlayerTank.setEngineOn(true);
        }
        if (keyPressedEventHandler.isKeyDown(KeyCode.S)) {
            firstPlayerTank.setDirection(ActiveUnit.Direction.DOWN);
            firstPlayerTank.setEngineOn(true);
        }
        if (keyPressedEventHandler.isKeyDown(KeyCode.A)) {
            firstPlayerTank.setDirection(ActiveUnit.Direction.LEFT);
            firstPlayerTank.setEngineOn(true);
        }
        if (keyPressedEventHandler.isKeyDown(KeyCode.D)) {
            firstPlayerTank.setDirection(ActiveUnit.Direction.RIGHT);
            firstPlayerTank.setEngineOn(true);
        }
    }

    private void secondPlayerMovements() {
        if (keyPressedEventHandler.isKeyDown(KeyCode.UP)) {
            secondPlayerTank.setDirection(ActiveUnit.Direction.UP);
            secondPlayerTank.setEngineOn(true);
        }
        if (keyPressedEventHandler.isKeyDown(KeyCode.DOWN)) {
            secondPlayerTank.setDirection(ActiveUnit.Direction.DOWN);
            secondPlayerTank.setEngineOn(true);
        }
        if (keyPressedEventHandler.isKeyDown(KeyCode.LEFT)) {
            secondPlayerTank.setDirection(ActiveUnit.Direction.LEFT);
            secondPlayerTank.setEngineOn(true);
        }
        if (keyPressedEventHandler.isKeyDown(KeyCode.RIGHT)) {
            secondPlayerTank.setDirection(ActiveUnit.Direction.RIGHT);
            secondPlayerTank.setEngineOn(true);
        }
    }

    private void secondPlayerMovements() {
        if (keyPressedEventHandler.isKeyDown(KeyCode.UP)) {
            secondPlayerTank.setDirection(ActiveUnit.Direction.UP);
            secondPlayerTank.setEngineOn(true);
        }
        if (keyPressedEventHandler.isKeyDown(KeyCode.DOWN)) {
            secondPlayerTank.setDirection(ActiveUnit.Direction.DOWN);
            secondPlayerTank.setEngineOn(true);
        }
        if (keyPressedEventHandler.isKeyDown(KeyCode.LEFT)) {
            secondPlayerTank.setDirection(ActiveUnit.Direction.LEFT);
            secondPlayerTank.setEngineOn(true);
        }
        if (keyPressedEventHandler.isKeyDown(KeyCode.RIGHT)) {
            secondPlayerTank.setDirection(ActiveUnit.Direction.RIGHT);
            secondPlayerTank.setEngineOn(true);
        }
    }

    private void handleSound(Long now) {
        SoundManager.handleSoundQueue(now);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        SoundManager.shutdown();

    }
}
