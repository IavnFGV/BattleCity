package ua.drozda.battlecity.fx;

import ua.drozda.battlecity.core.BulletUnit;
import ua.drozda.battlecity.fx.sprites.FxSpriteBullet;

/**
 * Created by GFH on 18.07.2015.
 */
public class FxBulletUnit extends FxGameUnit {
    public FxBulletUnit(BulletUnit gameUnit) {
        super(gameUnit);

        FxSpriteBullet baseSprite = new FxSpriteBullet(gameUnit);
        addSprite(baseSprite);

        FxSpriteBigExplosion explosionSprite = new FxSpriteBigExplosion(gameUnit);
        addSprite(explosionSprite);
        baseSprite.setVisible(false);
        explosionSprite.setVisible(false);

        gameUnit.basicStateProperty().addListener((observable, oldValue, newValue) -> {
            if ((oldValue == GameUnit.BasicState.CREATING) &&
                    (newValue == GameUnit.BasicState.ACTIVE)) {
                creationSprite.setVisible(false);
                baseSprite.setVisible(true);
            }
            if (newValue == GameUnit.BasicState.EXPLODING) {
                baseSprite.setVisible(false);
                explosionSprite.setVisible(true);
            }
            if (newValue == GameUnit.BasicState.DEAD) {
                explosionSprite.setVisible(false);
            }
        });


    }
}
