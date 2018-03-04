package com.bharathksunil.interrupt.events.repository;

import android.support.annotation.NonNull;

import com.bharathksunil.interrupt.FirebaseConstants;
import com.bharathksunil.interrupt.events.model.EventRegistrations;
import com.bharathksunil.interrupt.events.model.EventsManager;
import com.bharathksunil.interrupt.events.presenter.ParticipantRegistrationsPresenter;
import com.bharathksunil.interrupt.util.Debug;
import com.bharathksunil.interrupt.util.TextUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * This method connects to firebase to register user
 *
 * @author Bharath on 01-03-2018.
 */

public class FirebaseParticipantRegistrationRepository implements ParticipantRegistrationsPresenter.Repository {
    private RegistrationCompleteCallback callback;
    private EventRegistrations participant;
    private String regId;
    @NonNull
    private EventsManager eventsManager;

    public FirebaseParticipantRegistrationRepository() {
        this.eventsManager = EventsManager.getInstance();
    }

    @Override
    public void registerParticipant(@NonNull EventRegistrations participant, RegistrationCompleteCallback callback) {
        this.callback = callback;
        this.participant = participant;

        performRegistration();

    }

    private void performRegistration() {
        DocumentReference reference = FirebaseFirestore.getInstance()
                .collection(FirebaseConstants.COLLECTIONS_CATEGORIES)
                .document(eventsManager.getCurrentCategoryID())
                .collection(FirebaseConstants.COLLECTIONS_EVENTS)
                .document(eventsManager.getCurrentEventsID())
                .collection(FirebaseConstants.COLLECTION_REGISTRATIONS)
                .document();
        regId = reference.getId();
        participant.setId(regId);
        reference.set(participant)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        addRegistrationToUsersTree();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Debug.e(FirebaseParticipantRegistrationRepository.class.getName() + " performRegistration():"
                                + e.getMessage());
                        e.printStackTrace();
                        callback.userRegistrationFailed();
                    }
                });

    }

    private void addRegistrationToUsersTree() {
        FirebaseDatabase.getInstance().getReference(FirebaseConstants.EVENT_REGISTRATIONS_TREE)
                .child(TextUtils.getEmailAsFirebaseKey(participant.getpEmail()))
                .child(regId)
                .setValue(participant)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.userRegisteredSuccessfully();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Debug.e(FirebaseParticipantRegistrationRepository.class.getName() + " addRegistrationUsersTree(): " +
                                e.getMessage());
                        e.printStackTrace();
                        callback.userRegistrationFailed();
                    }
                });
    }
}
