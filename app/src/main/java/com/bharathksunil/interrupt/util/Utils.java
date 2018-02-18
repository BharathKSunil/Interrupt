package com.bharathksunil.interrupt.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import com.bharathksunil.interrupt.R;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * <h>Utils</h>
 * This is a Java Class Which provides many generic utilities for functioning of the app
 * <br/>
 *
 * @author Bharath <email>bharathk.sunil.k@gmail.com</email>
 */
public class Utils {
    /**
     * This function is used to check if the App is connected to the Internet
     * <b>NOTE:</b> The App Must have a Permission :
     * <uses-permission android:name="android.permission.INTERNET" />
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
     *
     * @return true if the app is connected to the internet
     */
    public static boolean isConnected(@NonNull Activity context) {
        ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cManager != null ? cManager.getActiveNetworkInfo() : null;
        //IF THE NETWORK IS AVAILABLE:
        return (nInfo != null && nInfo.isConnected());
    }

    /**
     * THIS IS TO SELECT A PICTURE FROM GALLERY TO UPLOAD THE PROFILE PIC
     */
    public static void showFileChooser(@NonNull String type, @NonNull String title,
                                       int requestCode, @NonNull Fragment context) {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType(type);
        context.startActivityForResult(getIntent, requestCode);
    }

    public static String getMediaPathFromURI(Uri contentUri, Context context) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        if (cursor != null) {
            cursor.close();
        }
        return res;
    }

    public static boolean isStoragePermissionGranted(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (activity.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    /**
     * Returns a formatted string containing the amount of time (days, hours,
     * minutes, seconds) between the current time and the specified future date.
     *
     * @param context    THE ACTIVITY CONTEXT
     * @param futureDate THE DATE TO WHICH THE COUNTDOWN IS SET TO
     * @return A STRING OF THE FORM 10 DAYS 17 HRS 23 MIN 16 SEC to GO.!
     */
    public static String getCountdownText(Context context, Date futureDate) {
        StringBuilder countdownText = new StringBuilder();

        // Calculate the time between now and the future date.
        long future = futureDate.getTime(), current = Calendar.getInstance().getTime().getTime();
        long timeRemaining = future - current;

        // If there is no time between (ie. the date is now or in the past), do nothing
        if (timeRemaining > 0) {
            Resources resources = context.getResources();

            // Calculate the days/hours/minutes/seconds within the time difference.
            //
            // It's important to subtract the value from the total time remaining after each is calculated.
            // For example, if we didn't do this and the time was 25 hours from now,
            // we would get `1 day, 25 hours`.
            int days = (int) TimeUnit.MILLISECONDS.toDays(timeRemaining);
            timeRemaining -= TimeUnit.DAYS.toMillis(days);
            int hours = (int) TimeUnit.MILLISECONDS.toHours(timeRemaining);
            timeRemaining -= TimeUnit.HOURS.toMillis(hours);
            int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(timeRemaining);
            timeRemaining -= TimeUnit.MINUTES.toMillis(minutes);
            int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(timeRemaining);

            // For each time unit, add the quantity string to the output, with a space.
            if (days > 0) {
                countdownText.append(resources.getQuantityString(R.plurals.days, days, days));
                countdownText.append(" ");
            }
            if (days > 0 || hours > 0) {
                countdownText.append(resources.getQuantityString(R.plurals.hours, hours, hours));
                countdownText.append(" ");
            }
            if (days > 0 || hours > 0 || minutes > 0) {
                countdownText.append(resources.getQuantityString(R.plurals.minutes, minutes, minutes));
                countdownText.append(" ");
            }
            if (days == 0 && (hours > 0 || minutes > 0 || seconds > 0)) {
                countdownText.append(resources.getQuantityString(R.plurals.seconds, seconds, seconds));
                countdownText.append(" ");
            }
            countdownText.append("to Go.!");
        } else {
            Calendar cal = Calendar.getInstance();
            Calendar finalDate = Calendar.getInstance();
            //todo: Make time dynamic
            finalDate.set(2018, 2, 7, 14, 0, 0);
            if (cal.compareTo(finalDate) > 0)
                countdownText.append("See you Next Year.!");
            else
                countdownText.append("The Extravaganza Has Begun.!!");
        }

        return countdownText.toString();
    }

}
