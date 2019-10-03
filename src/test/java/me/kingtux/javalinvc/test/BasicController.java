package me.kingtux.javalinvc.test;

import io.javalin.http.Context;
import me.kingtux.javalinvc.annotations.Controller;
import me.kingtux.javalinvc.view.View;

public class BasicController {
    @Controller(path = "/")
    public void index(Context context, View view){
        view.setTemplate("index");
    }
}
