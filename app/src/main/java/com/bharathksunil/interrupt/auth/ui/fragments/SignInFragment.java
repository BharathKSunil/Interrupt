package com.bharathksunil.interrupt.auth.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bharathksunil.interrupt.auth.presenter.SignInPresenter;
import com.bharathksunil.interrupt.auth.presenter.SignInPresenterImplementation;
import com.bharathksunil.interrupt.auth.repository.FirebaseSignInRepositoryImplementation;
import com.bharathksunil.interrupt.util.TextUtils;
import com.bharathksunil.interrupt.util.Utils;
import com.wang.avi.AVLoadingIndicatorView;

import com.bharathksunil.interrupt.R;
import com.bharathksunil.interrupt.auth.presenter.FormErrorType;
import com.bharathksunil.interrupt.util.ViewUtils;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass to sign-in the user to the servers.
 */
@SuppressWarnings("ConstantConditions")
public class SignInFragment extends Fragment implements SignInPresenter.View {

    public interface Interactor {
        void userSignedIn();

        void loadSignUpFragment();
    }

    public static final String TAG = "SignInFragment";

    public SignInFragment() {
        // Required empty public constructor
    }

    private SignInPresenter presenter;
    private Interactor interactor;
    private Unbinder unbinder;

    @BindViews({R.id.til_email, R.id.til_password})
    List<TextInputLayout> textInputLayoutList;
    @BindView(R.id.progress_bar)
    AVLoadingIndicatorView loadingIndicatorView;
    @BindView(R.id.btn_submit)
    Button signInButton;
    @BindView(R.id.tv_forgot_password)
    TextView tv_forgotPasswordText;

    @BindString(R.string.err_incorrect_email)
    String err_incorrect_email;
    @BindString(R.string.err_incorrect_password)
    String err_incorrect_password;
    @BindString(R.string.err_password_security)
    String err_password_invalid;
    @BindString(R.string.err_invalid_field)
    String err_invalid_field;
    @BindString(R.string.err_empty_field)
    String err_empty_field;
    @BindString(R.string.err_unexpected_error)
    String err_unexpected_error;
    @BindString(R.string.err_offline)
    String err_offline;

    @BindString(R.string.snack_signed_in)
    String snack_signed_in;
    @BindString(R.string.snack_password_reset_mail_sent)
    String snack_reset_mail_sent;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Interactor) {
            interactor = (Interactor) context;
            presenter = new SignInPresenterImplementation(new FirebaseSignInRepositoryImplementation());
        } else throw new RuntimeException(context.getClass().getName()
                + " must implement SignInFragment.Interactor");

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addTextWatcherToEmailField();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.auth_fragment_sign_in, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.setView(this);
        ViewUtils.setGone(textInputLayoutList.get(1));//make the password layout Gone
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.setView(null);
        unbinder.unbind();
    }

    /**
     * TextWatcher set on the email or password text input field to validate if the user is entering
     * a phone number or an email id. If they are syntactically valid then this enables the password
     * or pin entry layout accordingly
     */
    private void addTextWatcherToEmailField() {
        EditText editText = textInputLayoutList.get(0).getEditText();
        if (editText != null)
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (editable.length() > 8 && TextUtils.isEmailValid(editable.toString()))
                        ViewUtils.setVisible(textInputLayoutList.get(1));//make the password layout visible
                    else
                        ViewUtils.setGone(textInputLayoutList.get(1));//make the password layout gone
                }
            });
    }

    //react to sign In button click
    @OnClick(R.id.btn_submit)
    public void onSignInButtonClick() {
        ViewUtils.resetTextInputError(textInputLayoutList);
        if (Utils.isConnected(getActivity()))
            presenter.onSignInButtonClicked();
        else
            ViewUtils.errorBar(err_offline, getActivity());

    }

    @OnClick(R.id.tv_register)
    public void onRegisterNowPressed() {
        interactor.loadSignUpFragment();
    }

    @OnClick(R.id.tv_forgot_password)
    public void onForgotPasswordPressed() {
        presenter.onForgotPasswordTextClicked();
    }

    /**
     * Called when the presenter starts its work
     * enable any dialog-box or progress bars
     */
    @Override
    public void onProcessStarted() {
        loadingIndicatorView.smoothToShow();
        ViewUtils.setDisabled(signInButton, textInputLayoutList.get(0), textInputLayoutList.get(1));
    }

    /**
     * Called when the presenter has finished its work
     * disable any dialog-box or progress bars
     */
    @Override
    public void onProcessEnded() {
        loadingIndicatorView.smoothToHide();
        ViewUtils.setEnabled(signInButton, textInputLayoutList.get(0), textInputLayoutList.get(1));
    }

    /**
     * Called whenever there was an unexpected error in the process
     * Display any toast or messages to the user indicating that there was an error
     */
    @Override
    public void onUnexpectedError() {
        ViewUtils.errorBar(err_unexpected_error, getActivity());
    }

    /**
     * This method is called by the presenter when it needs to email address
     *
     * @return the emailId from the text field
     */
    @Override
    public String getEmailField() {
        return textInputLayoutList.get(0).getEditText().getText().toString();
    }

    /**
     * This method is called when the presenter needs the password
     *
     * @return the password as, entered by the user
     */
    @Override
    public String getPasswordField() {
        return textInputLayoutList.get(1).getEditText().getText().toString();
    }

    /**
     * This method is called when there is an error on the EmailId Field passed
     *
     * @param errorType the type of error indicating what kind of error was found in the field
     */
    @Override
    public void onEmailError(@NonNull FormErrorType errorType) {
        switch (errorType) {
            case INCORRECT:
                textInputLayoutList.get(0).setError(err_incorrect_email);
                break;
            case INVALID:
                textInputLayoutList.get(0).setError(err_invalid_field);
                break;
            case EMPTY:
                textInputLayoutList.get(0).setError(err_empty_field);
                break;
        }
    }

    /**
     * This method is called when there is an error on the Password Field passed
     *
     * @param errorType the type of error indicating what kind of error was found in the field
     */
    @Override
    public void onPasswordError(@NonNull FormErrorType errorType) {
        switch (errorType) {
            case INCORRECT:
                textInputLayoutList.get(1).setError(err_incorrect_password);
                break;
            case INVALID:
                textInputLayoutList.get(1).setError(err_password_invalid);
                break;
            case EMPTY:
                textInputLayoutList.get(1).setError(err_empty_field);
                break;
        }
    }

    /**
     * This method is called when the user was successfully signed in
     */
    @Override
    public void onUserSignedIn() {
        interactor.userSignedIn();
        ViewUtils.snackBar(snack_signed_in, getActivity());
    }

    /**
     * This method is called when the user is already signed in and is trying to sign in again
     */
    @Override
    public void onUserAlreadySignedIn() {
        interactor.userSignedIn();
    }

    /**
     * This method is called when the user has tried to login many times but has failed
     */
    @Override
    public void showForgotPasswordText() {
        ViewUtils.setVisible(tv_forgotPasswordText);
    }

    /**
     * This method is called when the password reset email has been sent
     */
    @Override
    public void showPasswordResetMailSentMessage() {
        ViewUtils.snackBar(snack_reset_mail_sent, getActivity());
        ViewUtils.setGone(tv_forgotPasswordText);
    }
}
