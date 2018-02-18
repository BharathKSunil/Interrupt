package com.bharathksunil.interrupt.auth.model;

import com.bharathksunil.interrupt.auth.repository.UserAccess;
import com.bharathksunil.interrupt.util.TextUtils;
import com.bharathksunil.interrupt.auth.repository.User;

/**
 * This is a singleton which will help store the user information to be available throughout the app.
 * It also helps in access management for various users
 *
 * @author Bharath Kumar S
 */

public class UserManager {

    private UserType userType;
    private boolean signedIn;
    private UserAccess access;
    private User user;
    private static UserManager instance = new UserManager();

    private UserManager() {
        access = new UserAccess();
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
     * Get the type of the user
     *
     * @return the type of user
     */
    public UserType getUserType() {
        return instance.userType;
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
     * This method loads the user Accesses instance to the singleton from the UserAccess object.
     *
     * @param userAccess passed by the repository
     */
    public void loadUserAccessFromRepositoryInstance(UserAccess userAccess) {
        checkIntegrity(userAccess);

        if (TextUtils.areEqual(userAccess.getAccessType(),
                UserType.CR.name()))
            instance.userType = UserType.CR;
        else if (TextUtils.areEqual(userAccess.getAccessType(),
                UserType.ADMINISTRATOR.name()))
            instance.userType = UserType.ADMINISTRATOR;
        else if (TextUtils.areEqual(userAccess.getAccessType(),
                UserType.COORDINATOR.name()))
            instance.userType = UserType.COORDINATOR;
        else if (TextUtils.areEqual(userAccess.getAccessType(),
                UserType.ORGANISER.name()))
            instance.userType = UserType.ORGANISER;
        else
            instance.userType = UserType.PARTICIPANT;

        instance.access = userAccess;

    }

    //======================Get User Information==================================//
    public String getUsersName() {
        return instance.user.getName();
    }

    public String getUsersDesignation() {
        return instance.access.getAccessType();
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
        //todo: FUTURE CHANGES: Change this to instance.user.getCollegeName()
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


    /**
     * Check if the admin has restricted access to the user to use th app
     *
     * @return true, when the user has the access
     */
    public boolean isUserAuthorisedToUseApp() {
        return instance.access.isEnabled();
    }

    /**
     * Check if the user is a Class Representative
     *
     * @return true, if the user is a class representative
     */
    public boolean isUserAClassRepresentative() {
        checkIntegrity(instance.access.getAccessType()); //all users must have an access type

        String[] types = instance.access.getAccessType().split("\\|");
        for (String type : types) {
            if (type.equals(UserType.CR.name()))
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
        checkIntegrity(instance.access.getAccessType()); //all users must have an access type

        String[] types = instance.access.getAccessType().split("\\|");
        for (String type : types) {
            if (type.equals(UserType.COORDINATOR.name()))
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
        checkIntegrity(instance.access.getAccessType()); //all users must have an access type

        String[] types = instance.access.getAccessType().split("\\|");
        for (String type : types) {
            if (type.equals(UserType.ORGANISER.name()))
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
        checkIntegrity(instance.access.getAccessType()); //all users must have an access type

        String[] types = instance.access.getAccessType().split("\\|");
        for (String type : types) {
            if (type.equals(UserType.ADMINISTRATOR.name()))
                return true;
        }
        return false;
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
