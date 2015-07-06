package ua.drozda;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by GFH on 06.07.2015.
 */
public class PropertyListenersTest {
    private DoubleProperty x = new SimpleDoubleProperty(0);
    private DoubleProperty y = new SimpleDoubleProperty(0);

    @Before
    public void prepare() {
        x.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.doubleValue() > 10) {
                    TestConstants.showMessage("From listener" + observable.getValue());
                    //((DoubleProperty)observable).set(67);
                    x.setValue(67);
                }
            }
        });
    }

    @Test
    public void testCheckInListener() {
        TestConstants.showMessage("Now  x = " + x.doubleValue());

        x.set(12);
        TestConstants.showMessage("Now  x = " + x.doubleValue());

    }
}
