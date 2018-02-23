package com.bharathksunil.interrupt.admin.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.interrupt.OnItemClickListener;
import com.bharathksunil.interrupt.admin.model.Users;
import com.bharathksunil.interrupt.auth.model.UserType;

import java.util.ArrayList;
import java.util.List;

/**
 * This Retrieves the User information and Populates it to the Recycler view
 *
 * @author Bharath on 19-02-2018.
 */

public class UserInfoRecyclerPresenterImplementation implements UsersInfoRecyclerPresenter,
        UsersInfoRecyclerPresenter.Repository.DataLoadedCallback, OnItemClickListener {


    @Nullable
    private View viewInstance;
    @NonNull
    private Repository repositoryInstance;
    @NonNull
    private List<Users> data;
    @NonNull
    private UserType userType;

    public UserInfoRecyclerPresenterImplementation(@NonNull Repository repositoryInstance, @NonNull UserType userType) {
        this.repositoryInstance = repositoryInstance;
        data = new ArrayList<>();
        this.userType = userType;
    }

    @Override
    public void setView(View view) {
        viewInstance = view;
        if (viewInstance != null) {
            switch (userType) {
                case ADMINISTRATOR:
                    repositoryInstance.loadAllAdministratorsInfo(this);
                    break;
                case CORE_TEAM:
                    repositoryInstance.loadCoreTeamsInfo(this);
                    break;
                case EVENT_TEAM:
                    repositoryInstance.loadEventsTeamsInfo(this);
                    break;
                case OFF_STAGE_TEAM:
                    repositoryInstance.loadOffStageTeamsInfo(this);
                    break;
                case DESIGN_TEAM:
                    repositoryInstance.loadDesignTeamsInfo(this);
                    break;
                default:
                    repositoryInstance.loadAllUsersInfo(this);
            }
        }
    }

    @Override
    public void onBindEventRowViewAtPosition(int position, RowView rowView) {
        Users users = data.get(position);
        rowView.setUsersName(users.getName());
        rowView.setUsersDesignation(users.getDesignation());
        rowView.setUsersEmail(users.getEmail());
        rowView.setUsersPhoneNo(users.getPhoneNo());
        rowView.loadUsersImageFromUrl(users.getProfileUrl());
        rowView.setOnItemClickListener(this);
    }

    @Override
    public int getUsersRowCount() {
        return data.size();
    }

    @Override
    public void onDataLoadedSuccessfully(@NonNull List<Users> data) {
        if (viewInstance == null)
            return;
        this.data = data;
        viewInstance.onProcessEnded();
        if (data.size() == 0)
            viewInstance.showNoUserExistText();
        else {
            viewInstance.hideNoUsersExistText();
            viewInstance.loadUsersRecyclerView();
        }
    }

    @Override
    public void onDataLoadFailed() {
        if (viewInstance == null)
            return;
        viewInstance.onProcessEnded();
        viewInstance.onUnexpectedError();
    }

    @Override
    public void onItemClick(int itemPosition) {
        if (viewInstance != null)
            viewInstance.loadUserInfoViewerForTheUserGivenBy(data.get(itemPosition).getEmail());

    }
}
