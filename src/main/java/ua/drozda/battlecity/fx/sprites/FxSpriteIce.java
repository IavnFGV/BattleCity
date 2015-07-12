package ua.drozda.battlecity.fx.sprites;

import javafx.geometry.Rectangle2D;
import ua.drozda.battlecity.core.TileUnit;
import ua.drozda.battlecity.fx.FxWorld;

/**
 * Created by GFH on 12.07.2015.
 */
public class FxSpriteIce extends FxSprite<TileUnit> {
    private static Rectangle2D iceTile = new Rectangle2D(FxWorld.tileZoneX, FxWorld.iceZoneY,
            FxWorld.tileSize, FxWorld.tileSize);

    public FxSpriteIce(TileUnit gameUnit) {
        super(gameUnit);
        setViewPort(iceTile);
    }

    @Override
    protected void updateSprite() {
        setViewPort(iceTile);
    }

}
