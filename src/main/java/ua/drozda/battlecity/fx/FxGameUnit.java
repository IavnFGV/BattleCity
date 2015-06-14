package ua.drozda.battlecity.fx;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import ua.drozda.battlecity.core.GameUnit;
import ua.drozda.battlecity.core.TileUnit;
import ua.drozda.battlecity.core.interfaces.Togglable;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by GFH on 14.06.2015.
 */
public abstract class FxGameUnit implements Observer, Togglable {
    protected GameUnit gameUnit;
    protected Integer maxToggle;
    protected Integer curToggle = 0;
    private ImageView imageView = new ImageView(FxWorld.sprites);
    private Rectangle2D curSprite;
    public FxGameUnit(GameUnit gameUnit) {
        this.gameUnit = gameUnit;
//        if (gameUnit instanceof TileUnit) {
//
//        }
//        if (gameUnit instanceof TankUnit) {
//            maxToggle = 2;
//        }
//        if (gameUnit instanceof BulletUnit) {
//            maxToggle = 1;
//        }
        gameUnit.addObserver(this);
//        updateSprite();
    }

    public static FxGameUnit createFxGameUnit(GameUnit gameUnit) {
        if (gameUnit instanceof TileUnit) {
            return new FxTileUnit((TileUnit) gameUnit);
        }
        return null;
    }

    public ImageView getImageView() {
        return imageView;
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
        //   setCurSprite(FxSpriteManager.getNextSprite(this));
        imageView.setX(gameUnit.getBounds().getMinX());
        imageView.setY(gameUnit.getBounds().getMinY());
    }

    protected abstract void nextSprite();

    @Override
    public Object toggle(Object o) {
        curToggle = ++curToggle % maxToggle;
        updateSprite();
        return null;
    }

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

