package com.bharathksunil.interrupt.admin.repository;

import android.net.Uri;
import androidx.annotation.NonNull;

import com.bharathksunil.interrupt.FirebaseConstants;
import com.bharathksunil.interrupt.admin.model.Users;
import com.bharathksunil.interrupt.admin.presenter.NewOrganiserPresenter;
import com.bharathksunil.interrupt.auth.model.AccessType;
import com.bharathksunil.interrupt.auth.model.UserPermissions;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * This repository adds a new Organiser
 *
 * @author Bharath on 26-02-2018.
 */

public class FirebaseNewOrganiserRepository implements NewOrganiserPresenter.Repository {

    private Users users;
    private AccessType accessType;
    private UserPermissions permissions;

    @Override
    public void uploadUserImage(String email, Uri uri, @NonNull final OnProfileUploadedCallback callback) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Debug.e(FirebaseNewOrganiserRepository.class.getName() +
                    "uploadProfilePicture(): User Not Signed In");
            callback.onProfileUploadFailed();
            return;
        }
        StorageReference reference = FirebaseStorage.getInstance()
                .getReference().child(FirebaseConstants.USERS_STORE +
                        TextUtils.getEmailAsFirebaseKey(email) + ".jpg");
        UploadTask uploadTask = reference.putFile(uri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //noinspection ConstantConditions
                reference.getDownloadUrl().addOnSuccessListener(uri -> {
                    callback.onProfileUploadedSuccessfully(uri.toString());
                });
            }
        }).addOnFailureListener(e -> {
            Debug.e(FirebaseNewOrganiserRepository.class.getName() +
                    "uploadProfilePicture()" + e.getLocalizedMessage());
            e.printStackTrace();
            callback.onProfileUploadFailed();
        });
    }

    @Override
    public void addOrganiser(Users users, UserPermissions userPermissions, final OnAddedCallback callback) {
        this.users = users;
        this.permissions = userPermissions;

        storeUserDataInUsersTree(callback);
    }

    private void storeUserDataInUsersTree(final OnAddedCallback callback) {
        DatabaseReference reference;
        if (TextUtils.areEqual(users.getDesignation(), UserType.CORE_TEAM.name()))
            reference = FirebaseDatabase.getInstance().getReference(FirebaseConstants.TEAM_CORE_TREE);
        else if (TextUtils.areEqual(users.getDesignation(), UserType.CULTURAL_TEAM.name()))
            reference = FirebaseDatabase.getInstance().getReference(FirebaseConstants.TEAM_EVENTS_TREE);
        else if (TextUtils.areEqual(users.getDesignation(), UserType.EVENT_TEAM.name()))
            reference = FirebaseDatabase.getInstance().getReference(FirebaseConstants.TEAM_EVENTS_TREE);
        else if (TextUtils.areEqual(users.getDesignation(), UserType.OFF_STAGE_TEAM.name()))
            reference = FirebaseDatabase.getInstance().getReference(FirebaseConstants.TEAM_OFF_STAGE);
        else if (TextUtils.areEqual(users.getDesignation(), UserType.TECH_TEAM.name()))
            reference = FirebaseDatabase.getInstance().getReference(FirebaseConstants.TEAM_DESIGN_TREE);
        else if (TextUtils.areEqual(users.getDesignation(), UserType.DESIGN_TEAM.name()))
            reference = FirebaseDatabase.getInstance().getReference(FirebaseConstants.TEAM_DESIGN_TREE);
        else if (TextUtils.areEqual(users.getDesignation(), UserType.DIGITAL_MARKETING.name()))
            reference = FirebaseDatabase.getInstance().getReference(FirebaseConstants.TEAM_OFF_STAGE);
        else if (TextUtils.areEqual(users.getDesignation(), UserType.VOLUNTEER_MANAGEMENT.name()))
            reference = FirebaseDatabase.getInstance().getReference(FirebaseConstants.TEAM_OFF_STAGE);
        else {
            Debug.i("ERROR: Wrong User: " + users.getDesignation());
            callback.onAddFailed();
            return;
        }

        reference.child(TextUtils.getEmailAsFirebaseKey(users.getEmail())).setValue(users)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        updateUserPermissions(callback);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Debug.e(FirebaseNewOrganiserRepository.class.getName() + " storeUserDataInUserTree(): "
                                + e.getMessage());
                        e.printStackTrace();
                        callback.onAddFailed();
                    }
                });
    }

    private void getPreviousUserPermissions(final OnAddedCallback callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(FirebaseConstants.USERS_PERMISSIONS_TREE)
                .child(TextUtils.getEmailAsFirebaseKey(users.getEmail()));

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    UserPermissions existingPerm = dataSnapshot.getValue(UserPermissions.class);
                    //todo: merge the two here
                } else {
                    updateUserPermissions(callback);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Debug.e(FirebaseNewOrganiserRepository.class.getName() + " getPreviousUserPermissions(): " + databaseError.getMessage());
                databaseError.toException().printStackTrace();
                callback.onAddFailed();
            }
        });

    }

    private void updateUserPermissions(final OnAddedCallback callback) {

        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference(FirebaseConstants.USERS_PERMISSIONS_TREE)
                .child(TextUtils.getEmailAsFirebaseKey(users.getEmail()));
        reference.setValue(permissions)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        giveUserUserAccess(callback);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Debug.e(FirebaseNewOrganiserRepository.class.getName() + " giveUserAccess(): " + e.getMessage());
                        e.printStackTrace();
                        callback.onAddFailed();
                    }
                });
    }

    private void giveUserUserAccess(final OnAddedCallback callback) {

        FirebaseDatabase.getInstance().getReference(FirebaseConstants.USER_ACCESS_TREE)
                .child(TextUtils.getEmailAsFirebaseKey(users.getEmail()))
                .child("accessTypes").child("secondary").setValue(users.getDesignation())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onAddSuccessful();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Debug.e(FirebaseNewOrganiserRepository.class.getName() + " giveUserUserAccess(): " + e.getMessage());
                        e.printStackTrace();
                        callback.onAddFailed();
                    }
                });
    }
}
