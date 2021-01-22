package me.kingtux.javalinvc.pebble;

import com.mitchellbosecke.pebble.attributes.AttributeResolver;
import com.mitchellbosecke.pebble.extension.*;
import com.mitchellbosecke.pebble.operator.BinaryOperator;
import com.mitchellbosecke.pebble.operator.UnaryOperator;
import com.mitchellbosecke.pebble.tokenParser.TokenParser;
import me.kingtux.javalinvc.JavalinVC;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavalinVCExtension implements Extension {
private JavalinVC javalinVC;

    public JavalinVCExtension(JavalinVC javalinVC) {
        this.javalinVC = javalinVC;
    }

    @Override
    public Map<String, Filter> getFilters() {
        return null;
    }

    @Override
    public Map<String, Test> getTests() {
        return null;
    }

    @Override
    public Map<String, Function> getFunctions() {
        Map<String, Function> functionMap = new HashMap<>();
        functionMap.put("route", new RouteFunction(javalinVC));
        functionMap.put("error_message", new ErrorMessageFunction(javalinVC));
        return functionMap;
    }

    @Override
    public List<TokenParser> getTokenParsers() {
        return null;
    }

    @Override
    public List<BinaryOperator> getBinaryOperators() {
        return null;
    }

    @Override
    public List<UnaryOperator> getUnaryOperators() {
        return null;
    }

    @Override
    public Map<String, Object> getGlobalVariables() {
        return null;
    }

    @Override
    public List<NodeVisitorFactory> getNodeVisitors() {
        return null;
    }

    @Override
    public List<AttributeResolver> getAttributeResolver() {
        return null;
    }
}
