package com.bharathksunil.interrupt.auth.repository;

import android.net.Uri;
import androidx.annotation.NonNull;

import com.bharathksunil.interrupt.FirebaseConstants;
import com.bharathksunil.interrupt.auth.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.bharathksunil.interrupt.auth.presenter.SignUpPresenter;
import com.bharathksunil.interrupt.util.Debug;

/**
 * This Connects to the Firebase Repository and performs the User SignUp and
 * implements {@link SignUpPresenter.Repository} interface
 */

public class FirebaseSignUpRepositoryImplementation implements SignUpPresenter.Repository {
    @Override
    public void signUpWithEmailAndPassword(@NonNull String email, @NonNull final String password,
                                           @NonNull final SignUpCallbacks signUpCallbacks) {
        email = email.toLowerCase();
        checkForEmailAvailabilityAndSignUpIfAvailable(email, password, signUpCallbacks);
    }

    private void checkForEmailAvailabilityAndSignUpIfAvailable(final @NonNull String email, final @NonNull String password,
                                                               final @NonNull SignUpCallbacks signUpCallbacks) {
        FirebaseAuth.getInstance().fetchSignInMethodsForEmail(email)
                .addOnSuccessListener(providerQueryResult -> {
                    if (providerQueryResult.getSignInMethods() != null) {
                        if (providerQueryResult.getSignInMethods().size() == 1) {
                            //email already in use
                            signUpCallbacks.onUserAlreadySignedUp();
                        } else {
                            //email is available
                            proceedWithUserSignUp(email, password, signUpCallbacks);
                        }
                    } else {
                        //email is available
                        proceedWithUserSignUp(email, password, signUpCallbacks);
                    }
                })
                .addOnFailureListener(TaskExecutors.MAIN_THREAD, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        signUpCallbacks.onUserAlreadySignedUp();
                        Debug.e(FirebaseSignInRepositoryImplementation.class.getName() +
                                "checkEmailAvailability():" + e.getLocalizedMessage());
                        e.printStackTrace();
                    }
                });
    }

    private void proceedWithUserSignUp(@NonNull String email, @NonNull String password,
                                       final @NonNull SignUpCallbacks signUpCallbacks) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        signUpCallbacks.onUserSignedUpSuccessfully();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        Debug.e(FirebaseSignInRepositoryImplementation.class.getName() +
                                "proceedWithUserSignUp():" + e.getLocalizedMessage());
                        if (e instanceof FirebaseAuthUserCollisionException) {
                            signUpCallbacks.onUserAlreadySignedUp();
                        } else
                            signUpCallbacks.onUserSignUpFailed();
                    }
                });
    }

    @Override
    public void uploadProfilePicture(@NonNull Uri profilePath,
                                     final @NonNull ProfileUploadCallback callback) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Debug.e(FirebaseSignInRepositoryImplementation.class.getName() +
                    "uploadProfilePicture(): User Not Signed In");
            callback.onProfileUploadFailed();
            return;
        }
        StorageReference reference = FirebaseStorage.getInstance()
                .getReference().child(FirebaseConstants.PROFILE_STORE +
                        user.getUid() + ".jpg");
        UploadTask uploadTask = reference.putFile(profilePath);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            reference.getDownloadUrl().addOnSuccessListener( uri-> {
                callback.onProfileUploaded(uri.toString());
            });
        }).addOnFailureListener(e -> {
            Debug.e(FirebaseSignInRepositoryImplementation.class.getName() +
                    "uploadProfilePicture()" + e.getLocalizedMessage());
            e.printStackTrace();
            callback.onProfileUploadFailed();
        });
    }

    @Override
    public void uploadUserInfo(@NonNull User user, @NonNull final UserUploadCallback callback) {
        FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        if (fuser == null) {
            callback.onUserInfoUploadFailed();
            return;
        }
        DatabaseReference userDataReference = FirebaseDatabase.getInstance()
                .getReference(FirebaseConstants.USERS_TREE).child(fuser.getUid());
        userDataReference.setValue(user)
                .addOnSuccessListener(aVoid -> callback.onUserInfoUploadedSuccessfully())
                .addOnFailureListener(e -> {
                    Debug.e(FirebaseSignInRepositoryImplementation.class.getName() +
                            "uploadUserInfo():" + e.getLocalizedMessage());
                    e.printStackTrace();
                    callback.onUserInfoUploadFailed();
                });
    }
}
