package com.bharathksunil.interrupt.dashboard.presenter;

import com.bharathksunil.interrupt.BaseView;
import com.bharathksunil.interrupt.admin.repository.Users;

import java.util.List;

/**
 * This Loads all the Organisers information to the information view
 * @author Bharath on 19-02-2018.
 */

public interface OrganisersInfoPresenter {
    interface View extends BaseView{
        void loadOrganisersListView(List<Users> data);
        void showNoOrganisersDataFound();
        void hideNoOrganisersDataFound();
    }
    void setView(View view);
}
