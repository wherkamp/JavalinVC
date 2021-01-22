package me.kingtux.javalinvc.controller;

import me.kingtux.javalinvc.JavalinVC;
import me.kingtux.tuxjsitemap.SiteURL;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SingleSitemapHandler {
    private Method method;
    private Object cl;

    public SingleSitemapHandler(Object c, Method method) {
        this.method = method;
        cl = c;
    }


    @SuppressWarnings("All")
    public List<SiteURL> execute(JavalinVC website) {
        List<SiteURL> list = new ArrayList<>();
        try {
            list = (List<SiteURL>) method.invoke(cl);
        } catch (IllegalAccessException | InvocationTargetException e) {
            JavalinVC.LOGGER.error("Unable to invoke SiteMapHandler", e);
        }
        return list;
    }


}
