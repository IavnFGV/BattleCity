package ua.drozda.battlecity.fx;

import ua.drozda.battlecity.core.BulletUnit;
import ua.drozda.battlecity.fx.sprites.FxSpriteBullet;

/**
 * Created by GFH on 18.07.2015.
 */
public class FxBulletUnit extends FxGameUnit {
    public FxBulletUnit(BulletUnit gameUnit) {
        super(gameUnit);
        this.addSprite(new FxSpriteBullet(gameUnit));
    }
}
