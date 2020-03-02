package com.bharathksunil.interrupt.events.model;

import androidx.annotation.Keep;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * This is the object structure which will be saved to the Firebase Database
 * for the event registrations
 * It is stored in both, RealTime Database and the Cloud FireStore as a document
 * <h>RealTime DB:</h>
 * EventRegistrations
 * |_userEmail
 * |_eventID
 * |_{data}
 * <h>Cloud Firestore</h>
 * Categories[categoryID]
 * -Events[eventID]
 * -Registrations[regID]
 *
 * @author Bharath on 18-02-2018.
 */
@Keep
@IgnoreExtraProperties
public class EventRegistrations {
    /*
     * Events Registration Data, as stored in the EventRegistration Tree in the Firebase RealTime Database
     */
    private String eName, eRegistrar, eBannerUrl;
    private String pTeamMembers;
    private String id, pName, pEmail, pPhoneNo, pUSN, pSem, pSection;
    private Boolean Attended;

    /**
     * Participants Data
     */
    public EventRegistrations() {
    }

    public EventRegistrations(String eName, String eRegistrar, String eBannerUrl, String pTeamMembers, String id, String pName, String pEmail, String pPhoneNo, String pUSN, String pSem, String pSection, Boolean attended) {
        this.eName = eName;
        this.eRegistrar = eRegistrar;
        this.eBannerUrl = eBannerUrl;
        this.pTeamMembers = pTeamMembers;
        this.id = id;
        this.pName = pName;
        this.pEmail = pEmail;
        this.pPhoneNo = pPhoneNo;
        this.pUSN = pUSN;
        this.pSem = pSem;
        this.pSection = pSection;
        Attended = attended;
    }

    public Boolean getAttended() {
        return Attended;
    }

    public void setAttended(Boolean attended) {
        Attended = attended;
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
        return pEmail.toLowerCase();
    }

    public void setpEmail(String pEmail) {
        this.pEmail = pEmail.toLowerCase();
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpSem() {
        return pSem;
    }

    public void setpSem(String pSem) {
        this.pSem = pSem;
    }

    public String getpSection() {
        return pSection;
    }

    public void setpSection(String pSection) {
        this.pSection = pSection;
    }

    public String getpTeamMembers() {
        return pTeamMembers;
    }

    public void setpTeamMembers(String pTeamMembers) {
        this.pTeamMembers = pTeamMembers;
    }
}
