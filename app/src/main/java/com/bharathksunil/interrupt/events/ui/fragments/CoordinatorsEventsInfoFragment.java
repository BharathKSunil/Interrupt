package com.bharathksunil.interrupt.events.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bharathksunil.interrupt.R;

/**
 * A simple {@link Fragment} subclass which displays all the events the user has registered to.
 */
public class CoordinatorsEventsInfoFragment extends Fragment {


    public CoordinatorsEventsInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.events_fragment_coordinators_events_info, container, false);
    }

}
