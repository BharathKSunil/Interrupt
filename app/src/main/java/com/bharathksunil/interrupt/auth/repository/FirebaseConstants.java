package com.bharathksunil.interrupt.auth.repository;

/**
 * This stores all the firebase related constants
 */

public interface FirebaseConstants {
    /**
     * The Primary Branches of the NoSql database
     */
    String USERS_TREE = "Users", USERS_ACCESS_TREE = "UserAccess", ADMINISTRATORS_TREE = "Administrators",
            EVENT_REGISTRATIONS_TREE = "EventRegistrations", FEEDBACK_TREE="Feedback", SCHEDULES_TREE="Schedules";
    /**
     * The Users Info Trees
     */
    String TEAM_CORE_TREE = "TeamCore", TEAM_EVENTS_TREE="TeamEvents", TEAM_OFF_STAGE="TeamOffStage",
            TEAM_DESIGN_TREE="TeamDesign";


    String COLLECTIONS_CATEGORIES = "Categories", COLLECTIONS_EVENTS="Events", COLLECTION_REGISTRATIONS="Registrations";
    String PROFILE_STORE = "/Profiles/";
}
