package com.bharathksunil.interrupt.auth.model;

import com.bharathksunil.interrupt.util.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This is a singleton which will help store the user information to be available throughout the app.
 * It also helps in permissions management for various users
 *
 * @author Bharath Kumar S
 */

public class UserManager {

    private boolean signedIn;
    private UserPermissions permissions;
    private AccessType accessType;
    private User user;
    private static UserManager instance = new UserManager();

    private UserManager() {
        permissions = new UserPermissions();
    }

    public static UserManager getInstance() {
        return instance;
    }

    /**
     * Check if the user is signed in
     *
     * @return true, if the user is signed in
     */
    public boolean isUserSignedIn() {
        return instance.signedIn;
    }

    /**
     * Set that the user is signed in
     */
    public void userSignedIn() {
        instance.signedIn = true;
    }

    /**
     * Set that the user signed out
     */
    public void userSignedOut() {
        instance.signedIn = false;
    }

    /**
     * This method loads the user instance to the singleton from the User object.
     *
     * @param user passed by the repository
     */
    public void loadUserFromRepositoryInstance(User user) {
        checkIntegrity(user);//it is very crucial that the user information provided in not null
        instance.user = user;
    }

    /**
     * This method loads the user Permissions instance to the singleton from the UserPermissions object.
     *
     * @param userPermissions passed by the repository
     */
    public void loadUserPermissionsFromRepositoryInstance(UserPermissions userPermissions) {
        checkIntegrity(userPermissions);
        instance.permissions = userPermissions;

    }

    /**
     * This method loads the user Accesses instance to the singleton from the AccessType object.
     *
     * @param accessType passed by the repository
     */
    public void loadUserAccessFromRepositoryInstance(AccessType accessType) {
        checkIntegrity(accessType);
        instance.accessType = accessType;

    }

    //======================Get User Information==================================//
    public String getUsersName() {
        return instance.user.getName();
    }

    public String getUsersDesignation() {
        StringBuilder designation = new StringBuilder("| ");
        Map<String, String> access = instance.accessType.getAccessTypes();
        //as The user may be coordinating more than one event
        boolean flagCoordinatorDesignationAlreadyAdded = false;
        for (String string : access.values()) {
            if (!flagCoordinatorDesignationAlreadyAdded)
                designation.append(string).append(" | ");
            if (TextUtils.areEqual(string, UserType.COORDINATOR.name()))
                flagCoordinatorDesignationAlreadyAdded = true;
        }
        return designation.toString();
    }

    public String getUsersEmailID() {
        return instance.user.getEmail();
    }

    public String getUsersPhoneNo() {
        return instance.user.getPhoneNo();
    }

    public String getUsersUSN() {
        return instance.user.getUSN();
    }

    public String getUsersCollege() {
        //: FUTURE CHANGES: Change this to instance.user.getCollegeName()
        /*
        Interrupt 7.0 was a interDepartment Fest, So no college name was accepted
        If it is inter-college use a college field too
         */
        return "Dr. Ambedkar Inst. of Tech.";
    }

    public String getUsersSemester() {
        return instance.user.getSemester();
    }

    public String getUsersSection() {
        return instance.user.getSection();
    }

    public String getUsersDepartment() {
        return instance.user.getDepartment();
    }

    public String getUsersProfileImageUrl() {
        return instance.user.getProfileUrl();
    }

    public List<String> getCoordinatingEventsPathID() {
        List<String> pathIds = new ArrayList<>();
        for (String key : instance.accessType.getAccessTypes().keySet()) {
            if (!TextUtils.areEqual(key, "main") && !TextUtils.areEqual(key, "primary")
                    && !TextUtils.areEqual(key, "secondary"))
                pathIds.add(key);
        }
        return pathIds;
    }


    /**
     * Check if the admin has restricted permissions to the user to use th app
     *
     * @return true, when the user has the permissions
     */
    public boolean isUserAuthorisedToUseApp() {
        return instance.permissions.isEnabled();
    }

    /**
     * Check if the user is a Class Representative
     *
     * @return true, if the user is a class representative
     */
    public boolean isUserAClassRepresentative() {
        checkIntegrity(instance.accessType.getAccessTypes()); //all users must have an permissions type
        for (String type : instance.accessType.getAccessTypes().values()) {
            if (TextUtils.areEqual(type, UserType.CR.name()))
                return true;
        }
        return false;
    }

    /**
     * Check if the user is a event coordinator
     *
     * @return true, if user is a event coordinator
     */
    public boolean isUserAEventsCoordinator() {
        checkIntegrity(instance.accessType.getAccessTypes()); //all users must have an permissions type

        for (String type : instance.accessType.getAccessTypes().values()) {
            if (TextUtils.areEqual(type, UserType.COORDINATOR.name()))
                return true;
        }
        return false;
    }

    /**
     * Check if the user is an organiser
     *
     * @return true, if the user is an organiser
     */
    public boolean isUserAnOrganiser() {
        checkIntegrity(instance.accessType.getAccessTypes()); //all users must have an permissions type

        for (String type : instance.accessType.getAccessTypes().values()) {
            if (TextUtils.areEqual(type, UserType.CORE_TEAM.name())
                    || TextUtils.areEqual(type, UserType.CULTURAL_TEAM.name())
                    || TextUtils.areEqual(type, UserType.OFF_STAGE_TEAM.name())
                    || TextUtils.areEqual(type, UserType.EVENT_TEAM.name())
                    || TextUtils.areEqual(type, UserType.DIGITAL_MARKETING.name())
                    || TextUtils.areEqual(type, UserType.DESIGN_TEAM.name())
                    || TextUtils.areEqual(type, UserType.TECH_TEAM.name())
                    )
                return true;
        }
        return false;
    }

    /**
     * Check if the user is an administrator
     *
     * @return true, if the user is an Administrator
     */
    public boolean isUserAnAdministrator() {
        checkIntegrity(instance.accessType.getAccessTypes()); //all users must have an permissions type

        for (String type : instance.accessType.getAccessTypes().values()) {
            if (TextUtils.areEqual(type, UserType.ADMINISTRATOR.name()))
                return true;
        }
        return false;
    }

    /**
     * This checks if the user[here an Administrator] can view all the user registered Information
     *
     * @return true, if the user can view the data
     */
    public boolean canUserViewAllUserData() {
        return isUserAnAdministrator() && instance.permissions.getCanViewUserData() != null
                && instance.permissions.getCanViewUserData();
    }

    /**
     * This checks if the user[here an Administrator] can view all the Organisers registered Information
     *
     * @return true, if the user can view the data
     */
    public boolean canUserModifyAllOrganisersData() {
        return isUserAnAdministrator() && instance.permissions.getCanModifyOrganiserData() != null
                && instance.permissions.getCanModifyOrganiserData();
    }

    /**
     * This checks if the user[here an Administrator] can view all Feedback
     *
     * @return true, if the user can view the data
     */
    public boolean canUserViewAllFeedback() {
        return isUserAnAdministrator() && instance.permissions.getCanViewFeedbackData() != null
                && instance.permissions.getCanViewFeedbackData();
    }

    /**
     * This method checks if any parameter, viz essential for the functioning of the app is not null
     * call this method before performing any action on such objects
     *
     * @param object the item whose integrity needs to be validated
     */
    private void checkIntegrity(Object object) {
        if (object == null)
            throw new RuntimeException("AppIntegrityException: this user field "
                    + "must not be a null value, kindly check your implementation");
    }
}
