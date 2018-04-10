package com.codefrog.dioritbajrami.einkaufsliste;

/**
 * Created by dioritbajrami on 23.02.18.
 */

public class Einkaufsliste_Model {

    private String name;
    private int ImageResource;

    public Einkaufsliste_Model(){}

    public Einkaufsliste_Model(String name, int imageRsc){
        this.name = name;
        this.ImageResource = imageRsc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageResource() {
        return ImageResource;
    }

    public void setImageResource(int imageResource) {
        ImageResource = imageResource;
    }
}