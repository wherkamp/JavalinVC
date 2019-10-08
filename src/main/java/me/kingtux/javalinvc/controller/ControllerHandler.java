package me.kingtux.javalinvc.controller;

import io.javalin.http.Context;
import me.kingtux.javalinvc.JavalinVC;
import me.kingtux.javalinvc.WebsiteRulesBuilder;
import org.apache.commons.lang3.exception.ExceptionUtils;


public class ControllerHandler {
    private SingleController sc;
    private JavalinVC website;


    public ControllerHandler(SingleController sc) {
        this.sc = sc;
    }

    public ControllerHandler(SingleController sc, JavalinVC website) {
        this.sc = sc;
        this.website = website;
    }

    public void execute(Context ctx) {
        if (website.getRules().baseURL().equalsIgnoreCase("{PFFC}")) {
            website.setRules(WebsiteRulesBuilder.create().setUrl(ctx.url().substring(0, ctx.url().length() - 1)).setName(website.getRules().name()).build());
            website.getViewManager().registerDefaultViewVariable("sr", website.getRules());
            JavalinVC.LOGGER.debug("Changing WebsiteRules " + website.getRules().toString());
        }
        ControllerExecutor se = sc.buildExecutor(ctx, website.getViewManager(), website);
        try {
            se.execute();
        } catch (ControllerExeception e) {
            ExceptionUtils.printRootCauseStackTrace(e.getCause());
            JavalinVC.LOGGER.error("Unable to invoke controller " + sc.getName() + "(" + sc.getPath() + ")", e.getCause());
        }
    }
}
