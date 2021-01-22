package me.kingtux.javalinvc.rg.templategrabbers;


import me.kingtux.javalinvc.JavalinVC;
import me.kingtux.javalinvc.rg.Resource;
import me.kingtux.javalinvc.rg.ResourceGrabber;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

public class ExternalResourceGrabber implements ResourceGrabber {
    private String location;


    public ExternalResourceGrabber(String location) {
        this.location = location;
    }


    @Override
    public Optional<Resource> getResource(String s) {
        File templateFile = new File(new File(location), s.replace("/", File.separator));
        if (!templateFile.exists()) return null;
        try {
            return Optional.ofNullable(new Resource(IOUtils.toByteArray(new FileInputStream(templateFile)), templateFile.toURI().toURL(), this, FilenameUtils.getExtension(templateFile.getAbsolutePath())));
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
