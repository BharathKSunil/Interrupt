package com.bharathksunil.interrupt.auth.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Map;

/**
 * This represents the structure of the database of UserAccess tree.
 * This is used for access management for the system
 *
 * @author Bharath Kumar S
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@IgnoreExtraProperties
public class UserAccess {
    /**
     * The type of the user, can be:
     * ADMINISTRATOR, PARTICIPANT, CR, COORDINATOR, CORE_TEAM,
     */
    private Map<String, String> AccessTypes;
    /**
     * Is the user allowed to use the app
     */
    private boolean Enabled;
    /**
     * All configurations for the user to have access to the Collections
     */
    private Boolean canViewCollection, canEditCollections, canViewCollectionsHistory;
    private Boolean canRegisterParticipant, canEditEventBanner;
    /**
     * All configurations for event managements
     */
    private Boolean canEditEventsInfo, canRegisterUser, canViewEventCollections,
            canViewRegistrations, canDownloadEventData, canAddCategories, canAddEvents,
            canChangeSchedule, canChangeVenue;
    /**
     * All configurations for access Management
     */
    protected Boolean canAddCoordinator, canAddOrganiser, canAddClassRepresentatives, canAddVolunteer;

    private Boolean canDownloadPaymentsInfo;


    public UserAccess() {
        this.canViewCollection = null;
        this.canEditCollections = null;
        this.canViewCollectionsHistory = null;
        this.canRegisterParticipant = null;
        this.canEditEventBanner = null;
        this.canEditEventsInfo = null;
        this.canRegisterUser = null;
        this.canViewEventCollections = null;
        this.canViewRegistrations = null;
        this.canDownloadEventData = null;
        this.canAddCategories = null;
        this.canAddEvents = null;
        this.canChangeSchedule = null;
        this.canChangeVenue = null;
        this.canAddCoordinator = null;
        this.canAddOrganiser = null;
        this.canAddClassRepresentatives = null;
        this.canAddVolunteer = null;
        this.canDownloadPaymentsInfo = null;
    }

    public Map<String, String> getAccessTypes() {
        return AccessTypes;
    }

    public void setAccessTypes(Map<String, String> accessTypes) {
        AccessTypes = accessTypes;
    }

    public boolean isEnabled() {
        return Enabled;
    }

    public void setEnabled(boolean enabled) {
        Enabled = enabled;
    }

    public Boolean getCanViewCollection() {
        return canViewCollection;
    }

    public void setCanViewCollection(Boolean canViewCollection) {
        this.canViewCollection = canViewCollection;
    }

    public Boolean getCanEditCollections() {
        return canEditCollections;
    }

    public void setCanEditCollections(Boolean canEditCollections) {
        this.canEditCollections = canEditCollections;
    }

    public Boolean getCanViewCollectionsHistory() {
        return canViewCollectionsHistory;
    }

    public void setCanViewCollectionsHistory(Boolean canViewCollectionsHistory) {
        this.canViewCollectionsHistory = canViewCollectionsHistory;
    }

    public Boolean getCanRegisterParticipant() {
        return canRegisterParticipant;
    }

    public void setCanRegisterParticipant(Boolean canRegisterParticipant) {
        this.canRegisterParticipant = canRegisterParticipant;
    }

    public Boolean getCanEditEventBanner() {
        return canEditEventBanner;
    }

    public void setCanEditEventBanner(Boolean canEditEventBanner) {
        this.canEditEventBanner = canEditEventBanner;
    }

    public Boolean getCanEditEventsInfo() {
        return canEditEventsInfo;
    }

    public void setCanEditEventsInfo(Boolean canEditEventsInfo) {
        this.canEditEventsInfo = canEditEventsInfo;
    }

    public Boolean getCanRegisterUser() {
        return canRegisterUser;
    }

    public void setCanRegisterUser(Boolean canRegisterUser) {
        this.canRegisterUser = canRegisterUser;
    }

    public Boolean getCanViewEventCollections() {
        return canViewEventCollections;
    }

    public void setCanViewEventCollections(Boolean canViewEventCollections) {
        this.canViewEventCollections = canViewEventCollections;
    }

    public Boolean getCanViewRegistrations() {
        return canViewRegistrations;
    }

    public void setCanViewRegistrations(Boolean canViewRegistrations) {
        this.canViewRegistrations = canViewRegistrations;
    }

    public Boolean getCanDownloadEventData() {
        return canDownloadEventData;
    }

    public void setCanDownloadEventData(Boolean canDownloadEventData) {
        this.canDownloadEventData = canDownloadEventData;
    }

    public Boolean getCanAddCategories() {
        return canAddCategories;
    }

    public void setCanAddCategories(Boolean canAddCategories) {
        this.canAddCategories = canAddCategories;
    }

    public Boolean getCanAddEvents() {
        return canAddEvents;
    }

    public void setCanAddEvents(Boolean canAddEvents) {
        this.canAddEvents = canAddEvents;
    }

    public Boolean getCanChangeSchedule() {
        return canChangeSchedule;
    }

    public void setCanChangeSchedule(Boolean canChangeSchedule) {
        this.canChangeSchedule = canChangeSchedule;
    }

    public Boolean getCanChangeVenue() {
        return canChangeVenue;
    }

    public void setCanChangeVenue(Boolean canChangeVenue) {
        this.canChangeVenue = canChangeVenue;
    }

    public Boolean getCanAddCoordinator() {
        return canAddCoordinator;
    }

    public void setCanAddCoordinator(Boolean canAddCoordinator) {
        this.canAddCoordinator = canAddCoordinator;
    }

    public Boolean getCanAddOrganiser() {
        return canAddOrganiser;
    }

    public void setCanAddOrganiser(Boolean canAddOrganiser) {
        this.canAddOrganiser = canAddOrganiser;
    }

    public Boolean getCanAddClassRepresentatives() {
        return canAddClassRepresentatives;
    }

    public void setCanAddClassRepresentatives(Boolean canAddClassRepresentatives) {
        this.canAddClassRepresentatives = canAddClassRepresentatives;
    }

    public Boolean getCanAddVolunteer() {
        return canAddVolunteer;
    }

    public void setCanAddVolunteer(Boolean canAddVolunteer) {
        this.canAddVolunteer = canAddVolunteer;
    }

    public Boolean getCanDownloadPaymentsInfo() {
        return canDownloadPaymentsInfo;
    }

    public void setCanDownloadPaymentsInfo(Boolean canDownloadPaymentsInfo) {
        this.canDownloadPaymentsInfo = canDownloadPaymentsInfo;
    }
}
