package com.bharathksunil.interrupt.util;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
/**
 * <h>Debug</h>
 * THIS IS A HOUSE KEEPING CLASS WHICH HELPS IN SENDING LOG MESSAGES, TOASTS AND simple SNACK BAR
 *
 * @author Bharath Kumar S
 * @since 04-01-2017
 */
public class Debug {
    /**
     * THIS METHOD PRINTS THE ERROR MESSAGE TO THE LOGGER
     * FORMAT: Exception Occurred in:  <class_name>.<method_name> : error message
     * @param message THIS GIVES THE CLASS_NAME.METHOD_NAME AT WHICH THE ERROR OCCURRED AND THE ERROR MESSAGE
     */
    public static void e(String message) {
        Log.e("BKS", "Exception Occurred in: " + message);
    }

    /**
     * THIS METHOD IS USED TO ANALISE THE EXECUTION, IT CAN BE USED TO PRINT VALUE OF ANY OBJECT AND IS HELP_FULL IN ANALYSING AND DEBUGGING
     * @param message THIS IS THE MESSAGE VIZ TO BE PRINTED
     */
    public static void i(String message) {
        Log.i("BKS", message);
    }

    /**
     * METHOD IS USED TO MAKE A TOAST
     * @param message THE MESSAGE TO BE TOASTED
     * @param context THE CONTEXT ON WHICH IT HAS TO BE TOASTED
     */
    public static void toast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * METHOD USED TO MAKE A SNACK BAR
     * @param message THE MESSAGE TO BE DISPLAYED
     * @param view THE VIEW OF THE LAYOUT ON WHICH THE SNACK BAR IS TO BE DRAWN
     * @param duration THE DURATION OF THE MESSAGE: Snackbar.LENGTH_SHORT or Snackbar.LENGTH_LONG
     * <B>NOTE:</B> THIS DOES NOT SUPPORT Snackbar.LENGTH_INDEFINITE AND CANNOT HAVE A BUTTON ON THE SNACK BAR
     */
    public static void snack(String message, View view, int duration) {
        //Snackbar.make(view, message, duration).show();
    }


}
