package com.bharathksunil.interrupt.events.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.interrupt.admin.model.Users;
import com.bharathksunil.interrupt.auth.model.UserManager;
import com.bharathksunil.interrupt.auth.model.UserType;
import com.bharathksunil.interrupt.auth.presenter.FormErrorType;
import com.bharathksunil.interrupt.events.model.Categories;
import com.bharathksunil.interrupt.events.model.Events;
import com.bharathksunil.interrupt.events.model.EventsManager;
import com.bharathksunil.interrupt.util.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the implementation of the {@link EventDashboardActivityPresenter}
 *
 * @author Bharath on 28-02-2018.
 */

public class EventDashboardActivityPresenterImplementation implements EventDashboardActivityPresenter {


    @NonNull
    private EventCategoriesPresenter.Repository categoriesRepositoryInstance;
    @NonNull
    private Repository eventRepositoryInstance;
    @Nullable
    private View viewInstance;
    @NonNull
    private EventsManager eventsManager;
    @NonNull
    private UserManager userManager;
    private List<Categories> allCategoriesList;
    private boolean isEditing;

    public EventDashboardActivityPresenterImplementation(@NonNull EventCategoriesPresenter.Repository
                                                                 categoriesRepositoryInstance,
                                                         @NonNull Repository eventRepository) {
        this.categoriesRepositoryInstance = categoriesRepositoryInstance;
        this.eventRepositoryInstance = eventRepository;
        this.eventsManager = EventsManager.getInstance();
        this.userManager = UserManager.getInstance();
    }

    @Override
    public void setView(@Nullable View view) {
        viewInstance = view;
        if (viewInstance == null)
            return;
        viewInstance.onProcessStarted();
        //check if the activity was added to add an event
        if (userManager.canUserAddEvents() && eventsManager.isEventsEmpty())
            loadNewEventsPage();
        else
            loadEventsViewerPage();

    }

    private void loadNewEventsPage() {
        if (viewInstance == null)
            return;
        isEditing = false;
        viewInstance.setEventBannerImageEnabled(true);
        viewInstance.loadEventBanner("");
        viewInstance.setEventNameFieldEnabled(true);
        viewInstance.setEventCategoryFieldEnabled(true);
        categoriesRepositoryInstance.downloadEventCategories(
                new EventCategoriesPresenter.Repository.DataLoadedCallback() {
                    @Override
                    public void onDataSuccessfullyLoaded(List<Categories> categoriesList) {
                        if (viewInstance == null)
                            return;
                        allCategoriesList = categoriesList;
                        List<String> categories = new ArrayList<>();
                        for (Categories category : categoriesList)
                            categories.add(category.getName());
                        viewInstance.loadEventCategories(categories);
                        viewInstance.onProcessEnded();
                    }

                    @Override
                    public void onDataLoadFailed() {
                        if (viewInstance == null)
                            return;
                        viewInstance.onProcessEnded();
                        viewInstance.onUnexpectedError();
                    }
                }
        );
        viewInstance.setEventDescriptionFieldEnabled(true);
        viewInstance.setEventTimeButtonEnabled(true);
        viewInstance.setEventVenueFieldEnabled(true);

        viewInstance.hideEventRegistrationsButton();
        viewInstance.hideAddRegistrationButton();
        viewInstance.hideDownloadRegistrationButton();
        viewInstance.hideEditButton();

        viewInstance.showSubmitButton();
        viewInstance.setSubmitButtonText("Add Event");
        viewInstance.showCancelButton();
    }

    private void loadEventsViewerPage() {
        if (viewInstance == null)
            return;
        if (eventsManager.isEventsEmpty()) {
            viewInstance.onUnexpectedError();
            viewInstance.onProcessEnded();
            return;
        }
        viewInstance.loadEventBanner(eventsManager.getCurrentEventBannerURL());
        viewInstance.setEventBannerImageEnabled(false);

        viewInstance.setEventName(eventsManager.getEventName());
        viewInstance.setEventNameFieldEnabled(false);

        List<String> categories = new ArrayList<>();
        categories.add(eventsManager.getEventCategory());
        allCategoriesList = new ArrayList<>();
        allCategoriesList.add(new Categories(
                eventsManager.getCurrentCategoryID(),
                eventsManager.getEventCategory(),
                null,
                null
        ));
        viewInstance.loadEventCategories(categories);
        viewInstance.setEventCategoryFieldEnabled(false);

        viewInstance.setEventDescription(eventsManager.getEventDescription());
        viewInstance.setEventDescriptionFieldEnabled(false);

        viewInstance.setEventPrice(eventsManager.getEventPrice());
        viewInstance.setEventPriceFieldEnabled(false);

        viewInstance.setTimeButtonText(eventsManager.getEventTime());
        viewInstance.setEventTimeButtonEnabled(false);

        viewInstance.setEventVenue(eventsManager.getEventVenue());
        viewInstance.setEventVenueFieldEnabled(false);

        viewInstance.setEventCoordinatorName(eventsManager.getEventCoordinatorName());
        viewInstance.setEventCoordinatorNameFieldEnabled(false);

        viewInstance.setEventCoordinatorEmail(eventsManager.getEventCoordinatorEmail());
        viewInstance.setEventCoordinatorEmailFieldEnabled(false);

        viewInstance.setEventCoordinatorPhoneNo(eventsManager.getEventCoordinatorPhone());
        viewInstance.setEventCoordinatorPhoneNoFieldEnabled(false);

        if (userManager.canUserViewEventRegistrations())
            viewInstance.showEventRegistrationsButton();
        else
            viewInstance.hideEventRegistrationsButton();

        if (userManager.canUserRegisterParticipant())
            viewInstance.showAddRegistrationButton();
        else
            viewInstance.hideAddRegistrationButton();

        if (userManager.canUserDownloadEventsRegistrations())
            viewInstance.showDownloadRegistrationButton();
        else
            viewInstance.hideDownloadRegistrationButton();

        if (userManager.canUserModifyEventSchedule() || userManager.canUserModifyEventInfo()
                || userManager.canUserModifyEventSchedule() || userManager.canUserModifyEventGraphics()
                || userManager.canUserModifyEventVenue())
            viewInstance.showEditButton();
        else
            viewInstance.hideEditButton();

        viewInstance.hideCancelButton();
        viewInstance.hideSubmitButton();

        viewInstance.onProcessEnded();
    }

