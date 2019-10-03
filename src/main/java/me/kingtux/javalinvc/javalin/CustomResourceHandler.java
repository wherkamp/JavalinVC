package me.kingtux.javalinvc.javalin;

import io.javalin.http.staticfiles.ResourceHandler;
import io.javalin.http.staticfiles.StaticFileConfig;
import me.kingtux.javalinvc.JavalinVC;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomResourceHandler implements ResourceHandler {
    private JavalinVC javalinVC;

    public CustomResourceHandler(JavalinVC javalinVC) {
        this.javalinVC = javalinVC;
    }

    @Override
    public void addStaticFileConfig(@NotNull StaticFileConfig staticFileConfig) {
    }

    @Override
    public boolean handle(@NotNull HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse) {
        return false;
    }
}
