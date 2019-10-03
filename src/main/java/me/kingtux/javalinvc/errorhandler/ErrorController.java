package me.kingtux.javalinvc.errorhandler;

import io.javalin.http.Context;
import me.kingtux.javalinvc.JavalinVC;
import me.kingtux.javalinvc.controller.ControllerExeception;
import me.kingtux.javalinvc.controller.ControllerExecutor;
import me.kingtux.javalinvc.core.ArgumentBuilder;
import me.kingtux.javalinvc.errorhandler.annotations.EHController;
import me.kingtux.javalinvc.view.View;
import me.kingtux.javalinvc.view.ViewManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ErrorController {
    private Method method;
    private Object cl;

    public ErrorController(Object c, Method method) {
        this.method = method;
        cl = c;
    }

    public int status() {
        return method.getAnnotation(EHController.class).status();
    }

    public String template() {
        return method.getAnnotation(EHController.class).template();
    }

    @SuppressWarnings("All")
    public ControllerExecutor buildExecutor(Context request, ViewManager vb, JavalinVC website) {
        return () -> {
            //request.res.setHeader("Access-Control-Allow-Origin", website.getCORS());
            View view = vb.buildView(website, request);
            view.setTemplate(template());

            try {
                method.invoke(cl, ArgumentBuilder.getArguments(method.getParameters(), view, request));
            } catch (InvocationTargetException e) {
                throw new ControllerExeception(this, e.getCause());
            } catch (Exception e) {
                throw new ControllerExeception(this, e);
            }
            //if (!request.res.isCommitted() || !view.getTemplate().equals(""))
                //request.respond(view, vb);
        };
    }

    public String getName() {
        return method.getName();
    }
}
