package com.bharathksunil.interrupt.auth.repository;

/**
 * This stores all the firebase related constants
 */

public interface FirebaseConstants {
    /**
     * The Primary Branches of the NoSql database
     */
    String USERS_TREE = "Users", USERS_ACCESS_TREE = "UserAccess", ADMINISTRATORS_TREE = "Administrators",
            ORGANISERS_TREE = "Organisers", EVENT_REGISTRATIONS_TREE = "EventRegistrations";
    String COLLECTIONS_CATEGORIES = "Categories", COLLECTIONS_EVENTS="Events", COLLECTION_REGISTRATIONS="Registrations";
    String PROFILE_STORE = "/Profiles/";
}
