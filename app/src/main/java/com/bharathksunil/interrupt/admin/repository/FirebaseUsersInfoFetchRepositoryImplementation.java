package com.bharathksunil.interrupt.admin.repository;

import com.bharathksunil.interrupt.admin.model.Users;
import com.bharathksunil.interrupt.admin.presenter.UsersInfoRecyclerPresenter;
import com.bharathksunil.interrupt.auth.repository.FirebaseConstants;
import com.bharathksunil.interrupt.util.Debug;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * This connects to the firebase database and fetched the user information stored for
 * Administrator, Organisers, Coordinators and all the users
 *
 * @author Bharath on 19-02-2018.
 */

public class FirebaseUsersInfoFetchRepositoryImplementation implements UsersInfoRecyclerPresenter.Repository {
    @Override
    public void loadAllUsersInfo(final DataLoadedCallback callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(FirebaseConstants.USERS_TREE);
        final List<Users> data = new ArrayList<>();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.exists()) {
                            Users user = snapshot.getValue(Users.class);
                            data.add(user);
                        }
                    }
                }
                callback.onDataLoadedSuccessfully(data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Debug.i(FirebaseUsersInfoFetchRepositoryImplementation.class.getName() +
                        ": loadAllUsersInfo():" + databaseError.getMessage());
                databaseError.toException().printStackTrace();
                callback.onDataLoadFailed();
            }
        });
    }

    @Override
    public void loadAllOrganisersInfo(final DataLoadedCallback callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(FirebaseConstants.ORGANISERS_TREE);
        final List<Users> data = new ArrayList<>();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.exists()) {
                            Users user = snapshot.getValue(Users.class);
                            data.add(user);
                        }
                    }
                }
                callback.onDataLoadedSuccessfully(data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Debug.i(FirebaseUsersInfoFetchRepositoryImplementation.class.getName() +
                        ": loadAllUsersInfo():" + databaseError.getMessage());
                databaseError.toException().printStackTrace();
                callback.onDataLoadFailed();
            }
        });
    }

    @Override
    public void loadAllAdministratorsInfo(final DataLoadedCallback callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(FirebaseConstants.ADMINISTRATORS_TREE);
        final List<Users> data = new ArrayList<>();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.exists()) {
                            Users user = snapshot.getValue(Users.class);
                            data.add(user);
                        }
                    }
                }
                callback.onDataLoadedSuccessfully(data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Debug.i(FirebaseUsersInfoFetchRepositoryImplementation.class.getName() +
                        ": loadAllUsersInfo():" + databaseError.getMessage());
                databaseError.toException().printStackTrace();
                callback.onDataLoadFailed();
            }
        });
    }
}
