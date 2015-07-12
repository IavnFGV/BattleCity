package ua.drozda.battlecity.fx;

import ua.drozda.battlecity.core.TileUnit;
import ua.drozda.battlecity.fx.sprites.*;

/**
 * Created by GFH on 15.06.2015.
 */
public class FxTileUnit extends FxGameUnit {

    public FxTileUnit(TileUnit tileUnit) {
        super(tileUnit);
        switch (tileUnit.getTileType()) {
            case BRICK:
                this.addSprite(new FxSpriteBrick(tileUnit));
                break;
            case EMPTY: {
            }
            break;
            case FOREST:
                this.addSprite(new FxSpriteForest(tileUnit));
                break;
            case ICE:
                this.addSprite(new FxSpriteIce(tileUnit));
                break;
            case STEEL:
                this.addSprite(new FxSpriteSteel(tileUnit));
                break;
            case WATER:
                this.addSprite(new FxSpriteWater(tileUnit));
                break;
        }
        //updateSprite();
    }


}
