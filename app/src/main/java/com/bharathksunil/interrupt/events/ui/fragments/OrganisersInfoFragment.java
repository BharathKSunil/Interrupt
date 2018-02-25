package com.bharathksunil.interrupt.events.ui.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bharathksunil.interrupt.OnItemClickListener;
import com.bharathksunil.interrupt.R;
import com.bharathksunil.interrupt.admin.model.Users;
import com.bharathksunil.interrupt.admin.repository.FirebaseUsersInfoFetchRepositoryImplementation;
import com.bharathksunil.interrupt.events.presenter.OrganisersInfoPresenter;
import com.bharathksunil.interrupt.events.presenter.OrganisersInfoPresenterImplementation;
import com.bharathksunil.interrupt.flipviewpager.adapter.BaseFlipAdapter;
import com.bharathksunil.interrupt.flipviewpager.utils.FlipSettings;
import com.bharathksunil.interrupt.events.ui.OrganiserInfoRecyclerAdapter;
import com.bharathksunil.interrupt.util.TextDrawable;
import com.bharathksunil.interrupt.util.ViewUtils;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass to view all the organisers.
 */
public class OrganisersInfoFragment extends Fragment implements OrganisersInfoPresenter.View {

    private Unbinder unbinder;
    private OrganisersInfoPresenter presenter;
    private boolean listItemClickedOnce;

