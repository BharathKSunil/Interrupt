package com.bharathksunil.interrupt.admin.ui;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bharathksunil.interrupt.OnItemClickListener;
import com.bharathksunil.interrupt.R;
import com.bharathksunil.interrupt.admin.presenter.UsersInfoRecyclerPresenter;
import com.bharathksunil.interrupt.util.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * The {@link RecyclerView} Adapter for Loading user Data
 *
 * @author Bharath on 19-02-2018.
 */

public class UserInfoRecyclerAdapter extends RecyclerView.Adapter<UserInfoRecyclerAdapter.UserInfoViewHolder>{

    private UsersInfoRecyclerPresenter presenter;
    private Context context;

    public UserInfoRecyclerAdapter(UsersInfoRecyclerPresenter presenter, Context context) {
        this.presenter = presenter;
        this.context = context;
    }

    @Override
    public UserInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserInfoViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.row_user_info, parent, false));
    }

    @Override
    public void onBindViewHolder(UserInfoViewHolder holder, int position) {
        presenter.onBindEventRowViewAtPosition(position, holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getUsersRowCount();
    }

    class UserInfoViewHolder extends RecyclerView.ViewHolder implements UsersInfoRecyclerPresenter.RowView {
        private final int USER = 0, DESIGNATION = 1, EMAIL = 2, PHONE_NO = 3;
        @BindViews({R.id.row_user_name, R.id.row_user_designation, R.id.row_user_email,
                R.id.row_user_phone})
        List<TextView> userInfoTextViewList;
        @BindView(R.id.row_user_image)
        ImageView userImage;
        private View viewItem;

        UserInfoViewHolder(View itemView) {
            super(itemView);
            viewItem = itemView;
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setUsersName(String name) {
            userInfoTextViewList.get(USER).setText(name);
        }

        @Override
        public void setUsersDesignation(String designation) {
            userInfoTextViewList.get(DESIGNATION).setText(designation);
        }

        @Override
        public void setUsersEmail(String email) {
            userInfoTextViewList.get(EMAIL).setText(email);
        }

        @Override
        public void setUsersPhoneNo(String phoneNo) {
            userInfoTextViewList.get(PHONE_NO).setText(phoneNo);
        }

        @Override
        public void loadUsersImageFromUrl(String imageUrl) {
            Picasso.get().load(imageUrl).error(R.drawable.ic_profile)
                    .transform(new CircleTransform())
                    .placeholder(R.drawable.ic_profile).into(userImage);
        }

        @Override
        public void setOnItemClickListener(final OnItemClickListener itemClickListener) {
            viewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
