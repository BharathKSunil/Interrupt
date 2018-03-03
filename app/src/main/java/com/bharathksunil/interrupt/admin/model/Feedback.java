package com.bharathksunil.interrupt.admin.model;

import android.support.annotation.Keep;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * This represents the structure of the Feedback Tree in the firebase
 * @author Bharath on 24-02-2018.
 */
@Keep
@IgnoreExtraProperties
public class Feedback {
    private String Name, EmailId, Feedback, Time;

    public Feedback() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmailId() {
        return EmailId.toLowerCase();
    }

    public void setEmailId(String emailId) {
        EmailId = emailId.toLowerCase();
    }

    public String getFeedback() {
        return Feedback;
    }

    public void setFeedback(String feedback) {
        Feedback = feedback;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
