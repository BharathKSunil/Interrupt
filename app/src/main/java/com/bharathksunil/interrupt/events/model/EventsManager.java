package com.bharathksunil.interrupt.events.model;

import com.bharathksunil.interrupt.FirebaseConstants;

import java.util.List;

/**
 * This Manager manages all the event related data and stored in the singleton instance
 *
 * @author Bharath on 20-02-2018.
 */

public class EventsManager {
    private static EventsManager instance = new EventsManager();
    private Events events;
    private Categories categories;
    private EventRegistrations registrations;


    private EventsManager() {
    }

    public static EventsManager getInstance() {
        return instance;
    }

    public void loadCategories(Categories categories) {
        instance.categories = categories;
    }

    public void loadEvents(Events events) {
        instance.events = events;
    }

    public boolean isEventsEmpty() {
        return instance.events == null;
    }

    public boolean isEventCategoriesEmpty() {
        return instance.categories == null;
    }

    public void loadEventRegistrations(EventRegistrations registrations) {
        instance.registrations = registrations;
    }

    public String getCurrentCategoryID() {
        return instance.categories.getId();
    }

    public String getEventCategoryID() {
        return instance.events.getCategoryID();
    }

    public String getCurrentEventPath() {
        return "/" + FirebaseConstants.COLLECTIONS_CATEGORIES + "/" + getEventCategoryID() + "/"
                + FirebaseConstants.COLLECTIONS_EVENTS + "/" + getCurrentEventsID() + "/";
    }

    public String getCurrentEventsID() {
        return instance.events.getId();
    }

    public String getCurrentRegistrationsID() {
        return instance.registrations.getId();
    }

    public String getCurrentEventBannerURL() {
        return instance.events.getBannerUrl();
    }

    public String getEventName() {
        return instance.events.getName();
    }

    public String getEventCategory() {
        return instance.events.getCategory();
    }

    public String getEventDescription() {
        return instance.events.getDescription();
    }

    public String getEventTime() {
        return instance.events.getDateTime();
    }

    public String getEventVenue() {
        return instance.events.getVenue();
    }

    public String getEventPrice() {
        return Integer.toString(instance.events.getPrice());
    }

    public String getEventCoordinatorName() {
        return instance.events.getCoordinators().get(0);
    }

    public String getEventCoordinatorEmail() {
        return instance.events.getCoordinators().get(1);
    }

    public String getEventCoordinatorPhone() {
        return instance.events.getCoordinators().get(2);
    }

    public List<String> getEventCoordinators() {
        return instance.events.getCoordinators();
    }

}
