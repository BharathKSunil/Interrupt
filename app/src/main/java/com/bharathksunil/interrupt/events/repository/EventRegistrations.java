package com.bharathksunil.interrupt.events.repository;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * This is the object structure which will be saved to the Firebase Database
 * for the event registrations
 * It is stored in both, RealTime Database and the Cloud FireStore as a document
 * <h>RealTime DB:</h>
 * EventRegistrations
 *          |_userEmail
 *              |_eventID
 *                  |_{data}
 *<h>Cloud Firestore</h>
 * Categories[categoryID]
 *      -Events[eventID]
 *          -Registrations[regID]
 * @author Bharath on 18-02-2018.
 */
@IgnoreExtraProperties
public class EventRegistrations {
    /*
     * Events Registration Data, as stored in the EventRegistration Tree in the Firebase RealTime Database
     */
    private String eventPath, eName, eRegistrar, eBannerUrl, pTeamMembers;
    private String pName, pEmail, pPhoneNo, pUSN;

    /**
     * Participants Data
     */
    public EventRegistrations() {
    }

    public String getEventPath() {
        return eventPath;
    }

    public void setEventPath(String eventPath) {
        this.eventPath = eventPath;
    }

    public String geteBannerUrl() {
        return eBannerUrl;
    }

    public void seteBannerUrl(String eBannerUrl) {
        this.eBannerUrl = eBannerUrl;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public String geteRegistrar() {
        return eRegistrar;
    }

    public void seteRegistrar(String eRegistrar) {
        this.eRegistrar = eRegistrar;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpEmail() {
        return pEmail;
    }

    public void setpEmail(String pEmail) {
        this.pEmail = pEmail;
    }

    public String getpPhoneNo() {
        return pPhoneNo;
    }

    public void setpPhoneNo(String pPhoneNo) {
        this.pPhoneNo = pPhoneNo;
    }

    public String getpUSN() {
        return pUSN;
    }

    public void setpUSN(String pUSN) {
        this.pUSN = pUSN;
    }

    public String getpTeamMembers() {
        return pTeamMembers;
    }

    public void setpTeamMembers(String pTeamMembers) {
        this.pTeamMembers = pTeamMembers;
    }
}
