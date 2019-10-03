package me.kingtux.javalinvc.core;

import io.javalin.http.Context;
import me.kingtux.javalinvc.annotations.RequestParam;
import me.kingtux.javalinvc.view.View;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class ArgumentBuilder {
    private ArgumentBuilder() {

    }
    public static Object[] getArguments(Parameter[] parameters, View view, Context context) {
        List<Object> o = new ArrayList<>();
        for (int i = 0; i < parameters.length; i++) {
            Class<?> type = parameters[i].getType();
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);

            if (requestParam != null && type == String.class) {
                if (requestParam.type() == RequestParam.Type.GET) {
                    o.add(i, context.queryParam(requestParam.key(), requestParam.defaultValue()));

                } else if (requestParam.type() == RequestParam.Type.REQUEST) {
                    o.add(i, context.formParam(requestParam.key(), requestParam.defaultValue()));

                } else if (requestParam.type() == RequestParam.Type.URL) {
                    o.add(i, context.pathParam(requestParam.key()));
                }
            } else if (type.isAssignableFrom(View.class)) {
                o.add(i, view);
            } else if (type.isAssignableFrom(Context.class)) {
                o.add(i, context);
            } else if (requestParam != null && requestParam.type() == RequestParam.Type.FILE) {
                if (type.isAssignableFrom(List.class)) {
                    //Get all
                    o.add(i, context.uploadedFile(requestParam.key()));
                } else {
                    o.add(i, context.uploadedFiles(requestParam.key()));
                }
            } else {
                o.add(i, null);
            }
        }
        return o.toArray();
    }
}
