package com.bharathksunil.interrupt.events.repository;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bharathksunil.interrupt.FirebaseConstants;
import com.bharathksunil.interrupt.admin.model.Users;
import com.bharathksunil.interrupt.auth.model.UserPermissions;
import com.bharathksunil.interrupt.auth.model.UserType;
import com.bharathksunil.interrupt.events.model.Events;
import com.bharathksunil.interrupt.events.model.Schedules;
import com.bharathksunil.interrupt.events.presenter.EventInfoPresenter;
import com.bharathksunil.interrupt.events.presenter.EventsViewerPresenter;
import com.bharathksunil.interrupt.util.DateUtil;
import com.bharathksunil.interrupt.util.Debug;
import com.bharathksunil.interrupt.util.TextUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

/**
 * This Repository loads all the Events under a Category also adds Events
 *
 * @author Bharath on 20-02-2018.
 */

public class FirebaseEventsRepositoryImplementation implements EventsViewerPresenter.Repository, EventInfoPresenter.Repository {
    private String eventID;
    private boolean isUpdate;
    private OnEventRegistrationCallback callback;
    private Events events;
    private Users coordinator;
    private DocumentReference reference;

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
                        events.setCategoryID(id);
                        eventsList.add(events);
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

    @Override
    public void addNewEvent(@NonNull Events event, final @NonNull Users coordinator, @Nullable Uri bannerImage,
                            final @NonNull OnEventRegistrationCallback callback) {
        isUpdate = false;
        events = event;
        this.coordinator = coordinator;
        this.callback = callback;

        reference = FirebaseFirestore.getInstance()
                .collection(FirebaseConstants.COLLECTIONS_CATEGORIES)
                .document(event.getCategoryID())
                .collection(FirebaseConstants.COLLECTIONS_EVENTS)
                .document();
        eventID = reference.getId();
        this.events.setId(eventID);
        if (bannerImage != null)
            uploadEventBanner(bannerImage);
        else
            addNewEvent();
    }

    private void addNewEvent() {
        reference.set(events)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        addEventToSchedule();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Debug.i(FirebaseEventsRepositoryImplementation.class.getName() + " addNewEvent(): " + e.getMessage());
                        e.printStackTrace();
                        callback.onEventAddFailed();
                    }
                });
    }

    private void addEventToSchedule() {
        Schedules schedules = new Schedules(events.getName(), events.getDateTime(), events.getVenue());
        schedules.setTimestamp(DateUtil.getTimestampFromDate(events.getDateTime()));
        FirebaseDatabase.getInstance().getReference(FirebaseConstants.SCHEDULES_TREE)
                .child(eventID).setValue(schedules)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if (isUpdate)
                            callback.onEventUpdatedSuccessfully();
                        else
                            addCoordinatorToEvent();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Debug.i(FirebaseEventsRepositoryImplementation.class.getName() + " addEventToSchedule(): " + e.getMessage());
                        e.printStackTrace();
                        if (isUpdate)
                            callback.onEventUpdateFailed();
                        else
                            addCoordinatorToEvent();
                    }
                });
    }

    private void addCoordinatorToEvent() {
        FirebaseDatabase.getInstance().getReference(FirebaseConstants.USER_ACCESS_TREE)
                .child(TextUtils.getEmailAsFirebaseKey(coordinator.getEmail()))
                .child("accessTypes")
                .child(events.getCategoryID() + "_" + eventID)
                .setValue(UserType.COORDINATOR.name())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        giveCoordinatorPermissions();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Debug.i(FirebaseEventsRepositoryImplementation.class.getName() + "addCoordinatorToEvent():" + e.getMessage());
                        e.printStackTrace();
                        giveCoordinatorPermissions();
                    }
                });
    }

    private void giveCoordinatorPermissions() {
        UserPermissions permissions = new UserPermissions();
        permissions.setEnabled(true);
        permissions.setCanEditEventBanner(true);
        permissions.setCanViewEventCollections(true);
        permissions.setCanViewRegistrations(true);
        permissions.setCanEditEventsInfo(true);
        permissions.setCanDownloadEventData(true);
        permissions.setCanRegisterParticipant(true);
        FirebaseDatabase.getInstance().getReference(FirebaseConstants.USERS_PERMISSIONS_TREE)
                .child(TextUtils.getEmailAsFirebaseKey(coordinator.getEmail()))
                .setValue(permissions)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onEventAddedSuccessfully();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Debug.e(FirebaseEventsRepositoryImplementation.class.getName() +
                                "giveCoordinatorPermissions()" + e.getLocalizedMessage());
                        e.printStackTrace();
                        callback.onEventAddFailed();
                    }
                });

    }

    private void uploadEventBanner(@NonNull Uri bannerImage) {
        StorageReference reference = FirebaseStorage.getInstance()
                .getReference().child(FirebaseConstants.EVENTS_STORE +
                        eventID + ".jpg");
        UploadTask uploadTask = reference.putFile(bannerImage);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(uri -> {
                    events.setBannerUrl(uri.toString());
                    if (isUpdate) {
                        updateEventInfo();
                    } else {
                        addNewEvent();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Debug.e(FirebaseEventsRepositoryImplementation.class.getName() +
                        "uploadEventImage()" + e.getLocalizedMessage());
                e.printStackTrace();
                if (isUpdate)
                    callback.onEventUpdateFailed();
                else
                    callback.onEventAddFailed();
            }
        });
    }

    @Override
    public void updateEvent(@NonNull Events event, @Nullable Uri bannerImage,
                            @NonNull OnEventRegistrationCallback callback) {
        isUpdate = true;
        events = event;
        this.callback = callback;
        eventID = event.getId();

        if (eventID == null)
            callback.onEventUpdateFailed();

        reference = FirebaseFirestore.getInstance()
                .collection(FirebaseConstants.COLLECTIONS_CATEGORIES)
                .document(event.getCategoryID())
                .collection(FirebaseConstants.COLLECTIONS_EVENTS)
                .document(eventID);
        if (bannerImage == null)
            updateEventInfo();
        else
            uploadEventBanner(bannerImage);

    }

    private void updateEventInfo() {
        reference.set(events)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        addEventToSchedule();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Debug.e(FirebaseEventsRepositoryImplementation.class.getName() +
                                "updateEventInfo()" + e.getLocalizedMessage());
                        e.printStackTrace();
                        callback.onEventUpdateFailed();
                    }
                });
    }
}
