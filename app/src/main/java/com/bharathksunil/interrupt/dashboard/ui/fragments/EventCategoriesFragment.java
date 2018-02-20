package com.bharathksunil.interrupt.dashboard.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bharathksunil.interrupt.R;
import com.bharathksunil.interrupt.events.presenter.EventCategoriesPresenter;
import com.bharathksunil.interrupt.events.presenter.EventCategoriesPresenterImplementation;
import com.bharathksunil.interrupt.events.repository.Categories;
import com.bharathksunil.interrupt.events.repository.MockEventCategoriesRepositoryImplementation;
import com.bharathksunil.interrupt.events.ui.EventsRecyclerSliderAdapter;
import com.bharathksunil.interrupt.events.ui.EventsViewerActivity;
import com.bharathksunil.interrupt.util.ViewUtils;
import com.ramotion.cardslider.CardSliderLayoutManager;
import com.ramotion.cardslider.CardSnapHelper;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass for showing the event categories
 */
public class EventCategoriesFragment extends Fragment implements EventCategoriesPresenter.View {

    private EventCategoriesPresenter presenter;
    private Unbinder unbinder;
    private CardSliderLayoutManager layoutManger;
    private int currentPosition;

    public EventCategoriesFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.rv_categories)
    RecyclerView recyclerView;
    @BindView(R.id.tv_category_name)
    TextView categoryNameSwitcher;
    @BindView(R.id.tv_category_description)
    TextView categoryDescriptionSwitcher;
    @BindView(R.id.progress_bar)
    AVLoadingIndicatorView loadingIndicatorView;
    @BindString(R.string.err_unexpected_error)
    String err_unexpectedError;
    @BindView(R.id.tv_empty_prompt)
    TextView tv_emptyPrompt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dash_fragment_event_categoies, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new EventCategoriesPresenterImplementation(
                new MockEventCategoriesRepositoryImplementation());
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
    public void loadEventCategoriesRecyclerView(List<String> imageUrls) {

        String[] pics = new String[imageUrls.size()];
        EventsRecyclerSliderAdapter eventsRecyclerSliderAdapter = new EventsRecyclerSliderAdapter(imageUrls.toArray(pics), getContext(),
                new OnCardClickListener());
        layoutManger = new CardSliderLayoutManager(50, 610, 50);
        recyclerView.setLayoutManager(layoutManger);
        recyclerView.setAdapter(eventsRecyclerSliderAdapter);
        recyclerView.setHasFixedSize(true);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    onScrollActiveCardChange();
                }
            }
        });

        new CardSnapHelper().attachToRecyclerView(recyclerView);
    }

    @Override
    public void showNoEventCategoriesAvailable() {
        ViewUtils.setVisible(tv_emptyPrompt);
    }

    @Override
    public void hideNoEventCategoriesAvailable() {
        ViewUtils.setGone(tv_emptyPrompt);
    }

    @Override
    public void initialiseView(Categories categories) {
        categoryNameSwitcher.setText(categories.getName());
        categoryDescriptionSwitcher.setText(categories.getDescription());
    }

    private void onScrollActiveCardChange() {
        final int pos = layoutManger.getActiveCardPosition();
        if (pos == RecyclerView.NO_POSITION || pos == currentPosition) {
            return;
        }
        presenter.onScrollChange(pos);
    }

    @Override
    public void onActiveCardChange(int pos, Categories categories) {
        recyclerView.smoothScrollToPosition(pos);
        categoryNameSwitcher.setText(categories.getName());

        categoryDescriptionSwitcher.setText(categories.getDescription());

        currentPosition = pos;
    }

    @Override
    public void loadEventsViewerActivity() {
        Intent intent = new Intent(getActivity(), EventsViewerActivity.class);
        startActivity(intent);
    }

    private class OnCardClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            final CardSliderLayoutManager lm = (CardSliderLayoutManager) recyclerView.getLayoutManager();

            if (lm.isSmoothScrolling()) {
                return;
            }

            final int activeCardPosition = lm.getActiveCardPosition();
            if (activeCardPosition == RecyclerView.NO_POSITION) {
                return;
            }

            final int clickedPosition = recyclerView.getChildAdapterPosition(view);
            presenter.onCategoriesItemPressed(clickedPosition, activeCardPosition);

        }
    }
}
