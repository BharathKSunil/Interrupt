package com.bharathksunil.interrupt.events.model;

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

    public void loadEventRegistrations(EventRegistrations registrations) {
        instance.registrations = registrations;
    }

    public String getCurrentCategoryID() {
        return instance.categories.getId();
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

}
