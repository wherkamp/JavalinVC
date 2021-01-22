package me.kingtux.javalinvc;

import java.lang.reflect.Field;

public class Utils {

    public static Object getFieldValue(Object o, String fieldName) {
        try {
            Field field = o.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(o);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            JavalinVC.LOGGER.error("Unable to get Field Value", e);
        }
        return null;
    }

    public static void setFieldValue(Object instanse, Object value, String fieldName) {
        try {
            Field field = instanse.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instanse, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            JavalinVC.LOGGER.error("Unable to set Field Value", e);
        }

    }
}
