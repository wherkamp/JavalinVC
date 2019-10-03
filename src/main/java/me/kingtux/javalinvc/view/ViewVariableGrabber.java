package me.kingtux.javalinvc.view;

import io.javalin.http.Context;

@FunctionalInterface
public interface ViewVariableGrabber {
    Object get(Context request);
}
