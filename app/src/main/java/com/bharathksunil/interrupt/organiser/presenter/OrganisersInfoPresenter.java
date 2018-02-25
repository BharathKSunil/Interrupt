package com.bharathksunil.interrupt.organiser.presenter;

import com.bharathksunil.interrupt.BaseView;
import com.bharathksunil.interrupt.admin.model.Users;

import java.util.List;

/**
 * This Loads all the Organisers information to the information view
 *
 * @author Bharath on 19-02-2018.
 */

public interface OrganisersInfoPresenter {
    interface View extends BaseView {
        void loadCoreTeamsRecyclerView(List<Users> data);

        void loadEventsTeamsRecyclerView(List<Users> data);

        void loadOffStageTeamsRecyclerView(List<Users> data);

        void loadDesignTeamsRecyclerView(List<Users> data);

        void showNoCoreTeamDataFound();

        void showNoEventsTeamDataFound();

        void showNoOffStageTeamDataFound();

        void showNoDesignTeamDataFound();

        void hideNoCoreTeamsDataFound();

        void hideNoEventsTeamsDataFound();

        void hideNoOffStageTeamsDataFound();

        void hideNoDesignTeamsDataFound();
    }

    void setView(View view);
}
