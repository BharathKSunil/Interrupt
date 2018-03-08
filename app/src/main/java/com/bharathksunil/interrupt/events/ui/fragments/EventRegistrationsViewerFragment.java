package com.bharathksunil.interrupt.events.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bharathksunil.interrupt.R;
import com.bharathksunil.interrupt.events.model.EventRegistrations;
import com.bharathksunil.interrupt.events.presenter.EventsRegistrationsViewerPresenter;
import com.bharathksunil.interrupt.events.presenter.EventsRegistrationsViewerPresenterImplementation;
import com.bharathksunil.interrupt.events.repository.FirebaseFetchEventRegistrationsRepository;
import com.bharathksunil.interrupt.util.ViewUtils;
import com.stone.vega.library.VegaLayoutManager;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass to view the registration that's happened in an event.
 */
public class EventRegistrationsViewerFragment extends Fragment implements EventsRegistrationsViewerPresenter.View {
    public EventRegistrationsViewerFragment() {
        // Required empty public constructor
    }

    private Unbinder unbinder;
    private EventsRegistrationsViewerPresenter presenter;

    @BindView(R.id.progress_bar)
    AVLoadingIndicatorView loadingIndicatorView;
    @BindView(R.id.tv_empty_prompt)
    TextView tv_emptyPrompt;
    @BindView(R.id.rv_registrations)
    RecyclerView recyclerView;
    @BindView(R.id.tv_reg_count)
    TextView tv_totalRegistrationCount;
    @BindView(R.id.tv_reg_amount)
    TextView tv_totalRegistrationAmount;
    @BindView(R.id.tv_event_name_1)
    TextView tv_eventName;

    @BindString(R.string.currency)
    String Rs;
    @BindString(R.string.err_unexpected_error)
    String err_unexpectedError;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.events_fragment_registrations_viewer, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new EventsRegistrationsViewerPresenterImplementation(new FirebaseFetchEventRegistrationsRepository());
        presenter.setView(this);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.setView(null);
    }

    @Override
    public void onProcessStarted() {
        loadingIndicatorView.smoothToShow();
        tv_totalRegistrationAmount.setText(String.format("%s0", Rs));
        tv_totalRegistrationCount.setText("0");
    }

    @Override
    public void onProcessEnded() {
        loadingIndicatorView.smoothToHide();
    }

    @Override
    public void onUnexpectedError() {
        ViewUtils.errorBar(err_unexpectedError, getActivity());
    }

    @Override
    public void setEventName(String name) {
        tv_eventName.setText(name);
    }

    @Override
    public void showNoRegistrationsText() {
        ViewUtils.setVisible(tv_emptyPrompt);
    }

    @Override
    public void hideNoRegistrationsText() {
        ViewUtils.setGone(tv_emptyPrompt);
    }

    @Override
    public void setTotalRegistrationsCount(@NonNull String count) {
        tv_totalRegistrationCount.setText(count);
    }

    @Override
    public void setTotalRegistrationsAmount(@NonNull String amount) {
        tv_totalRegistrationAmount.setText(String.format("%s %s", Rs, amount));
    }

    @Override
    public void loadRecyclerView(@NonNull List<EventRegistrations> registrationsList) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new VegaLayoutManager());
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new EventRegistrationsAdapter(registrationsList, getActivity()));
    }


    class EventRegistrationsAdapter extends RecyclerView.Adapter<EventRegistrationsAdapter.RegistrationsViewHolder> {

        private List<EventRegistrations> registrationsList;
        private LayoutInflater inflater;

        EventRegistrationsAdapter(List<EventRegistrations> registrationsList, Context context) {
            this.registrationsList = registrationsList;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public RegistrationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RegistrationsViewHolder(inflater.inflate(
                    R.layout.row_event_registrations,
                    parent,
                    false
            ));
        }

        @Override
        public void onBindViewHolder(RegistrationsViewHolder holder, int position) {
            holder.bind(registrationsList.get(position));
        }

        @Override
        public int getItemCount() {
            return registrationsList.size();
        }

        class RegistrationsViewHolder extends RecyclerView.ViewHolder {
            private static final int NAME = 0, EMAIL = 1, PHONE = 2, COLLEGE_INFO = 3, REGISTRAR = 4;
            @BindViews({R.id.tv_name, R.id.tv_email, R.id.tv_phoneNo, R.id.tv_college_details, R.id.tv_registrar})
            List<TextView> textViewList;

            RegistrationsViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            void bind(EventRegistrations registrations) {
                textViewList.get(NAME).setText(String.format("%s [%s]", registrations.getpName(), registrations.getpUSN()));
                textViewList.get(EMAIL).setText(registrations.getpEmail());
                textViewList.get(PHONE).setText(registrations.getpPhoneNo());
                textViewList.get(COLLEGE_INFO).setText(String.format("%s %s ;\nTeam Members:  %s",
                        registrations.getpSem(), registrations.getpSection(), registrations.getpTeamMembers()));

                textViewList.get(REGISTRAR).setText(registrations.geteRegistrar());
            }

        }
    }
}
