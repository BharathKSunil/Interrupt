package com.bharathksunil.interrupt;

/**
 * This is the common view operations
 * @author Bharath on 26-01-2018.
 */

public interface BaseView {
    /**
     * Called when the presenter starts its work
     * enable any dialog-box or progress bars
     */
    void onProcessStarted();

    /**
     * Called when the presenter has finished its work
     * disable any dialog-box or progress bars
     */
    void onProcessEnded();

    /**
     * Called whenever there was an unexpected error in the process
     * Display any toast or messages to the user indicating that there was an error
     */
    void onUnexpectedError();
}
