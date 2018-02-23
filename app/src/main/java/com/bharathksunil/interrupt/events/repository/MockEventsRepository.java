package com.bharathksunil.interrupt.events.repository;

import com.bharathksunil.interrupt.events.model.Events;
import com.bharathksunil.interrupt.events.presenter.EventsViewerPresenter;
import com.bharathksunil.interrupt.util.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * This Mocks the Events Repository and sends some data
 * Created by Bharath on 20-02-2018.
 */

public class MockEventsRepository implements EventsViewerPresenter.Repository {
    @Override
    public void loadEventsDataForCategoryGivenByID(String id, DataLoadedCallback callback) {
        List<Events> data = new ArrayList<>();
        //Technical Event
        if (TextUtils.areEqual(id, "1")) {
            data = getTechnicalEventsData();
        }else
            data = getCulturalEventsData();

        callback.onDataLoadedSuccessful(data);
    }

    private List<Events> getTechnicalEventsData() {
        List<Events> data = new ArrayList<>();

        List<String> one = new ArrayList<>();
        one.add("Bharath Kumar S [8867036863]");
        one.add("Deepika Singh [741659849]");
        one.add("Diwakar N [8867036863]");

        List<String> two = new ArrayList<>();
        two.add("Deepika Singh [741659849]");
        two.add("Bharath Kumar S [8867036863]");
        two.add("Diwakar N [8867036863]");

        List<String> three = new ArrayList<>();
        three.add("Diwakar N [8867036863]");
        three.add("Deepika Singh [741659849]");
        three.add("Bharath Kumar S [8867036863]");

        data.add(new Events(
                "1",
                "1",
                "Rapid Fire Coding",
                "Registered teams will appear for Screening Test which will be a MCQ round." +
                        " This round will test your knowledge in Programming." +
                        " Selected students will proceed to next level.",
                "06/03/2018 12:45:00",
                "http://a4logic.com/wp-content/uploads/2016/09/coding_flat-550x311-89218.png",
                "C122",
                60,
                one
        ));

        data.add(new Events(
                "2",
                "1",
                "Coding",
                "Registered teams will appear for Screening Test which will be a MCQ round." +
                        " Selected students will proceed to next level.",
                "06/03/2018 12:45:00",
                "https://st.depositphotos.com/1062085/3700/v/950/depositphotos_37000153-stock-illustration-web-and-html-programming-flat.jpg",
                "C122",
                90,
                two
        ));
        data.add(new Events(
                "3",
                "1",
                "Rock Paper",
                "Registered teams will appear for Screening Test which will be a MCQ round." +
                        " This round will test your knowledge in Programming." +
                        " Selected students will proceed to next level.",
                "06/03/2018 12:45:00",
                "http://superawesomevectors.com/wp-content/uploads/2015/02/isometric-flat-paper-plane.jpg",
                "C122",
                120,
                three
        ));

        return data;
    }

    private List<Events> getCulturalEventsData() {
        List<Events> data = new ArrayList<>();

        List<String> one = new ArrayList<>();
        one.add("Bharath Kumar S [8867036863]");
        one.add("Deepika Singh [741659849]");
        one.add("Diwakar N [8867036863]");

        List<String> two = new ArrayList<>();
        two.add("Deepika Singh [741659849]");
        two.add("Bharath Kumar S [8867036863]");
        two.add("Diwakar N [8867036863]");

        List<String> three = new ArrayList<>();
        three.add("Diwakar N [8867036863]");
        three.add("Deepika Singh [741659849]");
        three.add("Bharath Kumar S [8867036863]");

        data.add(new Events(
                "1",
                "2",
                "Dancing",
                "Registered teams will appear for Screening Test which will be a MCQ round." +
                        " This round will test your knowledge in Programming." +
                        " Selected students will proceed to next level.",
                "06/03/2018 12:45:00",
                "https://data.whicdn.com/images/30166793/original.jpg",
                "C122",
                60,
                one
        ));

        data.add(new Events(
                "2",
                "2",
                "Singing",
                "Registered teams will appear for Screening Test which will be a MCQ round." +
                        " Selected students will proceed to next level.",
                "06/03/2018 12:45:00",
                "https://d2gg9evh47fn9z.cloudfront.net/800px_COLOURBOX19817934.jpg",
                "C122",
                90,
                two
        ));
        data.add(new Events(
                "3",
                "2",
                "Blah- Blah",
                "Registered teams will appear for Screening Test which will be a MCQ round." +
                        " This round will test your knowledge in Programming." +
                        " Selected students will proceed to next level.",
                "06/03/2018 12:45:00",
                "https://cdn.dribbble.com/users/600386/screenshots/2173455/minionflatdribble_1x.jpg",
                "C122",
                120,
                three
        ));

        return data;
    }
}
