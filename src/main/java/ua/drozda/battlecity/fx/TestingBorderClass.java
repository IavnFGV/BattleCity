package ua.drozda.battlecity.fx;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by GFH on 05.07.2015.
 */
public class TestingBorderClass extends Application {
    Group root = new Group();
    Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        root.getChildren().addAll
                (FxBorder.border//,FxBorder.stageInfo
                );
        scene = new Scene(root, 640, 480,
                Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
