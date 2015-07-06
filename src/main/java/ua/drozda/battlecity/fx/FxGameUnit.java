package ua.drozda.battlecity.fx;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import ua.drozda.battlecity.core.GameUnit;
import ua.drozda.battlecity.core.TankUnit;
import ua.drozda.battlecity.core.TileUnit;
import ua.drozda.battlecity.core.interfaces.Togglable;

import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by GFH on 14.06.2015.
 */
public abstract class FxGameUnit implements Observer, Togglable {
    protected GameUnit gameUnit;
    protected Integer maxToggle;
    protected Integer curToggle = 0;
    protected Long toggleTime = 0l;
    protected ImageView imageView = new ImageView(FxWorld.sprites);
    private Rectangle2D curSprite;

    public FxGameUnit(GameUnit gameUnit) {
        this.gameUnit = gameUnit;
        gameUnit.addObserver(this);
    }

    public static FxGameUnit createFxGameUnit(GameUnit gameUnit) {
        if (gameUnit instanceof TileUnit) {
            return new FxTileUnit((TileUnit) gameUnit);
        }
        if (gameUnit instanceof TankUnit) {
            return new FxTankUnit((TankUnit) gameUnit);
        }
        throw new Error("Unknown gameUnit subClass = " + gameUnit.getClass().getName());

    }

    public List<ImageView> getImageViews() {
        return Arrays.asList(imageView);
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public void update(Observable o, Object arg) {
        updateSprite();
    }


    protected void updateSprite() {
        nextSprite();
        imageView.relocate(gameUnit.getX(), gameUnit.getY());
        handleSounds();
    }

    protected abstract void nextSprite();

    protected abstract void handleSounds();

    @Override
    public abstract void doToggle(Long now);

    public Rectangle2D getCurSprite() {
        return curSprite;
    }

    public void setCurSprite(Rectangle2D curSprite) {
        this.curSprite = curSprite;
        imageView.setViewport(curSprite);
    }

    public GameUnit getGameUnit() {
        return gameUnit;
    }

    public void setGameUnit(GameUnit gameUnit) {
        this.gameUnit = gameUnit;
    }

    public Integer getMaxToggle() {
        return maxToggle;
    }

    public void setMaxToggle(Integer maxToggle) {
        this.maxToggle = maxToggle;
    }

    public Integer getCurToggle() {
        return curToggle;
    }

    public void setCurToggle(Integer curToggle) {
        this.curToggle = curToggle;
    }
}