    @Override
    public void onBannerImagePressed() {
        if (viewInstance == null)
            return;
        if (userManager.canUserModifyEventGraphics())
            viewInstance.showBannerImageChooser();
        else
            viewInstance.showPermissionDeniedMessage();

    }

    @Override
    public void onSetDateButtonPressed() {
        if (viewInstance == null)
            return;
        if (userManager.canUserModifyEventSchedule())
            viewInstance.showDateTimeChooser();
        else
            viewInstance.showPermissionDeniedMessage();
    }

    @Override
    public void onEditButtonPressed() {
        if (viewInstance == null)
            return;
        if (userManager.canUserModifyEventVenue() || userManager.canUserModifyEventSchedule()
                || userManager.canUserModifyEventInfo() || userManager.canUserModifyEventGraphics()) {
            isEditing = true;
            viewInstance.setEventCategoryFieldEnabled(false);
            viewInstance.setEventCoordinatorNameFieldEnabled(false);
            viewInstance.setEventCoordinatorEmailFieldEnabled(false);
            viewInstance.setEventCoordinatorPhoneNoFieldEnabled(false);

            if (userManager.canUserModifyEventInfo()) {
                viewInstance.setEventNameFieldEnabled(true);
                viewInstance.setEventDescriptionFieldEnabled(true);
                viewInstance.hideEventRegistrationsButton();
            }
            viewInstance.setEventTimeButtonEnabled(userManager.canUserModifyEventSchedule());
            viewInstance.setEventVenueFieldEnabled(userManager.canUserModifyEventVenue());
            viewInstance.setEventTimeButtonEnabled(userManager.canUserModifyEventSchedule());
            viewInstance.setEventVenueFieldEnabled(userManager.canUserModifyEventVenue());
            viewInstance.setEventBannerImageEnabled(userManager.canUserModifyEventGraphics());

            viewInstance.hideEditButton();
            viewInstance.hideDownloadRegistrationButton();
            viewInstance.hideAddRegistrationButton();

            viewInstance.hideEventRegistrationsButton();
            viewInstance.showSubmitButton();
            viewInstance.setSubmitButtonText("Save Changes");
            viewInstance.showCancelButton();
        } else {
            isEditing = false;
            viewInstance.showPermissionDeniedMessage();
        }
    }

    @Override
    public void onAddRegistrationButtonPressed() {
        if (viewInstance == null)
            return;
        if (userManager.canUserRegisterParticipant()) {
            viewInstance.loadNewParticipantRegistrationPage();
        }
        else
            viewInstance.showPermissionDeniedMessage();
    }

    @Override
    public void onDownloadEventRegistrationButtonPressed() {
        if (viewInstance == null)
            return;
        if (userManager.canUserDownloadEventsRegistrations()) {
            //todo: Download Event Registrations
            viewInstance.showComingSoonMessage();
        } else
            viewInstance.showPermissionDeniedMessage();

    }

    @Override
    public void onViewRegistrationsButtonPressed() {
        if (viewInstance == null)
            return;
        if (userManager.canUserViewEventRegistrations())
            viewInstance.loadEventRegistrationsViewer();
        else
            viewInstance.showPermissionDeniedMessage();
    }

