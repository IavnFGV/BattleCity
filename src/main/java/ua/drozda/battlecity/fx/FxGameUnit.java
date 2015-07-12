package ua.drozda.battlecity.fx;

import ua.drozda.battlecity.core.GameUnit;
import ua.drozda.battlecity.core.TankUnit;
import ua.drozda.battlecity.core.TileUnit;
import ua.drozda.battlecity.core.interfaces.Togglable;
import ua.drozda.battlecity.fx.sprites.FxSprite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GFH on 14.06.2015.
 */
public abstract class FxGameUnit implements Togglable {
    protected GameUnit gameUnit;
    private List<FxSprite<? extends GameUnit>> sprites = new ArrayList<>();

    public FxGameUnit(GameUnit gameUnit) {
        this.gameUnit = gameUnit;

    }

    public static FxGameUnit createFxGameUnit(GameUnit gameUnit) {
        if (gameUnit instanceof TileUnit) {
            return new FxTileUnit((TileUnit) gameUnit);
        }
        if (gameUnit instanceof TankUnit) {
            return new FxTankUnit((TankUnit) gameUnit);
        }
        throw new Error("Unknown gameUnit subClass = " + gameUnit.getClass().getName());

    }

    public GameUnit getGameUnit() {
        return gameUnit;
    }

    public void setGameUnit(GameUnit gameUnit) {
        this.gameUnit = gameUnit;
    }

    public void addSprite(FxSprite<? extends GameUnit> sprite) {
        sprites.add(sprite);
    }

    public List<FxSprite<? extends GameUnit>> getSprites() {
        return sprites;
    }

    public void setSprites(List<FxSprite<? extends GameUnit>> sprites) {
        this.sprites = sprites;
    }

    @Override
    public void doToggle(Long now) {
        sprites.forEach(s -> s.toggle(now));
    }
}

