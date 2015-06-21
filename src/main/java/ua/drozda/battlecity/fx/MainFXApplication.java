package ua.drozda.battlecity.fx;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ua.drozda.battlecity.core.ActiveUnit;
import ua.drozda.battlecity.core.TankUnit;
import ua.drozda.battlecity.core.World;
import ua.drozda.battlecity.io.LevelLoader;

/**
 * Created by GFH on 17.05.2015.
 */
public class MainFXApplication extends Application {

    World world = new World();
    FxWorld fxWorld = new FxWorld();
    Boolean sleepCommandHandle = true;
    Long toggleCount = 0l;

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

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent t) {
                if (sleepCommandHandle) {
                    return;
                }
                TankUnit tankUnit = world.getFirstPlayer();
                switch (t.getCode()) {
                    case UP:
                        tankUnit.setDirection(ActiveUnit.Direction.UP);
                        tankUnit.setEngineOn(true);
                        break;
                    case DOWN:
                        tankUnit.setDirection(ActiveUnit.Direction.DOWN);
                        tankUnit.setEngineOn(true);
                        break;
                    case LEFT:
                        tankUnit.setDirection(ActiveUnit.Direction.LEFT);
                        tankUnit.setEngineOn(true);
                        break;
                    case RIGHT:
                        tankUnit.setDirection(ActiveUnit.Direction.RIGHT);
                        tankUnit.setEngineOn(true);
                        break;
                    default:
                        tankUnit.setEngineOn(false);
                        break;
                }
                System.out.println("KEY PRESSED " + t.getCode());
                sleepCommandHandle = true;
            }
        });
//        root.setOnKeyReleased(new EventHandler<KeyEvent>() {
//
//            @Override
//            public void handle(KeyEvent t) {
//                releaseKey(t.getCharacter().charAt(0));
//            }
//        });
//        root.addEventHandler(EventType.ROOT, new EventHandler<Event>() {
//
//            @Override
//            public void handle(Event t) {
//                if (t.getClass().equals(KeyEvent.class)) {
//                    System.out.println("AAARGH! " + ((KeyEvent)t).getCharacter());
//                }
//            }
//        });


        for (FxGameUnit fxGameUnit : fxWorld.fxGameUnitsList) {
            root.getChildren().add(fxGameUnit.getImageView());
        }
        AnimationTimer toggleAnimationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    if (now - toggleCount >= 500000000l) {
                        fxWorld.toggle(null);
                        toggleCount = now;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        AnimationTimer mainLoop = new AnimationTimer() {
            private Boolean init = false;

            @Override
            public void handle(long now) {
                if (!init) {
                    world.initializeWorld(now);
                }
                world.handleCollisions();
                world.heartBeat(now);
                MainFXApplication.this.handleCommands(); // TODO TEST required
                world.updatePositions(now);
                world.notifyObservers();
            }
        };

        //root.getChildren().add(fxWorld.getFirstPlayerTank().getImageView());
        //  root.getChildren().add(fxWorld.cellList.get(54).getImageView());

//        InputStream inputStreamSprites = MainFXApplication.class.getResourceAsStream
//                ("/ua/drozda/battlecity/images/sprites.png");

//
//        Image image = new Image(inputStreamSprites);
//        ImageView iv1 = new ImageView();
//        ImageView iv2 = new ImageView();
//        iv1.setImage(image);
//        iv2.setImage(image);
//        Rectangle2D viewportRect1 = new Rectangle2D(FxWorld.tileZoneX, FxWorld.forestZoneY, 8, 8);
//        Rectangle2D viewportRect2 = new Rectangle2D(FxWorld.tileZoneX, FxWorld.waterZoneY, 8, 8);

//        iv1.setViewport(viewportRect1);
//        root.getChildren().add(iv1);
//        root.getChildren().add(iv2);
//        iv1.setFitWidth(100);
//        iv1.setFitHeight(100);
//        iv2.setFitWidth(100);
//        iv2.setFitHeight(100);
        //  iv1.setPreserveRatio(true);
        //   iv1.setSmooth(false);
        //   iv1.setCache(true);
        //    final Integer tick = 0;
//        AnimationTimer animationTimer = new AnimationTimer() {
//            @Override
//            public void handle(long now) {
//                try {
//                    if (now - toggleCount >= 500000000l) {
//                        fxWorld.getWorld().toggle(null);
//                        fxWorld.getFirstPlayerTank().getTank().toggle(null);
//                        toggleCount = now;
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                if ((now % 1000000000) != 0) {
//                    if (iv1.getViewport() == viewportRect1) {
//                        iv1.setViewport(viewportRect2);
//                        //        iv1.setTranslateX(32);
//                    } else {
//                        iv1.setViewport(viewportRect1);
//                        //        iv1.setTranslateX(0);
        //   }
//                }
        //        }
        //    };
        //     iv1.setViewport(viewportRect2);
        //     iv2.setViewport(viewportRect1);
        //    animationTimer.start();
        //     fxWorld.getWorld().toggle(null);
        //     fxWorld.getWorld().toggle(null);
        //     fxWorld.getWorld().toggle(null);
        toggleAnimationTimer.start();
        mainLoop.start();
        primaryStage.setTitle("JavaFX Scene Graph Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void handleCommands() {
        sleepCommandHandle = false;

    }


}
