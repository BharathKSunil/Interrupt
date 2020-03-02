package com.bharathksunil.interrupt.auth.model;

import androidx.annotation.Keep;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Map;

/**
 * Created by Bharath on 26-02-2018.
 */
@Keep
@IgnoreExtraProperties
public final class AccessType {
    /**
     * The type of the user, can be:
     * ADMINISTRATOR, PARTICIPANT, CR, COORDINATOR, CORE_TEAM,
     */
    private Map<String, String> AccessTypes;

    public AccessType() {
    }

    public Map<String, String> getAccessTypes() {
        return AccessTypes;
    }

    public void setAccessTypes(Map<String, String> accessTypes) {
        AccessTypes = accessTypes;
    }
}
