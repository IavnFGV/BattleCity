package ua.drozda.battlecity.fx;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GFH on 04.07.2015.
 */
public class FxBorder {
    public static Group group = new Group();
    private static List<ImageView> imageViews = new ArrayList<>(4);

    static {
        ImageView imageView = new ImageView(FxWorld.sprites);//top
        imageView.setViewport(new Rectangle2D(FxWorld.borderZoneX, FxWorld.borderZoneY, 32 * FxWorld.tileSize,
                FxWorld.tankSize));
        group.getChildren().add(imageView);
        imageView = new ImageView(FxWorld.sprites);//bottom
        imageView.setViewport(new Rectangle2D(FxWorld.borderZoneX, FxWorld.borderZoneY, 32 * FxWorld.tileSize,
                FxWorld.tankSize));
        imageView.setY(28 * FxWorld.tileSize);
        group.getChildren().add(imageView);
        imageView = new ImageView(FxWorld.sprites);//right
        imageView.setViewport(new Rectangle2D(FxWorld.tankSize * 31 + FxWorld.tileSize, 0, 2 * FxWorld
                .tankSize,
                14 * FxWorld.tankSize));
        imageView.setX(28 * FxWorld.tileSize);
        imageView.setY(2 * FxWorld.tileSize);
        group.getChildren().add(imageView);
        imageView = new ImageView(FxWorld.sprites);//left
        imageView.setViewport(new Rectangle2D(FxWorld.borderZoneX, FxWorld.borderZoneY, 32 * FxWorld.tileSize,
                FxWorld.tankSize));
        imageView.setX(-15 * FxWorld.tileSize);
        imageView.setY(14 * FxWorld.tileSize);
        imageView.setRotate(90);
        group.getChildren().add(imageView);


    }

}
