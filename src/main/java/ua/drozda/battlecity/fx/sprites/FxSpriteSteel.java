package ua.drozda.battlecity.fx.sprites;

import javafx.geometry.Rectangle2D;
import ua.drozda.battlecity.core.TileUnit;
import ua.drozda.battlecity.fx.FxWorld;

/**
 * Created by GFH on 12.07.2015.
 */
public class FxSpriteSteel extends FxSprite<TileUnit> {
    private static Rectangle2D steelTile = new Rectangle2D(FxWorld.tileZoneX, FxWorld.steelZoneY,
            FxWorld.tileSize, FxWorld.tileSize);

    public FxSpriteSteel(TileUnit gameUnit) {
        super(gameUnit);
        setViewPort(steelTile);
    }

    @Override
    protected int getMaxToggle() {
        return 1;
    }

    @Override
    protected void updateSprite() {
        setViewPort(steelTile);
    }

}
