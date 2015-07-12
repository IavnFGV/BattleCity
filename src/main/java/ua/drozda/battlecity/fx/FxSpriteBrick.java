package ua.drozda.battlecity.fx;

import javafx.geometry.Rectangle2D;
import ua.drozda.battlecity.core.TileUnit;

/**
 * Created by GFH on 12.07.2015.
 */
public class FxSpriteBrick extends FxSprite<TileUnit> {
    private static Rectangle2D[] brickTiles;

    static {
        brickTiles = new Rectangle2D[15];
        for (int i = 0; i < 15; i++) {
            brickTiles[i] = new Rectangle2D(FxWorld.tileZoneX + FxWorld.tileSize * i, FxWorld.brickZoneY, FxWorld.tileSize,
                    FxWorld.tileSize);
        }
    }

    public FxSpriteBrick(TileUnit gameUnit) {
        super(gameUnit);
        setViewPort(brickTiles[0]);
    }

    @Override
    protected void updateSprite() {
        setViewPort(brickTiles[0]); //TODO MAKE RIGHT !)))
    }

}
