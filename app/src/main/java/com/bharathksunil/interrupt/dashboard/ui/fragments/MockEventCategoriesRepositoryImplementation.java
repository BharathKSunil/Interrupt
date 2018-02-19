package com.bharathksunil.interrupt.dashboard.ui.fragments;

import android.support.annotation.NonNull;

import com.bharathksunil.interrupt.events.presenter.EventCategoriesPresenter;
import com.bharathksunil.interrupt.events.repository.Categories;

import java.util.ArrayList;
import java.util.List;

/**
 * A MockImplementation to mock the repository
 * @author Bharath on 19-02-2018.
 */

public class MockEventCategoriesRepositoryImplementation implements EventCategoriesPresenter.Repository {
    @Override
    public void downloadEventCategories(@NonNull DataLoadedCallback callback) {
        List<Categories> categoriesList = new ArrayList<>();
        Categories categories = new Categories();
        categories.setId("1");
        categories.setDescription("This is a mock");
        categories.setName("Mock Category");
        categories.setImgUrl("https://www.ricemedia.co.uk/wp-content/uploads/2014/07/technicalseo-e1459347122279.jpg");
        Categories categories2 = new Categories();
        categories2.setId("5");
        categories2.setDescription("This is second mock");
        categories2.setName("Mock 2");
        categories2.setImgUrl("https://www.ricemedia.co.uk/wp-content/uploads/2014/07/technicalseo-e1459347122279.jpg");

        for (int i = 0; i<3; i++){
            categoriesList.add(categories);
            categoriesList.add(categories2);
        }


        callback.onDataSuccessfullyLoaded(categoriesList);
    }
}
