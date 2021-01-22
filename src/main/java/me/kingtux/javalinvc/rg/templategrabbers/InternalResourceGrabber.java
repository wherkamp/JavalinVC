package me.kingtux.javalinvc.rg.templategrabbers;


import me.kingtux.javalinvc.JavalinVC;
import me.kingtux.javalinvc.rg.Resource;
import me.kingtux.javalinvc.rg.ResourceGrabber;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

/**
 * This template grabber only grabs the internal one. The one inside the jar file;
 */
public class InternalResourceGrabber implements ResourceGrabber {
    private String location;

    public InternalResourceGrabber(String location) {
        this.location = location;
    }


    @Override
    public Optional<Resource> getResource(String s) {
        String path = "/" + location + "/" + s;
        URL url = getClass().getResource(path);
        if (url == null) return null;
        try {
            return Optional.of(new Resource(IOUtils.toByteArray(getClass().getResourceAsStream(path)), url, this, null));
        } catch (IOException e) {
            JavalinVC.LOGGER.error("Unable to get Resource", e);
        }
        return Optional.empty();
    }


    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public void setLocation(String s) {
        location = s;
    }
}
