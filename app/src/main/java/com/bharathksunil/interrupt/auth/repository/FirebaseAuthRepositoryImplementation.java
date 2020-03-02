package com.bharathksunil.interrupt.auth.repository;

import androidx.annotation.NonNull;

import com.bharathksunil.interrupt.FirebaseConstants;
import com.bharathksunil.interrupt.auth.model.AccessType;
import com.bharathksunil.interrupt.auth.model.UserManager;
import com.bharathksunil.interrupt.auth.model.UserPermissions;
import com.bharathksunil.interrupt.auth.model.UserType;
import com.bharathksunil.interrupt.auth.presenter.AuthPresenter;
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

import java.util.HashMap;
import java.util.Map;

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
            Debug.e(FirebaseAuthRepositoryImplementation.this.getClass().getName() + " getUserData(): user isn't signed in");
            dataLoadedCallback.onDataLoadFailed();
        }
    }

    /**
     * Downloads the users Access data from the repository
     *
     * @param dataLoadedCallback callback to receive data asynchronously
     */
    @Override
    public void getUserAccessData(final DataLoadedCallback dataLoadedCallback) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            //get a reference to the users tree
            DatabaseReference userAccessReference = FirebaseDatabase.getInstance()
                    .getReference(FirebaseConstants.USER_ACCESS_TREE)
                    .child(TextUtils.getEmailAsFirebaseKey(UserManager.getInstance().getUsersEmailID()));
            //read the value
            userAccessReference.keepSynced(true);
            userAccessReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Debug.i("UserAccess: "+dataSnapshot);
                    dataLoadedCallback.onDataLoaded(dataSnapshot);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    dataLoadedCallback.onDataLoadFailed();
                    Debug.e(FirebaseAuthRepositoryImplementation.class.getName() + databaseError.getMessage());
                }
            });
        } else {
            Debug.e(FirebaseAuthRepositoryImplementation.this.getClass().getName() + " getUserAccessData(): user isn't signed in");
            dataLoadedCallback.onDataLoadFailed();
        }
    }

    @Override
    public void getUserPermissionsData(final DataLoadedCallback dataLoadedCallback) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            //get a reference to the users tree
            DatabaseReference userAccessReference = FirebaseDatabase.getInstance()
                    .getReference(FirebaseConstants.USERS_PERMISSIONS_TREE)
                    .child(TextUtils.getEmailAsFirebaseKey(UserManager.getInstance().getUsersEmailID()));
            //read the value
            userAccessReference.keepSynced(true);
            userAccessReference.addListenerForSingleValueEvent(new ValueEventListener() {
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
            Debug.e(FirebaseAuthRepositoryImplementation.this.getClass().getName() + " getUserPermissionData(): user isn't signed in");
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
        final AccessType accessType = new AccessType();
        Map<String, String> accessMap = new HashMap<>();
        accessMap.put("primary", UserType.PARTICIPANT.name());
        accessType.setAccessTypes(accessMap);

        //noinspection ConstantConditions
        DatabaseReference userAccessReference = FirebaseDatabase.getInstance()
                .getReference(FirebaseConstants.USER_ACCESS_TREE)
                .child(TextUtils.getEmailAsFirebaseKey(UserManager.getInstance().getUsersEmailID()));
        userAccessReference.setValue(accessType)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        UserManager.getInstance().loadUserAccessFromRepositoryInstance(accessType);
                        dataLoadedCallback.onDataLoaded(null);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dataLoadedCallback.onDataLoadFailed();
                        Debug.e(FirebaseAuthRepositoryImplementation.class.getName() + " setUserAsParticipant(): "
                                + e.getMessage());
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void setUserPermissionsToDefault(final DataLoadedCallback dataLoadedCallback) {
        final UserPermissions permissions = new UserPermissions();
        permissions.setEnabled(true);
        //noinspection ConstantConditions
        DatabaseReference userAccessReference = FirebaseDatabase.getInstance()
                .getReference(FirebaseConstants.USERS_PERMISSIONS_TREE)
                .child(TextUtils.getEmailAsFirebaseKey(UserManager.getInstance().getUsersEmailID()));
        userAccessReference.setValue(permissions)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        UserManager.getInstance().loadUserPermissionsFromRepositoryInstance(permissions);
                        dataLoadedCallback.onDataLoaded(null);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dataLoadedCallback.onDataLoadFailed();
                        Debug.e(FirebaseAuthRepositoryImplementation.class.getName() + " setUserPermissionsToDefault():" + e.getMessage());
                        e.printStackTrace();
                    }
                });
    }
}
