package me.kingtux.javalinvc.pebble;

import com.mitchellbosecke.pebble.template.PebbleTemplate;
import me.kingtux.javalinvc.view.View;

import java.util.HashMap;
import java.util.Map;

public class PebbleView implements View {
    private String template = "";
    private Map<String, Object> values = new HashMap<>();

    @Override
    public View setTemplate(String s) {
        template = s;
        return this;

    }

    public Map<String, Object> getValues() {
        return values;
    }

    @Override
    public String getTemplate() {
        return template;
    }

    @Override
    public View set(String s, Object o) {
        values.put(s, o);
        return this;
    }
}
