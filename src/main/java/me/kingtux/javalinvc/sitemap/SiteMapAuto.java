package me.kingtux.javalinvc.sitemap;

import me.kingtux.javalinvc.JavalinVC;
import me.kingtux.javalinvc.controller.SingleController;
import me.kingtux.javalinvc.controller.SingleSitemapHandler;
import me.kingtux.javalinvc.core.RequestType;
import me.kingtux.tuxjsitemap.SiteMap;
import me.kingtux.tuxjsitemap.SiteMapGenerator;
import me.kingtux.tuxjsitemap.SiteURL;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class SiteMapAuto extends Thread {
    private File folder = new File("sitemap");
    private JavalinVC website;

    public SiteMapAuto(JavalinVC simpleWebsite) {
        this.website = simpleWebsite;
    }

    @Override
    public void run() {
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            JavalinVC.LOGGER.error("Unable to sleep thread", e);
        }
        while (website.isRunning()) {
            JavalinVC.LOGGER.debug("Creating SiteMap!");
            SiteMap map;
            SiteMapGenerator generator = new SiteMapGenerator(website.getRules().baseURL());
            List<SingleController> controllers = new ArrayList<>(website.getControllers());
            for (SingleController sc : controllers) {
                if (sc.getRequestType() == RequestType.GET && sc.sitemap()) generator.addURL(sc.getPath().substring(1));
            }
            List<SingleSitemapHandler> singleSitemapHandlers = new ArrayList<>(website.getSingleSitemapHandlers());

            for (SingleSitemapHandler singleSitemapHandler : singleSitemapHandlers) {

                List<SiteURL> urls = singleSitemapHandler.execute(website);
                urls.forEach(generator::addURL);
            }

            map = generator.build();

            try {
                map.writeToFolder(folder);
            } catch (IOException e) {
                JavalinVC.LOGGER.error("Unable to save to folder", e);
            }
            try {
                sleep(website.getConfig().getSiteMapRefreshRate());
            } catch (InterruptedException e) {
                JavalinVC.LOGGER.error("Unable to sleep thread", e);
            }
        }
    }
}
