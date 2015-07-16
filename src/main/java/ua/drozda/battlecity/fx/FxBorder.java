package ua.drozda.battlecity.fx;

import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;
import ua.drozda.battlecity.core.World;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GFH on 11.07.2015.
 */
public class FxBorder {
    public static Group group = new Group();
    private static FxBorder instance;
    private static Rectangle2D enemyRect = new Rectangle2D(912, 320, FxWorld.tileSize, FxWorld.tileSize);
    private static Rectangle2D emptyRect = new Rectangle2D(1008, 0, FxWorld.tileSize, FxWorld.tileSize);
    private static Map<Integer, Rectangle2D> digitMap = new HashMap<>(10);
    private static IntegerProperty enemiesCountProperty;
    private static IntegerProperty stageNumberProperty;
    private static IntegerProperty firstPlayerLifesProperty;
    private static IntegerProperty secondPlayerLifesProperty;

    static {
        double baseX = 929;
        double baseY = 303;
        for (int i = 0; i < 5; i++) {
            Rectangle2D rectangle2D = new Rectangle2D(baseX + FxWorld.tileSize * i, baseY, FxWorld.tileSize, FxWorld
                    .tileSize);
            digitMap.put(i, rectangle2D);
        }
        for (int i = 5; i < 10; i++) {
            Rectangle2D rectangle2D = new Rectangle2D(baseX + FxWorld.tileSize * (i - 5), baseY + FxWorld.tileSize,
                    FxWorld.tileSize, FxWorld
                    .tileSize);
            digitMap.put(i, rectangle2D);
        }


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FxBorder.class.getResource("Playground.fxml"));
        Node node;
        try {
            node = loader.load();
            group.getChildren().add(node);
            instance = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        instance.pauseImage.setViewport(new Rectangle2D(850, 288, 78, 14));
        instance.pauseImage.setX(13 * 16 - 39);
        instance.pauseImage.setY(13 * 16 - 7);
    }

    private FadeTransition pauseTransition;
    @FXML
    private TilePane enemyCounter;
    @FXML
    private ImageView secondPlayerLifes;
    @FXML
    private ImageView firstPlayerLifes;
    @FXML
    private Pane secondPlayerHider;
    @FXML
    private ImageView stageNumberFirstDigit;
    @FXML
    private ImageView stageNumberSecondDigit;

    private ImageView pauseImage = new ImageView(FxWorld.sprites);

    public static final int getEnemiesCount() {
        return enemiesCountProperty == null ? 0 : enemiesCountProperty.getValue();
    }

    public static final void setEnemiesCount(int value) {
        enemiesCountProperty().setValue(value);
    }

    public static final IntegerProperty enemiesCountProperty() {
        if (enemiesCountProperty == null) {
            enemiesCountProperty = new SimpleIntegerProperty();
        }
        return enemiesCountProperty;
    }

    public static final int getStageNumber() {
        return stageNumberProperty == null ? 0 : stageNumberProperty.getValue();
    }

    public static final void setStageNumber(int value) {
        stageNumberProperty().setValue(value);
    }

    public static final IntegerProperty stageNumberProperty() {
        if (stageNumberProperty == null) {
            stageNumberProperty = new SimpleIntegerProperty();
        }
        return stageNumberProperty;
    }

    public static final int getFirstPlayerLifes() {
        return firstPlayerLifesProperty == null ? 0 : firstPlayerLifesProperty.getValue();
    }

    public static final void setFirstPlayerLifes(int value) {
        firstPlayerLifesProperty().setValue(value);
    }

    public static final IntegerProperty firstPlayerLifesProperty() {
        if (firstPlayerLifesProperty == null) {
            firstPlayerLifesProperty = new SimpleIntegerProperty();
        }
        return firstPlayerLifesProperty;
    }

    public static final int getSecondPlayerLifes() {
        return secondPlayerLifesProperty == null ? 0 : secondPlayerLifesProperty.getValue();
    }

    public static final void setSecondPlayerLifes(int value) {
        secondPlayerLifesProperty().setValue(value);
    }

    public static final IntegerProperty secondPlayerLifesProperty() {
        if (secondPlayerLifesProperty == null) {
            secondPlayerLifesProperty = new SimpleIntegerProperty();
        }
        return secondPlayerLifesProperty;
    }

    public static void refresh(World.WorldType worldType, int stageNumber) {
        getInstance().secondPlayerHider.setVisible(worldType != World.WorldType.DOUBLE_PLAYER);
        int firstDigit = stageNumber / 10;
        int secondDigit = stageNumber % 10;
        getInstance().stageNumberFirstDigit.setViewport(digitMap.get(firstDigit));
        getInstance().stageNumberSecondDigit.setViewport(digitMap.get(secondDigit));

    }

    public static FxBorder getInstance() {
        return instance;
    }

    public static void showPause() {
        getInstance().pauseTransition.play();
    }

    public static Node getPause() {
        return getInstance().pauseImage;
    }

    public static void hidePause() {
        getInstance().pauseTransition.stop();
    }

    @FXML
    private void initialize() {
        for (int i = 0; i < 20; i++) {
            ImageView imageView = new ImageView(FxWorld.sprites);
            imageView.setViewport(enemyRect);
            imageView.setId("E" + i);
            enemyCounter.getChildren().add(imageView);
        }
        enemiesCountProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.longValue() < oldValue.longValue()) {
                ((ImageView) enemyCounter.lookup("#E" + oldValue)).setViewport(emptyRect);
            }
        });
        firstPlayerLifesProperty().addListener((observable, oldValue, newValue) -> {
            firstPlayerLifes.setViewport(digitMap.get(newValue));
        });
        secondPlayerLifesProperty().addListener((observable, oldValue, newValue) -> {
            secondPlayerLifes.setViewport(digitMap.get(newValue));
        });

        pauseTransition = new FadeTransition(Duration.millis(100), pauseImage);
        pauseTransition.setFromValue(1.0);
        pauseTransition.setToValue(0.0);
        pauseTransition.setCycleCount(Transition.INDEFINITE);
        pauseTransition.setAutoReverse(true);

    }


}
