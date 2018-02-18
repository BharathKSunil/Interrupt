package com.bharathksunil.interrupt.events.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bharathksunil.interrupt.OnItemClickListener;
import com.bharathksunil.interrupt.R;
import com.bharathksunil.interrupt.events.presenter.RegisteredEventsRecyclerPresenter;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * This is the Recycler RowView Adapter for the Events
 *
 * @author Bharath on 18-02-2018.
 */

public class RegisteredEventsRecyclerAdapter extends RecyclerView.Adapter<RegisteredEventsRecyclerAdapter.EventsRowViewHolder> {
    private RegisteredEventsRecyclerPresenter presenter;
    private Context context;

    public RegisteredEventsRecyclerAdapter(RegisteredEventsRecyclerPresenter presenter, Context context) {
        this.presenter = presenter;
        this.context = context;
    }

    @Override
    public EventsRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EventsRowViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_events_registered, parent, false));
    }

    @Override
    public void onBindViewHolder(EventsRowViewHolder holder, int position) {
        presenter.onBindEventRowViewAtPosition(position, holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getEventsRowCount();
    }

    public class EventsRowViewHolder extends RecyclerView.ViewHolder implements
            RegisteredEventsRecyclerPresenter.RowView {

        final int E_NAME = 0, P_NAME = 1, P_EMAIL = 2, P_PHONE = 3, P_USN = 4, P_MEMBERS = 5, E_REGISTRAR = 6;
        @BindViews({R.id.tv_row_event_name,
                R.id.tv_row_event_participant_name,
                R.id.tv_row_event_participant_email,
                R.id.tv_row_event_participant_phone,
                R.id.tv_row_event_participant_usn,
                R.id.tv_row_event_participant_members,
                R.id.tv_row_event_participant_registrar})
        List<TextView> eventsRegistrationTextViewList;
        @BindView(R.id.iv_events_image)
        ImageView eventsBanner;
        private View viewItem;

        EventsRowViewHolder(View itemView) {
            super(itemView);
            viewItem = itemView;
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setEventImage(String imageBannerUrl) {
            Picasso.with(context).load(imageBannerUrl).error(R.drawable.ic_placeholder)
                    .placeholder(R.drawable.ic_placeholder).into(eventsBanner);
        }

        @Override
        public void setEventName(String eventName) {
            eventsRegistrationTextViewList.get(E_NAME).setText(eventName);
        }

        @Override
        public void setParticipantsName(String participantsName) {
            eventsRegistrationTextViewList.get(P_NAME).setText(participantsName);
        }

        @Override
        public void setParticipantsEmailId(String participantsEmailId) {
            eventsRegistrationTextViewList.get(P_EMAIL).setText(participantsEmailId);
        }

        @Override
        public void setParticipantsPhoneNo(String participantsPhoneNo) {
            eventsRegistrationTextViewList.get(P_PHONE).setText(participantsPhoneNo);
        }

        @Override
        public void setParticipantsUSN(String participantsUSN) {
            eventsRegistrationTextViewList.get(P_USN).setText(participantsUSN);
        }

        @Override
        public void setParticipantsTeamMembers(String participantsTeamMembers) {
            eventsRegistrationTextViewList.get(P_MEMBERS).setText(participantsTeamMembers);
        }

        @Override
        public void setParticipantsRegistrar(String participantsRegistrar) {
            eventsRegistrationTextViewList.get(E_REGISTRAR).setText(participantsRegistrar);
        }

        @Override
        public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
            viewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
