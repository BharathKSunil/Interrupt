package com.bharathksunil.interrupt.auth.model;

import android.support.annotation.Keep;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * This represents the structure of the database of UserPermissions tree.
 * This is used for access management for the system
 *
 * @author Bharath Kumar S
 */
@Keep
@SuppressWarnings({"unused", "WeakerAccess"})
@IgnoreExtraProperties
public class UserPermissions {

    /**
     * Is the user allowed to use the app
     */
    private boolean Enabled;

    private Boolean canRegisterParticipant, canEditEventBanner;
    /**
     * All configurations for event managements
     */
    private Boolean canEditEventsInfo, canViewEventCollections,
            canViewRegistrations, canDownloadEventData, canAddCategories, canAddEvents,
            canChangeSchedule, canChangeVenue, canModifyCoordinatorData;
    /**
     * All configurations for Admin
     */
    protected Boolean canModifyOrganiserData, canViewUserData, canViewFeedbackData;

    private Boolean canDownloadPaymentsInfo, canViewPaymentsInfo;


    public UserPermissions() {
    }

    public boolean isEnabled() {
        return Enabled;
    }

    public void setEnabled(boolean enabled) {
        Enabled = enabled;
    }

    public Boolean getCanViewPaymentsInfo() {
        return canViewPaymentsInfo;
    }

    public void setCanViewPaymentsInfo(Boolean canViewPaymentsInfo) {
        this.canViewPaymentsInfo = canViewPaymentsInfo;
    }

    public Boolean getCanViewFeedbackData() {
        return canViewFeedbackData;
    }

    public void setCanViewFeedbackData(Boolean canViewFeedbackData) {
        this.canViewFeedbackData = canViewFeedbackData;
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
