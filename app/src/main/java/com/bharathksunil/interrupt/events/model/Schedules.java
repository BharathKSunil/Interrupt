package com.bharathksunil.interrupt.events.model;

import android.support.annotation.Keep;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * The Model of how the schedule is stored in the Schedules Tree
 *
 * @author Bharath on 24-02-2018.
 */
@Keep
@IgnoreExtraProperties
public class Schedules {
    private String EventName, Time, Venue;
    private Long Timestamp;

    public Schedules() {
    }

    public Schedules(String eventName, String time, String venue) {
        EventName = eventName;
        Time = time;
        Venue = venue;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getVenue() {
        return Venue;
    }

    public void setVenue(String venue) {
        Venue = venue;
    }

    public Long getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(Long timestamp) {
        Timestamp = timestamp;
    }
}
