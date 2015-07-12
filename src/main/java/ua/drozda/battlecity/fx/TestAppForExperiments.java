package ua.drozda.battlecity.fx;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by GFH on 12.07.2015.
 */
public class TestAppForExperiments extends Application {
    private Group group1 = new Group();
    private Group group2 = new Group();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Label label = new Label();
        Group root = new Group();
        group1.getChildren().add(label);
        group2.getChildren().add(label);
        //   group1.setTranslateY(50);
        root.getChildren().add(group1);
        //  root.getChildren().add(group2);
        label.setText("IS OK???");
        Scene scene = new Scene(root, 100, 200, Color.RED);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
