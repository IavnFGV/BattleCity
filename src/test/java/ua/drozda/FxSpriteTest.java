package ua.drozda;

import org.junit.Test;
import ua.drozda.battlecity.fx.FxSpriteWater;

import static ua.drozda.TestConstants.*;

/**
 * Created by GFH on 06.07.2015.
 */
public class FxSpriteTest {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor() {
        testMethodStarted("FxSprite testConstructor");
        try {
            new FxSpriteWater(null);
        } catch (Throwable t) {
            showMessage(t.getMessage());
            testComplete();
            throw t;
        }
    }
}
