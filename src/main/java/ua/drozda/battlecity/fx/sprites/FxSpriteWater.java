package ua.drozda.battlecity.fx.sprites;

import javafx.geometry.Rectangle2D;
import ua.drozda.battlecity.core.TileUnit;
import ua.drozda.battlecity.fx.FxWorld;

/**
 * Created by GFH on 12.07.2015.
 */
public class FxSpriteWater extends FxSprite<TileUnit> {
    static private Long waterTimer = 500_000_000l;
    private static Rectangle2D[] waterTiles;

    static {
        waterTiles = new Rectangle2D[3];
        for (int i = 0; i < 3; i++) {
            waterTiles[i] = new Rectangle2D(FxWorld.tileZoneX + FxWorld.tileSize * i, FxWorld.waterZoneY, FxWorld.tileSize, FxWorld.tileSize);
        }
    }

    public FxSpriteWater(TileUnit gameUnit) {
        super(gameUnit);
        maxToggle = 3;
        setViewPort(waterTiles[this.curToggle]);
    }

    @Override
    public Boolean canToggle(Long now) {
        return now - toggleTime >= waterTimer;
    }

    @Override
    protected int getMaxToggle() {
        return maxToggle;
    }

    @Override
    protected void updateSprite() {
        setViewPort(waterTiles[this.curToggle]);
    }

    @Override
    public void doToggle(Long now) {
        curToggle = ++curToggle % maxToggle;
        updateSprite();
        toggleTime = now;
    }
}
