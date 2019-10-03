package me.kingtux.javalinvc.jtwig;


import io.javalin.http.Context;
import me.kingtux.javalinvc.JavalinVC;
import me.kingtux.javalinvc.Utils;
import me.kingtux.javalinvc.rg.Resource;
import me.kingtux.javalinvc.rg.ResourceGrabber;

import me.kingtux.javalinvc.view.View;
import me.kingtux.javalinvc.view.ViewManager;
import me.kingtux.javalinvc.view.ViewVariableGrabber;
import org.jtwig.JtwigTemplate;
import org.jtwig.environment.Environment;
import org.jtwig.environment.EnvironmentConfiguration;
import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.environment.EnvironmentFactory;
import org.jtwig.resource.reference.ResourceReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JtwigViewManager implements ViewManager {
    private ResourceGrabber resourceGrabber;
    private Map<String, Object> defaultVariables = new HashMap<>();
    private Map<String, ViewVariableGrabber> viewVariableGrabbers = new HashMap<>();
    private EnvironmentConfiguration configurtation;
    private String extension;

    public JtwigViewManager(JavalinVC website, String extension) {
        this.resourceGrabber = website.getResourceGrabber();
        // sr.host sr.protocol and sr.baseURL
        if (website != null) {
            registerDefaultViewVariable("sr", website.getRules());
            configurtation = EnvironmentConfigurationBuilder.configuration().functions().add(new RouteFunction(website)).add(new ErrorMessage(website)).and().build();
        }
        this.extension = extension == null || extension.isEmpty() ? ".html" : extension;

    }

    @Override
    public ResourceGrabber getResourceGrabber() {
        return resourceGrabber;
    }

    @Override
    public void setResourceGrabber(ResourceGrabber resourceGrabber) {
        this.resourceGrabber = resourceGrabber;
    }

    @Override
    public String parseView(ResourceGrabber grabber, View view) {
        if (view.getTemplate().isEmpty()) {
            return null;
        }
        //Checks rather it ends with the set extension or .html. If not add the set extension
        if (!view.getTemplate().toLowerCase().endsWith(extension) && !view.getTemplate().toLowerCase().endsWith(".html")) {
            view.setTemplate(view.getTemplate() + extension);
        }
        if(JavalinVC.LOGGER.isDebugEnabled())
            JavalinVC.LOGGER.debug(String.format("Locating template %s", view.getTemplate()));
        // Just understand if this breaks I will be on the floor crying
        Environment environment = new EnvironmentFactory().create(configurtation);
        environment.getResourceEnvironment();
        Utils.setFieldValue(environment.getResourceEnvironment(), new TuxResourceService(this), "resourceService");
        Optional<Resource> resource = grabber.getResource(view.getTemplate());
        String s = null;
        if (resource.isPresent())
            s = new String(resource.get().getValue());
        else
            return null;
        JtwigTemplate jtwigTemplate = new JtwigTemplate(environment, ResourceReference.inline(s));
        return jtwigTemplate.render(((JtwigView) view).getjTwigModel());

    }

    @Override
    public View buildView(JavalinVC website, Context request) {
        View view = new JtwigView();
        defaultVariables.forEach(view::set);
        if (request != null)
            viewVariableGrabbers.forEach((s, viewVariableGrabber) -> view.set(s, viewVariableGrabber.get(request)));
        return view;
    }


    @Override
    public void registerDefaultViewVariable(String s, Object o) {
        if (o instanceof ViewVariableGrabber) {
            registerViewVariableGrabber(s, (ViewVariableGrabber) o);
            return;
        }
        defaultVariables.put(s, o);
    }

    @Override
    public void registerViewVariableGrabber(String s, ViewVariableGrabber viewVariableGrabber) {
        viewVariableGrabbers.put(s, viewVariableGrabber);
    }

    @Override
    public String getExtension() {
        return extension;
    }

    @Override
    public void setExtension(String extension) {
        this.extension = extension;
    }


}
