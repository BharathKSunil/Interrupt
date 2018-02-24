package com.bharathksunil.interrupt.admin.repository;

import com.bharathksunil.interrupt.admin.model.Users;
import com.bharathksunil.interrupt.admin.presenter.UsersInfoRecyclerPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * This mocks the firebase UserInfo Tree and the OrganiserInfo Trees
 *
 * @author Bharath on 22-02-2018.
 */

public class MockUsersInfoRepositoryImplementation implements UsersInfoRecyclerPresenter.Repository {
    @Override
    public void loadAllUsersInfo(DataLoadedCallback callback) {
        //List<Users> data
    }

    @Override
    public void loadCoreTeamsInfo(DataLoadedCallback callback) {
        List<Users> data = new ArrayList<>();
        List<String> roles = new ArrayList<>();
        roles.add("Android Developer");
        roles.add("Technical Team");
        roles.add("Administrator");

        data.add(new Users(
                "Bharath Kumar S",
                "Core Team",
                "8867036863",
                "bharathk.sunil.k@gmail.com",
                "https://firebasestorage.googleapis.com/v0/b/interrupt-62251.appspot.com/o/Profiles%2FG9blzC2ahjYkvPKGIixN85ASpCk2.jpg?alt=media&token=290671cf-e258-4154-87f1-b63411e26e3a",
                roles
        ));
        data.add(new Users(
                "Bharath Kumar S",
                "Core Team",
                "8867036863",
                "bharathk.sunil.k@gmail.com",
                "https://firebasestorage.googleapis.com/v0/b/interrupt-62251.appspot.com/o/Profiles%2FG9blzC2ahjYkvPKGIixN85ASpCk2.jpg?alt=media&token=290671cf-e258-4154-87f1-b63411e26e3a",
                roles
        ));
        data.add(new Users(
                "Bharath Kumar S",
                "Core Team",
                "8867036863",
                "bharathk.sunil.k@gmail.com",
                "https://firebasestorage.googleapis.com/v0/b/interrupt-62251.appspot.com/o/Profiles%2FG9blzC2ahjYkvPKGIixN85ASpCk2.jpg?alt=media&token=290671cf-e258-4154-87f1-b63411e26e3a",
                roles
        ));
        data.add(new Users(
                "Bharath Kumar S",
                "Core Team",
                "8867036863",
                "bharathk.sunil.k@gmail.com",
                "https://firebasestorage.googleapis.com/v0/b/interrupt-62251.appspot.com/o/Profiles%2FG9blzC2ahjYkvPKGIixN85ASpCk2.jpg?alt=media&token=290671cf-e258-4154-87f1-b63411e26e3a",
                roles
        ));

        List<String> roles2 = new ArrayList<>();
        roles2.add("Core Team");
        roles2.add("Event Management");
        roles2.add("off Stage");

        data.add(new Users(
                "Deepika Singh",
                "Core Team",
                "8867036863",
                "bharathk.sunil.k@gmail.com",
                "https://firebasestorage.googleapis.com/v0/b/interrupt-62251.appspot.com/o/Profiles%2F7SsCvw3k5iVrPyKc6xAzq6YEtyp2.jpg?alt=media&token=77916fe7-da97-4a2f-a99a-9935bb6b200a",
                roles2
        ));

        callback.onDataLoadedSuccessfully(data);
    }

    @Override
    public void loadEventsTeamsInfo(DataLoadedCallback callback) {
        loadCoreTeamsInfo(callback);
    }

    @Override
    public void loadOffStageTeamsInfo(DataLoadedCallback callback) {
        loadCoreTeamsInfo(callback);
    }

    @Override
    public void loadDesignTeamsInfo(DataLoadedCallback callback) {
        loadCoreTeamsInfo(callback);
    }

    @Override
    public void loadAllAdministratorsInfo(DataLoadedCallback callback) {
        List<Users> admins = new ArrayList<>();
        List<String> roles = new ArrayList<>();
        roles.add("Android Developer");
        roles.add("Technical Team");
        roles.add("Administrator");

        admins.add(new Users(
                "Bharath Kumar S",
                "Core Team",
                "8867036863",
                "bharathk.sunil.k@gmail.com",
                "https://firebasestorage.googleapis.com/v0/b/interrupt-62251.appspot.com/o/Profiles%2FG9blzC2ahjYkvPKGIixN85ASpCk2.jpg?alt=media&token=290671cf-e258-4154-87f1-b63411e26e3a",
                roles
        ));

        callback.onDataLoadedSuccessfully(admins);

    }
}
