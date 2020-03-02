package com.bharathksunil.interrupt.events.ui.fragments;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bharathksunil.interrupt.R;
import com.bharathksunil.interrupt.auth.presenter.FormErrorType;
import com.bharathksunil.interrupt.events.presenter.EventInfoPresenter;
import com.bharathksunil.interrupt.events.presenter.EventInfoPresenterImplementation;
import com.bharathksunil.interrupt.events.repository.FirebaseEventCategoriesRepositoryImplementation;
import com.bharathksunil.interrupt.events.repository.FirebaseEventsRepositoryImplementation;
import com.bharathksunil.interrupt.util.TextDrawable;
import com.bharathksunil.interrupt.util.TextUtils;
import com.bharathksunil.interrupt.util.Utils;
import com.bharathksunil.interrupt.util.ViewUtils;
import com.kbeanie.multipicker.api.ImagePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.callbacks.ImagePickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenImage;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass to view the events information and edit the event.
 */
@SuppressWarnings("ConstantConditions")
public class EventInfoFragment extends Fragment implements EventInfoPresenter.View {
    public EventInfoFragment() {
        // Required empty public constructor
    }

    public interface Interactor {
        /**
         * Load the fragment that registers a new Participant to the event
         */
        void loadParticipantRegistrationFragment();

        /**
         * Load the fragment that shows the participants registered for the event
         */
        void loadEventRegistrationsFragment();
    }

    private Unbinder unbinder;
    private Interactor interactor;
    private EventInfoPresenter presenter;
    private Uri bannerImage;
    private ImagePickerCallback imagePickerCallback;
    private ImagePicker imagePicker;

    private static final int NAME = 0, DESCRIPTION = 1, VENUE = 2, COORDINATOR_NAME = 3, EMAIL = 4,
            PHONE_NO = 5, PRICE = 6;
    @BindViews({R.id.til_name, R.id.til_description, R.id.til_venue, R.id.til_coordinator_name,
            R.id.til_coordinator_email, R.id.til_coordinator_phone, R.id.til_price})
    List<TextInputLayout> textInputLayoutList;
    private static final int EDIT = 0, ADD = 1, DOWNLOAD = 2;
    @BindViews({R.id.fab_edit, R.id.fab_add, R.id.fab_download})
    List<FloatingActionButton> floatingActionButtonList;
    @BindView(R.id.spinner_category)
    Spinner spinner_category;
    @BindView(R.id.iv_events_image)
    ImageView iv_eventBanner;
    private static final int TIME = 0, REGISTRATIONS = 1, SUBMIT = 2, CANCEL = 3;
    @BindViews({R.id.btn_time, R.id.btn_view, R.id.btn_submit, R.id.btn_cancel})
    List<Button> btnList;
    @BindView(R.id.progress_bar)
    AVLoadingIndicatorView loadingIndicatorView;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;

    @BindString(R.string.err_permission_denied)
    String err_permission_denied;
    @BindString(R.string.err_storage_permission)
    String err_storage_permission;
    @BindString(R.string.err_unexpected_error)
    String err_unexpectedError;
    @BindString(R.string.err_empty_field)
    String err_emptyField;
    @BindString(R.string.err_invalid_field)
    String err_invalid_field;

