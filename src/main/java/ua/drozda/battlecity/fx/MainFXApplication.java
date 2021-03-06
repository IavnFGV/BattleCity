package ua.drozda.battlecity.fx;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.binding.IntegerBinding;
import javafx.collections.ListChangeListener;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ua.drozda.battlecity.core.ActiveUnit;
import ua.drozda.battlecity.core.GameUnit;
import ua.drozda.battlecity.core.TankUnit;
import ua.drozda.battlecity.core.World;
import ua.drozda.battlecity.core.modificators.PauseWorldModificator;
import ua.drozda.battlecity.core.modificators.WorldModificator;
import ua.drozda.battlecity.fx.sprites.FxSpriteBigExplosion;
import ua.drozda.battlecity.io.LevelLoader;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by GFH on 17.05.2015.
 */
public class MainFXApplication extends Application {

    World world = new World(World.WorldType.DOUBLE_PLAYER, 2);
    FxWorld fxWorld = new FxWorld();
    IntegerBinding integerBinding;
    Boolean sleepKeyPressHandle = true;
    Long toggleCount = 0l;
    KeyPressedEventHandler keyPressedEventHandler = new KeyPressedEventHandler();
    TankUnit firstPlayerTank;
    TankUnit secondPlayerTank;
    Group playGround = new Group();
    Group root = new Group();
    Scene scene;

    private Set<KeyCode> firstPlayerMovements = EnumSet.of(KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D);
    private Set<KeyCode> secondPlayerMovements = EnumSet.of(KeyCode.UP, KeyCode.LEFT, KeyCode.DOWN, KeyCode.RIGHT);
    private KeyCode firstPlayerFire = KeyCode.SPACE;
    private KeyCode secondPlayerFire = KeyCode.NUMPAD0;


    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        LevelLoader.loadlevel("20", world);
        world.prepareWorld();
        world.setStageNumber(20);
        fxWorld.setWorld(world);


        root.getChildren().add(FxBorder.group);
        root.getChildren().add(playGround);
        scene = new Scene(root, fxWorld.getWorld().getWorldWiddthPixel() +
                fxWorld.getWorld().getCellWidth() * 6,//for bounds around playground
                fxWorld.getWorld().getWorldHeightPixel() +
                        fxWorld.getWorld().getCellWidth() * 4, //for bounds around playground
                Color.BLACK);
        playGround.setLayoutX(fxWorld.getWorld().getCellWidth() * 2);
        playGround.setLayoutY(fxWorld.getWorld().getCellHeight() * 2);
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
                    firstPlayerTank = world.getFirstPlayer();
                    secondPlayerTank = world.getSecondPlayer();
                    Collections.reverse(fxWorld.fxGameUnitsList);
                    for (FxGameUnit fxGameUnit : fxWorld.fxGameUnitsList) {
                        playGround.getChildren().addAll(fxGameUnit.getSprites().stream().map(s -> s.getImageView())
                                .collect(Collectors.toList()));
                    }
                    for (FxGameUnit fxGameUnit : fxWorld.fxGameUnitsList) {
                        fxGameUnit.getSprites().stream().filter(fxSprite -> fxSprite instanceof FxSpriteBigExplosion)
                                .map(fxSprite -> fxSprite.getImageView()).forEach(imageView -> imageView.toFront());
                    }
                    FxBorder.enemiesCountProperty().bind(world.enemiesCountProperty());
                    FxBorder.firstPlayerLifesProperty().bind(firstPlayerTank.lifesCountProperty());
                    if (secondPlayerTank != null) {
                        FxBorder.secondPlayerLifesProperty().bind(secondPlayerTank.lifesCountProperty());
                    }
                    FxBorder.refresh(world.getWorldType(), world.getStageNumber());
                    world.getModificators().getModificator(PauseWorldModificator.class)
                            .stateProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue == WorldModificator.State.ACTIVE) {
                            FxBorder.showPause();
                        } else {
                            FxBorder.hidePause();
                        }
                    });
                    playGround.getChildren().add(FxBorder.getPause());
                    FxBorder.getPause().toFront();
                    fxWorld.fxGameUnitsList.addListener(new ListChangeListener<FxGameUnit>() {
                        @Override
                        public void onChanged(Change<? extends FxGameUnit> c) {
                            while (c.next()) {
                                if (c.wasAdded()) {
                                    List<FxBulletUnit> addedBullets = c.getAddedSubList().stream()
                                            .filter(fxGameUnit -> fxGameUnit instanceof FxBulletUnit)
                                            .map(gameUnit -> (FxBulletUnit) gameUnit)
                                            .collect(Collectors.toList());
                                    System.out.print(addedBullets);
                                    for (FxBulletUnit fxBulletUnit : addedBullets) {
                                        List<Node> fxBulletList = fxBulletUnit.getSprites()
                                                .stream().map(s -> s.getImageView())
                                                .collect(Collectors.toList());
                                        playGround.getChildren().addAll(fxBulletList);
                                        fxBulletList.forEach(node -> node.toBack());
                                    }
                                }
                            }
                        }
                    });
                    init = true;
                }
                world.handleCollisions();
                world.handleModificators(now);
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
        //    primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void handleCommands() {
        if (!keyPressedEventHandler.isAnyKeyDown(firstPlayerMovements)) {
            firstPlayerTank.setEngineOn(false);
        } else {
            firstPlayerMovements();
        }
        if (keyPressedEventHandler.wasKeyPressed(firstPlayerFire)) {
            firstPlayerTank.fire();
        }
        if (secondPlayerTank == null) {
            return;
        }
        if (!keyPressedEventHandler.isAnyKeyDown(secondPlayerMovements)) {
            secondPlayerTank.setEngineOn(false);
        } else {
            secondPlayerMovements();
        }
        if (keyPressedEventHandler.wasKeyPressed(secondPlayerFire)) {
            secondPlayerTank.fire();
        }

    }

    private void handleSound(Long now) {
        SoundManager.handleSoundQueue(now);
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
        if (keyPressedEventHandler.isKeyDown(KeyCode.Z)) {
            firstPlayerTank.setBasicState(GameUnit.BasicState.EXPLODING);
            //      world.getModificators().getModificator(PauseWorldModificator.class).setState(WorldModificator.State
            //              .ACTIVE);
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

    @Override
    public void stop() throws Exception {
        super.stop();
        SoundManager.shutdown();

    }

}
