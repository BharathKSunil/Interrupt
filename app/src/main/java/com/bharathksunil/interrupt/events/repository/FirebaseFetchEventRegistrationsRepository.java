package com.bharathksunil.interrupt.events.repository;

import android.support.annotation.NonNull;

import com.bharathksunil.interrupt.FirebaseConstants;
import com.bharathksunil.interrupt.events.model.EventRegistrations;
import com.bharathksunil.interrupt.events.model.EventsManager;
import com.bharathksunil.interrupt.events.presenter.EventsRegistrationsViewerPresenter;
import com.bharathksunil.interrupt.util.Debug;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for downloading the event registrations from firebase
 *
 * @author Bharath on 05-03-2018.
 */

public class FirebaseFetchEventRegistrationsRepository implements EventsRegistrationsViewerPresenter.Repository {
    @Override
    public void loadEventRegistrations(@NonNull final OnDataLoadedCallback callback) {
        CollectionReference reference = FirebaseFirestore.getInstance()
                .collection(FirebaseConstants.COLLECTIONS_CATEGORIES)
                .document(EventsManager.getInstance().getCurrentCategoryID())
                .collection(FirebaseConstants.COLLECTIONS_EVENTS)
                .document(EventsManager.getInstance().getCurrentEventsID())
                .collection(FirebaseConstants.COLLECTION_REGISTRATIONS);
        reference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                List<EventRegistrations> registrations = new ArrayList<>();
                if (documentSnapshots.isEmpty()) {
                    callback.onDataLoadedSuccessfully(registrations);
                    return;
                }
                for (DocumentSnapshot snapshot : documentSnapshots) {
                    registrations.add(snapshot.toObject(EventRegistrations.class));
                }
                callback.onDataLoadedSuccessfully(registrations);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Debug.e(FirebaseFetchEventRegistrationsRepository.class.getName() + " loadEventRegistrations()+" + e.getMessage());
                e.printStackTrace();
            }
        });

    }
}
