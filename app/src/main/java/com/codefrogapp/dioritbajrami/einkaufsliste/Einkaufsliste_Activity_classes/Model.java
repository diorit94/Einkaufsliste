package com.codefrogapp.dioritbajrami.einkaufsliste.Einkaufsliste_Activity_classes;

/**
 * Created by dioritbajrami on 23.02.18.
 */

public class Model {

    public String name;
    public int ammount;
    public String verwalter;
    public boolean articel;
    public String firebaseid;

    public Model (){}

    public Model(String name, int ammount, String verwalter, boolean articel, String firebaseid){
        this.name = name;
        this.ammount = ammount;
        this.verwalter = verwalter;
        this.articel = articel;
        this.firebaseid = firebaseid;
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


    public void setArticel(boolean articel) {
        this.articel = articel;
    }

    public boolean getArticel() {
        return articel;
    }

    public void setFirebaseid(String firebaseid) {
        this.firebaseid = firebaseid;
    }

    public String getFirebaseid() {
        return firebaseid;
    }
}