package ua.drozda.battlecity.fx;

import ua.drozda.battlecity.core.GameUnit;
import ua.drozda.battlecity.core.TankUnit;
import ua.drozda.battlecity.fx.sprites.FxSpriteCreation;
import ua.drozda.battlecity.fx.sprites.FxSpriteShield;
import ua.drozda.battlecity.fx.sprites.FxSpriteTank;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created by GFH on 21.06.2015.
 */
public class FxTankUnit extends FxGameUnit {

    ThreadFactory threadFactory =
            new ThreadFactory() {
                public Thread newThread(Runnable r) {
                    Thread t = Executors.defaultThreadFactory().newThread(r);
                    t.setDaemon(true);
                    return t;
                }
            };

    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor(threadFactory);

    public FxTankUnit(TankUnit gameUnit) {
        super(gameUnit);
        FxSpriteTank baseSprite = new FxSpriteTank(gameUnit);
        addSprite(baseSprite);
        if (TankUnit.playerTanks.contains(gameUnit.getTankType())) {
            FxSpriteShield fxSpriteShield = new FxSpriteShield(gameUnit);
            addSprite(fxSpriteShield);
            fxSpriteShield.visibleProperty().bind(gameUnit.shildProperty());
        }
        FxSpriteCreation creationSprite = new FxSpriteCreation(gameUnit);
        addSprite(creationSprite);
        creationSprite.setVisible(true);
        baseSprite.setVisible(false);
        gameUnit.basicStateProperty().addListener((observable, oldValue, newValue) -> {
            if ((oldValue == GameUnit.BasicState.CREATING) &&
                    (newValue == GameUnit.BasicState.ACTIVE)) {
                creationSprite.setVisible(false);
                baseSprite.setVisible(true);
            }
        });

        service.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                if (getTank().isEngineOn()) {
                    SoundManager.playSound("moving");
                }
            }
        }, 0, 154, TimeUnit.MILLISECONDS);
    }

    public TankUnit getTank() {
        return (TankUnit) getGameUnit();
    }
}
