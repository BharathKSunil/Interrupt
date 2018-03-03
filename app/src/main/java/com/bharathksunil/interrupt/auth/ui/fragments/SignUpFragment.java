package com.bharathksunil.interrupt.auth.ui.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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

import com.bharathksunil.interrupt.R;
import com.bharathksunil.interrupt.auth.presenter.FormErrorType;
import com.bharathksunil.interrupt.auth.presenter.SignUpPresenter;
import com.bharathksunil.interrupt.auth.presenter.SignUpPresenterImplementation;
import com.bharathksunil.interrupt.auth.repository.FirebaseSignUpRepositoryImplementation;
import com.bharathksunil.interrupt.util.CircleTransform;
import com.bharathksunil.interrupt.util.TextDrawable;
import com.bharathksunil.interrupt.util.Utils;
import com.bharathksunil.interrupt.util.ViewUtils;
import com.kbeanie.multipicker.api.ImagePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.callbacks.ImagePickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenImage;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
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
 * A simple {@link Fragment} subclass. For Signing Up the User
 */
@SuppressWarnings("ConstantConditions")
public class SignUpFragment extends Fragment implements SignUpPresenter.View {

    public interface Interactor {
        void loadSignInFragment();

        void onUserSignedUp();
    }

    public static final String TAG = "SignUpFragment";

    public SignUpFragment() {
        // Required empty public constructor
    }

    private SignUpPresenter presenter;
    private Unbinder unbinder;
    private Interactor interactor;
    private Uri imagePath;
    private ImagePickerCallback imagePickerCallback;
    private ImagePicker imagePicker;

    @BindViews({R.id.til_name, R.id.til_email, R.id.til_phone, R.id.til_usn, R.id.til_department, R.id.til_password})
    List<TextInputLayout> textInputLayoutList;
    @BindView(R.id.progress_bar)
    AVLoadingIndicatorView loadingIndicatorView;
    @BindView(R.id.iv_profile)
    ImageView iv_profile;
    @BindView(R.id.btn_submit)
    Button btn_signUp;
    @BindView(R.id.btn_cancel)
    Button btn_signIn;
    @BindView(R.id.rg_section)
    RadioGroup rg_section;
    @BindView(R.id.rg_semester)
    RadioGroup rg_semester;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;

