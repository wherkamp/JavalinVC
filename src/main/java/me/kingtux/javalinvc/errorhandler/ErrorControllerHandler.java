package me.kingtux.javalinvc.errorhandler;

import io.javalin.http.Context;
import me.kingtux.javalinvc.JavalinVC;
import me.kingtux.javalinvc.controller.ControllerExeception;
import me.kingtux.javalinvc.controller.ControllerExecutor;


public class ErrorControllerHandler {
    private ErrorController sc;
    private JavalinVC website;


    public ErrorControllerHandler(ErrorController sc) {
        this.sc = sc;
    }

    public ErrorControllerHandler(ErrorController sc, JavalinVC website) {
        this.sc = sc;
        this.website = website;
    }

    public void execute(Context ctx) {
        try {
            ControllerExecutor se = sc.buildExecutor(ctx, website.getViewManager(), website);
            se.execute();
        } catch (ControllerExeception exeception) {

            // JavalinVC.LOGGER.error("Unable to execute Controller\n  ",
            //         controllerExeception.getStackTrace()[controllerExeception.getStackTrace().length - 1].);
        }
    }
}
