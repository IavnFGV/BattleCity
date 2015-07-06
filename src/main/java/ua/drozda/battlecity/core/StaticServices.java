package ua.drozda.battlecity.core;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by GFH on 06.07.2015.
 */
public class StaticServices {
    public static Locale DEFAULT_LOCALE = Locale.getDefault();
    public static ResourceBundle MESSAGES = ResourceBundle.getBundle("messages/messages", DEFAULT_LOCALE);


}
