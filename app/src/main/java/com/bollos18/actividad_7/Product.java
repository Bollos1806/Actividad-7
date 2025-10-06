package com.bollos18.actividad_7;

public class Product {
    private String name;
    private String description;
    private String imageUrl;
    private String webUrl;

    public Product(String name, String description, String imageUrl, String webUrl) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.webUrl = webUrl;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public String getWebUrl() { return webUrl; }
}
