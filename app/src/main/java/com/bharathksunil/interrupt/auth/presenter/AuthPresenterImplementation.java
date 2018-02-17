package com.bharathksunil.interrupt.auth.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.interrupt.util.TextUtils;
import com.google.firebase.database.DataSnapshot;

import com.bharathksunil.interrupt.auth.model.UserManager;
import com.bharathksunil.interrupt.auth.repository.User;
import com.bharathksunil.interrupt.auth.repository.UserAccess;

/**
 * This is the implementation of the AuthPresenter
 *
 * @author Bharath Kumar S
 */
//todo: Write Test Cases
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
     * @param view the AuthPresenter.View instance
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
                User user = snapshot.getValue(User.class);
                //if there is no user data ask the user to enter the user info
                if ((user == null || TextUtils.isEmpty(user.getName())) && viewInstance != null) {
                    viewInstance.onProcessEnded();
                    viewInstance.loadSignUpFragmentToRegisterUser();
                }
                //if the user data exists then load the data and also fetch the access data from the repository
                else {
                    userManager.loadUserFromRepositoryInstance(user);
                    loadTheUserAccessDataFromTheRepositoryToSingleton();
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
     * This method connects to the repository and downloads the user access information
     * the information is then loaded into the singleton instance of {@link UserManager}.
     * If the User access isn't available, it means that the user is a participant and has not been
     * allotted any special privileges.
     */
    private void loadTheUserAccessDataFromTheRepositoryToSingleton() {
        repositoryInstance.getAccessData(new Repository.DataLoadedCallback() {
            @Override
            public void onDataLoaded(DataSnapshot snapshot) {
                UserAccess userAccess = snapshot.getValue(UserAccess.class);
                //if the user access is null or not present then load the user as a participant
                if (userAccess == null || TextUtils.isEmpty(userAccess.getAccessType())) {
                    setTheUserAsAParticipant();
                }
                //Check if the user is allowed to use the app, if not load the fragment
                else if (viewInstance != null) {
                    userManager.loadUserAccessFromRepositoryInstance(userAccess);
                    viewInstance.onProcessEnded();
                    if (userManager.isUserAuthorisedToUseApp())
                        viewInstance.loadDashboard();
                    else
                        viewInstance.loadUserNotAllowedToUserAppFragment();
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
     * This method is called when the {@link UserAccess} tree in the repository is null
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
}
