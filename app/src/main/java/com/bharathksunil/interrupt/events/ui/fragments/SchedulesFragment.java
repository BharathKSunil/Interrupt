package com.bharathksunil.interrupt.events.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bharathksunil.interrupt.R;
import com.bharathksunil.interrupt.events.model.Schedules;
import com.bharathksunil.interrupt.events.presenter.SchedulesPresenter;
import com.bharathksunil.interrupt.events.presenter.SchedulesPresenterImplementation;
import com.bharathksunil.interrupt.events.repository.FirebaseSchedulesRepository;
import com.bharathksunil.interrupt.util.DateUtil;
import com.bharathksunil.interrupt.util.ViewUtils;
import com.github.vipulasri.timelineview.TimelineView;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass for displaying the schedules of the events
 */
public class SchedulesFragment extends Fragment implements SchedulesPresenter.View {


    private Unbinder unbinder;
    private SchedulesPresenter presenter;

    @BindView(R.id.progress_bar)
    AVLoadingIndicatorView loadingIndicatorView;
    @BindView(R.id.tv_empty_prompt)
    TextView tv_emptyPrompt;
    @BindView(R.id.rv_schedules)
    RecyclerView timelineRecyclerView;

    @BindString(R.string.err_unexpected_error)
    String err_unexpectedError;

    public SchedulesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dash_fragment_schedules, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new SchedulesPresenterImplementation(new FirebaseSchedulesRepository());
        presenter.setView(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * Called when the presenter starts its work
     * enable any dialog-box or progress bars
     */
    @Override
    public void onProcessStarted() {
        loadingIndicatorView.smoothToShow();
    }

    /**
     * Called when the presenter has finished its work
     * disable any dialog-box or progress bars
     */
    @Override
    public void onProcessEnded() {
        loadingIndicatorView.smoothToHide();
    }

    /**
     * Called whenever there was an unexpected error in the process
     * Display any toast or messages to the user indicating that there was an error
     */
    @Override
    public void onUnexpectedError() {
        ViewUtils.errorBar(err_unexpectedError, getActivity());
    }

    @Override
    public void showNoSchedulesFoundText() {
        ViewUtils.setVisible(tv_emptyPrompt);
    }

    @Override
    public void hideNoSchedulesFoundText() {
        ViewUtils.setGone(tv_emptyPrompt);
    }

    @Override
    public void loadSchedulesTimelineRecyclerView(List<Schedules> schedulesList) {
        timelineRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        timelineRecyclerView.setNestedScrollingEnabled(false);
        timelineRecyclerView.setHasFixedSize(true);
        timelineRecyclerView.setAdapter(new SchedulesTimelineAdapter(getActivity(), schedulesList));
    }

    class SchedulesTimelineAdapter extends RecyclerView.Adapter<SchedulesTimelineAdapter.TimeLineViewHolder> {

        private List<Schedules> schedules;
        private LayoutInflater inflater;

        SchedulesTimelineAdapter(Context context, List<Schedules> schedules) {
            this.inflater = LayoutInflater.from(context);
            this.schedules = schedules;
        }

        @Override
        public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new TimeLineViewHolder(
                    inflater.inflate(R.layout.row_schedules_timeline, parent, false), viewType);
        }

        @Override
        public void onBindViewHolder(TimeLineViewHolder holder, int position) {
            Schedules schedule = schedules.get(position);
            if (DateUtil.isTimePast(schedule.getTime())) {
                holder.mTimelineView.setMarker(getResources().getDrawable(R.drawable.ic_timeline_marker_past));
            } else {
                holder.mTimelineView.setMarker(getResources().getDrawable(R.drawable.ic_timeline_marker));
            }
            holder.date.setText(schedule.getTime());
            holder.name.setText(schedule.getEventName());
            holder.venue.setText(schedule.getVenue());
        }

        @Override
        public int getItemCount() {
            return schedules.size();
        }

        @Override
        public int getItemViewType(int position) {
            return TimelineView.getTimeLineViewType(position, getItemCount());
        }

        class TimeLineViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_timeline_date)
            TextView date;
            @BindView(R.id.tv_timeline_title)
            TextView name;
            @BindView(R.id.tv_timeline_venue)
            TextView venue;

            @BindView(R.id.time_marker)
            TimelineView mTimelineView;

            TimeLineViewHolder(View itemView, int viewType) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                mTimelineView.initLine(viewType);
            }
        }
    }
}
