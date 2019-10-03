package me.kingtux.javalinvc;

public class JavalinVConfig {
    private long siteMapRefreshRate = 900000;
    private boolean siteMapEnabled = true;

    public long getSiteMapRefreshRate() {
        return siteMapRefreshRate;
    }

    public void setSiteMapRefreshRate(long siteMapRefreshRate) {
        this.siteMapRefreshRate = siteMapRefreshRate;
    }

    public boolean isSiteMapEnable() {
        return true;
    }

    public void setSiteMapEnabled(boolean siteMapEnabled) {
        this.siteMapEnabled = siteMapEnabled;
    }
}
