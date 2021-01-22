package me.kingtux.javalinvc.view;


import io.javalin.http.Context;
import me.kingtux.javalinvc.JavalinVC;
import me.kingtux.javalinvc.rg.ResourceGrabber;

public interface ViewManager {
    /**
     * @param tg The template grabber
     */
    void setResourceGrabber(ResourceGrabber tg);

    /**
     * This returns the template grabber
     *
     * @return
     */
    ResourceGrabber getResourceGrabber();

    /**
     * This takes the view and replaces the variables
     *
     * @param view the view to run
     * @return the String to return
     */
    default String parseView(View view) {
        return parseView(getResourceGrabber(), view);
    }

    String parseView(ResourceGrabber grabber, View view);

    /**
     * This builds a view!
     *
     * @param website thewebsite
     * @param rq      the request
     * @return the view
     */
    View buildView(JavalinVC website, Context rq);

    default View buildView(JavalinVC website) {
       return buildView(website, null);
    }

    /**
     * A Default template Variable takes is put into every view that is rendered
     * @param key the key
     * @param value the value
     */
    void registerDefaultViewVariable(String key, Object value);

    void registerViewVariableGrabber(String key, ViewVariableGrabber value);

    String getExtension();

    void setExtension(String extension);
}
