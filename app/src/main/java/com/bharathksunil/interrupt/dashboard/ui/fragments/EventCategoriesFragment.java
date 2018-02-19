package com.bharathksunil.interrupt.dashboard.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bharathksunil.interrupt.R;

/**
 * A simple {@link Fragment} subclass for showing the event categories
 */
public class EventCategoriesFragment extends Fragment {


    public EventCategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dash_fragment_event_categoies, container, false);
    }

}
