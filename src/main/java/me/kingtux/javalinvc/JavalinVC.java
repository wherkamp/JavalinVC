package me.kingtux.javalinvc;

import io.javalin.Javalin;
import io.javalin.core.security.Role;
import io.javalin.http.Handler;
import io.javalin.http.HandlerType;
import me.kingtux.javalinvc.annotations.Controller;
import me.kingtux.javalinvc.annotations.SitemapHandler;
import me.kingtux.javalinvc.controller.ControllerHandler;
import me.kingtux.javalinvc.controller.SingleController;
import me.kingtux.javalinvc.controller.SingleSitemapHandler;
import me.kingtux.javalinvc.core.RequestType;
import me.kingtux.javalinvc.errorhandler.ErrorController;
import me.kingtux.javalinvc.errorhandler.ErrorControllerHandler;
import me.kingtux.javalinvc.errorhandler.annotations.EHController;
import me.kingtux.javalinvc.javalin.CustomResourceHandler;
import me.kingtux.javalinvc.rg.ResourceGrabber;
import me.kingtux.javalinvc.sitemap.SiteMapAuto;
import me.kingtux.javalinvc.view.ViewManager;
import me.kingtux.javalinvc.view.ViewManagerBuilder;
import me.kingtux.simpleannotation.MethodFinder;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JavalinVC {
    private Javalin javalin;
    private WebsiteRules rules;
    public static final Logger LOGGER = LoggerFactory.getLogger(JavalinVC.class);
    private List<SingleSitemapHandler> singleSitemapHandlers = new ArrayList<>();
    private final JavalinVConfig config = new JavalinVConfig();
    private List<SingleController> controllers = new ArrayList<>();
    private ViewManager viewManager;
    private boolean running;
    private ResourceGrabber internalResourceGrabber;
    private ResourceGrabber resourceGrabber;

    public JavalinVC(Javalin javalin, WebsiteRules rules, ViewManagerBuilder viewManager, ResourceGrabber resourceGrabber) {
        this.javalin = javalin;
        this.rules = rules;
        this.resourceGrabber = resourceGrabber;
        this.viewManager = viewManager.setWebsite(this).createViewManager();

        this.javalin.config.inner.resourceHandler = new CustomResourceHandler(this);
        this.javalin.start();
        running = true;
        new SiteMapAuto(this).start();
    }


    /**
     * Registers a Object. That can contain: Controllers, Error Handlers, and or SiteMap Handlers
     *
     * @param controller the object to register
     * @return this
     */
    public JavalinVC registerController(Object controller) {
        for (Method method : MethodFinder.getAllMethodsWithAnnotation(controller.getClass(), Controller.class)) {
            SingleController sc = new SingleController(controller, method);
            if (LOGGER.isDebugEnabled())
                LOGGER.debug(String.format("%s -> %s#%s", sc.getPath(), controller.getClass().getSimpleName(), method.getName()));
            controllers.add(sc);
            javalin.addHandler(getHandlerType(sc.getRequestType()), sc.getPath(), new ControllerHandler(sc, this)::execute);
        }
        for (Method method : MethodFinder.getAllMethodsWithAnnotation(controller.getClass(), SitemapHandler.class)) {
            SingleSitemapHandler sc = new SingleSitemapHandler(controller, method);
            singleSitemapHandlers.add(sc);
        }
        for (Method method : MethodFinder.getAllMethodsWithAnnotation(controller.getClass(), EHController.class)) {
            ErrorController errorController = new ErrorController(controller, method);
            if (LOGGER.isDebugEnabled())
                LOGGER.debug(String.format("%d -> %s#%s", errorController.status(), errorController.getClass().getSimpleName(), method.getName()));
            javalin.error(errorController.status(), new ErrorControllerHandler(errorController, this)::execute);
        }
        return this;
    }

    private HandlerType getHandlerType(RequestType requestType) {
        if (requestType == RequestType.GET) {
            return HandlerType.GET;
        } else if (requestType == RequestType.POST) {
            return HandlerType.POST;
        }
        return HandlerType.GET;
    }

    public Javalin getJavalin() {
        return javalin;
    }

    public WebsiteRules getRules() {
        return rules;
    }


    public Javalin stop() {
        running = false;
        return javalin.stop();
    }

    public Javalin error(int statusCode, @NotNull Handler handler) {
        return javalin.error(statusCode, handler);
    }

    public Javalin error(int statusCode, @NotNull String contentType, @NotNull Handler handler) {
        return javalin.error(statusCode, contentType, handler);
    }

    public Javalin get(@NotNull String path, @NotNull Handler handler) {
        return javalin.get(path, handler);
    }

    public Javalin post(@NotNull String path, @NotNull Handler handler) {
        return javalin.post(path, handler);
    }

    public Javalin get(@NotNull String path, @NotNull Handler handler, @NotNull Set<Role> permittedRoles) {
        return javalin.get(path, handler, permittedRoles);
    }

    public Javalin post(@NotNull String path, @NotNull Handler handler, @NotNull Set<Role> permittedRoles) {
        return javalin.post(path, handler, permittedRoles);
    }

    public ViewManager getViewManager() {
        return viewManager;
    }

    public JavalinVConfig getConfig() {
        return config;
    }

    public List<SingleSitemapHandler> getSingleSitemapHandlers() {
        return singleSitemapHandlers;
    }

    public List<SingleController> getControllers() {
        return controllers;
    }

    public boolean isRunning() {
        return running;
    }

    public ResourceGrabber getInternalResourceGrabber() {
        return internalResourceGrabber;
    }

    public String route(String path) {
        return getRules().baseURL() + "/" + path;
    }

    public ResourceGrabber getResourceGrabber() {
        return resourceGrabber;
    }
}
