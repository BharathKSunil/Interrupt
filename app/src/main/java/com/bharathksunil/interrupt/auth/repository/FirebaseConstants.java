package com.bharathksunil.interrupt.auth.repository;

/**
 * This stores all the firebase related constants
 */

public interface FirebaseConstants {
    /**
     * The Primary Branches of the NoSql database
     */
    String USERS_TREE = "Users", USERS_ACCESS_TREE = "UserAccess",
            EVENT_REGISTRATIONS_TREE = "EventRegistrations";

    String PROFILE_STORE = "/Profiles/";
}
