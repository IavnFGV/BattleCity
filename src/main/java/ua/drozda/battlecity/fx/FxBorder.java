package ua.drozda.battlecity.fx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import ua.drozda.battlecity.core.World;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GFH on 04.07.2015.
 */
public class FxBorder {
    public static Group border = new Group();
    public static Group stageInfo = new Group();
    public static Group stageNumberGroup = new Group();
    public static Group enemyCounter = new Group();
    public static ImageView firstPlayerLifesImageView = new ImageView(FxWorld.sprites);
    private static Map<Long, Rectangle2D> digitMap = new HashMap<>(10);
    private static Rectangle2D enemyRect = new Rectangle2D(912, 320, FxWorld.tileSize, FxWorld.tileSize);
    private static Rectangle2D emptyRect = new Rectangle2D(1008, 0, FxWorld.tileSize, FxWorld.tileSize);
    private static ImageView secondPlayerLifesImageView = new ImageView(FxWorld.sprites);

    private static ImageView disableSecondPlayerLifesImageView = new ImageView(FxWorld.sprites);
    private static ImageView stageNumberFirstDigitImageView = new ImageView(FxWorld.sprites);
    private static ImageView stageNumberSecondDigitImageView = new ImageView(FxWorld.sprites);
    private static IntegerProperty enemiesCount;
    private static IntegerProperty stageNumber;
    private static LongProperty firstPlayerLifes;
    private static LongProperty secondPlayerLifes;

    static {

        double baseX = 929;
        double baseY = 303;

        //Parent parent
        Parent node = null;
        try {
            node = FXMLLoader.load(FxBorder.class.getResource("Playground.fxml"));
            border.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 5; i++) {
            Rectangle2D rectangle2D = new Rectangle2D(baseX + FxWorld.tileSize * i, baseY, FxWorld.tileSize, FxWorld
                    .tileSize);
            digitMap.put((long) i, rectangle2D);
        }
        for (int i = 5; i < 10; i++) {
            Rectangle2D rectangle2D = new Rectangle2D(baseX + FxWorld.tileSize * (i - 5), baseY + FxWorld.tileSize,
                    FxWorld.tileSize, FxWorld
                    .tileSize);
            digitMap.put((long) i, rectangle2D);
        }

        ImageView imageView = new ImageView(FxWorld.sprites);//right
        imageView.setViewport(new Rectangle2D(FxWorld.tankSize * 31 + FxWorld.tileSize, 0, 2 * FxWorld
                .tankSize,
                14 * FxWorld.tankSize));
        stageInfo.getChildren().add(imageView);

        stageInfo.setLayoutX(28 * FxWorld.tileSize);
        stageInfo.setLayoutY(2 * FxWorld.tileSize);

        stageInfo.getChildren().add(firstPlayerLifesImageView);

        firstPlayerLifesProperty().addListener((observable, oldValue, newValue) -> {
            firstPlayerLifesImageView.setViewport(digitMap.get(newValue));
        });
        firstPlayerLifesImageView.setY(256);
        firstPlayerLifesImageView.setX(32);

        stageInfo.getChildren().add(secondPlayerLifesImageView);

        secondPlayerLifesProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                secondPlayerLifesImageView.setViewport(digitMap.get(newValue));
            }
        });
        secondPlayerLifesImageView.setY(304);
        secondPlayerLifesImageView.setX(32);

        stageInfo.getChildren().add(disableSecondPlayerLifesImageView);

        disableSecondPlayerLifesImageView.setViewport(new Rectangle2D(480, 256, FxWorld.tankSize, FxWorld.tankSize));
        disableSecondPlayerLifesImageView.setX(16);
        disableSecondPlayerLifesImageView.setY(288);

        stageInfo.getChildren().add(stageNumberGroup);
        stageNumberGroup.setTranslateY(368);
        stageNumberGroup.setTranslateX(16);

        stageNumberGroup.getChildren().addAll(stageNumberFirstDigitImageView, stageNumberSecondDigitImageView);
        stageNumberSecondDigitImageView.setX(FxWorld.tileSize);

        stageInfo.getChildren().add(enemyCounter);

        enemyCounter.setTranslateY(FxWorld.tileSize);
        enemyCounter.setTranslateX(FxWorld.tileSize);


