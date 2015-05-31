package ua.drozda.battlecity.fx;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import ua.drozda.battlecity.core.TileType;
import ua.drozda.battlecity.core.World;
import ua.drozda.battlecity.core.world.cells.GameCell;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by GFH on 18.05.2015.
 */
public class FxCell implements Observer {

    private static HashMap<TileType, Rectangle2D[]> spritesMap = new HashMap();

    static {
        Rectangle2D[] rectangle2Ds = new Rectangle2D[5];
        for (int i = 0; i < 5; i++) {
            rectangle2Ds[i] = new Rectangle2D(FxWorld.tileZoneX + FxWorld.tileSize * i, FxWorld.brickZoneY, 8, 8);
        }
        spritesMap.put(TileType.BRICK, rectangle2Ds);

        rectangle2Ds = new Rectangle2D[3];
        for (int i = 0; i < 3; i++) {
            rectangle2Ds[i] = new Rectangle2D(FxWorld.tileZoneX + FxWorld.tileSize * i, FxWorld.waterZoneY, 8, 8);
        }
        spritesMap.put(TileType.WATER, rectangle2Ds);

        rectangle2Ds = new Rectangle2D[1];
        for (int i = 0; i < 1; i++) {
            rectangle2Ds[i] = new Rectangle2D(FxWorld.tileZoneX + FxWorld.tileSize * i, FxWorld.emptyZoneY, 8, 8);
        }
        spritesMap.put(TileType.EMPTY, rectangle2Ds);

        rectangle2Ds = new Rectangle2D[1];
        for (int i = 0; i < 1; i++) {
            rectangle2Ds[i] = new Rectangle2D(FxWorld.tileZoneX + FxWorld.tileSize * i, FxWorld.steelZoneY, 8, 8);
        }
        spritesMap.put(TileType.STEEL, rectangle2Ds);

        rectangle2Ds = new Rectangle2D[1];
        for (int i = 0; i < 1; i++) {
            rectangle2Ds[i] = new Rectangle2D(FxWorld.tileZoneX, FxWorld.forestZoneY + FxWorld.tileSize * i, 8, 8);
        }
        spritesMap.put(TileType.FOREST, rectangle2Ds);
    }

    private ImageView imageView = new ImageView(FxWorld.sprites);
    private GameCell gameCell;
    private Rectangle2D curSprite;

    public FxCell(Integer x, Integer y, World world, GameCell gameCell) {
        this.gameCell = gameCell;
        curSprite = spritesMap.get(gameCell.getTile())[gameCell.getState().getState()];
        gameCell.addObserver(this);
        imageView.setViewport(curSprite);
        imageView.setX(x * FxWorld.tileSize);
        imageView.setY(y * FxWorld.tileSize);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public GameCell getGameCell() {
        return gameCell;
    }

    public void setGameCell(GameCell gameCell) {
        this.gameCell = gameCell;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof GameCell) {
            GameCell cell = (GameCell) o;
            imageView.setViewport(spritesMap.get(cell.getTile())[cell.getState().getState()]);
        }
    }
}
