package me.kingtux.javalinvc.pebble;

import com.mitchellbosecke.pebble.loader.Loader;
import me.kingtux.javalinvc.JavalinVC;
import me.kingtux.javalinvc.rg.Resource;

import java.io.Reader;
import java.util.Optional;

public class PebbleLoader implements Loader<String> {
    private JavalinVC javalinVC;
    private String charset = "UTF-8";

    public PebbleLoader(JavalinVC javalinVC) {
        this.javalinVC = javalinVC;
    }

    @Override
    public Reader getReader(String s) {
        Optional<Resource> resourceO = javalinVC.getResourceGrabber().getResource(s);
        if (resourceO.isPresent()) {
            Resource resource = resourceO.get();
            return resource.getReader();
        }
        return null;
    }

    @Override
    public void setCharset(String s) {
        charset = s;
    }

    @Override
    public void setPrefix(String s) {

    }

    @Override
    public void setSuffix(String s) {

    }

    @Override
    public String resolveRelativePath(String s, String s1) {
        return null;
    }

    @Override
    public String createCacheKey(String s) {
        return null;
    }

    @Override
    public boolean resourceExists(String s) {
        return false;
    }
}
