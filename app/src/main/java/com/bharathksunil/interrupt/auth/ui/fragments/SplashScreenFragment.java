package com.bharathksunil.interrupt.auth.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wang.avi.AVLoadingIndicatorView;

import com.bharathksunil.interrupt.R;
import com.bharathksunil.interrupt.auth.presenter.AuthPresenter;
import com.bharathksunil.interrupt.auth.presenter.AuthPresenterImplementation;
import com.bharathksunil.interrupt.auth.repository.FirebaseAuthRepositoryImplementation;
import com.bharathksunil.interrupt.util.ViewUtils;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass to welcome the user and Authenticate them.
 * Activities that contain this fragment must implement the {@link Interactor} interface
 * to handle interaction events.
 */
public class SplashScreenFragment extends Fragment implements AuthPresenter.View {

    public static final String TAG = "SplashScreenFragment";

    public SplashScreenFragment() {
        // Required empty public constructor
    }

    private Interactor interactor;
    private AuthPresenter presenter;

    @BindView(R.id.progress_bar)
    AVLoadingIndicatorView loadingIndicatorView;
    @BindString(R.string.err_unexpected_error)
    String unexpectedErrorText;

    private Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Interactor) {
            interactor = (Interactor) context;
            presenter = new AuthPresenterImplementation(new FirebaseAuthRepositoryImplementation());
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interactor = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.auth_fragment_splash_screen, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.appStarted(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.appStarted(null);
    }

    /**
     * Called when the presenter starts its work
     * enable any dialog-box or progress bars
     */
    @Override
    public void onProcessStarted() {
        loadingIndicatorView.smoothToShow();
    }

    /**
     * Called when the presenter has finished its work
     * disable any dialog-box or progress bars
     */
    @Override
    public void onProcessEnded() {
        loadingIndicatorView.smoothToHide();
    }

    /**
     * Called whenever there was an unexpected error in the process
     * Display any toast or messages to the user indicating that there was an error
     */
    @Override
    public void onUnexpectedError() {
        ViewUtils.errorBar(unexpectedErrorText, getActivity());
    }

    /**
     * User isn't signed in, load the sign in fragment
     */
    @Override
    public void loadSignInFragmentToSignInUser() {
        interactor.loadSignInFragment();
    }

    /**
     * User isn't Registered, load the Sign Up Fragment
     */
    @Override
    public void loadSignUpFragmentToRegisterUser() {
        interactor.loadSignUpFragment();
    }

    /**
     * The user is disabled to use the app, so, contact admin
     */
    @Override
    public void loadUserNotAllowedToUserAppFragment() {
        interactor.loadForbiddenUserFragment();
    }

    /**
     * Load the permissions fragment to get all permissions
     */
    @Override
    public void loadPermissionsFragment() {
        interactor.loadPermissionsFragment();
    }

    /**
     * Authentication is successful, load the dashboard
     */
    @Override
    public void loadDashboard() {
        interactor.launchDashboardActivity();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface Interactor {
        /**
         * Load the SignInFragment here
         */
        void loadSignInFragment();

        /**
         * Load the SignUpFragment
         */
        void loadSignUpFragment();

        /**
         * Load the permissions Fragment
         */
        void loadPermissionsFragment();

        /**
         * Called when the authentication is over
         */
        void launchDashboardActivity();

        /**
         * The user is disabled to use the app, load the fragment to show the same
         */
        void loadForbiddenUserFragment();
    }
}
