package com.bharathksunil.interrupt.events.ui.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;

import com.bharathksunil.interrupt.R;
import com.bharathksunil.interrupt.events.model.EventsManager;
import com.bharathksunil.interrupt.util.TextDrawable;
import com.squareup.picasso.Picasso;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class EventsBannerViewer extends AppCompatActivity {

    @BindView(R.id.iv_slider_card)
    ImageView imageView;
    @BindColor(R.color.turquoise)
    int backGroundColor;
    @BindColor(R.color.white)
    int fontColor;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_activity_banner_viewer);
        unbinder = ButterKnife.bind(this);
        TextDrawable drawable = TextDrawable.builder()
                .beginConfig().textColor(fontColor).bold()
                .fontSize(100).endConfig()
                .buildRect("Interrupt 7.0", backGroundColor);
        Picasso.get().load(EventsManager.getInstance().getCurrentEventBannerURL())
                .fit()
                .error(drawable)
                .placeholder(drawable)
                .into(imageView);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
