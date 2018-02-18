package com.bharathksunil.interrupt.dashboard.ui.fragments;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bharathksunil.interrupt.R;
import com.bharathksunil.interrupt.util.Debug;
import com.bharathksunil.interrupt.util.Utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass to display details about interrupt.
 */
public class AboutFragment extends Fragment {

    private Unbinder unbinder;
    private final String PROFILE_ID = "bharathksunil";
    private final String APP_PROFILE = "interrupt7.0";
    private static final int COUNTDOWN_UPDATE_INTERVAL = 500;

    private Handler countdownHandler;
    private boolean isThreadActive;
    public AboutFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.tv_count_down_timer)
    TextView countdown_text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dash_fragment_about, container, false);
        unbinder = ButterKnife.bind(this, view);
        isThreadActive = true;
        startCountdown();
        return view;
    }
    /**
     * Stops the  countdown timer.
     */
    private void stopCountdown() {
        if (countdownHandler != null) {
            isThreadActive =false;
            countdownHandler.removeCallbacks(updateCountdown);
            countdownHandler = null;
        }
    }

    /**
     * (Optionally stops) and starts the countdown timer.
     */
    private void startCountdown() {
        stopCountdown();
        countdownHandler = new Handler();
        updateCountdown.run();
    }

    /**
     * Updates the countdown.
     */
    private Runnable updateCountdown = new Runnable() {
        @Override
        public void run() {
            try {
                Date futureDate;
                Calendar cal= GregorianCalendar.getInstance();
                //todo: Make time dynamic
                cal.set(2018,2,6,10,0,0);
                futureDate=cal.getTime();
                String countdownText= Utils.getCountdownText(getContext(),futureDate);
                if (isThreadActive)
                    countdown_text.setText(countdownText);
            } finally {
                if (isThreadActive)
                    countdownHandler.postDelayed(updateCountdown, COUNTDOWN_UPDATE_INTERVAL);
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopCountdown();
        unbinder.unbind();
    }

    @OnClick(R.id.iv_app_fb)
    public void onAppsFBButtonPressed() {
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        String facebookUrl = getFacebookPageURL(APP_PROFILE);
        facebookIntent.setData(Uri.parse(facebookUrl));
        startActivity(facebookIntent);
    }

    @OnClick(R.id.iv_app_instagram)
    public void onAppsInstagramButtonPressed() {
        Uri uri = Uri.parse("http://instagram.com/_u/" + APP_PROFILE);
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/" + APP_PROFILE)));
        }
    }

    @OnClick(R.id.iv_app_play)
    public void onAppsPlayStoreButtonPressed() {
        String appPackageName = getActivity().getApplication().getPackageName();
        Debug.i("ApplicationPackage: " + appPackageName);
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    @OnClick(R.id.iv_dev_fb)
    public void onDevFBButtonPressed() {
        Intent facebookIntent;
        try {
            getContext().getPackageManager()
                    .getPackageInfo("com.facebook.katana", 0); //Checks if FB is even installed.
            facebookIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("fb://profile/" + PROFILE_ID)); //Trys to make intent with FB's URI
        } catch (Exception e) {
            facebookIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/" + PROFILE_ID)); //catches and opens a url to the desired page
        }
        startActivity(facebookIntent);
    }

    @OnClick(R.id.iv_dev_twitter)
    public void onDevTwitterButtonPressed() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + PROFILE_ID)));
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/#!/" + PROFILE_ID)));
        }
    }

    @OnClick(R.id.iv_dev_instagram)
    public void onDevInstagramButtonPressed() {
        Uri uri = Uri.parse("http://instagram.com/_u/the._.developer");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/the._.developer")));
        }
    }

    @OnClick(R.id.iv_dev_play)
    public void onDevPlayButtonPressed() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://dev?id=" + "6734202426383547966")));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=6734202426383547966")));
        }
    }

    @OnClick(R.id.iv_dev_linkedin)
    public void onDevLinkedInButtonPressed() {
        String url = "https://www.linkedin.com/in/" + PROFILE_ID;
        Intent linkedInAppIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        linkedInAppIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        startActivity(linkedInAppIntent);
    }

    @OnClick(R.id.iv_dev_github)
    public void onDevGithubButtonPressed() {
//        String url = "https://www.github.com/" + PROFILE_ID;
        String url = "https://"+PROFILE_ID+".github.io/";
        Intent gitIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(gitIntent);
    }

    private String getFacebookPageURL(String id) {
        PackageManager packageManager = getContext().getPackageManager();
        String FACEBOOK_URL = "https://www.facebook.com/" + id + "/";
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + id + "/";
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }

}
