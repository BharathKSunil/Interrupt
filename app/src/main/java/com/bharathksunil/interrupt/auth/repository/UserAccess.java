package com.bharathksunil.interrupt.auth.repository;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * This represents the structure of the database of UserAccess tree.
 * This is used for access management for the system
 * @author Bharath Kumar S
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@IgnoreExtraProperties
public class UserAccess {
    /**
     * The type of the user, can be:
     * ADMINISTRATOR, PARTICIPANT, CR, COORDINATOR, ORGANISER,
     */
    protected String AccessType;
    /**
     * Is the user allowed to use the app
     */
    protected boolean Enabled;
    /**
     * All configurations for the user to have access to the Collections
     */
    protected boolean canViewCollection, canEditCollections, canViewCollectionsHistory;
    /**
     * All configurations for event managements
     */
    protected boolean canEditEventsInfo, canRegisterUser, canViewEventCollections,
            canRequestResourceForEvents, canViewRegistrations, canDownloadEventData;
    /**
     * All configurations for access Management
     */
    protected boolean canAddCoordinator, canAddOrganiser, canAddClassRepresentatives, canAddVolunteer;

    @SuppressWarnings("WeakerAccess")
    public UserAccess() {
    }

    public String getAccessType() {
        return AccessType;
    }

    public void setAccessType(String accessType) {
        AccessType = accessType;
    }

    public boolean isEnabled() {
        return Enabled;
    }

    public void setEnabled(boolean enabled) {
        Enabled = enabled;
    }

    public boolean isCanViewCollection() {
        return canViewCollection;
    }

    public void setCanViewCollection(boolean canViewCollection) {
        this.canViewCollection = canViewCollection;
    }

    public boolean isCanEditCollections() {
        return canEditCollections;
    }

    public void setCanEditCollections(boolean canEditCollections) {
        this.canEditCollections = canEditCollections;
    }

    public boolean isCanViewCollectionsHistory() {
        return canViewCollectionsHistory;
    }

    public void setCanViewCollectionsHistory(boolean canViewCollectionsHistory) {
        this.canViewCollectionsHistory = canViewCollectionsHistory;
    }

    public boolean isCanEditEventsInfo() {
        return canEditEventsInfo;
    }

    public void setCanEditEventsInfo(boolean canEditEventsInfo) {
        this.canEditEventsInfo = canEditEventsInfo;
    }

    public boolean isCanRegisterUser() {
        return canRegisterUser;
    }

    public void setCanRegisterUser(boolean canRegisterUser) {
        this.canRegisterUser = canRegisterUser;
    }

    public boolean isCanViewEventCollections() {
        return canViewEventCollections;
    }

    public void setCanViewEventCollections(boolean canViewEventCollections) {
        this.canViewEventCollections = canViewEventCollections;
    }

    public boolean isCanRequestResourceForEvents() {
        return canRequestResourceForEvents;
    }

    public void setCanRequestResourceForEvents(boolean canRequestResourceForEvents) {
        this.canRequestResourceForEvents = canRequestResourceForEvents;
    }

    public boolean isCanViewRegistrations() {
        return canViewRegistrations;
    }

    public void setCanViewRegistrations(boolean canViewRegistrations) {
        this.canViewRegistrations = canViewRegistrations;
    }

    public boolean isCanDownloadEventData() {
        return canDownloadEventData;
    }

    public void setCanDownloadEventData(boolean canDownloadEventData) {
        this.canDownloadEventData = canDownloadEventData;
    }

    public boolean isCanAddCoordinator() {
        return canAddCoordinator;
    }

    public void setCanAddCoordinator(boolean canAddCoordinator) {
        this.canAddCoordinator = canAddCoordinator;
    }

    public boolean isCanAddOrganiser() {
        return canAddOrganiser;
    }

    public void setCanAddOrganiser(boolean canAddOrganiser) {
        this.canAddOrganiser = canAddOrganiser;
    }

    public boolean isCanAddClassRepresentatives() {
        return canAddClassRepresentatives;
    }

    public void setCanAddClassRepresentatives(boolean canAddClassRepresentatives) {
        this.canAddClassRepresentatives = canAddClassRepresentatives;
    }

    public boolean isCanAddVolunteer() {
        return canAddVolunteer;
    }

    public void setCanAddVolunteer(boolean canAddVolunteer) {
        this.canAddVolunteer = canAddVolunteer;
    }
}
