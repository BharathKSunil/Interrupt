package com.bharathksunil.interrupt.events.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.interrupt.admin.presenter.UsersInfoRecyclerPresenter;
import com.bharathksunil.interrupt.admin.model.Users;

import java.util.List;

/**
 * @author Bharath on 19-02-2018.
 */

public class OrganisersInfoPresenterImplementation implements OrganisersInfoPresenter {
    @Nullable
    private View viewInstance;
    @NonNull
    private UsersInfoRecyclerPresenter.Repository repositoryInstance;

    private boolean isCoreTeamsDataLoaded;
    private boolean isEventsTeamsDataLoaded;
    private boolean isOffStageTeamsDataLoaded;
    private boolean isDesignTeamsDataLoaded;

    public OrganisersInfoPresenterImplementation(@NonNull UsersInfoRecyclerPresenter.Repository repositoryInstance) {
        this.repositoryInstance = repositoryInstance;
    }

    @Override
    public void setView(@Nullable final View view) {
        viewInstance = view;
        if (viewInstance != null) {
            viewInstance.onProcessStarted();
            loadCoreTeamsInfo();
            loadEventsTeamsInfo();
            loadOffStageTeamsInfo();
            loadDesignTeamsInfo();
        }
    }

    private void loadCoreTeamsInfo() {
        repositoryInstance.loadCoreTeamsInfo(new UsersInfoRecyclerPresenter.Repository.DataLoadedCallback() {
            @Override
            public void onDataLoadedSuccessfully(@NonNull List<Users> users) {
                if (viewInstance != null) {
                    isCoreTeamsDataLoaded = true;
                    if (users.size() == 0)
                        viewInstance.showNoCoreTeamDataFound();
                    else {
                        viewInstance.hideNoCoreTeamsDataFound();
                        viewInstance.loadCoreTeamsRecyclerView(users);
                    }
                }
                checkProcessEndedAndNotifyEnd();
            }

            @Override
            public void onDataLoadFailed() {
                if (viewInstance != null) {
                    isCoreTeamsDataLoaded = true;
                    viewInstance.showNoCoreTeamDataFound();
                    viewInstance.onUnexpectedError();
                }
                checkProcessEndedAndNotifyEnd();
            }
        });
    }

    private void loadEventsTeamsInfo() {
        repositoryInstance.loadEventsTeamsInfo(new UsersInfoRecyclerPresenter.Repository.DataLoadedCallback() {
            @Override
            public void onDataLoadedSuccessfully(@NonNull List<Users> users) {
                if (viewInstance != null) {
                    isEventsTeamsDataLoaded = true;
                    if (users.size() == 0)
                        viewInstance.showNoEventsTeamDataFound();
                    else {
                        viewInstance.hideNoEventsTeamsDataFound();
                        viewInstance.loadEventsTeamsRecyclerView(users);
                    }
                }
                checkProcessEndedAndNotifyEnd();
            }

            @Override
            public void onDataLoadFailed() {
                if (viewInstance != null) {
                    isEventsTeamsDataLoaded = true;
                    viewInstance.showNoEventsTeamDataFound();
                    viewInstance.onUnexpectedError();
                }
                checkProcessEndedAndNotifyEnd();
            }
        });
    }

    private void loadOffStageTeamsInfo() {
        repositoryInstance.loadOffStageTeamsInfo(new UsersInfoRecyclerPresenter.Repository.DataLoadedCallback() {
            @Override
            public void onDataLoadedSuccessfully(@NonNull List<Users> users) {
                if (viewInstance != null) {
                    isOffStageTeamsDataLoaded = true;
                    if (users.size() == 0)
                        viewInstance.showNoOffStageTeamDataFound();
                    else {
                        viewInstance.hideNoOffStageTeamsDataFound();
                        viewInstance.loadOffStageTeamsRecyclerView(users);
                    }
                }
                checkProcessEndedAndNotifyEnd();
            }

            @Override
            public void onDataLoadFailed() {
                if (viewInstance != null) {
                    isOffStageTeamsDataLoaded = true;
                    viewInstance.showNoOffStageTeamDataFound();
                    viewInstance.onUnexpectedError();
                }
                checkProcessEndedAndNotifyEnd();
            }
        });
    }

    private void loadDesignTeamsInfo() {
        repositoryInstance.loadDesignTeamsInfo(new UsersInfoRecyclerPresenter.Repository.DataLoadedCallback() {
            @Override
            public void onDataLoadedSuccessfully(@NonNull List<Users> users) {
                if (viewInstance != null) {
                    isDesignTeamsDataLoaded = true;
                    if (users.size() == 0)
                        viewInstance.showNoDesignTeamDataFound();
                    else {
                        viewInstance.hideNoDesignTeamsDataFound();
                        viewInstance.loadDesignTeamsRecyclerView(users);
                    }
                }
                checkProcessEndedAndNotifyEnd();
            }

            @Override
            public void onDataLoadFailed() {
                if (viewInstance != null) {
                    isDesignTeamsDataLoaded = true;
                    viewInstance.showNoDesignTeamDataFound();
                    viewInstance.onUnexpectedError();
                }
                checkProcessEndedAndNotifyEnd();
            }
        });
    }

    private void checkProcessEndedAndNotifyEnd() {
        if (viewInstance != null && isCoreTeamsDataLoaded && isOffStageTeamsDataLoaded
                && isDesignTeamsDataLoaded && isEventsTeamsDataLoaded)
            viewInstance.onProcessEnded();
    }
}
