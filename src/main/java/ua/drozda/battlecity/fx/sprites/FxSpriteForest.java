package ua.drozda.battlecity.fx.sprites;

import javafx.geometry.Rectangle2D;
import ua.drozda.battlecity.core.TileUnit;
import ua.drozda.battlecity.fx.FxWorld;

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
    protected int getMaxToggle() {
        return 1;
    }

    @Override
    protected void updateSprite() {
        setViewPort(forestTile);
    }

}
