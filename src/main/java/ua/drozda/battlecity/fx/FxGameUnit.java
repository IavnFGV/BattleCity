package ua.drozda.battlecity.fx;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import ua.drozda.battlecity.core.BulletUnit;
import ua.drozda.battlecity.core.GameUnit;
import ua.drozda.battlecity.core.TankUnit;
import ua.drozda.battlecity.core.TileUnit;
import ua.drozda.battlecity.core.interfaces.Togglable;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by GFH on 14.06.2015.
 */
public class FxGameUnit implements Observer, Togglable {
//    public static FxGameUnit createFxGameUnit(GameUnit gameUnit) {
//
//    }

    protected static HashMap<TileUnit.TileType, Integer> toggleTileCountMap = new HashMap();
    //protected static HashMap<TankUnit.TankType, Integer> toggleTankCountMap = new HashMap();

    static {
        toggleTileCountMap.put(TileUnit.TileType.BRICK, 1);
        toggleTileCountMap.put(TileUnit.TileType.WATER, 3);
        toggleTileCountMap.put(TileUnit.TileType.EMPTY, 1);
        toggleTileCountMap.put(TileUnit.TileType.FOREST, 1);
        toggleTileCountMap.put(TileUnit.TileType.ICE, 1);
        toggleTileCountMap.put(TileUnit.TileType.STEEL, 1);

        //toggleTankCountMap.
    }

    protected GameUnit gameUnit;
    protected Integer maxToggle;
    protected Integer curToggle = 0;
    private ImageView imageView = new ImageView(FxWorld.sprites);
    private Rectangle2D curSprite;

    public FxGameUnit(GameUnit gameUnit) {
        this.gameUnit = gameUnit;
        if (gameUnit instanceof TileUnit) {
            maxToggle = toggleTileCountMap.get(((TileUnit) gameUnit).getTileType());
        }
        if (gameUnit instanceof TankUnit) {
            maxToggle = 2;
        }
        if (gameUnit instanceof BulletUnit) {
            maxToggle = 1;
        }
        gameUnit.addObserver(this);
        updateSprite();
    }

    protected void updateSprite() {
        setCurSprite(FxSpriteManager.getNextSprite(this));
        imageView.setX(gameUnit.getBounds().getMinX());
        imageView.setY(gameUnit.getBounds().getMinY());
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

