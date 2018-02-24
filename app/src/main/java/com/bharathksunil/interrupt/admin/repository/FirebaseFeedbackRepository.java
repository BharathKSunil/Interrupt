package com.bharathksunil.interrupt.admin.repository;

import android.support.annotation.NonNull;

import com.bharathksunil.interrupt.admin.model.Feedback;
import com.bharathksunil.interrupt.admin.presenter.FeedbackPresenter;
import com.bharathksunil.interrupt.auth.repository.FirebaseConstants;
import com.bharathksunil.interrupt.util.Debug;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * This Repository pushes the data to the Firebase and also retrieves it
 *
 * @author Bharath on 24-02-2018.
 */

public class FirebaseFeedbackRepository implements FeedbackPresenter.Repository {
    @Override
    public void postFeedback(@NonNull Feedback feedback, @NonNull final OnFeedbackPostedCallback callback) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference(FirebaseConstants.FEEDBACK_TREE);
        String id = databaseReference.push().getKey();
        databaseReference.child(id).setValue(feedback)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onFeedbackPostedSuccessfully();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Debug.e(FirebaseFeedbackRepository.class.getName() + " postFeedback(): " + e.getMessage());
                        e.printStackTrace();
                        callback.onFeedbackPostFailed();
                    }
                });
    }

    @Override
    public void getAllFeedbacks(@NonNull final DataLoadedCallback dataLoadedCallback) {
        final List<Feedback> data = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(FirebaseConstants.FEEDBACK_TREE);
        reference.keepSynced(true);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.exists()) {
                            Feedback feedback = snapshot.getValue(Feedback.class);
                            data.add(feedback);
                        }
                    }
                }
                dataLoadedCallback.onDataLoadedSuccessfully(data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Debug.i(FirebaseFeedbackRepository.class.getName() + " getAllFeedbacks(): " + databaseError.getMessage());
                databaseError.toException().printStackTrace();
                dataLoadedCallback.onDataLoadFailed();
            }
        });
    }
}
