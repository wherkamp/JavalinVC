package me.kingtux.javalinvc.rg;


import me.kingtux.javalinvc.JavalinVC;
import me.kingtux.javalinvc.rg.templategrabbers.ExternalResourceGrabber;
import me.kingtux.javalinvc.rg.templategrabbers.IEResourceGrabber;
import me.kingtux.javalinvc.rg.templategrabbers.InternalResourceGrabber;

import java.lang.reflect.InvocationTargetException;

public enum ResourceGrabbers {
    INTERNAL_EXTERNAL_GRABBER(IEResourceGrabber.class),
    INTERNAL_GRABBER(InternalResourceGrabber.class),
    EXTERNAL_GRABBER(ExternalResourceGrabber.class);


    private Class<?> clazz;

    ResourceGrabbers(Class<?> clazz) {
        this.clazz = clazz;
    }

    public ResourceGrabber build(String path) {
        try {
            return (ResourceGrabber) clazz.getConstructor(String.class).newInstance(path);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            JavalinVC.LOGGER.error("Unable to create ResourceGrabber", e);
        }
        return null;
    }
}
