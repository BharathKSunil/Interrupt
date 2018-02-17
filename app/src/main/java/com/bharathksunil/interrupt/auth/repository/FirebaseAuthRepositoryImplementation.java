package com.bharathksunil.interrupt.auth.repository;

import android.support.annotation.NonNull;

import com.bharathksunil.interrupt.auth.model.UserType;
import com.bharathksunil.interrupt.util.Debug;
import com.bharathksunil.interrupt.util.TextUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.bharathksunil.interrupt.auth.model.UserManager;
import com.bharathksunil.interrupt.auth.presenter.AuthPresenter;

/**
 * This connects to the firebase repository to implement the AuthPresenter.Repository
 */

public class FirebaseAuthRepositoryImplementation implements AuthPresenter.Repository {
    /**
     * Checks if the user is signed in or not
     *
     * @return true, if the user is signed in
     */
    @Override
    public boolean isSignedIn() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    /**
     * Downloads the user data from the repository
     *
     * @param dataLoadedCallback callback to receive data asynchronously
     */
    @Override
    public void getUserData(final DataLoadedCallback dataLoadedCallback) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            //get a reference to the users tree
            DatabaseReference userReference = FirebaseDatabase.getInstance()
                    .getReference(FirebaseConstants.USERS_TREE).child(user.getUid());
            //read the value
            userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    dataLoadedCallback.onDataLoaded(dataSnapshot);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    dataLoadedCallback.onDataLoadFailed();
                    Debug.e(FirebaseAuthRepositoryImplementation.class.getName() + databaseError.getMessage());
                }
            });
        } else {
            Debug.e(FirebaseAuthRepositoryImplementation.this.getClass().getName()+" getUserData(): user isn't signed in");
            dataLoadedCallback.onDataLoadFailed();
        }
    }

    /**
     * Downloads the users Access data from the repository
     *
     * @param dataLoadedCallback callback to receive data asynchronously
     */
    @Override
    public void getAccessData(final DataLoadedCallback dataLoadedCallback) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            //get a reference to the users tree
            DatabaseReference userReference = FirebaseDatabase.getInstance()
                    .getReference(FirebaseConstants.USERS_ACCESS_TREE)
                    .child(TextUtils.getEmailAsFirebaseKey(UserManager.getInstance().getUsersEmailID()));
            //read the value
            userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    dataLoadedCallback.onDataLoaded(dataSnapshot);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    dataLoadedCallback.onDataLoadFailed();
                    Debug.e(FirebaseAuthRepositoryImplementation.class.getName() + databaseError.getMessage());
                }
            });
        } else {
            Debug.e(FirebaseAuthRepositoryImplementation.this.getClass().getName()+" getAccessData(): user isn't signed in");
            dataLoadedCallback.onDataLoadFailed();
        }
    }

    /**
     * This method is used to set the user access for a participant user
     *
     * @param dataLoadedCallback callback to interact with the presenter
     */
    @Override
    public void setUserAsParticipant(final DataLoadedCallback dataLoadedCallback) {
        UserAccess userAccess = new UserAccess();
        userAccess.setEnabled(true);
        userAccess.setAccessType(UserType.PARTICIPANT.name());

        //noinspection ConstantConditions
        DatabaseReference userAccessReference = FirebaseDatabase.getInstance()
                .getReference(FirebaseConstants.USERS_ACCESS_TREE)
                .child(TextUtils.getEmailAsFirebaseKey(UserManager.getInstance().getUsersEmailID()));
        userAccessReference.setValue(userAccess)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataLoadedCallback.onDataLoaded(null);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dataLoadedCallback.onDataLoadFailed();
                    }
                });
    }
}
