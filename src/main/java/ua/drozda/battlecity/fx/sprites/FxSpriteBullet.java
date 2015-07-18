package ua.drozda.battlecity.fx.sprites;

import javafx.geometry.Rectangle2D;
import ua.drozda.battlecity.core.ActiveUnit;
import ua.drozda.battlecity.core.BulletUnit;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GFH on 18.07.2015.
 */
public class FxSpriteBullet extends FxSprite<BulletUnit> {
    private static Map<ActiveUnit.Direction, Rectangle2D> bulletTiles = new HashMap<>();

    {
        bulletTiles.put(ActiveUnit.Direction.UP, new Rectangle2D(292, 288, 8, 8));
        bulletTiles.put(ActiveUnit.Direction.RIGHT, new Rectangle2D(300, 288, 8, 8));
        bulletTiles.put(ActiveUnit.Direction.DOWN, new Rectangle2D(308, 288, 8, 8));
        bulletTiles.put(ActiveUnit.Direction.LEFT, new Rectangle2D(316, 288, 8, 8));

    }


    public FxSpriteBullet(BulletUnit gameUnit) {
        super(gameUnit);
        setViewPort(bulletTiles.get(gameUnit.getDirection()));
    }

    @Override
    protected int getMaxToggle() {
        return 1;
    }

    @Override
    protected void updateSprite() {
        setViewPort(bulletTiles.get(getGameUnit().getDirection()));
    }
}
