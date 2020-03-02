package com.bharathksunil.interrupt.events.ui.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bharathksunil.interrupt.OnItemClickListener;
import com.bharathksunil.interrupt.R;
import com.bharathksunil.interrupt.events.model.Events;
import com.bharathksunil.interrupt.events.presenter.CoordinatorsEventsInfoPresenter;
import com.bharathksunil.interrupt.events.presenter.CoordinatorsEventsInfoPresenterImplementation;
import com.bharathksunil.interrupt.events.repository.FirebaseCoordinatorsEventsInfoRepository;
import com.bharathksunil.interrupt.events.ui.activities.EventDashboardActivity;
import com.bharathksunil.interrupt.util.ViewUtils;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass which displays all the events the user has registered to.
 */
public class CoordinatorsEventsInfoFragment extends Fragment implements CoordinatorsEventsInfoPresenter.View {

    private Unbinder unbinder;
    private CoordinatorsEventsInfoPresenter presenter;

    public CoordinatorsEventsInfoFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.progress_bar)
    AVLoadingIndicatorView loadingIndicatorView;
    @BindView(R.id.tv_empty_prompt)
    TextView tv_emptyPrompt;
    @BindView(R.id.rv_events)
    RecyclerView recyclerView;

    @BindString(R.string.err_unexpected_error)
    String err_unexpectedError;
    @BindString(R.string.err_permission_denied)
    String err_permissionDenied;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.events_fragment_coordinators_events_info, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new CoordinatorsEventsInfoPresenterImplementation(
                new FirebaseCoordinatorsEventsInfoRepository());
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
    public void showNoEventsCoordinating() {
        ViewUtils.setVisible(tv_emptyPrompt);
    }

    @Override
    public void hideNoEventsCoordinating() {
        ViewUtils.setGone(tv_emptyPrompt);
    }

    @Override
    public void loadCoordinatorsEventsRecyclerView(List<Events> events) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new CoordinatingEventsRecyclerAdapter(
                events,
                getContext(),
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(int itemPosition) {
                        presenter.onRecyclerViewItemPressed(itemPosition);
                    }
                }
        ));
    }

    @Override
    public void loadEventsDashboard() {
        startActivity(new Intent(getActivity(), EventDashboardActivity.class));
    }

    @Override
    public void showPermissionDenied() {
        ViewUtils.errorBar(err_permissionDenied, getActivity());
    }


    class CoordinatingEventsRecyclerAdapter extends RecyclerView.Adapter<CoordinatingEventsRecyclerAdapter.EventsViewHolder> {

        @NonNull
        private List<Events> data;
        @NonNull
        private Context context;
        @NonNull
        private LayoutInflater inflater;

        @NonNull
        private OnItemClickListener listener;

        CoordinatingEventsRecyclerAdapter(@NonNull List<Events> data,
                                          @NonNull Context context, @NonNull OnItemClickListener listener) {
            this.data = data;
            this.context = context;
            this.listener = listener;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public EventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new EventsViewHolder(inflater
                    .inflate(R.layout.row_coordinating_events, parent, false));
        }

        @Override
        public void onBindViewHolder(EventsViewHolder holder, int position) {
            holder.bind(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class EventsViewHolder extends RecyclerView.ViewHolder {
            private static final int NAME = 0, CATEGORY = 1, DESCRIPTION = 2, TIME = 3, VENUE = 4,
                    COORDINATORS = 5;
            @BindViews({R.id.tv_event_name_1, R.id.tv_event_category, R.id.tv_event_description,
                    R.id.tv_event_time, R.id.tv_event_venue, R.id.tv_event_coordinators})
            List<TextView> textViewList;
            @BindView(R.id.iv_events_image)
            ImageView imageView;

            EventsViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemClick(getAdapterPosition());
                    }
                });
            }

            void bind(Events event) {
                Picasso.get().load(event.getBannerUrl()).placeholder(R.drawable.app_icon)
                        .error(R.drawable.app_icon).into(imageView);

                textViewList.get(NAME).setText(event.getName());
                textViewList.get(CATEGORY).setText(event.getCategory());
                textViewList.get(DESCRIPTION).setText(event.getDescription());
                textViewList.get(TIME).setText(event.getDateTime());
                textViewList.get(VENUE).setText(event.getVenue());
                StringBuilder builder = new StringBuilder();
                for (String coordinator : event.getCoordinators())
                    builder.append(coordinator).append("\n");
                textViewList.get(COORDINATORS).setText(builder.toString().trim());
            }
        }
    }
}
