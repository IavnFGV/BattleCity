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
                (FxBorderX.group//,FxBorder.stageInfo
                );
        scene = new Scene(root, 500, 470,
                Color.BLACK);
        primaryStage.setScene(scene);
        // primaryStage.setResizable(false);
        primaryStage.show();

    }
}
