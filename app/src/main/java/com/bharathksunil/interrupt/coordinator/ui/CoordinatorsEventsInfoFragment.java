package com.bharathksunil.interrupt.coordinator.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bharathksunil.interrupt.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CoordinatorsEventsInfoFragment extends Fragment {


    public CoordinatorsEventsInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.codr_fragment_events_info, container, false);
    }

}
