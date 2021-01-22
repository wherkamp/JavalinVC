package me.kingtux.javalinvc;

@SuppressWarnings("WeakerAccess")
public class WebsiteRules {
    private String url;
    private String name;

    WebsiteRules(String url, String name) {
        this.url = url;
        this.name = name;
    }


    @Override
    public String toString() {
        return "WebsiteRules{" +
                "url='" + url + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    /**
     * Gets the host of this website
     *
     * @return the host
     */
    public String getHost() {
        return url.replaceAll("(https://|http://)", "");
    }

    public String baseURL() {
        return url;
    }

    public String baseWSURL() {
        return url.toLowerCase().startsWith("https://") ? "wss" : "ws" + "://" + getHost();
    }

    public String name() {
        return name;
    }
}
