package me.kingtux.javalinvc;

import io.javalin.Javalin;
import me.kingtux.javalinvc.rg.ResourceGrabber;
import me.kingtux.javalinvc.view.ViewManager;
import me.kingtux.javalinvc.view.ViewManagerBuilder;

public class JavalinVCBuilder {
    private Javalin javalin;
    private WebsiteRules rules;
    private ViewManagerBuilder viewManager;
    private ResourceGrabber resourceGrabber;

    public JavalinVCBuilder setJavalin(Javalin javalin) {
        this.javalin = javalin;
        return this;
    }

    public JavalinVCBuilder setRules(WebsiteRules rules) {
        this.rules = rules;
        return this;
    }

    public JavalinVCBuilder setViewManager(ViewManagerBuilder viewManager) {
        this.viewManager = viewManager;
        return this;
    }

    public JavalinVCBuilder setResourceGrabber(ResourceGrabber resourceGrabber) {
        this.resourceGrabber = resourceGrabber;
        return this;
    }

    public JavalinVC createJavalinVC() {
        return new JavalinVC(javalin, rules, viewManager, resourceGrabber);
    }

    public static JavalinVCBuilder create() {
        return new JavalinVCBuilder();
    }
}