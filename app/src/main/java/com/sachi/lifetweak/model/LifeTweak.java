package com.sachi.lifetweak.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by jombay on 28/3/16.
 */
public class LifeTweak extends RealmObject {

    private int id;
    private String imgUrl;
    private String lifeHacks;
    private int isFavorite;

    public LifeTweak(int id, String imgUrl, String lifeHacks, int isFavorite) {
        this.imgUrl = imgUrl;
        this.lifeHacks = lifeHacks;
        this.isFavorite = isFavorite;
        this.id = id;
    }

    public LifeTweak() {
    }


    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getLifeHacks() {
        return lifeHacks;
    }

    public void setLifeHacks(String lifeHacks) {
        this.lifeHacks = lifeHacks;
    }

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
