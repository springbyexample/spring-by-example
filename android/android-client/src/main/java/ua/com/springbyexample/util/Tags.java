package ua.com.springbyexample.util;

/**
 * Logging helper class
 * Created by akaverin on 5/27/13.
 */
public class Tags {
    private static final String TAG_PREFIX = "uaspring";

    static public String getTag(Class<?> clazz) {
        return TAG_PREFIX + "." + clazz.getSimpleName();
    }
}
