package me.kingtux.javalinvc.jtwig;

import me.kingtux.javalinvc.JavalinVC;
import me.kingtux.javalinvc.rg.Resource;
import org.apache.commons.io.IOUtils;
import org.jtwig.resource.reference.ResourceReference;
import org.jtwig.resource.reference.ResourceReferenceExtractor;

import java.io.IOException;
import java.util.Optional;

public class TuxResourceReferenceExtractor implements ResourceReferenceExtractor {
    private JtwigViewManager manager;

    public TuxResourceReferenceExtractor(JtwigViewManager manager) {
        this.manager = manager;
    }

    @Override
    public ResourceReference extract(String s) {
        //Get the extension. If present return "" else get default extension
        String extension = ((s.toLowerCase().endsWith(manager.getExtension()) || s.toLowerCase().toLowerCase().endsWith(".html")) ? "" : manager.getExtension());
        //Get Resource ANd Check
        Optional<Resource> resourceOptional =
                manager.getResourceGrabber().getResource(s + extension);
        if (!resourceOptional.isPresent()) return null;
        try {
            //Return String ResourceReference
            byte[] bytes = IOUtils.toByteArray(resourceOptional.get().getUrl().openStream());
            return ResourceReference.inline(new String(bytes));
        } catch (IOException e) {
            JavalinVC.LOGGER.error("Unable to do it?", e);
        }
        return null;
    }


}