    public OrganisersInfoFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.rv_core_team)
    RecyclerView coreTeamRecyclerView;
    @BindView(R.id.rv_events_team)
    RecyclerView eventsTeamRecyclerView;
    @BindView(R.id.rv_offstage_team)
    RecyclerView offStageTeamRecyclerView;
    @BindView(R.id.rv_design_team)
    RecyclerView designTeamRecyclerView;

    @BindView(R.id.progress_bar)
    AVLoadingIndicatorView loadingIndicatorView;

    @BindView(R.id.tv_empty_prompt_team_core)
    TextView tv_empty_prompt_team_core;
    @BindView(R.id.tv_empty_prompt_team_event)
    TextView tv_empty_prompt_team_events;
    @BindView(R.id.tv_empty_prompt_team_offstage)
    TextView tv_empty_prompt_team_offstage;
    @BindView(R.id.tv_empty_prompt_team_design)
    TextView tv_empty_prompt_team_design;

    @BindArray(R.array.flip_pager_bg_colors)
    int[] bg_colors;
    @BindString(R.string.err_unexpected_error)
    String err_unexpected_error;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listItemClickedOnce = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dash_fragment_organisers_info, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new OrganisersInfoPresenterImplementation(new FirebaseUsersInfoFetchRepositoryImplementation());
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
        ViewUtils.errorBar(err_unexpected_error, getActivity());
    }

    @Override
    public void showNoCoreTeamDataFound() {
        ViewUtils.setVisible(tv_empty_prompt_team_core);
    }

    @Override
    public void showNoEventsTeamDataFound() {
        ViewUtils.setVisible(tv_empty_prompt_team_events);
    }

    @Override
    public void showNoOffStageTeamDataFound() {
        ViewUtils.setVisible(tv_empty_prompt_team_offstage);
    }

    @Override
    public void showNoDesignTeamDataFound() {
        ViewUtils.setVisible(tv_empty_prompt_team_design);
    }

    @Override
    public void hideNoCoreTeamsDataFound() {
        ViewUtils.setGone(tv_empty_prompt_team_core);
    }

    @Override
    public void hideNoEventsTeamsDataFound() {
        ViewUtils.setGone(tv_empty_prompt_team_events);
    }

    @Override
    public void hideNoOffStageTeamsDataFound() {
        ViewUtils.setGone(tv_empty_prompt_team_offstage);
    }

    @Override
    public void hideNoDesignTeamsDataFound() {
        ViewUtils.setGone(tv_empty_prompt_team_design);
    }

    @Override
    public void loadEventsTeamsRecyclerView(List<Users> data) {
        eventsTeamRecyclerView.setAdapter(new OrganiserInfoRecyclerAdapter(getActivity(), data));
        eventsTeamRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        eventsTeamRecyclerView.setNestedScrollingEnabled(false);
        eventsTeamRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void loadOffStageTeamsRecyclerView(List<Users> data) {
        offStageTeamRecyclerView.setAdapter(new OrganiserInfoRecyclerAdapter(getActivity(), data));
        offStageTeamRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        offStageTeamRecyclerView.setNestedScrollingEnabled(false);
        offStageTeamRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public void loadDesignTeamsRecyclerView(List<Users> data) {
        designTeamRecyclerView.setAdapter(new OrganiserInfoRecyclerAdapter(getActivity(), data));
        designTeamRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        designTeamRecyclerView.setNestedScrollingEnabled(false);
        designTeamRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void loadCoreTeamsRecyclerView(final List<Users> data) {
        FlipSettings settings = new FlipSettings.Builder().defaultPage(1).build();
        final OrganisersAdapter organisersAdapter = new OrganisersAdapter(getContext(), data, settings);
        organisersAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int itemPosition) {
                if (!listItemClickedOnce) {
                    Toast.makeText(getContext(), "Swipe Card to View More, Click Again to Contact", Toast.LENGTH_LONG).show();
                    listItemClickedOnce = true;
                } else
                    handleListItemClick(data.get(itemPosition));
            }
        });
        coreTeamRecyclerView.setAdapter(organisersAdapter);
        coreTeamRecyclerView.setHasFixedSize(true);
        coreTeamRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        coreTeamRecyclerView.setItemAnimator(new DefaultItemAnimator());
        coreTeamRecyclerView.setNestedScrollingEnabled(false);

    }

    private void handleListItemClick(final Users organiser) {
        new AlertDialog.Builder(getContext())
                .setTitle("Contact Organiser")
                .setMessage("Call or Email " + organiser.getName() + ".?")
                .setPositiveButton("Call", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + organiser.getPhoneNo()));
                        startActivity(intent);
                    }
                })
                .setNeutralButton("Email", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        composeEmail(new String[]{organiser.getEmail()});
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create()
                .show();

    }

    private void composeEmail(String[] addresses) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Interrupt7.0: <Subject Here>");
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private int getRandomBackground() {
        return bg_colors[new Random().nextInt(bg_colors.length)];
    }

    class OrganisersAdapter extends BaseFlipAdapter {

        private final int PAGES = 3;
        private int[] IDS_INTEREST = {R.id.interest_1, R.id.interest_2, R.id.interest_3};
        private OnItemClickListener listener;

        OrganisersAdapter(Context context, List<Users> items, FlipSettings settings) {
            //noinspection unchecked
            super(context, items, settings);
        }

        @Override
        public View getPage(int position, View convertView, ViewGroup parent, Object organiser1,
                            Object organiser2, BaseFlipAdapter.CloseListener closeListener) {
            final OrganisersHolderView holder;
            if (convertView == null) {
                holder = new OrganisersHolderView();
                convertView = getLayoutInflater().inflate(R.layout.dash_organisers_merged_page, parent, false);
                holder.leftAvatar = convertView.findViewById(R.id.first);
                holder.rightAvatar = convertView.findViewById(R.id.second);
                holder.infoPage = getLayoutInflater().inflate(R.layout.layout_organisers_info, parent, false);
                holder.name = holder.infoPage.findViewById(R.id.tv_name);

                for (int id : IDS_INTEREST)
                    holder.interests.add((TextView) holder.infoPage.findViewById(id));
                holder.email = holder.infoPage.findViewById(R.id.tv_email);
                holder.phoneNo = holder.infoPage.findViewById(R.id.tv_phoneNo);
                convertView.setTag(holder);
            } else {
                holder = (OrganisersHolderView) convertView.getTag();
            }

            switch (position) {
                // Merged page with 2 organisers
                case 1:
                    String profileUrl = ((Users) organiser1).getProfileUrl();
                    String name = ((Users) organiser1).getName().substring(1);
                    TextDrawable drawable = TextDrawable.builder()
                            .beginConfig().textColor(getContext().getResources().getColor(R.color.white)).bold()
                            .fontSize(100).endConfig()
                            .buildRect(name, getContext().getResources().getColor(R.color.silver));
                    Picasso.with(getContext()).load(profileUrl)
                            .placeholder(drawable)
                            .error(drawable)
                            .into(holder.leftAvatar);
                    if (organiser2 != null) {
                        profileUrl = ((Users) organiser2).getProfileUrl();
                        name = ((Users) organiser2).getName().substring(1);
                        drawable = TextDrawable.builder()
                                .beginConfig().textColor(getContext().getResources().getColor(R.color.white)).bold()
                                .fontSize(100).endConfig()
                                .buildRect(name, getContext().getResources().getColor(R.color.silver));
                        Picasso.with(getContext()).load(profileUrl)
                                .placeholder(drawable)
                                .error(drawable)
                                .into(holder.rightAvatar);
                    }
                    break;
                default:
                    fillHolder(holder, position == 0 ? (Users) organiser1 : (Users) organiser2);
                    holder.infoPage.setTag(holder);
                    return holder.infoPage;
            }

            return convertView;
        }

        @Override
        public int getPagesCount() {
            return PAGES;
        }

        private void fillHolder(OrganisersHolderView holder, Users organisers) {
            if (organisers == null)
                return;

            Iterator<TextView> iViews = holder.interests.iterator();
            Iterator<String> iInterests = organisers.getRoles().iterator();
            while (iViews.hasNext() && iInterests.hasNext())
                iViews.next().setText(iInterests.next());

            holder.infoPage.setBackgroundColor(getRandomBackground());
            holder.name.setText(organisers.getName());
            holder.email.setText(organisers.getEmail());
            holder.phoneNo.setText(organisers.getPhoneNo());
        }

        void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.listener = onItemClickListener;
        }

        @Override
        public void onItemClick(int itemPosition) {
            if (listener != null) {
                listener.onItemClick(itemPosition);
            }
        }

        class OrganisersHolderView {
            ImageView leftAvatar;
            ImageView rightAvatar;
            View infoPage;
            TextView email, name, phoneNo;

            List<TextView> interests = new ArrayList<>();
        }
    }
}
