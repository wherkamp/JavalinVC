package me.kingtux.javalinvc.test;

import io.javalin.Javalin;
import me.kingtux.javalinvc.JavalinVC;
import me.kingtux.javalinvc.JavalinVCBuilder;
import me.kingtux.javalinvc.WebsiteRulesBuilder;
import me.kingtux.javalinvc.rg.templategrabbers.InternalResourceGrabber;
import me.kingtux.javalinvc.view.ViewManagerBuilder;
import org.eclipse.jetty.server.Server;
import org.junit.jupiter.api.Test;

public class TestMain {
    public static void main(String[] args) {
        new TestMain().test();
    }

    @Test
    public void test() {
        //org.eclipse.jetty.util.log.Log.setLog(new NoLogging());
        JavalinVC javalinVC = JavalinVCBuilder.create().setJavalin(getJavalin()).
                setResourceGrabber(new InternalResourceGrabber("templates")).setRules(WebsiteRulesBuilder.create().setName("Test - JavalinVC").setUrl("http://127.0.0.1:1234").build())
                .setViewManager(ViewManagerBuilder.create().setExtension(".html").setViewManager("me.kingtux.javalinvc.jtwig.JtwigViewManager")).createJavalinVC();
        javalinVC.registerController(new BasicController());
    }


    private static Javalin getJavalin() {
        return Javalin.create(javalinConfig -> {
            javalinConfig.server(() -> new Server(1234));
        });
    }
}
