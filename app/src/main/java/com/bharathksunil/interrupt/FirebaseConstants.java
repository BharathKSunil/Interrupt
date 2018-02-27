package com.bharathksunil.interrupt;

import android.support.annotation.Keep;

/**
 * This stores all the firebase related constants
 */
@Keep
public interface FirebaseConstants {
    /**
     * The Primary Branches of the NoSql database
     */
    String USERS_TREE = "Users", USERS_PERMISSIONS_TREE = "UserPermissions", ADMINISTRATORS_TREE = "Administrators",
    USER_ACCESS_TREE="UserAccess", EVENT_REGISTRATIONS_TREE = "EventRegistrations", FEEDBACK_TREE="Feedback",
            SCHEDULES_TREE="Schedules";
    /**
     * The Users Info Trees
     */
    String TEAM_CORE_TREE = "TeamCore", TEAM_EVENTS_TREE="TeamEvents", TEAM_OFF_STAGE="TeamOffStage",
            TEAM_DESIGN_TREE="TeamDesign";


    String COLLECTIONS_CATEGORIES = "Categories", COLLECTIONS_EVENTS="Events", COLLECTION_REGISTRATIONS="Registrations";
    String PROFILE_STORE = "/Profiles/", USERS_STORE="/Users/";
}
