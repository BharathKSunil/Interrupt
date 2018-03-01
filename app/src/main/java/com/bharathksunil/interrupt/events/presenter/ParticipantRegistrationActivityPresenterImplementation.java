package com.bharathksunil.interrupt.events.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.interrupt.auth.model.UserManager;
import com.bharathksunil.interrupt.events.model.EventRegistrations;
import com.bharathksunil.interrupt.events.model.EventsManager;
import com.bharathksunil.interrupt.util.TextUtils;

import static com.bharathksunil.interrupt.auth.presenter.FormErrorType.EMPTY;
import static com.bharathksunil.interrupt.auth.presenter.FormErrorType.INVALID;

/**
 * This implements the {@link ParticipantRegistrationActivityPresenter}
 *
 * @author Bharath on 01-03-2018.
 */

public class ParticipantRegistrationActivityPresenterImplementation implements ParticipantRegistrationActivityPresenter {

    private String name, email, phoneNo, usn, department, section, semester, teamMembers;
    @Nullable
    private View viewInstance;
    @NonNull
    private Repository repositoryInstance;
    @NonNull
    private EventsManager eventsManager;
    @NonNull
    private UserManager userManager;

    public ParticipantRegistrationActivityPresenterImplementation(@NonNull Repository repositoryInstance) {
        this.repositoryInstance = repositoryInstance;
        this.eventsManager = EventsManager.getInstance();
        this.userManager = UserManager.getInstance();
    }

    @Override
    public void setView(@Nullable View view) {
        viewInstance = view;
        if (viewInstance == null)
            return;
        viewInstance.setEventNameText(eventsManager.getEventName());
        viewInstance.loadEventBanner(eventsManager.getCurrentEventBannerURL());
    }

    @Override
    public void onCancelButtonPressed() {
        if (viewInstance == null)
            return;
        viewInstance.resetAllUserData();
    }

    @Override
    public void onRegisterButtonPressed() {
        if (viewInstance == null)
            return;
        viewInstance.onProcessStarted();
        name = viewInstance.getNameField();
        email = viewInstance.getEmailField();
        phoneNo = viewInstance.getPhoneNumberField();
        usn = viewInstance.getUSNField();
        department = viewInstance.getDepartmentField();
        section = viewInstance.getSectionField();
        semester = viewInstance.getSemesterField();
        teamMembers = viewInstance.getTeamMembers();
        if (checkIfAnyUserInfoFieldIsEmpty() && checkForAnyInvalidField())
            repositoryInstance.registerParticipant(new EventRegistrations(
                    eventsManager.getEventName(),
                    userManager.getUsersName(),
                    eventsManager.getCurrentEventBannerURL(),
                    teamMembers,
                    null,
                    name,
                    email,
                    phoneNo,
                    usn,
                    semester,
                    section,
                    null
            ), new Repository.RegistrationCompleteCallback() {
                @Override
                public void userRegisteredSuccessfully() {
                    if (viewInstance != null) {
                        viewInstance.userRegisteredSuccessfully();
                        viewInstance.resetAllUserData();
                        viewInstance.onProcessEnded();
                        String subject = "Interrupt7: Welcome " + name + ", We are glad to have you here.";
                        String body = "Hi " + name + ",\n" +
                                "We are happy to have you with us on Interrupt. This email confirms that you have registered of the event on Interrupt 7" +
                                "\nEvent Name: " + eventsManager.getEventName() +
                                "\nParticipant Name: " + name +
                                "\nEmail ID: " + email +
                                "\nPhone No: " + phoneNo +
                                "\nUSN: " + usn +
                                "\nSemester: " + semester +
                                "\nSection: " + section +
                                "\nDepartment: " + department +
                                "\nTeam Members: " + teamMembers +
                                "\n\n To Be Updated on Schedules, view all of your event registrations and see more events download the Interrupt7 android App from the Google Play Store." +
                                "https://play.google.com/store/apps/details?id=com.bharathksunil.interrupt" +
                                "\n\nRegister on the app with the same emailId to view the events on the email." +
                                "\n\nCheers," +
                                "\n\n" + userManager.getUsersName() +
                                "\nEvent Coordinator- " + eventsManager.getEventName() +
                                "\nInterrupt7";
                        viewInstance.sendEmail(email, subject, body);
                    }
                }

                @Override
                public void userRegistrationFailed() {
                    if (viewInstance != null) {
                        viewInstance.onProcessEnded();
                        viewInstance.onUnexpectedError();
                    }

                }

                @Override
                public void userAlreadyRegistered() {
                    if (viewInstance == null)
                        return;
                    viewInstance.onProcessEnded();
                    viewInstance.showUserAlreadyRegisteredError();
                }
            });
        else
            viewInstance.onProcessEnded();
    }

    private boolean checkIfAnyUserInfoFieldIsEmpty() {
        if (viewInstance == null)
            return false;

        if (TextUtils.isEmpty(name))
            viewInstance.onNameFieldError(EMPTY);
        else if (TextUtils.isEmpty(email))
            viewInstance.onEmailFieldError(EMPTY);
        else if (TextUtils.isEmpty(phoneNo))
            viewInstance.onPhoneNumberFieldError(EMPTY);
        else if (TextUtils.isEmpty(usn))
            viewInstance.onUSNFieldError(EMPTY);
        else if (TextUtils.isEmpty(department))
            viewInstance.onDepartmentFieldError(EMPTY);
        else if (TextUtils.isEmpty(section))
            viewInstance.onSectionFieldError(EMPTY);
        else if (TextUtils.isEmpty(semester))
            viewInstance.onSemFieldError(EMPTY);
        else
            return true;
        if (TextUtils.isEmpty(teamMembers))
            teamMembers = "none";
        return false;
    }

    private boolean checkForAnyInvalidField() {
        if (viewInstance == null)
            return false;

        if (name.length() < 4)
            viewInstance.onNameFieldError(INVALID);
        else if (!TextUtils.isEmailValid(email))
            viewInstance.onEmailFieldError(INVALID);
        else if (!TextUtils.isPhoneNumberValid(phoneNo))
            viewInstance.onPhoneNumberFieldError(INVALID);
        else if (department.length() < 3)
            viewInstance.onDepartmentFieldError(INVALID);
        else
            return true;
        return false;
    }

}
