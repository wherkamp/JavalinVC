package me.kingtux.javalinvc.errorhandler;

import io.javalin.http.Context;
import me.kingtux.javalinvc.JavalinVC;
import me.kingtux.javalinvc.errorhandler.annotations.EHController;
import me.kingtux.javalinvc.view.View;
import me.kingtux.javalinvc.view.ViewUtils;

public class DefaultErrorHandlers {
    private JavalinVC javalinVC;

    public DefaultErrorHandlers(JavalinVC javalinVC) {
        this.javalinVC = javalinVC;
    }

    @EHController(status = 404)
    public void four0four(Context request, View view) {
        view.setTemplate("404.html");
        respond(view, request);
    }

    protected void respond(View s, Context request) {
        ViewUtils.respond(request, s, javalinVC, javalinVC.getInternalResourceGrabber());
    }


}
