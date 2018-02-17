package com.bharathksunil.interrupt.auth.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.interrupt.BaseView;

/**
 * This interface is to be used for signIn to the app by the view
 * the view must implement the
 *
 * @author Bharath on 26-01-2018.
 */

public interface SignInPresenter {

    /**
     * Implement this interface on the view (activity or the fragment) for interaction between the
     * presenter and the view. This abstracts the presenter to the view
     */
    interface View extends BaseView {
        /**
         * This method is called by the presenter when it needs to email address
         *
         * @return the emailId from the text field
         */
        String getEmailField();

        /**
         * This method is called when the presenter needs the password
         *
         * @return the password as, entered by the user
         */
        String getPasswordField();

        /**
         * This method is called when there is an error on the EmailId Field passed
         *
         * @param errorType the type of error indicating what kind of error was found in the field
         */
        void onEmailError(@NonNull FormErrorType errorType);

        /**
         * This method is called when there is an error on the Password Field passed
         *
         * @param errorType the type of error indicating what kind of error was found in the field
         */
        void onPasswordError(@NonNull FormErrorType errorType);

        /**
         * This method is called when the user was successfully signed in
         */
        void onUserSignedIn();

        /**
         * This method is called when the user is already signed in and is trying to sign in again
         */
        void onUserAlreadySignedIn();
    }

    /**
     * Implement this repository to perform various signIn related operations on the repository(backend)
     * this abstracts the backend to the presenter and offers backend independence.
     * You may implement the backend in any fashion you like: REST, Firebase, SQLite etc..
     *
     * @author Bharath on 26-01-2018.
     */
    interface Repository {
        /**
         * This is the callback interface for the SignIn method to interact with the presenter
         */
        interface SignInCallbacks {
            /**
             * Called when the signIn was Successful
             */
            void onSignInSuccessful();

            /**
             * Called when the Email provided was incorrect
             * i.e., was not registered in the repository
             */
            void onEmailIncorrect();

            /**
             * Called when the password provided was incorrect
             */
            void onPasswordIncorrect();

            /**
             * Called whenever there was an exception in processing the request
             */
            void onRepositoryException();

            /**
             * This is called when the user tries to sign in, but he is already signed in
             */
            void isAlreadySignedIn();
        }

        /**
         * This method sign in the user with the email and password
         *
         * @param email           the email id of the user
         * @param password        the password of the user
         * @param signInCallbacks callbacks to the presenter
         */
        void signInWithEmailAndPassword(@NonNull String email, @NonNull String password, @NonNull final SignInCallbacks signInCallbacks);

        /**
         * This method performs the sign out of the user
         */
        void signOut();
    }

    /**
     * Call this method in the onResume to set the current view
     *
     * @param view the SignInPresenter.View
     */
    void setView(@Nullable View view);

    /**
     * Call this method to perform SignIn
     */
    void onSignInButtonClicked();
}
