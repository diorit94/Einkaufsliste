package com.codefrogapp.dioritbajrami.einkaufsliste.Einkauf_Activity;

/**
 * Created by dioritbajrami on 28.02.18.
 */

public class EinkaufModel {

    private String name;
    private int ammount;
    private String verwalter;
    private boolean bought;

    public EinkaufModel (){}

    public EinkaufModel(String name, int ammount, String verwalter, boolean bought){
        this.name = name;
        this.ammount = ammount;
        this.verwalter = verwalter;
        this.bought = bought;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAmmount(int ammount) {
        this.ammount = ammount;
    }

    public int getAmmount() {
        return ammount;
    }

    public void setVerwalter(String verwalter) {
        this.verwalter = verwalter;
    }
    public String getVerwalter() {
        return verwalter;
    }


    public boolean getBought() {
        return bought;
    }


    public void setBought(boolean bought) {
        this.bought = bought;
    }
}