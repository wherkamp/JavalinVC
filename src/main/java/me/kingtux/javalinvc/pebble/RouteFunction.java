package me.kingtux.javalinvc.pebble;

import com.mitchellbosecke.pebble.extension.Function;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import me.kingtux.javalinvc.JavalinVC;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RouteFunction implements Function {
    private JavalinVC javalinVC;

    public RouteFunction(JavalinVC javalinVC) {
        this.javalinVC = javalinVC;
    }

    @Override
    public Object execute(Map<String, Object> map, PebbleTemplate pebbleTemplate, EvaluationContext evaluationContext, int i) {
        return javalinVC.route((String) map.get("path"));
    }

    @Override
    public List<String> getArgumentNames() {
        return Collections.singletonList("path");
    }
}
