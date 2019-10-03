package me.kingtux.javalinvc.jtwig;

import me.kingtux.javalinvc.view.View;
import org.jtwig.JtwigModel;

public class JtwigView implements View {
    private String template="";
    private JtwigModel jTwigModel = new JtwigModel();

    public JtwigModel getjTwigModel() {
        return jTwigModel;
    }

    @Override
    public View setTemplate(String s) {
        template = s;
        return this;

    }

    @Override
    public String getTemplate() {
        return template;
    }

    @Override
    public View set(String s, Object o) {
        jTwigModel.with(s, o);
        return this;
    }
}
