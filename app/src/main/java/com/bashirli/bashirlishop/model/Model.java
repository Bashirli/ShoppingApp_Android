package com.bashirli.bashirlishop.model;

public class Model {
    private String price;
    private String name;
    private String about;
    private String image;


    public Model(String price, String name, String about,String image) {
        this.price = price;
        this.name = name;
        this.about = about;
        this.image=image;

    }



    public String getImage() {
        return image;
    }

    public String getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getAbout() {
        return about;
    }

}
