package com.bharathksunil.interrupt.admin.presenter;

import android.support.annotation.NonNull;

import com.bharathksunil.interrupt.BaseView;
import com.bharathksunil.interrupt.OnItemClickListener;
import com.bharathksunil.interrupt.admin.repository.Users;

import java.util.List;

/**
 * This presenter is for loading all user information
 *
 * @author Bharath on 19-02-2018.
 */

public interface UsersInfoRecyclerPresenter {
    interface RowView {
        void setUsersName(String name);

        void setUsersDesignation(String designation);

        void setUsersEmail(String email);

        void setUsersPhoneNo(String phoneNo);

        void loadUsersImageFromUrl(String imageUrl);

        void setOnItemClickListener(final OnItemClickListener itemClickListener);
    }

    interface View extends BaseView {
        void showNoUserExistText();

        void hideNoUsersExistText();

        void loadUsersRecyclerView();

        void loadUserInfoViewerForTheUserGivenBy(String userID);
    }

    interface Repository {
        interface DataLoadedCallback {
            void onDataLoadedSuccessfully(@NonNull List<Users> data);

            void onDataLoadFailed();
        }

        void loadAllUsersInfo(DataLoadedCallback callback);

        void loadAllOrganisersInfo(DataLoadedCallback callback);

        void loadAllAdministratorsInfo(DataLoadedCallback callback);
    }

    /**
     * This sets the interaction with the view
     *
     * @param view the view instance,activity or fragment
     */
    void setView(View view);

    /**
     * Attach the User Information to the rowView item here
     *
     * @param position for the position of the item
     * @param rowView  the rowView of the item
     */
    void onBindEventRowViewAtPosition(final int position, RowView rowView);

    /**
     * Return the Size of the Users to be populated in the list view
     *
     * @return int, the size of list
     */
    int getUsersRowCount();

}