//        for (int i = 0; i < 20; i++) {
//            imageView = new ImageView(FxWorld.sprites);
//            imageView.setViewport(enemyRect);
//            imageView.setY(i / 2 * FxWorld.tileSize);
//            imageView.setX(i % 2 * FxWorld.tileSize);
//            imageView.setId("E" + i);
//            enemyCounter.getChildren().add(imageView);
//        }

        enemiesCountProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.longValue() < oldValue.longValue()) {
                ((ImageView) enemyCounter.lookup("#E" + oldValue)).setViewport(emptyRect);
            }
        });
    }

    @FXML
    private TilePane enemyCounterTest;

    public static void refresh(World world) {
        disableSecondPlayerLifesImageView.setVisible(world.getWorldType() != World.WorldType.DoublePlayer);
        int firstDigit = world.getStageNumber() / 10;
        int secondDigit = world.getStageNumber() % 10;
        stageNumberFirstDigitImageView.setViewport(digitMap.get((long) firstDigit));
        stageNumberSecondDigitImageView.setViewport(digitMap.get((long) secondDigit));

    }

    public static final int getEnemiesCount() {
        return enemiesCount == null ? 0 : enemiesCount.getValue();
    }

    public static final void setEnemiesCount(int value) {
        enemiesCountProperty().setValue(value);
    }

    public static final int getStageNumber() {
        return stageNumber == null ? 0 : stageNumber.getValue();
    }

    public static final void setStageNumber(int value) {
        stageNumberProperty().setValue(value);
    }

    public static final IntegerProperty stageNumberProperty() {
        if (stageNumber == null) {
            stageNumber = new SimpleIntegerProperty();
        }
        return stageNumber;
    }

    public static final void setfirstPlayerLifes(long value) {
        firstPlayerLifesProperty().setValue(value);
    }

    public static final LongProperty firstPlayerLifesProperty() {
        if (firstPlayerLifes == null) {
            firstPlayerLifes = new SimpleLongProperty();
        }
        return firstPlayerLifes;
    }

    public static final long getfirstPlayerLifes() {
        return firstPlayerLifes == null ? 0 : firstPlayerLifes.getValue();
    }

    public static final long getSecondPlayerLifes() {
        return secondPlayerLifes == null ? 0 : secondPlayerLifes.getValue();
    }

    public static final void setSecondPlayerLifes(long value) {
        secondPlayerLifesProperty().setValue(value);
    }

    public static final LongProperty secondPlayerLifesProperty() {
        if (secondPlayerLifes == null) {
            secondPlayerLifes = new SimpleLongProperty();
        }
        return secondPlayerLifes;
    }

    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.

        for (int i = 0; i < 20; i++) {
            ImageView imageView = new ImageView(FxWorld.sprites);
            imageView.setViewport(enemyRect);
            //   imageView.setY(i / 2 * FxWorld.tileSize);
            //  imageView.setX(i % 2 * FxWorld.tileSize);
            imageView.setId("E" + i);
            enemyCounterTest.getChildren().add(imageView);
        }
        stageInfo.getChildren().add(enemyCounterTest);
        //enemyCounterTest

        enemiesCountProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.longValue() < oldValue.longValue()) {
                ((ImageView) enemyCounter.lookup("#E" + oldValue)).setViewport(emptyRect);
            }
        });


    }

    public static final IntegerProperty enemiesCountProperty() {
        if (enemiesCount == null) {
            enemiesCount = new SimpleIntegerProperty();
        }
        return enemiesCount;
    }


}
