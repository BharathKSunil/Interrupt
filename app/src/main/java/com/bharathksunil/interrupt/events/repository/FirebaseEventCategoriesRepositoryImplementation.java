package com.bharathksunil.interrupt.events.repository;

import android.support.annotation.NonNull;

import com.bharathksunil.interrupt.auth.repository.FirebaseConstants;
import com.bharathksunil.interrupt.events.presenter.EventCategoriesPresenter;
import com.bharathksunil.interrupt.util.Debug;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * This downloads the Event categories from the Firebase Repository
 *
 * @author Bharath on 19-02-2018.
 */

public class FirebaseEventCategoriesRepositoryImplementation implements EventCategoriesPresenter.Repository {
    @Override
    public void downloadEventCategories(@NonNull final DataLoadedCallback callback) {

        FirebaseFirestore.getInstance()
                .collection(FirebaseConstants.COLLECTIONS_CATEGORIES)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                List<Categories> categoriesList = new ArrayList<>();
                if (documentSnapshots.isEmpty()) {
                    callback.onDataSuccessfullyLoaded(categoriesList);
                    return;
                }
                for (DocumentSnapshot snapshot : documentSnapshots.getDocuments()) {
                    if (snapshot.exists()) {
                        Categories category = snapshot.toObject(Categories.class);
                        category.setId(snapshot.getId());
                        categoriesList.add(category);
                        Debug.i("Categories: " + category.getName() + "Category ID: " + category.getId());
                    }
                }
                callback.onDataSuccessfullyLoaded(categoriesList);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Debug.i(FirebaseEventCategoriesRepositoryImplementation.class.getName()
                                + "downloadEventCategories():" + e.getMessage());
                        callback.onDataLoadFailed();
                        e.printStackTrace();
                    }
                });
    }
}
