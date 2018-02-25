package com.bharathksunil.interrupt.events.ui.activities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.bharathksunil.interrupt.R;
import com.bharathksunil.interrupt.events.model.Events;
import com.bharathksunil.interrupt.events.presenter.EventsViewerPresenter;
import com.bharathksunil.interrupt.events.presenter.EventsViewerPresenterImplementation;
import com.bharathksunil.interrupt.events.repository.FirebaseEventsRepositoryImplementation;
import com.bharathksunil.interrupt.events.ui.EventsRecyclerSliderAdapter;
import com.bharathksunil.interrupt.util.Debug;
import com.bharathksunil.interrupt.util.ViewUtils;
import com.ramotion.cardslider.CardSliderLayoutManager;
import com.ramotion.cardslider.CardSnapHelper;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import butterknife.BindDimen;
import butterknife.BindInt;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class EventsViewerActivity extends AppCompatActivity implements EventsViewerPresenter.View {

    private CardSliderLayoutManager layoutManger;
    private int currentPosition;
    private Unbinder unbinder;
    private EventsViewerPresenter presenter;

    @BindView(R.id.ts_price)
    TextSwitcher priceSwitcher;
    @BindView(R.id.ts_venue)
    TextSwitcher venueSwitcher;
    @BindView(R.id.ts_coordinator)
    TextSwitcher coordinatorSwitcher;
    @BindView(R.id.ts_time)
    TextSwitcher timeSwitcher;
    @BindView(R.id.ts_description)
    TextSwitcher descriptionsSwitcher;
    @BindView(R.id.tv_event_name_1)
    TextView eventName1TextView;
    @BindView(R.id.tv_event_name_2)
    TextView eventName2TextView;
    @BindView(R.id.progress_bar)
    AVLoadingIndicatorView loadingIndicatorView;
    @BindView(R.id.tv_empty_prompt)
    TextView tv_empty_prompt;

    @BindDimen(R.dimen.left_offset)
    int eventNameOffset1;
    @BindDimen(R.dimen.card_width)
    int eventNameOffset2;
    @BindInt(R.integer.labels_animation_duration)
    int eventNameAnimDuration;
    @BindView(R.id.rv_events)
    RecyclerView recyclerView;
    @BindString(R.string.currency)
    String currencySymbol;
    @BindString(R.string.err_unexpected_error)
    String err_unexpected_error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_activity_events_viewer);
        unbinder = ButterKnife.bind(this);
        presenter = new EventsViewerPresenterImplementation(
                new FirebaseEventsRepositoryImplementation());
        presenter.setView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        presenter.setView(null);
    }

    private void setEventNameText(String text, boolean left2right) {
        final TextView invisibleText;
        final TextView visibleText;
        if (eventName1TextView.getAlpha() > eventName2TextView.getAlpha()) {
            visibleText = eventName1TextView;
            invisibleText = eventName2TextView;
        } else {
            visibleText = eventName2TextView;
            invisibleText = eventName1TextView;
        }

        final int vOffset;
        if (left2right) {
            invisibleText.setX(0);
            vOffset = eventNameOffset2;
        } else {
            invisibleText.setX(eventNameOffset2);
            vOffset = 0;
        }

        invisibleText.setText(text);

        final ObjectAnimator iAlpha = ObjectAnimator.ofFloat(invisibleText, "alpha", 1f);
        final ObjectAnimator vAlpha = ObjectAnimator.ofFloat(visibleText, "alpha", 0f);
        final ObjectAnimator iX = ObjectAnimator.ofFloat(invisibleText, "x", eventNameOffset1);
        final ObjectAnimator vX = ObjectAnimator.ofFloat(visibleText, "x", vOffset);

        final AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(iAlpha, vAlpha, iX, vX);
        animSet.setDuration(eventNameAnimDuration);
        animSet.start();
    }

    private void onActiveCardChange() {
        final int pos = layoutManger.getActiveCardPosition();
        if (pos == RecyclerView.NO_POSITION || pos == currentPosition) {
            return;
        }
        presenter.onActiveCardChange(pos);
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
        ViewUtils.errorBar(err_unexpected_error, this);
    }

    @Override
    public void showNoEventsInCategoryText() {
        ViewUtils.setVisible(tv_empty_prompt);
    }

    @Override
    public void hideNoEventsInCategoryText() {
        ViewUtils.setGone(tv_empty_prompt);
    }

    @Override
    public void loadRecyclerView(@NonNull List<String> imageUrls) {
        String[] urls = new String[imageUrls.size()];
        EventsRecyclerSliderAdapter sliderAdapter =
                new EventsRecyclerSliderAdapter(imageUrls.toArray(urls),
                        this,
                        new OnCardClickListener());
        Debug.i("LoadRecyclerCalled:" + urls.length);

        layoutManger = new CardSliderLayoutManager(this);
        recyclerView.setAdapter(sliderAdapter);
        recyclerView.setLayoutManager(layoutManger);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    onActiveCardChange();
                }
            }
        });

        new CardSnapHelper().attachToRecyclerView(recyclerView);
    }

    @Override
    public void initialiseEventDataForFirstCard(@NonNull Events events) {
        eventName1TextView.setX(eventNameOffset1);
        eventName2TextView.setX(eventNameOffset2);
        eventName1TextView.setText(events.getName());
        eventName2TextView.setAlpha(0f);

        priceSwitcher.setFactory(new TextViewFactory(R.style.PriceTextView, true));
        priceSwitcher.setCurrentText(currencySymbol + events.getPrice());

        descriptionsSwitcher.setInAnimation(this, android.R.anim.fade_in);
        descriptionsSwitcher.setOutAnimation(this, android.R.anim.fade_out);
        descriptionsSwitcher.setFactory(new TextViewFactory(R.style.DescriptionTextView, false));
        descriptionsSwitcher.setCurrentText(events.getDescription());

        timeSwitcher.setFactory(new TextViewFactory(R.style.ClockTextView, false));
        timeSwitcher.setCurrentText(events.getDateTime());

        venueSwitcher.setFactory(new TextViewFactory(R.style.VenueTextView, false));
        venueSwitcher.setCurrentText(events.getVenue());

        coordinatorSwitcher.setFactory(new TextViewFactory(R.style.CoordinatorsTextView, false));
        coordinatorSwitcher.setCurrentText(events.getCoordinators().get(0));
    }

    @Override
    public void setActiveSlide(int pos, @NonNull Events event) {
        int animH[] = new int[]{R.anim.slide_in_right, R.anim.slide_out_left};
        int animV[] = new int[]{R.anim.slide_in_top, R.anim.slide_out_bottom};

        final boolean left2right = pos < currentPosition;
        if (left2right) {
            animH[0] = R.anim.slide_in_left;
            animH[1] = R.anim.slide_out_right;

            animV[0] = R.anim.slide_in_bottom;
            animV[1] = R.anim.slide_out_top;
        }


        setEventNameText(event.getName(), left2right);

        priceSwitcher.setInAnimation(this, animH[0]);
        priceSwitcher.setOutAnimation(this, animH[1]);
        priceSwitcher.setText(currencySymbol + event.getPrice());

        descriptionsSwitcher.setText(event.getDescription());

        timeSwitcher.setInAnimation(this, animV[0]);
        timeSwitcher.setOutAnimation(this, animV[1]);
        timeSwitcher.setText(event.getDateTime());

        venueSwitcher.setInAnimation(this, animV[0]);
        venueSwitcher.setOutAnimation(this, animV[1]);
        venueSwitcher.setText(event.getVenue());

        coordinatorSwitcher.setInAnimation(this, animV[0]);
        coordinatorSwitcher.setOutAnimation(this, animV[1]);
        coordinatorSwitcher.setText(event.getCoordinators().get(0));

        currentPosition = pos;
    }

    private class TextViewFactory implements ViewSwitcher.ViewFactory {

        @StyleRes
        final int styleId;
        final boolean center;

        TextViewFactory(@StyleRes int styleId, boolean center) {
            this.styleId = styleId;
            this.center = center;
        }

        @SuppressWarnings("deprecation")
        @Override
        public View makeView() {
            final TextView textView = new TextView(EventsViewerActivity.this);

            if (center) {
                textView.setGravity(Gravity.CENTER);
            }

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                textView.setTextAppearance(EventsViewerActivity.this, styleId);
            } else {
                textView.setTextAppearance(styleId);
            }

            return textView;
        }

    }

    private class OnCardClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            if (layoutManger.isSmoothScrolling()) {
                return;
            }

            final int activeCardPosition = layoutManger.getActiveCardPosition();
            if (activeCardPosition == RecyclerView.NO_POSITION) {
                return;
            }

            final int clickedPosition = recyclerView.getChildAdapterPosition(view);
            if (clickedPosition == activeCardPosition) {
                startActivity(new Intent(EventsViewerActivity.this, EventsBannerViewer.class));
            } else if (clickedPosition > activeCardPosition) {
                recyclerView.smoothScrollToPosition(clickedPosition);
                presenter.onActiveCardChange(clickedPosition);
            }
        }
    }

}
