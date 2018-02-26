package com.bharathksunil.interrupt.admin.repository;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.bharathksunil.interrupt.FirebaseConstants;
import com.bharathksunil.interrupt.admin.model.Users;
import com.bharathksunil.interrupt.admin.presenter.NewOrganiserPresenter;
import com.bharathksunil.interrupt.auth.model.UserPermissions;
import com.bharathksunil.interrupt.auth.model.UserType;
import com.bharathksunil.interrupt.util.Debug;
import com.bharathksunil.interrupt.util.TextUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * This repository adds a new Organiser
 *
 * @author Bharath on 26-02-2018.
 */

public class FirebaseNewOrganiserRepository implements NewOrganiserPresenter.Repository {
    @Override
    public void uploadUserImage(Uri uri, @NonNull final OnProfileUploadedCallback callback) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Debug.e(FirebaseNewOrganiserRepository.class.getName() +
                    "uploadProfilePicture(): User Not Signed In");
            callback.onProfileUploadFailed();
            return;
        }
        StorageReference reference = FirebaseStorage.getInstance()
                .getReference().child(FirebaseConstants.USERS_STORE +
                        user.getUid() + ".jpg");
        UploadTask uploadTask = reference.putFile(uri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //noinspection ConstantConditions
                callback.onProfileUploadedSuccessfully(taskSnapshot.getDownloadUrl().toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Debug.e(FirebaseNewOrganiserRepository.class.getName() +
                        "uploadProfilePicture()" + e.getLocalizedMessage());
                e.printStackTrace();
                callback.onProfileUploadFailed();
            }
        });
    }

    @Override
    public void addOrganiser(Users users, UserPermissions userPermissions, final OnAddedCallback callback) {
        storeUserDataInUsersTree(users, userPermissions, callback);
    }

    private void storeUserDataInUsersTree(final Users users, final UserPermissions userPermissions, final OnAddedCallback callback) {
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
        else {
            Debug.i("ERROR: Wrong User: " + users.getDesignation());
            callback.onAddFailed();
            return;
        }

        reference.child(TextUtils.getEmailAsFirebaseKey(users.getEmail())).setValue(users)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        giveUserUserAccess(users, userPermissions, callback);
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

    private void giveUserUserAccess(final Users users, final UserPermissions userPermissions, final OnAddedCallback callback) {

        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference(FirebaseConstants.USERS_PERMISSIONS_TREE)
                .child(TextUtils.getEmailAsFirebaseKey(users.getEmail()));
        reference.setValue(userPermissions)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onAddSuccessful();
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
}