    @BindString(R.string.err_email_existing)
    String err_email_existing;
    @BindString(R.string.err_invalid_section)
    String err_invalid_section;
    @BindString(R.string.err_invalid_sem)
    String err_invalid_sem;
    @BindString(R.string.err_password_security)
    String err_password_invalid;
    @BindString(R.string.err_invalid_field)
    String err_invalid_field;
    @BindString(R.string.err_empty_field)
    String err_empty_field;
    @BindString(R.string.err_unexpected_error)
    String err_unexpected_error;
    @BindString(R.string.snack_signed_up)
    String snack_signed_up;
    @BindString(R.string.tv_profile_Image)
    String iv_profileImage;
    @BindString(R.string.prompt_pick_image)
    String prompt_pickImage;
    @BindString(R.string.err_storage_permission)
    String err_perm_storage;
    @BindString(R.string.err_offline)
    String err_offline;
    @BindColor(R.color.background_dark_material)
    int iv_background;
    @BindColor(R.color.white)
    int iv_text_color;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Interactor) {
            interactor = (Interactor) context;
        } else
            throw new RuntimeException(context.getClass().getName() + " must implement the SignUpFragment.Interactor");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.auth_fragment_sign_up, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new SignUpPresenterImplementation(new FirebaseSignUpRepositoryImplementation());
        presenter.setView(this);
        if (imagePath != null) {
            Picasso.with(getActivity()).load(imagePath)
                    .transform(new CircleTransform()).into(iv_profile);
        } else {
            TextDrawable textDrawable = TextDrawable.builder().beginConfig()
                    .withBorder(1).fontSize(35).textColor(iv_text_color)
                    .endConfig().buildRound(iv_profileImage, iv_background);
            iv_profile.setImageDrawable(textDrawable);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.setView(null);
    }

    //SignUp Button Pressed
    @OnClick(R.id.btn_submit)
    public void signUp() {
        ViewUtils.resetTextInputError(textInputLayoutList);
        if (Utils.isConnected(getActivity()))
            presenter.onSignUpButtonClicked();
        else
            ViewUtils.errorBar(err_offline, getActivity());
    }

    //SignIn Button Pressed
    @OnClick(R.id.btn_cancel)
    public void signIn() {
        interactor.loadSignInFragment();
    }

    //Profile Image Clicked
    @OnClick(R.id.iv_profile)
    public void chooseProfileImage() {
        if (Utils.isStoragePermissionGranted(getActivity())){
            imagePicker = new ImagePicker(this);
            imagePickerCallback = new ImagePickerCallback() {
                @Override
                public void onImagesChosen(List<ChosenImage> list) {
                    //Compress the image and set the image
                    try {
                        File compressedFile;
                        File file = new File(list.get(0).getOriginalPath());
                        compressedFile = new Compressor(getContext())
                                .setQuality(5)
                                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                                .compressToFile(file);
                        imagePath = Uri.fromFile(compressedFile);
                        Picasso.with(getActivity()).load(imagePath).transform(new CircleTransform()).into(iv_profile);
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
        else
            ViewUtils.errorBar(err_perm_storage, getActivity());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (imagePicker == null){
                imagePicker = new ImagePicker(this);
                imagePicker.setImagePickerCallback(imagePickerCallback);
            }
            if (requestCode == Picker.PICK_IMAGE_DEVICE)
                imagePicker.submit(data);
        }
    }


    /**
     * Called when the presenter starts its work
     * enable any dialog-box or progress bars
     */
    @Override
    public void onProcessStarted() {
        loadingIndicatorView.smoothToShow();
        ViewUtils.focusOnView(loadingIndicatorView, scrollView);
        ViewUtils.setDisabled(iv_profile, btn_signUp, btn_signIn,
                textInputLayoutList.get(0),
                textInputLayoutList.get(1),
                textInputLayoutList.get(2),
                textInputLayoutList.get(3),
                textInputLayoutList.get(4),
                textInputLayoutList.get(5),
                rg_section,
                rg_semester
        );
    }

    /**
     * Called when the presenter has finished its work
     * disable any dialog-box or progress bars
     */
    @Override
    public void onProcessEnded() {
        loadingIndicatorView.smoothToHide();
        ViewUtils.setEnabled(iv_profile, btn_signUp, btn_signIn,
                textInputLayoutList.get(0),
                textInputLayoutList.get(1),
                textInputLayoutList.get(2),
                textInputLayoutList.get(3),
                textInputLayoutList.get(4),
                textInputLayoutList.get(5),
                rg_section,
                rg_semester
        );
    }

    /**
     * Called whenever there was an unexpected error in the process
     * Display any toast or messages to the user indicating that there was an error
     */
    @Override
    public void onUnexpectedError() {
        ViewUtils.errorBar(err_unexpected_error, getActivity());
    }

    @Override
    public String getNameField() {
        return textInputLayoutList.get(0).getEditText().getText().toString();
    }

    @Override
    public String getEmailField() {
        return textInputLayoutList.get(1).getEditText().getText().toString();
    }

    @Override
    public String getPhoneNumberField() {
        return textInputLayoutList.get(2).getEditText().getText().toString();
    }

    @Override
    public String getUSNField() {
        return textInputLayoutList.get(3).getEditText().getText().toString();
    }

    @Override
    public String getDepartmentField() {
        return textInputLayoutList.get(4).getEditText().getText().toString();
    }

    @Override
    public String getPasswordField() {
        return textInputLayoutList.get(5).getEditText().getText().toString();
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
    public Uri getCompressedProfileImagePath() {
        return imagePath;
    }

    @Override
    public void onNameFieldError(FormErrorType errorType) {
        ViewUtils.focusOnView(textInputLayoutList.get(0), scrollView);
        switch (errorType) {
            case EMPTY:
                textInputLayoutList.get(0).setError(err_empty_field);
                break;
            case INVALID:
                textInputLayoutList.get(0).setError(err_invalid_field);
                break;
        }
    }

    @Override
    public void onEmailFieldError(FormErrorType errorType) {
        ViewUtils.focusOnView(textInputLayoutList.get(1), scrollView);
        switch (errorType) {
            case EMPTY:
                textInputLayoutList.get(1).setError(err_empty_field);
                break;
            case INVALID:
                textInputLayoutList.get(1).setError(err_invalid_field);
                break;
            case INCORRECT:
                textInputLayoutList.get(1).setError(err_email_existing);
                break;
        }
    }

    @Override
    public void onPhoneNumberFieldError(FormErrorType errorType) {
        ViewUtils.focusOnView(textInputLayoutList.get(2), scrollView);
        switch (errorType) {
            case EMPTY:
                textInputLayoutList.get(2).setError(err_empty_field);
                break;
            case INVALID:
                textInputLayoutList.get(2).setError(err_invalid_field);
                break;
        }
    }

    @Override
    public void onUSNFieldError(FormErrorType errorType) {
        ViewUtils.focusOnView(textInputLayoutList.get(3), scrollView);
        switch (errorType) {
            case EMPTY:
                textInputLayoutList.get(3).setError(err_empty_field);
                break;
            case INVALID:
                textInputLayoutList.get(3).setError(err_invalid_field);
                break;
        }
    }

    @Override
    public void onDepartmentFieldError(FormErrorType errorType) {
        ViewUtils.focusOnView(textInputLayoutList.get(4), scrollView);
        switch (errorType) {
            case EMPTY:
                textInputLayoutList.get(4).setError(err_empty_field);
                break;
            case INVALID:
                textInputLayoutList.get(4).setError(err_invalid_field);
                break;
        }
    }

    @Override
    public void onPasswordFieldError(FormErrorType errorType) {
        ViewUtils.focusOnView(textInputLayoutList.get(5), scrollView);
        switch (errorType) {
            case EMPTY:
                textInputLayoutList.get(5).setError(err_empty_field);
                break;
            case INVALID:
                textInputLayoutList.get(5).setError(err_password_invalid);
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
    public void onProfilePathError(FormErrorType errorType) {
        ViewUtils.focusOnView(iv_profile, scrollView);
        ViewUtils.errorBar(err_invalid_field, getActivity());
    }

    @Override
    public void onUserSuccessfullySignedUp() {
        interactor.onUserSignedUp();
        ViewUtils.snackBar(snack_signed_up, getActivity());
    }
}
