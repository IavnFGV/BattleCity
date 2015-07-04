package ua.drozda.battlecity.fx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GFH on 04.07.2015.
 */
public class FxBorder {
    public static Group border = new Group();
    public static Group stageInfo = new Group();
    public static ImageView firstPlayerLifesImageView;// = new ImageView(FxWorld.sprites);
    private static Map<Long, Rectangle2D> digitMap = new HashMap<>(10);
    // private static ImageView secondPlayerLifesImageView = new ImageView(FxWorld.sprites);

    static {

        Rectangle2D rectangle2D = new Rectangle2D(929, 303, FxWorld.tileSize, FxWorld.tileSize);
        digitMap.put(0l, rectangle2D);
        rectangle2D = new Rectangle2D(945, 303, FxWorld.tileSize, FxWorld.tileSize);
        digitMap.put(1l, rectangle2D);


        ImageView imageView = new ImageView(FxWorld.sprites);//top
        imageView.setViewport(new Rectangle2D(FxWorld.borderZoneX, FxWorld.borderZoneY, 32 * FxWorld.tileSize,
                FxWorld.tankSize));
        border.getChildren().add(imageView);
        imageView = new ImageView(FxWorld.sprites);//bottom
        imageView.setViewport(new Rectangle2D(FxWorld.borderZoneX, FxWorld.borderZoneY, 32 * FxWorld.tileSize,
                FxWorld.tankSize));
        imageView.setY(28 * FxWorld.tileSize);
        border.getChildren().add(imageView);
        imageView = new ImageView(FxWorld.sprites);//right
        imageView.setViewport(new Rectangle2D(FxWorld.tankSize * 31 + FxWorld.tileSize, 0, 2 * FxWorld
                .tankSize,
                14 * FxWorld.tankSize));
        imageView.setX(28 * FxWorld.tileSize);
        imageView.setY(2 * FxWorld.tileSize);
        stageInfo.getChildren().add(imageView);
        border.getChildren().add(stageInfo);
        imageView = new ImageView(FxWorld.sprites);//left
        imageView.setViewport(new Rectangle2D(FxWorld.borderZoneX, FxWorld.borderZoneY, 32 * FxWorld.tileSize,
                FxWorld.tankSize));
        imageView.setX(-15 * FxWorld.tileSize);
        imageView.setY(14 * FxWorld.tileSize);
        imageView.setRotate(90);
        border.getChildren().add(imageView);
        imageView = new ImageView(FxWorld.sprites);
        //    stageInfo.getChildren().add(imageView);

    }

    private IntegerProperty enemiesCount;
    private IntegerProperty stageNumber;
    private LongProperty firstPlayerLifes;
    private LongProperty secondPlayerLifes;

    public FxBorder() {
        firstPlayerLifesImageView = new ImageView(FxWorld.sprites);

        enemiesCountProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                firstPlayerLifesImageView.setViewport(digitMap.get(newValue));
            }


        });
        //   border.getChildren().add(firstPlayerLifesImageView);
        //    border.getChildren().add(stageInfo);
    }

    public final IntegerProperty enemiesCountProperty() {
        if (enemiesCount == null) {
            enemiesCount = new SimpleIntegerProperty(this, "enemiesCount", 20);
        }
        return enemiesCount;
    }

    public final int getEnemiesCount() {
        return enemiesCount == null ? 0 : enemiesCount.getValue();
    }

    public final void setEnemiesCount(int value) {
        enemiesCountProperty().setValue(value);
    }

    public final int getStageNumber() {
        return stageNumber == null ? 0 : stageNumber.getValue();
    }

    public final void setStageNumber(int value) {
        stageNumberProperty().setValue(value);
    }

    public final IntegerProperty stageNumberProperty() {
        if (stageNumber == null) {
            stageNumber = new SimpleIntegerProperty(this, "stageNumber", 20);
        }
        return stageNumber;
    }

    public final void setfirstPlayerLifes(long value) {
        firstPlayerLifesProperty().setValue(value);
    }

    public final LongProperty firstPlayerLifesProperty() {
        if (firstPlayerLifes == null) {
            firstPlayerLifes = new SimpleLongProperty(this, "firstPlayerLifes", 2);
        }
        return firstPlayerLifes;
    }

    public final long getfirstPlayerLifes() {
        return firstPlayerLifes == null ? 0 : firstPlayerLifes.getValue();
    }

    public final long getSecondPlayerLifes() {
        return secondPlayerLifes == null ? 0 : secondPlayerLifes.getValue();
    }

    public final void setSecondPlayerLifes(long value) {
        secondPlayerLifesProperty().setValue(value);
    }

    public final LongProperty secondPlayerLifesProperty() {
        if (secondPlayerLifes == null) {
            secondPlayerLifes = new SimpleLongProperty(this, "secondPlayerLifes", 2);
        }
        return secondPlayerLifes;
    }


}
