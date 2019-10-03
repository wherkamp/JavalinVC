package me.kingtux.javalinvc.controller;

import io.javalin.http.Context;
import me.kingtux.javalinvc.JavalinVC;


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
        //if(website.getSiteRules().baseURL().equalsIgnoreCase("{PFFC}")){
        //        ((SimpleWebsite)website).setWebsiteRules(new WebsiteRules(ctx.url().substring(0, ctx.url().length()-1), website.getSiteRules().name()));
        //        website.getViewManager().registerDefaultViewVariable("sr", website.getSiteRules());
        //        TuxMVC.TUXMVC_LOGGER.debug("Changing WebsiteRules "+website.getSiteRules().toString() );
        //    }
        try {
            ControllerExecutor se = sc.buildExecutor(ctx, website.getViewManager(), website);
            se.execute();
        } catch (ControllerExeception controllerExeception) {
            controllerExeception.printStackTrace();
        }
    }
}
