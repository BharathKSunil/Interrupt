package com.bharathksunil.interrupt.events.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bharathksunil.interrupt.R;

public class EventsBannerViewer extends AppCompatActivity {

    static final String BUNDLE_IMAGE_ID = "BUNDLE_IMAGE_ID";

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_activity_banner_viewer);

        /*final int smallResId = getIntent().getIntExtra(BUNDLE_IMAGE_ID, -1);
        if (smallResId == -1) {
            finish();
            return;
        }

        imageView.setImageResource(smallResId);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventsBannerViewer.super.onBackPressed();
            }
        });

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            loadFullSizeBitmap(smallResId);
        } else {
            getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {

                private boolean isClosing = false;

                @Override public void onTransitionPause(Transition transition) {}
                @Override public void onTransitionResume(Transition transition) {}
                @Override public void onTransitionCancel(Transition transition) {}

                @Override public void onTransitionStart(Transition transition) {
                    if (isClosing) {
                        addCardCorners();
                    }
                }

                @Override public void onTransitionEnd(Transition transition) {
                    if (!isClosing) {
                        isClosing = true;

                        removeCardCorners();
                    }
                }
            });
        }*/
    }

}
