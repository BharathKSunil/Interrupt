package com.bharathksunil.interrupt.events.repository;

import android.support.annotation.NonNull;

import com.bharathksunil.interrupt.FirebaseConstants;
import com.bharathksunil.interrupt.events.model.EventRegistrations;
import com.bharathksunil.interrupt.events.presenter.RegisteredEventsRecyclerPresenter;
import com.bharathksunil.interrupt.util.Debug;
import com.bharathksunil.interrupt.util.TextUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * This repository connects to the firebase repository and gets the Registered event of the email
 *
 * @author Bharath on 18-02-2018.
 */

public class FirebaseRegisteredEventsRepositoryImplementation implements RegisteredEventsRecyclerPresenter.Repository {
    private List<EventRegistrations> data;
    /**
     * This loads the registered events data of the user
     *
     * @param email    the email Id of the user whose events must be fetched
     * @param callback the Callback interface to when the data gets loaded
     */
    @Override
    public void getRegisteredEventsData(@NonNull String email,
                                        @NonNull final DataLoadedCallback callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference(FirebaseConstants.EVENT_REGISTRATIONS_TREE)
                .child(TextUtils.getEmailAsFirebaseKey(email));
        reference.keepSynced(true);
        data = new ArrayList<>();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //get all registrations
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.exists()) {
                            EventRegistrations registrations = snapshot.getValue(EventRegistrations.class);
                            if (registrations == null)
                                break;
                            data.add(registrations);
                        }
                    }
                }
                callback.finishedSuccessfully(data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Debug.e(FirebaseRegisteredEventsRepositoryImplementation.class.getName()
                        + "getRegisteredEventsData(): " + databaseError.getDetails());
                databaseError.toException().printStackTrace();
                callback.failed();
            }
        });

    }
}
