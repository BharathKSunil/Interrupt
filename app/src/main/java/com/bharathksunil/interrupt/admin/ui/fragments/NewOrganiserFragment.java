package com.bharathksunil.interrupt.admin.ui.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bharathksunil.interrupt.R;
import com.bharathksunil.interrupt.admin.presenter.NewOrganiserPresenter;
import com.bharathksunil.interrupt.admin.presenter.NewOrganiserPresenterImplementation;
import com.bharathksunil.interrupt.admin.repository.FirebaseNewOrganiserRepository;
import com.bharathksunil.interrupt.auth.model.UserPermissions;
import com.bharathksunil.interrupt.auth.model.UserType;
import com.bharathksunil.interrupt.auth.presenter.FormErrorType;
import com.bharathksunil.interrupt.util.CircleTransform;
import com.bharathksunil.interrupt.util.Utils;
import com.bharathksunil.interrupt.util.ViewUtils;
import com.kbeanie.multipicker.api.ImagePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.callbacks.ImagePickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenImage;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass which takes the details from the user[admin] and adds a new organiser.
 */
@SuppressWarnings("ConstantConditions")
public class NewOrganiserFragment extends Fragment implements NewOrganiserPresenter.View {


    private ImagePickerCallback imagePickerCallback;
    private ImagePicker imagePicker;
    private Unbinder unbinder;
    private NewOrganiserPresenter presenter;
    private Uri profilePath;
    private UserPermissions permissions;
    private File profileFile;

