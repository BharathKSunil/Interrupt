package com.bharathksunil.interrupt.events.presenter;

import android.support.annotation.NonNull;

import com.bharathksunil.interrupt.BaseView;
import com.bharathksunil.interrupt.OnItemClickListener;
import com.bharathksunil.interrupt.events.repository.EventRegistrations;

import java.util.List;

/**
 * This presenter Downloads the Event Information from the Repository
 *
 * @author Bharath on 18-02-2018.
 */

public interface RegisteredEventsRecyclerPresenter {
    interface View extends BaseView {
        void showNoRegisteredEventsText();

        void hideNoRegisteredEventsText();

        void loadRegisteredEventsRecyclerView();

        void loadEventsViewerForTheEventGivenBy(String eventID);

        void showDataUpdatedMessage();
    }

    /**
     * The Interface to interact with the row view
     */
    interface RowView {
        void setEventImage(String imageBannerUrl);

        void setEventName(String eventName);

        void setParticipantsName(String participantsName);

        void setParticipantsEmailId(String participantsEmailId);

        void setParticipantsPhoneNo(String participantsPhoneNo);

        void setParticipantsUSN(String participantsUSN);

        void setParticipantsTeamMembers(String participantsTeamMembers);

        void setParticipantsRegistrar(String participantsRegistrar);

        void setOnItemClickListener(OnItemClickListener onItemClickListener);

    }

    interface Repository {
        interface DataLoadedCallback {
            void finishedSuccessfully(@NonNull final List<EventRegistrations> data);

            void failed();
        }

        /**
         * This loads the registered events data of the user
         *
         * @param email    the email Id of the user whose events must be fetched
         * @param callback the Callback interface to when the data gets loaded
         */
        void getRegisteredEventsData(@NonNull final String email, @NonNull final DataLoadedCallback callback);
    }

    /**
     * This sets the interaction with the view
     *
     * @param view the view instance,activity or fragment
     */
    void setView(View view);

    /**
     * Attach the Events Information to the rowView item here
     *
     * @param position for the position of the item
     * @param rowView  the rowView of the item
     */
    void onBindEventRowViewAtPosition(final int position, RowView rowView);

    /**
     * Return the Size of the Events to be populated in the list view
     *
     * @return int, the size of list
     */
    int getEventsRowCount();

    void refreshRecyclerData();
}
