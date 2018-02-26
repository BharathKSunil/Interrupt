package com.bharathksunil.interrupt.auth.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * This represents the structure of the database of UserPermissions tree.
 * This is used for access management for the system
 *
 * @author Bharath Kumar S
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@IgnoreExtraProperties
public class UserPermissions {

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
    private Boolean canEditEventsInfo, canViewEventCollections,
            canViewRegistrations, canDownloadEventData, canAddCategories, canAddEvents,
            canChangeSchedule, canChangeVenue;
    /**
     * All configurations for access Management
     */
    protected Boolean canModifyCoordinatorData, canModifyOrganiserData, canAddClassRepresentatives,
            canViewUserData, canViewFeedbackData;

    private Boolean canDownloadPaymentsInfo;


    public UserPermissions() {
    }

    public boolean isEnabled() {
        return Enabled;
    }

    public void setEnabled(boolean enabled) {
        Enabled = enabled;
    }

    public Boolean getCanViewFeedbackData() {
        return canViewFeedbackData;
    }

    public void setCanViewFeedbackData(Boolean canViewFeedbackData) {
        this.canViewFeedbackData = canViewFeedbackData;
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

    public Boolean getCanModifyCoordinatorData() {
        return canModifyCoordinatorData;
    }

    public void setCanModifyCoordinatorData(Boolean canModifyCoordinatorData) {
        this.canModifyCoordinatorData = canModifyCoordinatorData;
    }

    public Boolean getCanModifyOrganiserData() {
        return canModifyOrganiserData;
    }

    public void setCanModifyOrganiserData(Boolean canModifyOrganiserData) {
        this.canModifyOrganiserData = canModifyOrganiserData;
    }

    public Boolean getCanAddClassRepresentatives() {
        return canAddClassRepresentatives;
    }

    public void setCanAddClassRepresentatives(Boolean canAddClassRepresentatives) {
        this.canAddClassRepresentatives = canAddClassRepresentatives;
    }

    public Boolean getCanViewUserData() {
        return canViewUserData;
    }

    public void setCanViewUserData(Boolean canViewUserData) {
        this.canViewUserData = canViewUserData;
    }

    public Boolean getCanDownloadPaymentsInfo() {
        return canDownloadPaymentsInfo;
    }

    public void setCanDownloadPaymentsInfo(Boolean canDownloadPaymentsInfo) {
        this.canDownloadPaymentsInfo = canDownloadPaymentsInfo;
    }
}
