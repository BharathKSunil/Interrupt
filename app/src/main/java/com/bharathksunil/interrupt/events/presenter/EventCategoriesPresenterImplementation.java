package com.bharathksunil.interrupt.events.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.interrupt.events.model.EventsManager;
import com.bharathksunil.interrupt.events.model.Categories;
import com.bharathksunil.interrupt.util.Debug;

import java.util.ArrayList;
import java.util.List;

/**
 * This implementation of the {@link EventCategoriesPresenter} downloads the categories and presents
 * to the view.
 *
 * @author Bharath on 19-02-2018.
 */

public class EventCategoriesPresenterImplementation implements EventCategoriesPresenter,
        EventCategoriesPresenter.Repository.DataLoadedCallback {
    @Nullable
    private View viewInstance;
    @NonNull
    private Repository repositoryInstance;
    @NonNull
    private List<Categories> data;

    public EventCategoriesPresenterImplementation(@NonNull Repository repositoryInstance) {
        this.repositoryInstance = repositoryInstance;
        data = new ArrayList<>();
    }

    @Override
    public void setView(@Nullable final View view) {
        viewInstance = view;
        if (viewInstance != null) {
            viewInstance.onProcessStarted();
            repositoryInstance.downloadEventCategories(this);
        }
    }
    @Override
    public void onDataSuccessfullyLoaded(@NonNull List<Categories> categoriesList) {
        if (viewInstance != null) {
            viewInstance.onProcessEnded();
            if (categoriesList.size() == 0) {
                viewInstance.showNoEventCategoriesAvailable();
                return;
            }
            else
                viewInstance.hideNoEventCategoriesAvailable();
            List<String> urls = new ArrayList<>();
            for (Categories categories : categoriesList) {
                urls.add(categories.getImgUrl());
            }
            viewInstance.loadEventCategoriesRecyclerView(urls);
            viewInstance.initialiseView(categoriesList.get(0));
            data = categoriesList;
        }
    }

    @Override
    public void onDataLoadFailed() {
        if (viewInstance != null) {
            viewInstance.onUnexpectedError();
            viewInstance.onProcessEnded();
        }
    }

    @Override
    public void onCategoriesItemPressed(int clickedPosition, int activeCardPosition) {
        if (clickedPosition == activeCardPosition) {
            EventsManager.getInstance().loadCategories(data.get(activeCardPosition));
            if (viewInstance!=null){
                viewInstance.loadEventsViewerActivity();
                Debug.i("item Clicked:" + data.get(activeCardPosition).getId());
            }
        } else if (clickedPosition > activeCardPosition) {
            if (viewInstance != null)
                viewInstance.onActiveCardChange(clickedPosition, data.get(clickedPosition));
        }
    }

    @Override
    public void onScrollChange(int pos) {
        if (viewInstance!=null)
            viewInstance.onActiveCardChange(pos, data.get(pos));
    }


}
