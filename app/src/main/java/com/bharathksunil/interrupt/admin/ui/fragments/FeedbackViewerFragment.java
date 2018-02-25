package com.bharathksunil.interrupt.admin.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bharathksunil.interrupt.R;
import com.bharathksunil.interrupt.admin.model.Feedback;
import com.bharathksunil.interrupt.admin.presenter.FeedbackPresenter;
import com.bharathksunil.interrupt.admin.presenter.FeedbackPresenterImplementation;
import com.bharathksunil.interrupt.admin.repository.FirebaseFeedbackRepository;
import com.bharathksunil.interrupt.auth.presenter.FormErrorType;
import com.bharathksunil.interrupt.util.ViewUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass which shows the feedback
 */
public class FeedbackViewerFragment extends Fragment implements FeedbackPresenter.View {


    private Unbinder unbinder;
    private FeedbackPresenter presenter;

    @BindView(R.id.progress_bar)
    AVLoadingIndicatorView loadingIndicatorView;
    @BindView(R.id.tv_empty_prompt)
    TextView tv_emptyPrompt;

    @BindView(R.id.rv_feedback)
    RecyclerView recyclerView;

    @BindString(R.string.err_unexpected_error)
    String err_unexpectedError;

    public FeedbackViewerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_feedback_viewer, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new FeedbackPresenterImplementation(new FirebaseFeedbackRepository());

        presenter.setView(this);
        presenter.loadAllFeedback();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.setView(null);
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
    public void loadFeedbackRecyclerView(List<Feedback> feedbacks) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new FeedbackRecyclerAdapter(feedbacks, getContext()));
    }

    @Override
    public void showNoFeedbackText() {
        ViewUtils.setVisible(tv_emptyPrompt);
    }

    @Override
    public void hideNoFeedbackText() {
        ViewUtils.setGone(tv_emptyPrompt);
    }

    @Override
    public String getFeedbackFromField() {
        //not required here
        return null;
    }

    @Override
    public void setFeedbackFieldError(FormErrorType error) {
        //not required here
    }

    @Override
    public void showFeedbackSuccessfullyPosted() {
        //not required here
    }

    @Override
    public void showFeedbackPostFailed() {
        //not required here
    }


    class FeedbackRecyclerAdapter extends RecyclerView.Adapter<FeedbackRecyclerAdapter.FeedbackViewHolder> {

        private List<Feedback> data;
        private LayoutInflater inflater;

        FeedbackRecyclerAdapter(List<Feedback> data, Context context) {
            this.data = data;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public FeedbackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new FeedbackViewHolder(inflater
                    .inflate(R.layout.row_feedback, parent, false));
        }

        @Override
        public void onBindViewHolder(FeedbackViewHolder holder, int position) {
            holder.bind(data.get(position), holder);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class FeedbackViewHolder extends RecyclerView.ViewHolder {
            @BindViews({R.id.tv_row_feedback_time, R.id.tv_row_feedback_name,
                    R.id.tv_row_feedback_email, R.id.tv_row_feedback_text})
            List<TextView> textViewList;

            FeedbackViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            void bind(Feedback feedback, FeedbackViewHolder holder) {
                holder.textViewList.get(0).setText(feedback.getTime());
                holder.textViewList.get(1).setText(feedback.getName());
                holder.textViewList.get(2).setText(feedback.getEmailId());
                holder.textViewList.get(3).setText(feedback.getFeedback());
            }
        }
    }
}
