package com.bharathksunil.interrupt.events.repository;

import android.support.annotation.NonNull;

import com.bharathksunil.interrupt.FirebaseConstants;
import com.bharathksunil.interrupt.events.model.Events;
import com.bharathksunil.interrupt.events.presenter.EventsViewerPresenter;
import com.bharathksunil.interrupt.util.Debug;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * This Repository loads all the Events under a Category
 * @author Bharath on 20-02-2018.
 */

public class FirebaseEventsRepositoryImplementation implements EventsViewerPresenter.Repository {
    @Override
    public void loadEventsDataForCategoryGivenByID(final String id, @NonNull final DataLoadedCallback callback) {
        FirebaseFirestore.getInstance()
                .collection(FirebaseConstants.COLLECTIONS_CATEGORIES)
                .document(id)
                .collection(FirebaseConstants.COLLECTIONS_EVENTS)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                List<Events> eventsList = new ArrayList<>();
                if (documentSnapshots.isEmpty()) {
                    callback.onDataLoadedSuccessful(eventsList);
                    return;
                }
                for (DocumentSnapshot snapshot : documentSnapshots.getDocuments()) {
                    if (snapshot.exists()) {
                        Events events = snapshot.toObject(Events.class);
                        events.setId(id);
                        eventsList.add(events);
                        Debug.i("Events: " + events.getName() + "Event ID: " + events.getId());
                    }
                }
                callback.onDataLoadedSuccessful(eventsList);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Debug.i(FirebaseEventCategoriesRepositoryImplementation.class.getName()
                                + "downloadEvents():" + e.getMessage());
                        callback.onDataLoadFailed();
                        e.printStackTrace();
                    }
                });
    }
}
