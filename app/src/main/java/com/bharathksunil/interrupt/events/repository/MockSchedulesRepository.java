package com.bharathksunil.interrupt.events.repository;

import android.support.annotation.NonNull;

import com.bharathksunil.interrupt.events.presenter.SchedulesPresenter;
import com.bharathksunil.interrupt.events.model.Schedules;

import java.util.ArrayList;
import java.util.List;

/**
 * This mocks the Schedules tree in the Repository
 * Created by Bharath on 24-02-2018.
 */

public class MockSchedulesRepository implements SchedulesPresenter.Repository {
    @Override
    public void getEventSchedules(@NonNull DataLoadedCallback callback) {
        List<Schedules> schedulesList = new ArrayList<>();

        schedulesList.add(new Schedules(
                "Rapid Fire Coding",
                "03:13 PM, 25 February 2018",
                "JPN Lab"
        ));
        schedulesList.add(new Schedules(
                "Something Crazy",
                "03:20 PM, 25 February 2018",
                "BBL Lab"
        ));
        schedulesList.add(new Schedules(
                "Something Good",
                "03:25 PM, 25 February 2018",
                "Room C558"
        ));
        schedulesList.add(new Schedules(
                "Gala Item",
                "03:30 PM, 25 February 2018",
                "C668"
        ));
        schedulesList.add(new Schedules(
                "Dance Masters",
                "01:13 PM, 26 February 2018"
                , "D444"
        ));
        schedulesList.add(new Schedules(
                "Rapid Fire Coding",
                "02:13 PM, 26 February 2018",
                "JPN Lab"
        ));
        schedulesList.add(new Schedules(
                "Rapid Fire Coding",
                "03:13 PM, 26 February 2018",
                "JPN Lab"
        ));
        schedulesList.add(new Schedules(
                "Rapid Fire Coding",
                "03:45 PM, 26 February 2018",
                "JPN Lab"
        ));
        schedulesList.add(new Schedules(
                "Rapid Fire Coding",
                "03:13 PM, 26 February 2018",
                "JPN Lab"
        ));

        callback.onDataLoadSuccessful(schedulesList);

    }
}
