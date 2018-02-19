package com.bharathksunil.interrupt.dashboard.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.interrupt.admin.presenter.UsersInfoRecyclerPresenter;
import com.bharathksunil.interrupt.admin.repository.Users;

import java.util.List;

/**
 * @author Bharath on 19-02-2018.
 */

public class OrganisersInfoPresenterImplementation implements OrganisersInfoPresenter {
    @Nullable
    private View viewInstance;
    @NonNull
    private UsersInfoRecyclerPresenter.Repository repositoryInstance;

    public OrganisersInfoPresenterImplementation(@NonNull UsersInfoRecyclerPresenter.Repository repositoryInstance) {
        this.repositoryInstance = repositoryInstance;
    }

    @Override
    public void setView(@Nullable final View view) {
        viewInstance = view;
        if (viewInstance != null) {
            repositoryInstance.loadAllOrganisersInfo(new UsersInfoRecyclerPresenter.Repository.DataLoadedCallback() {
                @Override
                public void onDataLoadedSuccessfully(@NonNull List<Users> users) {
                    if (viewInstance != null) {
                        viewInstance.loadOrganisersListView(users);
                        viewInstance.onProcessEnded();
                    }
                }

                @Override
                public void onDataLoadFailed() {
                    if (viewInstance != null) {
                        viewInstance.onProcessEnded();
                        viewInstance.onUnexpectedError();
                    }
                }
            });
        }
    }
}
