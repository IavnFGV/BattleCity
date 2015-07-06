package ua.drozda.battlecity.fx;

import javafx.beans.property.DoubleProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

import static ua.drozda.battlecity.core.StaticServices.MESSAGES;

/**
 * Created by GFH on 06.07.2015.
 */
public class FxSprite {
    private ImageView imageView = new ImageView(FxWorld.sprites);

    public FxSprite(Rectangle2D viewPort) {
        if (viewPort == null) {
            throw new IllegalArgumentException(MESSAGES.getString("fxsprite.constructor.nullrectangle2d"));
        }
        this.setViewPort(viewPort);
    }

    public final Rectangle2D getViewPort() {
        return imageView.getViewport();
    }

    public final void setViewPort(Rectangle2D value) {
        imageView.setViewport(value);
    }

    public final DoubleProperty xProperty() {
        return imageView.xProperty();
    }

    public final DoubleProperty yProperty() {
        return imageView.yProperty();
    }

}
