package com.bharathksunil.interrupt.events.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bharathksunil.interrupt.R;
import com.bharathksunil.interrupt.auth.presenter.FormErrorType;
import com.bharathksunil.interrupt.events.model.EventsManager;
import com.bharathksunil.interrupt.events.presenter.ParticipantRegistrationsPresenter;
import com.bharathksunil.interrupt.events.presenter.ParticipantRegistrationsPresenterImplementation;
import com.bharathksunil.interrupt.events.repository.FirebaseParticipantRegistrationRepository;
import com.bharathksunil.interrupt.util.TextDrawable;
import com.bharathksunil.interrupt.util.TextUtils;
import com.bharathksunil.interrupt.util.Utils;
import com.bharathksunil.interrupt.util.ViewUtils;
import com.cloudrail.si.CloudRail;
import com.cloudrail.si.services.MailJet;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Collections;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass that Registers a New Participant to the event.
 */
@SuppressWarnings("ConstantConditions")
public class NewParticipantRegistrationFragment extends Fragment implements ParticipantRegistrationsPresenter.View {
    public NewParticipantRegistrationFragment() {
        // Required empty public constructor
    }

    private Unbinder unbinder;
    private ParticipantRegistrationsPresenter presenter;
    private MailJet mailJet;

    @BindView(R.id.progress_bar)
    AVLoadingIndicatorView loadingIndicatorView;
    @BindView(R.id.tv_event_name_1)
    TextView tv_eventName;
    private final int NAME = 0, EMAIL = 1, PHONE_NO = 2, USN = 3, DEPARTMENT = 4, TEAM = 5;
    @BindViews({R.id.til_name, R.id.til_email, R.id.til_phone, R.id.til_usn, R.id.til_department, R.id.til_team_members})
    List<TextInputLayout> textInputLayoutList;
    @BindView(R.id.btn_submit)
    Button btn_register;
    @BindView(R.id.btn_cancel)
    Button btn_cancel;
    @BindView(R.id.rg_section)
    RadioGroup rg_section;
    @BindView(R.id.rg_semester)
    RadioGroup rg_semester;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.iv_events_image)
    ImageView iv_banner;

    @BindString(R.string.err_invalid_section)
    String err_invalid_section;
    @BindString(R.string.err_invalid_sem)
    String err_invalid_sem;
    @BindString(R.string.err_invalid_field)
    String err_invalid_field;
    @BindString(R.string.err_empty_field)
    String err_empty_field;
    @BindString(R.string.err_unexpected_error)
    String err_unexpected_error;
    @BindString(R.string.err_offline)
    String err_offline;

    @BindColor(R.color.white)
    int banner_image_text_color;
    @BindColor(R.color.admin_bg)
    int banner_image_bg_color;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        CloudRail.setAppKey("5a9ba23c2d1ce0242d0fd785");
        mailJet = new MailJet(context, "30904bf10495d81dc2d5c4f41a44542e",
                "7abcbb3d9fa093dc511eeb31e9279577");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.events_fragment_participant_registration, container, false);
        unbinder = ButterKnife.bind(this, view);

        presenter = new ParticipantRegistrationsPresenterImplementation(
                new FirebaseParticipantRegistrationRepository()
        );
        presenter.setView(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.setView(null);
    }

    @OnClick(R.id.btn_submit)
    public void onRegisterButtonPressed() {
        ViewUtils.resetTextInputError(textInputLayoutList);
        if (Utils.isConnected(getContext()))
            presenter.onRegisterButtonPressed();
        else
            ViewUtils.errorBar(err_offline, getActivity());
    }

    @OnClick(R.id.btn_cancel)
    public void onCancelButtonPressed() {
        presenter.onCancelButtonPressed();
    }

    @Override
    public void onProcessStarted() {
        loadingIndicatorView.smoothToShow();
        ViewUtils.focusOnView(loadingIndicatorView, scrollView);
        ViewUtils.setDisabled(btn_register, btn_cancel,
                textInputLayoutList.get(NAME),
                textInputLayoutList.get(EMAIL),
                textInputLayoutList.get(PHONE_NO),
                textInputLayoutList.get(USN),
                textInputLayoutList.get(DEPARTMENT),
                textInputLayoutList.get(TEAM),
                rg_section,
                rg_semester
        );
    }

    @Override
    public void onProcessEnded() {
        loadingIndicatorView.smoothToHide();
        ViewUtils.focusOnView(loadingIndicatorView, scrollView);
        ViewUtils.setEnabled(btn_register, btn_cancel,
                textInputLayoutList.get(NAME),
                textInputLayoutList.get(EMAIL),
                textInputLayoutList.get(PHONE_NO),
                textInputLayoutList.get(USN),
                textInputLayoutList.get(DEPARTMENT),
                textInputLayoutList.get(TEAM),
                rg_section,
                rg_semester
        );

    }

    @Override
    public void onUnexpectedError() {
        ViewUtils.errorBar(err_unexpected_error, getActivity());
    }

    @Override
    public void loadEventBanner(String url) {
        if (TextUtils.isEmpty(url)) {
            TextDrawable textDrawable = TextDrawable.builder().beginConfig()
                    .withBorder(1).fontSize(55).textColor(banner_image_text_color)
                    .endConfig().buildRect(EventsManager.getInstance().getEventName(), banner_image_bg_color);
            iv_banner.setImageDrawable(textDrawable);
        }
        Picasso.with(getContext()).load(url).placeholder(R.drawable.app_icon)
                .error(R.drawable.app_icon)
                .into(iv_banner);

    }

    @Override
    public void setEventNameText(String eventName) {
        tv_eventName.setText(eventName);
    }

    @Override
    public String getNameField() {
        return textInputLayoutList.get(NAME).getEditText().getText().toString();
    }

    @Override
    public String getEmailField() {
        return textInputLayoutList.get(EMAIL).getEditText().getText().toString();
    }

    @Override
    public String getPhoneNumberField() {
        return textInputLayoutList.get(PHONE_NO).getEditText().getText().toString();
    }

    @Override
    public String getUSNField() {
        return textInputLayoutList.get(USN).getEditText().getText().toString();
    }

    @Override
    public String getDepartmentField() {
        return textInputLayoutList.get(DEPARTMENT).getEditText().getText().toString();
    }

    @Override
    public String getSectionField() {
        switch (rg_section.getCheckedRadioButtonId()) {
            case R.id.rb_section_a:
                return "A";
            case R.id.rb_section_b:
                return "B";
            case R.id.rb_section_c:
                return "C";
            default:
                return "NA";
        }
    }

    @Override
    public String getSemesterField() {
        switch (rg_semester.getCheckedRadioButtonId()) {
            case R.id.rb_sem_2:
                return "2nd";
            case R.id.rb_sem_4:
                return "4th";
            case R.id.rb_sem_6:
                return "6th";
            case R.id.rb_sem_8:
                return "8th";
            default:
                return "NA";
        }
    }

    @Override
    public String getTeamMembers() {
        return textInputLayoutList.get(TEAM).getEditText().getText().toString();
    }

    @Override
    public void onNameFieldError(FormErrorType errorType) {
        ViewUtils.focusOnView(textInputLayoutList.get(NAME), scrollView);
        switch (errorType) {
            case EMPTY:
                textInputLayoutList.get(NAME).setError(err_empty_field);
                break;
            case INVALID:
                textInputLayoutList.get(NAME).setError(err_invalid_field);
                break;
        }
    }

    @Override
    public void onEmailFieldError(FormErrorType errorType) {
        ViewUtils.focusOnView(textInputLayoutList.get(EMAIL), scrollView);
        switch (errorType) {
            case EMPTY:
                textInputLayoutList.get(EMAIL).setError(err_empty_field);
                break;
            case INVALID:
                textInputLayoutList.get(EMAIL).setError(err_invalid_field);
                break;
        }
    }

    @Override
    public void onPhoneNumberFieldError(FormErrorType errorType) {
        ViewUtils.focusOnView(textInputLayoutList.get(PHONE_NO), scrollView);
        switch (errorType) {
            case EMPTY:
                textInputLayoutList.get(PHONE_NO).setError(err_empty_field);
                break;
            case INVALID:
                textInputLayoutList.get(PHONE_NO).setError(err_invalid_field);
                break;
        }
    }

    @Override
    public void onUSNFieldError(FormErrorType errorType) {
        ViewUtils.focusOnView(textInputLayoutList.get(USN), scrollView);
        switch (errorType) {
            case EMPTY:
                textInputLayoutList.get(USN).setError(err_empty_field);
                break;
            case INVALID:
                textInputLayoutList.get(USN).setError(err_invalid_field);
                break;
        }
    }

    @Override
    public void onDepartmentFieldError(FormErrorType errorType) {
        ViewUtils.focusOnView(textInputLayoutList.get(DEPARTMENT), scrollView);
        switch (errorType) {
            case EMPTY:
                textInputLayoutList.get(DEPARTMENT).setError(err_empty_field);
                break;
            case INVALID:
                textInputLayoutList.get(DEPARTMENT).setError(err_invalid_field);
                break;
        }
    }

    @Override
    public void onSectionFieldError(FormErrorType errorType) {
        ViewUtils.focusOnView(rg_section, scrollView);
        switch (errorType) {
            case EMPTY:
                ViewUtils.errorBar(err_empty_field, getActivity());
                break;
            case INVALID:
                ViewUtils.errorBar(err_invalid_section, getActivity());
                break;
        }
    }

    @Override
    public void onSemFieldError(FormErrorType errorType) {
        ViewUtils.focusOnView(rg_semester, scrollView);
        switch (errorType) {
            case EMPTY:
                ViewUtils.errorBar(err_empty_field, getActivity());
                break;
            case INVALID:
                ViewUtils.errorBar(err_invalid_sem, getActivity());
                break;
        }
    }

    @Override
    public void userRegisteredSuccessfully() {
        ViewUtils.snackBar("User Registered Successfully, Sending Mail...", getActivity());
    }

    @Override
    public void showUserAlreadyRegisteredError() {
        ViewUtils.errorBar("This User Is Already Registered", getActivity());
    }

    @Override
    public void resetAllUserData() {
        for (TextInputLayout layout : textInputLayoutList) {
            layout.getEditText().setText("");
        }
    }

    @Override
    public void sendEmail(final String emailID, final String subject, final String body) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        mailJet.sendEmail(
                                "bharath.cse@dr-ait.org",
                                "Bharath Kumar S",
                                Collections.singletonList(emailID),
                                subject,
                                body,
                                null,
                                null,
                                null,
                                null
                        );
                    }
                }
        ).start();
    }

    @Override
    public void exit() {
        getActivity().onBackPressed();
    }

}

