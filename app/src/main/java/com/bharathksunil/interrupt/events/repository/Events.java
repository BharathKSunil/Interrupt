package com.bharathksunil.interrupt.events.repository;

import com.google.firebase.firestore.IgnoreExtraProperties;

/**
 * This is the structure of the all the Events in Firebase
 *
 * @author Bharath on 18-02-2018.
 */
@IgnoreExtraProperties
public class Events {
    private String id, category, name, description, dateTime, bannerUrl;

    public Events() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }
}
