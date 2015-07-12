package ua.drozda.battlecity.fx.sprites;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import ua.drozda.battlecity.core.GameUnit;
import ua.drozda.battlecity.core.interfaces.Togglable;
import ua.drozda.battlecity.fx.FxWorld;

import java.util.Observable;
import java.util.Observer;

import static ua.drozda.battlecity.core.StaticServices.MESSAGES;

/**
 * Created by GFH on 06.07.2015.
 */
public abstract class FxSprite<T extends GameUnit> implements Togglable, Observer {
    protected Integer maxToggle = 1;
    protected Integer curToggle = 0;
    protected Long toggleTime = 0l;
    private ImageView imageView = new ImageView(FxWorld.sprites);
    private T gameUnit;

    public FxSprite(T gameUnit) {
        if (gameUnit == null) {
            throw new IllegalArgumentException(MESSAGES.getString("fxsprite.constructor.nullgameunit"));
        }
        xProperty().bind(gameUnit.xProperty());
        yProperty().bind(gameUnit.yProperty());
        gameUnit.addObserver(this);
        this.gameUnit = gameUnit;
        //    this.setViewPort(viewPort);
    }

    public final DoubleProperty xProperty() {
        return imageView.xProperty();
    }

    public final DoubleProperty yProperty() {
        return imageView.yProperty();
    }

    public final BooleanProperty visibleProperty() {
        return imageView.visibleProperty();
    }

    public T getGameUnit() {
        return gameUnit;
    }

    public void setGameUnit(T gameUnit) {
        this.gameUnit = gameUnit;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public final Rectangle2D getViewPort() {
        return imageView.getViewport();
    }

    public final void setViewPort(Rectangle2D value) {
        imageView.setViewport(value);
    }


    @Override
    public void update(Observable o, Object arg) {
        updateSprite();
    }

    protected abstract void updateSprite();

    @Override
    public void doToggle(Long now) {
        updateSprite();
    }
}
