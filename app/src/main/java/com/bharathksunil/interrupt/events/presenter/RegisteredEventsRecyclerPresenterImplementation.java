package com.bharathksunil.interrupt.events.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.interrupt.OnItemClickListener;
import com.bharathksunil.interrupt.auth.model.UserManager;
import com.bharathksunil.interrupt.events.repository.EventRegistrations;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the implementation of the {@link RegisteredEventsRecyclerPresenter} for presenting the
 * Registered Events Recycler RowView
 *
 * @author Bharath on 18-02-2018.
 */

public class RegisteredEventsRecyclerPresenterImplementation implements RegisteredEventsRecyclerPresenter,
        RegisteredEventsRecyclerPresenter.Repository.DataLoadedCallback, OnItemClickListener {
    @Nullable
    private View viewInstance;
    @NonNull
    private String email;
    @NonNull
    private Repository repositoryInstance;
    @NonNull
    private List<EventRegistrations> data;
    private boolean isRefresh;

    public RegisteredEventsRecyclerPresenterImplementation(@NonNull Repository repositoryInstance,
                                                           @NonNull String email) {
        this.repositoryInstance = repositoryInstance;
        data = new ArrayList<>();
        this.email = email;
    }

    @Override
    public void setView(View view) {
        viewInstance = view;
        if (view != null) {
            isRefresh = false;
            repositoryInstance.getRegisteredEventsData(email, this);
            viewInstance.onProcessStarted();
        }
    }

    /**
     * Attach the Events Information to the rowView item here
     *
     * @param position for the position of the item
     * @param rowView  the rowView of the item
     */
    @Override
    public void onBindEventRowViewAtPosition(int position, RowView rowView) {
        EventRegistrations registrations = data.get(position);
        UserManager userManager = UserManager.getInstance();
        //since we are retrieving the details of the events registered by the user,
        // there is no need to store these information in the database
        rowView.setParticipantsName(userManager.getUsersName());
        rowView.setParticipantsEmailId(userManager.getUsersEmailID());
        rowView.setParticipantsPhoneNo(userManager.getUsersPhoneNo());
        rowView.setParticipantsUSN(userManager.getUsersUSN());
        rowView.setEventName(registrations.geteName());
        rowView.setParticipantsRegistrar(registrations.geteRegistrar());
        rowView.setOnItemClickListener(this);
        StringBuilder teamMembers = new StringBuilder();
        for (String member : registrations.getpTeamMembers())
            teamMembers.append(member).append("\n");
        rowView.setParticipantsTeamMembers(teamMembers.toString().trim());
        rowView.setEventImage(registrations.geteBannerUrl());
    }


    /**
     * Return the Size of the Events to be populated in the list view
     *
     * @return int, the size of list
     */
    @Override
    public int getEventsRowCount() {
        return data.size();
    }

    @Override
    public void refreshRecyclerData() {
        isRefresh = true;
        repositoryInstance.getRegisteredEventsData(UserManager.getInstance().getUsersEmailID(),
                this);
    }

    @Override
    public void finishedSuccessfully(@NonNull List<EventRegistrations> data) {
        if (viewInstance == null)
            return;
        this.data = data;
        viewInstance.onProcessEnded();
        if (data.size() == 0)
            viewInstance.showNoRegisteredEventsText();
        else {
            viewInstance.hideNoRegisteredEventsText();
            viewInstance.loadRegisteredEventsRecyclerView();
            if (isRefresh)
                viewInstance.showDataUpdatedMessage();
        }
    }

    @Override
    public void failed() {
        if (viewInstance != null) {
            viewInstance.onProcessEnded();
            viewInstance.onUnexpectedError();
        }
    }

    @Override
    public void onItemClick(int itemPosition) {
        if (viewInstance != null)
            viewInstance.loadEventsViewerForTheEventGivenBy(data.get(itemPosition).getEventPath());
    }
}
