package com.bharathksunil.interrupt.events.model;

import android.support.annotation.Keep;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.List;

/**
 * This is the structure of the all the Events in Firebase
 *
 * @author Bharath on 18-02-2018.
 */
@Keep
@IgnoreExtraProperties
public class Events {
    private String id, category, categoryID, name, description, dateTime, bannerUrl, venue;
    private int price;
    private List<String> coordinators;

    public Events() {
    }

    public Events(String id, String category, String name, String description, String dateTime,
                  String bannerUrl, String venue, int price, List<String> coordinators) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.description = description;
        this.dateTime = dateTime;
        this.bannerUrl = bannerUrl;
        this.venue = venue;
        this.price = price;
        this.coordinators = coordinators;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<String> getCoordinators() {
        return coordinators;
    }

    public void setCoordinators(List<String> coordinators) {
        this.coordinators = coordinators;
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
