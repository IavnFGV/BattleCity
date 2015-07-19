package ua.drozda.battlecity.fx;

import ua.drozda.battlecity.core.BulletUnit;
import ua.drozda.battlecity.core.GameUnit;
import ua.drozda.battlecity.fx.sprites.FxSpriteBullet;
import ua.drozda.battlecity.fx.sprites.FxSpriteSmallExplosion;

/**
 * Created by GFH on 18.07.2015.
 */
public class FxBulletUnit extends FxGameUnit {
    public FxBulletUnit(BulletUnit gameUnit) {
        super(gameUnit);

        FxSpriteBullet baseSprite = new FxSpriteBullet(gameUnit);
        addSprite(baseSprite);
        FxSpriteSmallExplosion explosionSprite = new FxSpriteSmallExplosion(gameUnit);
        addSprite(explosionSprite);
        baseSprite.setVisible(true);
        explosionSprite.setVisible(false);

        gameUnit.basicStateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == GameUnit.BasicState.EXPLODING) {
                baseSprite.setVisible(false);
                explosionSprite.initToggle();
                explosionSprite.setVisible(true);
            }
            if (newValue == GameUnit.BasicState.DEAD) {
                explosionSprite.setVisible(false);
            }
        });


    }
}
