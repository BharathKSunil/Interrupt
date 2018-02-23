package com.bharathksunil.interrupt.organiser.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bharathksunil.interrupt.R;
import com.bharathksunil.interrupt.admin.model.Users;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * A Recycler Adapter to load the Organisers info
 * Created by Bharath on 23-02-2018.
 */

public class OrganiserInfoRecyclerAdapter extends
        RecyclerView.Adapter<OrganiserInfoRecyclerAdapter.OrganisersInfoViewHolder> {
    private Context context;
    private List<Users> data;

    public OrganiserInfoRecyclerAdapter(Context context, List<Users> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public OrganisersInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrganisersInfoViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_user_info, parent, false));
    }

    @Override
    public void onBindViewHolder(OrganisersInfoViewHolder holder, int position) {
        Users user = data.get(position);
        holder.setUsersName(user.getName());
        holder.setUsersDesignation(user.getDesignation());
        holder.setUsersEmail(user.getEmail());
        holder.setUsersPhoneNo(user.getPhoneNo());
        holder.loadUsersImageFromUrl(user.getProfileUrl());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class OrganisersInfoViewHolder extends RecyclerView.ViewHolder {
        private final int USER = 0, DESIGNATION = 1, EMAIL = 2, PHONE_NO = 3;
        @BindViews({R.id.row_user_name, R.id.row_user_designation, R.id.row_user_email,
                R.id.row_user_phone})
        List<TextView> userInfoTextViewList;
        @BindView(R.id.row_user_image)
        ImageView userImage;

        OrganisersInfoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setUsersName(String name) {
            userInfoTextViewList.get(USER).setText(name);
        }

        void setUsersDesignation(String designation) {
            userInfoTextViewList.get(DESIGNATION).setText(designation);
        }

        void setUsersEmail(String email) {
            userInfoTextViewList.get(EMAIL).setText(email);
        }

        void setUsersPhoneNo(String phoneNo) {
            userInfoTextViewList.get(PHONE_NO).setText(phoneNo);
        }

        void loadUsersImageFromUrl(String imageUrl) {
            Picasso.with(context).load(imageUrl).error(R.drawable.ic_profile)
                    .placeholder(R.drawable.ic_profile).into(userImage);
        }
    }
}
