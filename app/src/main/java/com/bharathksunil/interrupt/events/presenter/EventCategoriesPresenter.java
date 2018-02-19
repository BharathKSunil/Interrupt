package com.bharathksunil.interrupt.events.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.interrupt.BaseView;
import com.bharathksunil.interrupt.events.repository.Categories;

import java.util.List;

/**
 * A Presenter which loads all the event categories
 * @author Bharath on 19-02-2018.
 */

public interface EventCategoriesPresenter {
    interface View extends BaseView{
        void loadEventCategoriesRecyclerView(List<String> imageUrls);
        void initialiseView(Categories categories);
        void showNoEventCategoriesAvailable();
        void hideNoEventCategoriesAvailable();
        void onActiveCardChange(int pos, Categories categories);
    }

    interface Repository{
        interface DataLoadedCallback{
            void onDataSuccessfullyLoaded(List<Categories> categoriesList);
            void onDataLoadFailed();
        }
        void downloadEventCategories(@NonNull final DataLoadedCallback callback);
    }

    void setView(@Nullable View view);

    void onCategoriesItemPressed(int clickedPosition, int activeCardPosition);

    void onScrollChange(int pos);
}
