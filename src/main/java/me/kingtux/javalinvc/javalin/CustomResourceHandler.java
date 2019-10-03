package me.kingtux.javalinvc.javalin;

import io.javalin.core.util.Header;
import io.javalin.http.staticfiles.ResourceHandler;
import io.javalin.http.staticfiles.StaticFileConfig;
import me.kingtux.javalinvc.JavalinVC;
import me.kingtux.javalinvc.core.MimeType;
import me.kingtux.javalinvc.rg.Resource;
import me.kingtux.javalinvc.rg.ResourceGrabber;
import me.kingtux.javalinvc.rg.templategrabbers.ExternalResourceGrabber;
import me.kingtux.javalinvc.rg.templategrabbers.IEResourceGrabber;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.webjars.WebJarAssetLocator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class CustomResourceHandler implements ResourceHandler {
    private ResourceGrabber grabber = new IEResourceGrabber("public");
    private ResourceGrabber siteMapGrabber;


    public CustomResourceHandler(JavalinVC javalinVC) {
        if (javalinVC.getConfig().isSiteMapEnable()) {
            siteMapGrabber = new ExternalResourceGrabber("sitemap");
        }
    }

    @Override
    public void addStaticFileConfig(@NotNull StaticFileConfig staticFileConfig) {
        JavalinVC.LOGGER.warn("Adding static files configs are not enabled!");
    }

    @Override
    public boolean handle(HttpServletRequest request, @NotNull HttpServletResponse response) {
        String url = request.getRequestURI();
        //Get Extension
        String extension = url.substring(url.lastIndexOf('.') + 1).trim();
        //Get URL For File
        URL urlForFile = getURLForFile(url);
        //Check the url
        if (urlForFile == null) return false;

        //Respond
        return respond(urlForFile, request, response, extension);
    }

    private URL getURLForFile(String url) {
        URL urlForFile = null;
        //Check for site map
        if (url.toLowerCase().contains("sitemap")) {
            JavalinVC.LOGGER.debug(url);
            Optional<Resource> resource = siteMapGrabber.getResource(url.substring(1));
            if (resource.isPresent()) urlForFile = resource.get().getUrl();
        }
        //Web Jar
        if (url.toLowerCase().startsWith("/assets/libs/") && urlForFile == null) {
            urlForFile = webjar(url);
        }
        //Check for regular Static FIle
        if (urlForFile == null) {
            Optional<Resource> resource = grabber.getResource(url.substring(1));
            if (!resource.isPresent()) {
                JavalinVC.LOGGER.info("Unable to locate: " + url.substring(1));
                return null;
            }
            urlForFile = resource.get().getUrl();
            //.getFile(url.substring(1));
        }
        return urlForFile;
    }

    private boolean respond(URL urlForFile, HttpServletRequest request, HttpServletResponse response, String result) {

        //Do the response
        response.setContentType(MimeType.getMimeTypeFromExtension(result).getMimeType());
        JavalinVC.LOGGER.debug(String.format("%s Was found for %s", response.getContentType(), result));
        request.setAttribute("handled-as-static-file", true);
        response.setHeader(Header.CACHE_CONTROL, "max-age=31622400");

        try (InputStream stream = urlForFile.openStream()) {
            String content = IOUtils.toString(stream, StandardCharsets.UTF_8.name());
            //response.setContentLength(content.length());
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(content);
        } catch (IOException e) {
            JavalinVC.LOGGER.error("Unable to copy stream", e);
            return false;
        }

        return true;
    }

    private URL webjar(String url) {
        String libPath = url.replace("/assets/libs/", "");
        StringBuilder path = new StringBuilder();
        if (JavalinVC.class.getResource("/META-INF/resources/webjars/" + libPath) != null) {
            path = new StringBuilder("/META-INF/resources/webjars/" + libPath);
        } else {
            //Get the Version using random apis!
            String[] split = libPath.split("/");
            String localPath;
            WebJarAssetLocator locator = new WebJarAssetLocator();
            StringBuilder builder = new StringBuilder();
            for (int i = 1; i < split.length; i++) {
                if (i != 1) builder.append("/");
                builder.append(split[i]);
            }
            try {
                localPath = locator.getFullPath(split[0], builder.toString());
                path = new StringBuilder(String.format("/%s", localPath));
            } catch (IllegalArgumentException ignored) {
            }
        }

        if ((!path.toString().isEmpty()) && JavalinVC.class.getResource(path.toString()) != null) {
            JavalinVC.LOGGER.debug("Locating Internal Library File " + path.toString());

            return JavalinVC.class.getResource(path.toString());
        }
        return null;
    }

}
