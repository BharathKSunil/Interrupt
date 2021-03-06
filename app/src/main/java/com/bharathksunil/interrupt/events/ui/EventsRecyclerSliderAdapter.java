package com.bharathksunil.interrupt.events.ui;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bharathksunil.interrupt.R;
import com.bharathksunil.interrupt.util.TextDrawable;
import com.squareup.picasso.Picasso;

/**
 * @author Bharath on 19-02-2018.
 */

public class EventsRecyclerSliderAdapter extends RecyclerView.Adapter<EventsRecyclerSliderAdapter.SliderCard> {

    private Context context;
    private final String[] urls;
    private final View.OnClickListener listener;

    public EventsRecyclerSliderAdapter(String[] urls, Context context, View.OnClickListener listener) {
        this.urls = urls;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public SliderCard onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.layout_slider_card, parent, false);

        if (listener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(view);
                }
            });
        }

        return new SliderCard(view, context);
    }

    @Override
    public void onBindViewHolder(SliderCard holder, int position) {
        holder.setContent(urls[position]);
    }

    @Override
    public int getItemCount() {
        return urls.length;
    }

    /**
     * This is the View Holder for the events and the event categories
     * and the events
     *
     * @author Bharath on 19-02-2018.
     */

    class SliderCard extends RecyclerView.ViewHolder {

        private int viewWidth = 0;
        private int viewHeight = 0;

        private final ImageView imageView;
        private Context context;


        SliderCard(View itemView, Context context) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_slider_card);
            this.context = context;
        }

        void setContent(final String imgUrl) {
            if (viewWidth == 0) {
                itemView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        itemView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                        viewWidth = itemView.getWidth();
                        viewHeight = itemView.getHeight();
                        loadImage(imgUrl);
                    }
                });
            } else {
                loadImage(imgUrl);
            }
        }

        private void loadImage(String imgUrl) {
            TextDrawable drawable = TextDrawable.builder()
                    .beginConfig().textColor(context.getResources().getColor(R.color.white)).bold()
                    .fontSize(65).endConfig()
                    .buildRect("Interrupt 7", context.getResources().getColor(R.color.admin_bg));
            Picasso.get().load(imgUrl)
                    .placeholder(drawable)
                    .error(drawable)
                    .fit()
                    .into(imageView);
        }
    }

}