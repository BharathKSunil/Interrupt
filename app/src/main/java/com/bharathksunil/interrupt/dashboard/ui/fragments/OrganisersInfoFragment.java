package com.bharathksunil.interrupt.dashboard.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bharathksunil.interrupt.R;
import com.bharathksunil.interrupt.admin.repository.FirebaseUsersInfoFetchRepositoryImplementation;
import com.bharathksunil.interrupt.admin.repository.Users;
import com.bharathksunil.interrupt.dashboard.presenter.OrganisersInfoPresenter;
import com.bharathksunil.interrupt.dashboard.presenter.OrganisersInfoPresenterImplementation;
import com.bharathksunil.interrupt.util.Debug;
import com.bharathksunil.interrupt.util.ViewUtils;
import com.squareup.picasso.Picasso;
import com.yalantis.flipviewpager.adapter.BaseFlipAdapter;
import com.yalantis.flipviewpager.utils.FlipSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass to view all the organisers.
 * todo: Incomplete
 */
public class OrganisersInfoFragment extends Fragment implements OrganisersInfoPresenter.View {

    private Unbinder unbinder;
    private OrganisersInfoPresenter presenter;

    public OrganisersInfoFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.lv_organisers)
    ListView organisersListView;

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

    private int getRandomBackground() {
        int[] colors = {
                R.color.sienna,
                R.color.saffron,
                R.color.green,
                R.color.pink,
                R.color.orange,
                R.color.purple
        };
        return colors[new Random().nextInt(5)];
    }

    @Override
    public void onProcessStarted() {

    }

    @Override
    public void onProcessEnded() {

    }

    @Override
    public void onUnexpectedError() {
        ViewUtils.errorBar("oops Something went wrong", getActivity());
    }

    @Override
    public void loadOrganisersListView(List<Users> data) {
        FlipSettings settings = new FlipSettings.Builder().defaultPage(1).build();
        organisersListView.setAdapter(new OrganisersAdapter(getContext(), data, settings));
        organisersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Users f = (Users) organisersListView.getAdapter().getItem(position);

                Debug.i("Clicked" + f.getName());
            }
        });
    }

    @Override
    public void showNoOrganisersDataFound() {

    }

    @Override
    public void hideNoOrganisersDataFound() {

    }

    class OrganisersAdapter extends BaseFlipAdapter {

        private final int PAGES = 3;
        private int[] IDS_INTEREST = {R.id.interest_1, R.id.interest_2, R.id.interest_3, R.id.interest_4, R.id.interest_5};

        OrganisersAdapter(Context context, List<Users> items, FlipSettings settings) {
            //noinspection unchecked
            super(context, items, settings);
        }

        @Override
        public View getPage(int position, View convertView, ViewGroup parent, Object organiser1,
                            Object organiser2) {
            final OrganisersHolder holder;

            if (convertView == null) {
                holder = new OrganisersHolder();
                convertView = getLayoutInflater().inflate(R.layout.dash_organisers_merged_page, parent, false);
                holder.leftAvatar = convertView.findViewById(R.id.first);
                holder.rightAvatar = convertView.findViewById(R.id.second);
                holder.infoPage = getLayoutInflater().inflate(R.layout.layout_organisers_info, parent, false);
                holder.name = holder.infoPage.findViewById(R.id.nickname);

                for (int id : IDS_INTEREST)
                    holder.interests.add((TextView) holder.infoPage.findViewById(id));

                convertView.setTag(holder);
            } else {
                holder = (OrganisersHolder) convertView.getTag();
            }

            switch (position) {
                // Merged page with 2 friends
                case 1:
                    Picasso.with(getContext()).load(((Users) organiser1).getProfileUrl())
                            .placeholder(R.drawable.ic_profile).into(holder.leftAvatar);
                    if (organiser2 != null)
                        Picasso.with(getContext()).load(((Users) organiser2).getProfileUrl())
                                .placeholder(R.drawable.ic_profile).into(holder.leftAvatar);
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

        private void fillHolder(OrganisersHolder holder, Users organisers) {
            if (organisers == null)
                return;
            Iterator<TextView> iViews = holder.interests.iterator();
            Iterator<String> iInterests = Arrays.asList(organisers.getRoles()).iterator();
            while (iViews.hasNext() && iInterests.hasNext())
                iViews.next().setText(iInterests.next());
            holder.infoPage.setBackgroundColor(getResources().getColor(getRandomBackground()));
            holder.name.setText(organisers.getName());
        }

        class OrganisersHolder {
            ImageView leftAvatar;
            ImageView rightAvatar;
            View infoPage;

            List<TextView> interests = new ArrayList<>();
            TextView name;
        }
    }
}
