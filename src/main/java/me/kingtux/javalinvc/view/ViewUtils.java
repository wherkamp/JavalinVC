package me.kingtux.javalinvc.view;

import io.javalin.http.Context;
import me.kingtux.javalinvc.JavalinVC;
import me.kingtux.javalinvc.rg.ResourceGrabber;

public class ViewUtils {
    private ViewUtils() {

    }

    public static void respond(Context request, View view, JavalinVC javalinVC) {
        request.result(javalinVC.getViewManager().parseView(view));
    }

    public static void respond(Context request, View view, JavalinVC javalinVC, ResourceGrabber internalResourceGrabber) {
        request.result(javalinVC.getViewManager().parseView(internalResourceGrabber, view));
    }
}
