package com.bharathksunil.interrupt.events.repository;

import android.support.annotation.NonNull;

import com.bharathksunil.interrupt.FirebaseConstants;
import com.bharathksunil.interrupt.auth.model.UserManager;
import com.bharathksunil.interrupt.events.model.Events;
import com.bharathksunil.interrupt.events.presenter.CoordinatorsEventsInfoPresenter;
import com.bharathksunil.interrupt.util.Debug;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * This gets the Coordinators Events from the Firebase Repository
 *
 * @author Bharath on 28-02-2018.
 */

public class FirebaseCoordinatorsEventsInfoRepository implements CoordinatorsEventsInfoPresenter.Repository {
    @NonNull
    private UserManager userManager;
    private List<Events> eventsList;
    private Queue<String> downloadQueue;

    public FirebaseCoordinatorsEventsInfoRepository() {
        this.userManager = UserManager.getInstance();
    }

    @Override
    public void loadUsersCoordinatingEventsInfo(@NonNull DataLoadedCallback callback) {
        eventsList = new ArrayList<>();
        downloadQueue = new ArrayDeque<>();
        for (String path : userManager.getCoordinatingEventsPathID())
            downloadQueue.add(getFirebaseEventPathFromFirebaseKey(path));
        downloadEventsData(callback);
    }

    private synchronized void downloadEventsData(final DataLoadedCallback callback) {
        String path = downloadQueue.poll();
        if (path == null) {
            callback.onDataLoadSuccessful(eventsList);
            return;
        }

        FirebaseFirestore.getInstance().document(path).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            eventsList.add(documentSnapshot.toObject(Events.class));
                        }
                        downloadEventsData(callback);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Debug.e(FirebaseCoordinatorsEventsInfoRepository.class.getName() + "downloadEventsData(): " + e.getMessage());
                        e.printStackTrace();
                        callback.onDataLoadFailed();
                    }
                });
    }


    private String getFirebaseEventPathFromFirebaseKey(String firebaseKey) {
        if (!firebaseKey.contains("_"))
            return null;
        String categoryID = firebaseKey.split("_")[0];
        String eventID = firebaseKey.split("_")[1];

        return "/" + FirebaseConstants.COLLECTIONS_CATEGORIES + "/" + categoryID +
                "/" + FirebaseConstants.COLLECTIONS_EVENTS + "/" + eventID + "/";
    }
}
