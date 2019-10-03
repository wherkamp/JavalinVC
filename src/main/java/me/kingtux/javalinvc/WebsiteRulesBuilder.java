package me.kingtux.javalinvc;

public class WebsiteRulesBuilder {
    private String url;
    private String name;

    private WebsiteRulesBuilder() {

    }

    public static WebsiteRulesBuilder create() {
        return new WebsiteRulesBuilder();
    }

    public WebsiteRulesBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    public WebsiteRulesBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public WebsiteRules build() {
        return new WebsiteRules(url, name);
    }
}