package com.bharathksunil.interrupt.util;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.Stack;

/**
 * TODO: Not Correct Implementation, Need Help
 * @author Bharath Kumar S on 13-01-2018.
 */
public class FragmentStackHelper {

    private Stack<String> fragmentStack;
    private OnActiveFragmentListener listener;
    private boolean fragmentWasAdded;

    public interface OnActiveFragmentListener{
        /**
         * This gives the currently active fragment in the activity. You may use this when you
         * want to make dynamic changes in the activity depending on which fragment is active
         * like updating the title in the AppBar/TitleBar, enabling/disabling views or updating the
         * FloatingActionButton and its click response
         * @param tag the unique Tag of the fragment which is currently active
         */
        void onFragmentActive(String tag);
    }

    public FragmentStackHelper(final AppCompatActivity activity, OnActiveFragmentListener activeFragmentListener){
        fragmentWasAdded = true;
        fragmentStack = new Stack<>();
        listener = activeFragmentListener;
        activity.getSupportFragmentManager()
                .addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        if (fragmentWasAdded) {
                            fragmentWasAdded = false;
                        }else{
                            fragmentRemoved();
                            if (listener != null && getActiveFragment()!=null)
                                listener.onFragmentActive(getActiveFragment());
                        }
                    }
                });
    }
    public FragmentStackHelper(AppCompatActivity activity) {
        fragmentStack = new Stack<>();
        activity.getSupportFragmentManager()
                .addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        fragmentRemoved();
                    }
                });

    }

    /**
     * Call this whenever you add/replace a fragment in the activity
     * @param tag pass the unique Tag identifying the fragment
     */
    public void fragmentAdded(String tag){
        Debug.i("AddedToStack: "+tag);
        if (!TextUtils.isEmpty(tag)) {
            fragmentWasAdded = true;
            fragmentStack.push(tag);
            if (listener!=null){
                listener.onFragmentActive(tag);
            }
        }/*
        else
            throw new RuntimeException("Tag Passed to FragmentStackHelper.fragmentAdded(String tag) " +
                    "was Empty, Kindly pass a fragment Tag");*/
    }

    /**
     * Use this to get the ActiveFragment or the topmost fragment in the FragmentStack
     * @return the unique tag of fragment
     */
    public String getActiveFragment(){
        if (!fragmentStack.isEmpty())
            return fragmentStack.peek();
        /*else
            throw new RuntimeException("Tag Passed to FragmentStackHelper.fragmentAdded(String tag) " +
                    "was Empty, Kindly pass a fragment Tag");*/
        return null;
    }

    public void onDestroy(){
        listener = null;
    }

    private String fragmentRemoved(){
        if (!fragmentStack.empty()){
            String pop = fragmentStack.pop();
            Debug.i("FragmentPopped: "+pop);
            return pop;
        }//else throw new RuntimeException("FragmentStack is empty");
        return null;
    }
}
