package me.kingtux.javalinvc.controller;


import io.javalin.http.Context;
import me.kingtux.javalinvc.JavalinVC;
import me.kingtux.javalinvc.annotations.Controller;
import me.kingtux.javalinvc.core.ArgumentBuilder;
import me.kingtux.javalinvc.core.RequestType;
import me.kingtux.javalinvc.view.View;
import me.kingtux.javalinvc.view.ViewManager;
import me.kingtux.javalinvc.view.ViewUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SingleController {
    private Method method;
    private Object cl;

    public SingleController(Object c, Method method) {
        this.method = method;
        cl = c;
    }

    public String getPath() {
        return method.getAnnotation(Controller.class).path();
    }

    public boolean sitemap() {
        return method.getAnnotation(Controller.class).sitemap();
    }

    public RequestType getRequestType() {
        return method.getAnnotation(Controller.class).requestType();
    }

    private String getTemplate() {
        return method.getAnnotation(Controller.class).template();
    }

    @SuppressWarnings("All")
    public ControllerExecutor buildExecutor(Context request, ViewManager vb, JavalinVC website) {
        return () -> {
            //request.setResponseHeader("Access-Control-Allow-Origin", website.getCORS());
            View view = vb.buildView(website, request);
            view.setTemplate(getTemplate());
            try {
                method.invoke(cl, ArgumentBuilder.getArguments(method.getParameters(), view, request));
            } catch (InvocationTargetException e) {
                throw new ControllerExeception(this, e.getCause());
            } catch (Exception e) {
                throw new ControllerExeception(this, e);
            }

            if (!request.res.isCommitted() || !view.getTemplate().equals(""))
                ViewUtils.respond(request, view, website);
        };
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SingleController)) return false;
        SingleController singleController = (SingleController) obj;
        return ((singleController.getPath().equals(getPath())) && singleController.getRequestType() == getRequestType());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    String getName() {
        return method.getName();
    }

}
