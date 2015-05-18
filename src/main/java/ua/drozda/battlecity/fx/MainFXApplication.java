package ua.drozda.battlecity.fx;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import ua.drozda.battlecity.core.World;

import java.io.InputStream;

/**
 * Created by GFH on 17.05.2015.
 */
public class MainFXApplication extends Application {
    World world = new World();

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        Group root = new Group();
        //     Scene scene = new Scene(root, World.WORLD_WIDTH_PIXEL, World.WORLD_HEIGHT_PIXEL, Color.BLACK);


        InputStream inputStreamSprites = MainFXApplication.class.getResourceAsStream("/ua/drozda/battlecity/images/sprites.png");


        Image image = new Image(inputStreamSprites);
        ImageView iv1 = new ImageView();
        iv1.setImage(image);
        Rectangle2D viewportRect1 = new Rectangle2D(0, 0, 16, 16);
        Rectangle2D viewportRect2 = new Rectangle2D(32, 0, 16, 16);
        iv1.setViewport(viewportRect1);
        root.getChildren().add(iv1);
        iv1.setFitWidth(100);
        iv1.setPreserveRatio(true);
        iv1.setSmooth(false);
        iv1.setCache(true);
        final Integer tick = 0;
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if ((now % 1000000000) != 0) {
                    if (iv1.getViewport() == viewportRect1) {
                        iv1.setViewport(viewportRect2);
                        iv1.setTranslateX(32);
                    } else {
                        iv1.setViewport(viewportRect1);
                        iv1.setTranslateX(0);
                    }
                }
            }
        };
        animationTimer.start();
        primaryStage.setTitle("JavaFX Scene Graph Demo");
        //      primaryStage.setScene(scene);
        primaryStage.show();
    }
}
