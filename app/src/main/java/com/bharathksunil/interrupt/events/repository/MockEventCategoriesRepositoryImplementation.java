package com.bharathksunil.interrupt.events.repository;

import android.support.annotation.NonNull;

import com.bharathksunil.interrupt.events.presenter.EventCategoriesPresenter;

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
        categoriesList.add(new Categories(
                "1",
                "Technical Events",
                "This is the perfect choice to showcase your technical skills",
                "https://image.freepik.com/free-vector/technology-template-in-flat-design_1028-51.jpg"));
        categoriesList.add(new Categories(
                "2",
                "Cultural Events",
                "This is the perfect choice to showcase your Cultural skills",
                "https://previews.123rf.com/images/enotmaks/enotmaks1609/enotmaks160900017/64800772-vector-modern-stylish-flat-linear-icons-set-of-event-management-event-service-and-special-event-orga.jpg"));
        categoriesList.add(new Categories(
                "3",
                "Games",
                "This is the perfect choice to showcase your Gaming skills",
                "https://image.freepik.com/free-vector/player-with-video-game-elements-in-flat-design_23-2147571826.jpg"));
        categoriesList.add(new Categories(
                "4",
                "Sports",
                "This is the perfect choice to showcase your Sporting skills",
                "https://image.freepik.com/free-vector/flat-sport-accessories-background_23-2147554317.jpg"));
        categoriesList.add(new Categories(
                "5",
                "Entertainment",
                "This is the perfect choice to Enjoy",
                "https://image.freepik.com/free-vector/collection-of-flat-cine-elements_23-2147543885.jpg"));



        callback.onDataSuccessfullyLoaded(categoriesList);
    }
}
