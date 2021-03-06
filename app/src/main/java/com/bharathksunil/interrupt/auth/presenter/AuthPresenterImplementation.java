package com.bharathksunil.interrupt.auth.presenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bharathksunil.interrupt.auth.model.AccessType;
import com.bharathksunil.interrupt.auth.model.User;
import com.bharathksunil.interrupt.auth.model.UserPermissions;
import com.bharathksunil.interrupt.auth.model.UserManager;
import com.google.firebase.database.DataSnapshot;

/**
 * This is the implementation of the {@link AuthPresenter} for the loading sequence.
 *
 * @author Bharath Kumar S
 */
//: Write Test Cases
public class AuthPresenterImplementation implements AuthPresenter {
    @Nullable
    private View viewInstance;
    private Repository repositoryInstance;
    private UserManager userManager;

    public AuthPresenterImplementation(@NonNull Repository repositoryInstance) {
        this.repositoryInstance = repositoryInstance;
    }

    /**
     * To be called when the app starts.
     * Implement the startup sequence and authentication.
     *
     * @param view the AuthPresenter.RowView instance
     */
    @Override
    public void appStarted(@Nullable View view) {
        this.viewInstance = view;
        if (viewInstance == null)
            return;

        viewInstance.onProcessStarted();
        userManager = UserManager.getInstance();
        //if the user is signed in, load the user information
        if (repositoryInstance.isSignedIn()) {
            userManager.userSignedIn();
            loadTheUserDataFromRepositoryToSingleton();
        }
        //if the user isn't signed in then load teh sign in fragment and ask user to sign in
        else {
            userManager.userSignedOut();
            viewInstance.onProcessEnded();
            viewInstance.loadSignInFragmentToSignInUser();
        }
    }

    /**
     * This method connects to the repository and downloads the user information.
     * the information is then loaded onto the singleton instance of {@link UserManager}.
     */
    private void loadTheUserDataFromRepositoryToSingleton() {
        repositoryInstance.getUserData(new Repository.DataLoadedCallback() {
            @Override
            public void onDataLoaded(DataSnapshot snapshot) {
                if (viewInstance == null)
                    return;
                if (snapshot.exists()) {
                    //if the user data exists then load the data and also fetch the access data from the repository
                    User user = snapshot.getValue(User.class);
                    userManager.loadUserFromRepositoryInstance(user);
                    loadTheUserPermissionsFromRepositoryToSingleton();
                }
                //if there is no user data ask the user to enter the user info
                else {
                    viewInstance.onProcessEnded();
                    viewInstance.loadSignUpFragmentToRegisterUser();
                }
            }

            @Override
            public void onDataLoadFailed() {
                if (viewInstance != null) {
                    viewInstance.onProcessEnded();
                    viewInstance.onUnexpectedError();
                }
            }
        });
    }

    private void loadTheUserPermissionsFromRepositoryToSingleton() {
        repositoryInstance.getUserPermissionsData(new Repository.DataLoadedCallback() {
            @Override
            public void onDataLoaded(DataSnapshot snapshot) {
                if (viewInstance == null)
                    return;
                if (snapshot.exists()) {
                    UserPermissions userPermissions = snapshot.getValue(UserPermissions.class);
                    userManager.loadUserPermissionsFromRepositoryInstance(userPermissions);
                    loadTheUserAccessDataFromTheRepositoryToSingleton();
                } else {
                    setTheUsersPermissionsAsDefault();
                }
            }

            @Override
            public void onDataLoadFailed() {
                if (viewInstance == null)
                    return;
                viewInstance.onProcessEnded();
                viewInstance.onUnexpectedError();
            }
        });
    }

    /**
     * This method connects to the repository and downloads the user access information
     * the information is then loaded into the singleton instance of {@link UserManager}.
     * If the User access isn't available, it means that the user is a participant and has not been
     * allotted any special privileges.
     */
    private void loadTheUserAccessDataFromTheRepositoryToSingleton() {
        repositoryInstance.getUserAccessData(new Repository.DataLoadedCallback() {
            @Override
            public void onDataLoaded(DataSnapshot snapshot) {
                if (viewInstance == null)
                    return;
                if (snapshot.exists()) {
                    AccessType accessType = snapshot.getValue(AccessType.class);
                    //Check if the user is allowed to use the app, if not load the fragment
                    userManager.loadUserAccessFromRepositoryInstance(accessType);
                    viewInstance.onProcessEnded();
                    if (userManager.isUserAuthorisedToUseApp())
                        viewInstance.loadDashboard();
                    else
                        viewInstance.loadUserNotAllowedToUserAppFragment();
                } else {
                    setTheUserAsAParticipant();
                }
            }

            @Override
            public void onDataLoadFailed() {
                if (viewInstance != null) {
                    viewInstance.onProcessEnded();
                    viewInstance.onUnexpectedError();
                }
            }
        });
    }


    /**
     * This method is called when the {@link UserPermissions} tree in the repository is null
     * Basically the user has no special access assigned.
     */
    private void setTheUserAsAParticipant() {
        repositoryInstance.setUserAsParticipant(new Repository.DataLoadedCallback() {
            @Override
            public void onDataLoaded(DataSnapshot snapshot) {
                if (viewInstance != null) {
                    viewInstance.onProcessEnded();
                    viewInstance.loadDashboard();
                }
            }

            @Override
            public void onDataLoadFailed() {
                if (viewInstance != null) {
                    viewInstance.onProcessEnded();
                    viewInstance.onUnexpectedError();
                }
            }
        });
    }

    private void setTheUsersPermissionsAsDefault() {
        repositoryInstance.setUserPermissionsToDefault(new Repository.DataLoadedCallback() {
            @Override
            public void onDataLoaded(DataSnapshot snapshot) {
                loadTheUserAccessDataFromTheRepositoryToSingleton();
            }

            @Override
            public void onDataLoadFailed() {
                if (viewInstance == null)
                    return;
                viewInstance.onUnexpectedError();
                viewInstance.onProcessEnded();
            }
        });
    }
}
