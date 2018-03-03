package com.bharathksunil.interrupt.events.repository;

import android.support.annotation.NonNull;

import com.bharathksunil.interrupt.FirebaseConstants;
import com.bharathksunil.interrupt.events.presenter.SchedulesPresenter;
import com.bharathksunil.interrupt.events.model.Schedules;
import com.bharathksunil.interrupt.util.Debug;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * This Downloads the Firebase Schedules
 *
 * @author Bharath on 24-02-2018.
 */

public class FirebaseSchedulesRepository implements SchedulesPresenter.Repository {
    @Override
    public void getEventSchedules(@NonNull final DataLoadedCallback callback) {
        final List<Schedules> schedulesList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference(FirebaseConstants.SCHEDULES_TREE);
        reference.keepSynced(true);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.exists()) {
                            Schedules schedule = snapshot.getValue(Schedules.class);
                            schedulesList.add(schedule);
                        }
                    }
                }
                callback.onDataLoadSuccessful(schedulesList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Debug.i(FirebaseSchedulesRepository.class.getName() + " getEventsData(): " + databaseError.getMessage());
                databaseError.toException().printStackTrace();
                callback.onDataLoadFailed();
            }
        });

    }
}
