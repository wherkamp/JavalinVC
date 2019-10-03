package me.kingtux.javalinvc.jtwig;

import me.kingtux.javalinvc.JavalinVC;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

public class RouteFunction extends SimpleJtwigFunction {
    private JavalinVC site;

    public RouteFunction(JavalinVC site) {
        this.site = site;
    }

    @Override
    public String name() {
        return "route";
    }

    @Override
    public Object execute(FunctionRequest functionRequest) {
        return site.route((String) functionRequest.get(0));
    }
}