    public NewOrganiserFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.progress_bar)
    AVLoadingIndicatorView loadingIndicatorView;
    @BindView(R.id.spinner_designation)
    Spinner designationSpinner;
    @BindView(R.id.layout_permissions_organisers)
    View layoutPermissionsOrganisers;
    @BindView(R.id.layout_permissions_coordinators)
    View layoutPermissionsCoordinators;


    private static final int NAME = 0, EMAIL = 1, PHONE = 2, ROLES = 3;
    @BindViews({R.id.til_name, R.id.til_email, R.id.til_phone, R.id.til_roles})
    List<TextInputLayout> textInputLayoutList;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.btn_cancel)
    Button btn_cancel;
    @BindView(R.id.iv_profile)
    ImageView iv_profile;
    @BindView(R.id.tv_heading)
    TextView heading;
    @BindView(R.id.scroll)
    ScrollView scrollView;
    @BindViews({R.id.cb_edit_event_schedule, R.id.cb_edit_event_venue, R.id.cb_add_category,
            R.id.cb_add_event, R.id.cb_add_coordinator, R.id.cb_view_event_collection, R.id.cb_edit_event_info,
            R.id.cb_view_eventRegistrations, R.id.cb_download_eventData, R.id.cb_perform_registrations,
            R.id.cb_edit_event_banner, R.id.cb_view_payment_info, R.id.cb_download_payment_info})
    List<CheckBox> permissionsCheckBoxList;

    @BindString(R.string.err_unexpected_error)
    String err_unexpectedError;
    @BindString(R.string.err_storage_permission)
    String errStoragePermission;
    @BindString(R.string.err_empty_field)
    String errEmptyField;
    @BindString(R.string.err_invalid_field)
    String err_invalid_field;
    @BindString(R.string.prompt_pick_image)
    String prompt_pickImage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_new_organiser, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new NewOrganiserPresenterImplementation(new FirebaseNewOrganiserRepository());
        presenter.setView(this);

        permissions = new UserPermissions();
        permissions.setEnabled(true);


        setPermissionsCheckboxCheckedListener();
        return view;
    }

    private void setPermissionsCheckboxCheckedListener() {
        permissionsCheckBoxList.get(0).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                permissions.setCanChangeSchedule(b ? true : null);
            }
        });
        permissionsCheckBoxList.get(1).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                permissions.setCanChangeVenue(b ? true : null);
            }
        });
        permissionsCheckBoxList.get(2).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                permissions.setCanAddCategories(b ? true : null);
            }
        });
        permissionsCheckBoxList.get(3).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                permissions.setCanAddEvents(b ? true : null);
            }
        });
        permissionsCheckBoxList.get(4).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                permissions.setCanModifyCoordinatorData(b ? true : null);
            }
        });
        permissionsCheckBoxList.get(5).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                permissions.setCanViewEventCollections(b ? true : null);
            }
        });
        permissionsCheckBoxList.get(6).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                permissions.setCanEditEventsInfo(b ? true : null);
            }
        });
        permissionsCheckBoxList.get(7).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                permissions.setCanViewRegistrations(b ? true : null);
            }
        });
        permissionsCheckBoxList.get(8).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                permissions.setCanDownloadEventData(b ? true : null);
            }
        });
        permissionsCheckBoxList.get(9).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                permissions.setCanRegisterParticipant(b ? true : null);
            }
        });
        permissionsCheckBoxList.get(10).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                permissions.setCanEditEventBanner(b ? true : null);
            }
        });
        permissionsCheckBoxList.get(11).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                permissions.setCanViewPaymentsInfo(b ? true : null);
            }
        });
        permissionsCheckBoxList.get(12).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                permissions.setCanDownloadPaymentsInfo(b ? true : null);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.setView(null);
    }

    @OnClick(R.id.btn_submit)
    public void onSubmitButtonPressed() {
        presenter.onSubmitOrganiserButtonPressed();
    }

    @OnClick(R.id.iv_profile)
    public void onProfileImageButtonPressed() {
        if (Utils.isStoragePermissionGranted(getActivity())) {
            imagePicker = new ImagePicker(this);
            imagePickerCallback = new ImagePickerCallback() {
                @Override
                public void onImagesChosen(List<ChosenImage> list) {
                    //Compress the image and set the image
                    try {
                        File compressedFile;
                        profileFile = new File(list.get(0).getOriginalPath());
                        compressedFile = new Compressor(getContext())
                                .setQuality(5)
                                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                                .compressToFile(profileFile);
                        profilePath = Uri.fromFile(compressedFile);
                        Picasso.get().load(profilePath).transform(new CircleTransform()).into(iv_profile);
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
        } else
            ViewUtils.errorBar(errStoragePermission, getActivity());

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

    @Override
    public void onProcessStarted() {
        loadingIndicatorView.smoothToShow();
        ViewUtils.focusOnView(loadingIndicatorView, scrollView);
        ViewUtils.setDisabled(
                textInputLayoutList.get(0),
                textInputLayoutList.get(1),
                textInputLayoutList.get(2),
                textInputLayoutList.get(3),
                designationSpinner,
                layoutPermissionsCoordinators,
                layoutPermissionsOrganisers,
                btn_submit,
                btn_cancel,
                iv_profile
        );
    }

    @Override
    public void onProcessEnded() {
        loadingIndicatorView.smoothToHide();
        ViewUtils.setEnabled(
                textInputLayoutList.get(0),
                textInputLayoutList.get(1),
                textInputLayoutList.get(2),
                textInputLayoutList.get(3),
                designationSpinner,
                layoutPermissionsOrganisers,
                layoutPermissionsCoordinators,
                btn_submit,
                btn_cancel,
                iv_profile
        );
    }

    @Override
    public void onUnexpectedError() {
        ViewUtils.errorBar(err_unexpectedError, getActivity());

    }

    @Override
    public void setHeadingText(@NonNull String text) {
        heading.setText(text);
    }

    @Override
    public void setSubmitButtonText(@NonNull String text) {
        btn_submit.setText(text);
    }

    @Override
    public void loadDesignations(@NonNull List<String> designations) {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, designations);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        designationSpinner.setAdapter(dataAdapter);
    }

    @Override
    public void loadUserData(@NonNull String name, @NonNull String email, @NonNull String PhoneNo, @NonNull String Roles, @NonNull String designation, @NonNull List<String> permissions) {
        textInputLayoutList.get(NAME).getEditText().setText(name);
        textInputLayoutList.get(EMAIL).getEditText().setText(email);
        ViewUtils.setDisabled(textInputLayoutList.get(EMAIL));
        textInputLayoutList.get(PHONE).getEditText().setText(PhoneNo);
        textInputLayoutList.get(ROLES).getEditText().setText(Roles);
        //designationSpinner

    }

    @NonNull
    @Override
    public String getOrganiserName() {
        return textInputLayoutList.get(NAME).getEditText().getText().toString();
    }

    @NonNull
    @Override
    public String getOrganiserEmail() {
        return textInputLayoutList.get(EMAIL).getEditText().getText().toString();
    }

    @NonNull
    @Override
    public String getOrganiserPhoneNo() {
        return textInputLayoutList.get(PHONE).getEditText().getText().toString();
    }

    @NonNull
    @Override
    public String getOrganiserRoles() {
        return textInputLayoutList.get(ROLES).getEditText().getText().toString();
    }

    @Override
    public Uri getProfileImageUri() {
        if (profileFile != null) {
            try {
                File compressedFile;
                int quality = 5;
                if (getUserDesignation().equals(UserType.CORE_TEAM.name()))
                    quality = 15;
                compressedFile = new Compressor(getContext())
                        .setQuality(quality)
                        .setCompressFormat(Bitmap.CompressFormat.JPEG)
                        .compressToFile(profileFile);
                profilePath = Uri.fromFile(compressedFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return profilePath;
    }

    @NonNull
    @Override
    public UserPermissions getPermissionsSelected() {
        return permissions;
    }

    @NonNull
    @Override
    public String getUserDesignation() {
        return designationSpinner.getSelectedItem().toString();
    }

    @Override
    public void setNameFieldError(FormErrorType type) {
        ViewUtils.focusOnView(textInputLayoutList.get(NAME), scrollView);
        switch (type) {
            case EMPTY:
                textInputLayoutList.get(NAME).setErrorEnabled(true);
                textInputLayoutList.get(NAME).setError(errEmptyField);
                break;
        }

    }

    @Override
    public void setEmailIDFieldError(FormErrorType type) {
        ViewUtils.focusOnView(textInputLayoutList.get(EMAIL), scrollView);
        switch (type) {
            case INVALID:
                textInputLayoutList.get(EMAIL).setError(err_invalid_field);
                break;
            case EMPTY:
                textInputLayoutList.get(EMAIL).setError(errEmptyField);
                break;
        }

    }

    @Override
    public void setPhoneNoFieldError(FormErrorType type) {
        ViewUtils.focusOnView(textInputLayoutList.get(PHONE), scrollView);
        switch (type) {
            case INVALID:
                textInputLayoutList.get(PHONE).setError(err_invalid_field);
                break;
            case EMPTY:
                textInputLayoutList.get(PHONE).setError(errEmptyField);
                break;
        }

    }

    @Override
    public void setRolesFieldError(FormErrorType type) {
        ViewUtils.focusOnView(textInputLayoutList.get(ROLES), scrollView);
        switch (type) {
            case INVALID:
                textInputLayoutList.get(ROLES).setError(err_invalid_field);
                break;
            case EMPTY:
                textInputLayoutList.get(ROLES).setError(errEmptyField);
                break;
        }

    }

    @Override
    public void showNoPermissionsAssignedAlert() {
        new AlertDialog.Builder(getActivity()).setTitle("No Permissions Set for User")
                .setMessage("Basic Permissions will be set if you proceed")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.onProceedWithDefaultPermissionsButtonPressed();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create().show();

    }

    @Override
    public void showOperationSuccessfulMessage(@NonNull String message) {
        ViewUtils.snackBar(message, getActivity());
    }

    @Override
    public void exitPage() {
        getActivity().onBackPressed();
    }
}
