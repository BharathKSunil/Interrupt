package com.bharathksunil.interrupt.events.repository;

import com.google.firebase.firestore.IgnoreExtraProperties;

/**
 * This is the structure of the Categories stored in the Firebase
 *
 * @author Bharath on 18-02-2018.
 */
@IgnoreExtraProperties
public class Categories {
    private String id;
    private String name;
    private String description;
    private String imgUrl;

    public Categories() {
    }

    public Categories(String id, String name, String description, String imgUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
