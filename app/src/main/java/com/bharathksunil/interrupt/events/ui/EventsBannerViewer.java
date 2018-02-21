package com.bharathksunil.interrupt.events.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bharathksunil.interrupt.R;
import com.bharathksunil.interrupt.events.model.EventsManager;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class EventsBannerViewer extends AppCompatActivity {

    @BindView(R.id.iv_slider_card)
    ImageView imageView;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_activity_banner_viewer);
        unbinder = ButterKnife.bind(this);
        Picasso.with(this).load(EventsManager.getInstance().getCurrentEventBannerURL())
                .fit()
                .error(R.drawable.app_icon)
                .placeholder(R.drawable.ic_placeholder)
                .into(imageView);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
