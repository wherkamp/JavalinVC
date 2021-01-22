package me.kingtux.javalinvc.view;

import me.kingtux.javalinvc.JavalinVC;

import java.lang.reflect.InvocationTargetException;

public class ViewManagerBuilder {
    private JavalinVC website;
    private String extension;
    private String viewManager;

    public ViewManagerBuilder setViewManager(String viewManager) {
        this.viewManager = viewManager;
        return this;
    }

    public ViewManagerBuilder setWebsite(JavalinVC website) {
        this.website = website;
        return this;
    }

    public ViewManagerBuilder setExtension(String extension) {
        this.extension = extension;
        return this;
    }

    public ViewManager createViewManager() {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(viewManager);
        } catch (ClassNotFoundException e) {
            JavalinVC.LOGGER.error(String.format("Unable to locate %s", viewManager), e);
            return null;
        }

        try {
            return (ViewManager) clazz.getConstructors()[0].newInstance(website, extension);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            JavalinVC.LOGGER.error(String.format("Unable to create %s", clazz.getName()), e);
        }
        return null;
    }
    public static ViewManagerBuilder create(){
        return new ViewManagerBuilder();
    }
}