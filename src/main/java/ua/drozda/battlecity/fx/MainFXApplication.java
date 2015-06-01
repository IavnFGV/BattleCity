package ua.drozda.battlecity.fx;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ua.drozda.battlecity.io.LevelLoader;

/**
 * Created by GFH on 17.05.2015.
 */
public class MainFXApplication extends Application {
    FxWorld fxWorld = new FxWorld();
    Long toggleCount = 0l;
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // world;

        Group root = new Group();
        Scene scene = new Scene(root, fxWorld.getWorld().getWorldWiddthPixel(), fxWorld.getWorld().getWorldHeightPixel(), Color
                .BLACK);


        LevelLoader.loadlevel("20", fxWorld);
        for (FxCell fxCell : fxWorld.cellList) {
            root.getChildren().add(fxCell.getImageView());
        }
        root.getChildren().add(fxWorld.getFirstPlayerTank().getImageView());
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
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    if (now - toggleCount >= 500000000l) {
                        fxWorld.getWorld().toggle(null);
                        fxWorld.getFirstPlayerTank().getTank().toggle(null);
                        toggleCount = now;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                if ((now % 1000000000) != 0) {
//                    if (iv1.getViewport() == viewportRect1) {
//                        iv1.setViewport(viewportRect2);
//                        //        iv1.setTranslateX(32);
//                    } else {
//                        iv1.setViewport(viewportRect1);
//                        //        iv1.setTranslateX(0);
            }
//                }
            //        }
        };
        //     iv1.setViewport(viewportRect2);
        //     iv2.setViewport(viewportRect1);
        animationTimer.start();
        fxWorld.getWorld().toggle(null);
        fxWorld.getWorld().toggle(null);
        fxWorld.getWorld().toggle(null);
        primaryStage.setTitle("JavaFX Scene Graph Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