    @BindColor(R.color.white)
    int banner_image_text_color;
    @BindColor(R.color.admin_bg)
    int banner_image_bg_color;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Interactor)
            interactor = (Interactor) context;
        else
            throw new RuntimeException(context.getClass().getName() + " Must implement the " + Interactor.class.getName() +
                    " For using this fragment: " + this.getClass().getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.events_fragment_event_info, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new EventInfoPresenterImplementation(
                new FirebaseEventCategoriesRepositoryImplementation(),
                new FirebaseEventsRepositoryImplementation()
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (imagePicker == null) {
                imagePicker = new ImagePicker(this);
                imagePicker.setImagePickerCallback(imagePickerCallback);
            }
            if (requestCode == Picker.PICK_IMAGE_DEVICE)
                imagePicker.submit(data);
        }
    }

    @OnClick(R.id.fab_add)
    public void onAddNewParticipantPressed() {
        presenter.onAddRegistrationButtonPressed();
    }

    @OnClick(R.id.fab_download)
    public void onDownloadButtonPressed() {
        presenter.onDownloadEventRegistrationButtonPressed();
    }

    @OnClick(R.id.fab_edit)
    public void onEditButtonPressed() {
        presenter.onEditButtonPressed();
    }

    @OnClick(R.id.btn_view)
    public void onViewRegistrationsButtonPressed() {
        presenter.onViewRegistrationsButtonPressed();
    }

    @OnClick(R.id.btn_time)
    public void onTimeButtonPressed() {
        presenter.onSetDateButtonPressed();
    }

    @OnClick(R.id.btn_cancel)
    public void onCancelButtonPressed() {
        presenter.onCancelButtonPressed();
    }

    @OnClick(R.id.btn_submit)
    public void onSubmitButtonPressed() {
        presenter.onSubmitButtonPressed();
    }

    @OnClick(R.id.iv_events_image)
    public void onEventBannerPressed() {
        presenter.onBannerImagePressed();
    }


    @Override
    public void onProcessStarted() {
        loadingIndicatorView.smoothToShow();
        ViewUtils.focusOnView(loadingIndicatorView, scrollView);
    }

    @Override
    public void onProcessEnded() {
        loadingIndicatorView.smoothToHide();
    }

    @Override
    public void onUnexpectedError() {
        ViewUtils.errorBar(err_unexpectedError, getActivity());
    }

    @Override
    public void setEventName(String eventName) {
        textInputLayoutList.get(NAME).getEditText().setText(eventName);
    }

    @Override
    public void setEventNameFieldEnabled(boolean isEnabled) {
        textInputLayoutList.get(NAME).setEnabled(isEnabled);
    }

    @Override
    public void loadEventCategories(List<String> categories) {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_category.setAdapter(dataAdapter);
    }

    @Override
    public void setEventCategoryFieldEnabled(boolean isEnabled) {
        spinner_category.setEnabled(isEnabled);
    }

    @Override
    public void setEventDescription(String descriptions) {
        textInputLayoutList.get(DESCRIPTION).getEditText().setText(descriptions);
    }

    @Override
    public void setEventDescriptionFieldEnabled(boolean isEnabled) {
        textInputLayoutList.get(DESCRIPTION).setEnabled(isEnabled);
    }

    @Override
    public void setEventPrice(String price) {
        textInputLayoutList.get(PRICE).getEditText().setText(price);
    }

    @Override
    public void setEventPriceFieldEnabled(boolean isEnabled) {
        textInputLayoutList.get(PRICE).setEnabled(isEnabled);
    }

    @Override
    public void setEventCoordinatorName(String name) {
        textInputLayoutList.get(COORDINATOR_NAME).getEditText().setText(name);
    }

    @Override
    public void setEventCoordinatorNameFieldEnabled(boolean isEnabled) {
        textInputLayoutList.get(COORDINATOR_NAME).setEnabled(isEnabled);
    }

    @Override
    public void setEventCoordinatorEmail(String email) {
        textInputLayoutList.get(EMAIL).getEditText().setText(email);
    }

    @Override
    public void setEventCoordinatorEmailFieldEnabled(boolean isEnabled) {
        textInputLayoutList.get(EMAIL).setEnabled(isEnabled);
    }

    @Override
    public void setEventCoordinatorPhoneNo(String phoneNo) {
        textInputLayoutList.get(PHONE_NO).getEditText().setText(phoneNo);
    }

    @Override
    public void setEventCoordinatorPhoneNoFieldEnabled(boolean isEnabled) {
        textInputLayoutList.get(PHONE_NO).setEnabled(isEnabled);
    }

    @Override
    public void setTimeButtonText(String time) {
        btnList.get(TIME).setText(time);
    }

    @Override
    public void setEventTimeButtonEnabled(boolean isEnabled) {
        btnList.get(TIME).setEnabled(isEnabled);
    }

    @Override
    public void setEventVenue(String eventVenue) {
        textInputLayoutList.get(VENUE).getEditText().setText(eventVenue);
    }

    @Override
    public void setEventVenueFieldEnabled(boolean isEnabled) {
        textInputLayoutList.get(VENUE).setEnabled(isEnabled);
    }

    @Override
    public void showEventRegistrationsButton() {
        ViewUtils.setVisible(btnList.get(REGISTRATIONS));
    }

    @Override
    public void hideEventRegistrationsButton() {
        ViewUtils.setGone(btnList.get(REGISTRATIONS));
    }

    @Override
    public void loadEventBanner(String url) {
        if (TextUtils.isEmpty(url)) {
            TextDrawable textDrawable = TextDrawable.builder().beginConfig()
                    .withBorder(1).fontSize(65).textColor(banner_image_text_color)
                    .endConfig().buildRect("Upload Event Banner", banner_image_bg_color);
            iv_eventBanner.setImageDrawable(textDrawable);
        } else
            Picasso.get().load(url).placeholder(R.drawable.app_icon)
                    .error(R.drawable.app_icon)
                    .into(iv_eventBanner);
    }

    @Override
    public void setEventBannerImageEnabled(boolean isEnabled) {
        iv_eventBanner.setEnabled(isEnabled);
    }

    @Override
    public void showBannerImageChooser() {
        if (Utils.isStoragePermissionGranted(getActivity())) {
            imagePicker = new ImagePicker(this);
            imagePickerCallback = new ImagePickerCallback() {
                @Override
                public void onImagesChosen(List<ChosenImage> list) {
                    //Compress the image and set the image
                    try {
                        File compressedFile;
                        File file = new File(list.get(0).getOriginalPath());
                        compressedFile = new Compressor(getActivity())
                                .setQuality(35)
                                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                                .compressToFile(file);
                        bannerImage = Uri.fromFile(compressedFile);
                        Picasso.get().load(bannerImage).into(iv_eventBanner);
                    } catch (Exception e) {
                        ViewUtils.errorBar("Couldn't Load File", getActivity());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String s) {
                    ViewUtils.errorBar(s, getActivity());
                }
            };
            imagePicker.setImagePickerCallback(imagePickerCallback);
            imagePicker.pickImage();
        }
    }

    @Override
    public void loadNewParticipantRegistrationPage() {
        interactor.loadParticipantRegistrationFragment();
    }

    @Override
    public void loadEventRegistrationsViewer() {
        interactor.loadEventRegistrationsFragment();
    }

    @Override
    public void showComingSoonMessage() {
        Toast.makeText(getActivity(), "Coming Soon", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDateTimeChooser() {
        showDatePicker();
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        showTimePicker(year, month, day);
                    }
                }, 2018, 2, 9);
        datePickerDialog.setTitle("Select Date Of Event");
        datePickerDialog.show();
    }

    private void showTimePicker(final int year, int month, final int date) {
        Calendar mcurrentTime = Calendar.getInstance();
        mcurrentTime.set(year, month, date, 9, 0);
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String hour = "", minute = "";
                if (selectedHour < 10 || (selectedHour > 12 && selectedHour < 22))
                    hour = "0";
                if (selectedMinute < 10)
                    minute = "0";
                if (selectedHour < 12)
                    btnList.get(TIME).setText(hour + selectedHour + ":" + minute + selectedMinute + " AM, " + date + " March " + year);
                else if (selectedHour == 12)
                    btnList.get(TIME).setText(hour + selectedHour + ":" + hour + selectedMinute + " PM, " + date + " March " + year);
                else
                    btnList.get(TIME).setText(hour + (selectedHour - 12) + ":" + minute + selectedMinute + " PM, " + date + " March " + year);
            }
        }, hour, minute, true);//Not 24 hour time
        mTimePicker.setTitle("Select Event Time");
        mTimePicker.show();
    }

    @Override
    public void showPermissionDeniedMessage() {
        ViewUtils.errorBar(err_permission_denied, getActivity());
    }

    @Override
    public void exitActivity() {
        getActivity().onBackPressed();
    }

    @Override
    public void setSubmitButtonText(String text) {
        btnList.get(SUBMIT).setText(text);
    }

    @Override
    public void hideSubmitButton() {
        ViewUtils.setGone(btnList.get(SUBMIT));
    }

    @Override
    public void showSubmitButton() {
        ViewUtils.setVisible(btnList.get(SUBMIT));
    }

    @Override
    public void hideCancelButton() {
        ViewUtils.setGone(btnList.get(CANCEL));
    }

    @Override
    public void showCancelButton() {
        ViewUtils.setVisible(btnList.get(CANCEL));
    }

    @Override
    public void showEditButton() {
        floatingActionButtonList.get(EDIT).show();
    }

    @Override
    public void hideEditButton() {
        floatingActionButtonList.get(EDIT).hide();
    }

    @Override
    public void showAddRegistrationButton() {
        floatingActionButtonList.get(ADD).show();
    }

    @Override
    public void hideAddRegistrationButton() {
        floatingActionButtonList.get(ADD).hide();
    }

    @Override
    public void showDownloadRegistrationButton() {
        floatingActionButtonList.get(DOWNLOAD).show();
    }

    @Override
    public void hideDownloadRegistrationButton() {
        floatingActionButtonList.get(DOWNLOAD).hide();
    }

    @Override
    public String getEventName() {
        return textInputLayoutList.get(NAME).getEditText().getText().toString();
    }

    @Override
    public void setNameFieldError(FormErrorType errorType) {
        if (errorType == FormErrorType.EMPTY) {
            textInputLayoutList.get(NAME).setErrorEnabled(true);
            textInputLayoutList.get(NAME).setError(err_emptyField);
            ViewUtils.focusOnView(textInputLayoutList.get(NAME), scrollView);
        }
    }

    @Override
    public int getEventCategoryPosition() {
        return spinner_category.getSelectedItemPosition();
    }

    @Override
    public String getEventDescription() {
        return textInputLayoutList.get(DESCRIPTION).getEditText().getText().toString();
    }

    @Override
    public void setDescriptionFieldError(FormErrorType errorType) {
        if (errorType == FormErrorType.EMPTY) {
            textInputLayoutList.get(DESCRIPTION).setErrorEnabled(true);
            textInputLayoutList.get(DESCRIPTION).setError(err_emptyField);
            ViewUtils.focusOnView(textInputLayoutList.get(DESCRIPTION), scrollView);
        }
    }

    @Override
    public String getEventTime() {
        return btnList.get(TIME).getText().toString();
    }

    @Override
    public void setTimeFieldError() {
        ViewUtils.errorBar("Invalid Time", getActivity());
        ViewUtils.focusOnView(btnList.get(TIME), scrollView);
    }

    @Override
    public String getEventVenue() {
        return textInputLayoutList.get(VENUE).getEditText().getText().toString();
    }

    @Override
    public void setVenueFieldError(FormErrorType errorType) {
        if (errorType == FormErrorType.EMPTY) {
            textInputLayoutList.get(VENUE).setErrorEnabled(true);
            textInputLayoutList.get(VENUE).setError(err_emptyField);
            ViewUtils.focusOnView(textInputLayoutList.get(VENUE), scrollView);

        }
    }

    @Override
    public String getCoordinatorName() {
        return textInputLayoutList.get(COORDINATOR_NAME).getEditText().getText().toString();
    }

    @Override
    public void setCoordinatorNameError(FormErrorType errorType) {
        if (errorType == FormErrorType.EMPTY) {
            textInputLayoutList.get(COORDINATOR_NAME).setErrorEnabled(true);
            textInputLayoutList.get(COORDINATOR_NAME).setError(err_emptyField);
            ViewUtils.focusOnView(textInputLayoutList.get(COORDINATOR_NAME), scrollView);
        }
    }

    @Override
    public String getCoordinatorEmail() {
        return textInputLayoutList.get(EMAIL).getEditText().getText().toString();
    }

    @Override
    public void setCoordinatorEmailError(FormErrorType errorType) {
        textInputLayoutList.get(EMAIL).setErrorEnabled(true);
        ViewUtils.focusOnView(textInputLayoutList.get(EMAIL), scrollView);
        switch (errorType) {
            case EMPTY:
                textInputLayoutList.get(EMAIL).setError(err_emptyField);
                break;
            case INVALID:
                textInputLayoutList.get(EMAIL).setError(err_invalid_field);
                break;
        }
    }

    @Override
    public String getCoordinatorPhone() {
        return textInputLayoutList.get(PHONE_NO).getEditText().getText().toString();
    }

    @Override
    public void setCoordinatorPhoneError(FormErrorType errorType) {
        textInputLayoutList.get(PHONE_NO).setErrorEnabled(true);
        ViewUtils.focusOnView(textInputLayoutList.get(PHONE_NO), scrollView);
        switch (errorType) {
            case EMPTY:
                textInputLayoutList.get(PHONE_NO).setError(err_emptyField);
                break;
            case INVALID:
                textInputLayoutList.get(PHONE_NO).setError(err_invalid_field);
                break;
        }
    }

    @Override
    public String getPrice() {
        return textInputLayoutList.get(PRICE).getEditText().getText().toString();
    }

    @Override
    public void setPriceError(FormErrorType errorType) {
        textInputLayoutList.get(PRICE).setErrorEnabled(true);
        ViewUtils.focusOnView(textInputLayoutList.get(PRICE), scrollView);
        switch (errorType) {
            case EMPTY:
                textInputLayoutList.get(PRICE).setError(err_emptyField);
                break;
            case INVALID:
                textInputLayoutList.get(PRICE).setError(err_invalid_field);
                break;
        }
    }

    @Override
    public Uri getEventBanner() {
        return bannerImage;
    }

    @Override
    public void showProcessFinishedMessage(String message) {
        ViewUtils.snackBar(message, getActivity());
    }
}
