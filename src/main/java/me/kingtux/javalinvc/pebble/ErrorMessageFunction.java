package me.kingtux.javalinvc.pebble;

import com.mitchellbosecke.pebble.extension.Function;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import me.kingtux.javalinvc.JavalinVC;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ErrorMessageFunction implements Function {
    private JavalinVC javalinVC;

    public ErrorMessageFunction(JavalinVC javalinVC) {
        this.javalinVC = javalinVC;
    }

    @Override
    public Object execute(Map<String, Object> map, PebbleTemplate pebbleTemplate, EvaluationContext evaluationContext, int i) {
        if (!map.containsKey("error_message")) return null;
        if (javalinVC.getErrorMessageProvider() == null) {
            return map.get("error_message");
        }
        return javalinVC.getErrorMessageProvider().getErrorMessage((String) map.get("error_message"));
    }

    @Override
    public List<String> getArgumentNames() {
        return Collections.singletonList("error_message");
    }
}
