package me.kingtux.javalinvc.rg;

import java.net.URL;
import java.util.Optional;

/**
 * What is a template grabber you may ask?
 * Well it grabs the template file! and returns the file name
 *
 * @author KingTux
 */
public interface ResourceGrabber {


    /**
     * Returns a resource based on the path
     * @param s the path to the file.
     * @return The Resource null if not found
     */
    Optional<Resource> getResource(String s);

    String getLocation();

    void setLocation(String s);
}
