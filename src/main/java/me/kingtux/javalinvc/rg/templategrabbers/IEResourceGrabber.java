package me.kingtux.javalinvc.rg.templategrabbers;

import me.kingtux.javalinvc.JavalinVC;
import me.kingtux.javalinvc.rg.Resource;
import me.kingtux.javalinvc.rg.ResourceGrabber;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

/**
 * Basically how this one works. When you request a template file  it grabs it. If the file exists it uses that. If not it uses that one inside the jar.
 */
public class IEResourceGrabber implements ResourceGrabber {
    private String location;


    public IEResourceGrabber(String location) {
        this.location = location;
    }



    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public void setLocation(String s) {
        location = s;
    }

    @Override
    public Optional<Resource> getResource(String s) {
        if (s == null) return Optional.empty();
        Resource resource = null;
        try {
            File externalTemplate = new File(new File(location), s.replace("/", File.separator));
            if (externalTemplate.exists()) {
                resource = new Resource(IOUtils.toByteArray(new FileInputStream(externalTemplate)), externalTemplate.toURI().toURL(), this,FilenameUtils.getExtension(externalTemplate.getAbsolutePath()));
            } else {
                URL url = getClass().getResource("/" + location + "/" + s);
                if (url == null) return Optional.empty();
                resource = new Resource(IOUtils.toByteArray(getClass().getResourceAsStream("/" + location + "/" + s)), url,this, null);
            }
        } catch (IOException e) {
            JavalinVC.LOGGER.error("Unable to get Resource", e);
        }
        return Optional.ofNullable(resource);
    }
}
