package ua.drozda.battlecity.fx;

import javafx.geometry.Rectangle2D;
import ua.drozda.battlecity.core.TileUnit;

/**
 * Created by GFH on 12.07.2015.
 */
public class FxSpriteForest extends FxSprite<TileUnit> {
    private static Rectangle2D forestTile = new Rectangle2D(FxWorld.tileZoneX, FxWorld.forestZoneY,
            FxWorld.tileSize, FxWorld.tileSize);

    public FxSpriteForest(TileUnit gameUnit) {
        super(gameUnit);
        setViewPort(forestTile);
    }

    @Override
    protected void updateSprite() {
        setViewPort(forestTile);
    }

}
