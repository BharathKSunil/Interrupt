package com.bharathksunil.interrupt.auth.repository;

import androidx.annotation.NonNull;

import com.bharathksunil.interrupt.auth.presenter.SignInPresenter;
import com.bharathksunil.interrupt.util.Debug;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

/**
 * This lets the user to signIn to firebase by implementing the SignInPresenter.Repository
 */

public class FirebaseSignInRepositoryImplementation implements SignInPresenter.Repository {
    /**
     * This method sign in the user with the email and password
     *
     * @param email           the email id of the user
     * @param password        the password of the user
     * @param signInCallbacks callbacks to the presenter
     */
    @Override
    public void signInWithEmailAndPassword(@NonNull String email, @NonNull String password,
                                           @NonNull final SignInCallbacks signInCallbacks) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            signInCallbacks.isAlreadySignedIn();
            return;
        }
        email = email.toLowerCase();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        signInCallbacks.onSignInSuccessful();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof FirebaseAuthInvalidUserException)
                            signInCallbacks.onEmailIncorrect();
                        else if (e instanceof FirebaseAuthInvalidCredentialsException)
                            signInCallbacks.onPasswordIncorrect();
                        else {
                            Debug.e(FirebaseSignInRepositoryImplementation.class.getName() + " signInWithEmailAndPassword");
                            e.printStackTrace();
                            signInCallbacks.onRepositoryException();
                        }
                    }
                });
    }

    /**
     * This method performs the sign out of the user
     */
    @Override
    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    /**
     * Sends the password reset link
     *
     * @param email the email of the user whom the email must be sent to
     */
    @Override
    public void sendPasswordResetEmail(@NonNull String email, @NonNull final PasswordResetTaskCallback callback) {
        email = email.toLowerCase();
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onPasswordResetMailSent();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Debug.e(FirebaseSignInRepositoryImplementation.class.getName()
                                + " sendPasswordResetEmail:" + e.getMessage());
                        e.printStackTrace();

                        if (e instanceof FirebaseAuthEmailException)
                            callback.emailDoesNotExists();
                        else
                            callback.onProcessEnded();
                    }
                });
    }


}