    @Override
    public void onSubmitButtonPressed() {
        if (viewInstance == null)
            return;
        viewInstance.onProcessStarted();
        if (validateInputs()) {
            List<String> coordinators = new ArrayList<>();
            Users coordinator = new Users();
            if (!isEditing) {
                coordinator = new Users(
                        viewInstance.getCoordinatorName(),
                        UserType.COORDINATOR.name(),
                        viewInstance.getCoordinatorPhone(),
                        viewInstance.getCoordinatorEmail(),
                        null,
                        null
                );
                coordinators.add(coordinator.getName());
                coordinators.add(coordinator.getEmail());
                coordinators.add(coordinator.getPhoneNo());
            } else
                coordinators = eventsManager.getEventCoordinators();

            final Events events = new Events(
                    null,
                    allCategoriesList.get(viewInstance.getEventCategoryPosition()).getName(),
                    allCategoriesList.get(viewInstance.getEventCategoryPosition()).getId(),
                    viewInstance.getEventName(),
                    viewInstance.getEventDescription(),
                    viewInstance.getEventTime(),
                    null,
                    viewInstance.getEventVenue(),
                    Integer.parseInt(viewInstance.getPrice()),
                    coordinators
            );

            if (isEditing) {
                events.setId(eventsManager.getCurrentEventsID());
                events.setBannerUrl(eventsManager.getCurrentEventBannerURL());
                eventRepositoryInstance.updateEvent(events, viewInstance.getEventBanner(),
                        new Repository.OnEventRegistrationCallback() {
                            @Override
                            public void onEventAddedSuccessfully() {
                                if (viewInstance != null) {
                                    viewInstance.showProcessFinishedMessage("Event Added Successfully");
                                    viewInstance.onProcessEnded();
                                    loadEventsViewerPage();
                                }
                            }

                            @Override
                            public void onEventUpdatedSuccessfully() {
                                if (viewInstance != null) {
                                    viewInstance.showProcessFinishedMessage("Event Updated Successfully");
                                    viewInstance.onProcessEnded();
                                    eventsManager.loadEvents(events);
                                    loadEventsViewerPage();
                                }
                            }

                            @Override
                            public void onEventUpdateFailed() {
                                if (viewInstance != null) {
                                    viewInstance.onUnexpectedError();
                                    viewInstance.onProcessEnded();
                                    loadEventsViewerPage();
                                }
                            }

                            @Override
                            public void onEventAddFailed() {
                                if (viewInstance != null) {
                                    viewInstance.onUnexpectedError();
                                    viewInstance.onProcessEnded();
                                    loadEventsViewerPage();
                                }
                            }
                        });
            } else
                eventRepositoryInstance.addNewEvent(events, coordinator, viewInstance.getEventBanner(),
                        new Repository.OnEventRegistrationCallback() {
                            @Override
                            public void onEventAddedSuccessfully() {
                                if (viewInstance != null) {
                                    viewInstance.showProcessFinishedMessage("Event Added Successfully");
                                    viewInstance.onProcessEnded();
                                    loadNewEventsPage();
                                }
                            }

                            @Override
                            public void onEventUpdatedSuccessfully() {

                            }

                            @Override
                            public void onEventUpdateFailed() {

                            }

                            @Override
                            public void onEventAddFailed() {
                                if (viewInstance != null) {
                                    viewInstance.onUnexpectedError();
                                    viewInstance.onProcessEnded();
                                    loadEventsViewerPage();
                                }
                            }
                        });

        } else
            viewInstance.onProcessEnded();
    }

    private boolean validateInputs() {
        if (viewInstance == null)
            return false;
        if (TextUtils.isEmpty(viewInstance.getEventName())) {
            viewInstance.setNameFieldError(FormErrorType.EMPTY);
            return false;
        } else if (TextUtils.isEmpty(viewInstance.getEventDescription())) {
            viewInstance.setDescriptionFieldError(FormErrorType.EMPTY);
            return false;
        } else if (TextUtils.isEmpty(viewInstance.getEventTime())) {
            viewInstance.setTimeFieldError();
            return false;
        } else if (TextUtils.isEmpty(viewInstance.getEventVenue())) {
            viewInstance.setVenueFieldError(FormErrorType.EMPTY);
            return false;
        } else if (!isEditing && TextUtils.isEmpty(viewInstance.getCoordinatorName())) {
            viewInstance.setCoordinatorNameError(FormErrorType.EMPTY);
            return false;
        } else if (!isEditing && TextUtils.isEmpty(viewInstance.getCoordinatorEmail())) {
            viewInstance.setCoordinatorEmailError(FormErrorType.EMPTY);
            return false;
        } else if (!isEditing && !TextUtils.isEmailValid(viewInstance.getCoordinatorEmail())) {
            viewInstance.setCoordinatorEmailError(FormErrorType.INVALID);
            return false;
        } else if (!isEditing && TextUtils.isEmpty(viewInstance.getCoordinatorPhone())) {
            viewInstance.setCoordinatorPhoneError(FormErrorType.EMPTY);
            return false;
        } else if (!isEditing && !TextUtils.isPhoneNumberValid(viewInstance.getCoordinatorPhone())) {
            viewInstance.setCoordinatorPhoneError(FormErrorType.INVALID);
            return false;
        } else if (TextUtils.isEmpty(viewInstance.getPrice())) {
            viewInstance.setPriceError(FormErrorType.EMPTY);
            return false;
        } else if (!TextUtils.isDigitsOnly(viewInstance.getPrice())) {
            viewInstance.setPriceError(FormErrorType.INVALID);
            return false;
        }

        return true;
    }

    @Override
    public void onCancelButtonPressed() {
        if (viewInstance == null)
            return;
        viewInstance.exitActivity();
    }
}
