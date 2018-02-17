package com.bharathksunil.interrupt.auth.model;

/**
 * This app has 5 types of users, these types are mentioned here
 * Some users can have a combination of more than one type. e.g,
 * A User can be a PARTICIPANT & a COORDINATOR.
 * In such circumstances the they are stored as "PARTICIPANT | COORDINATOR".
 * These must be split to these basic types as and when required( see {@link UserManager}.isUserAEventsCoordinator()
 * for how this condition is be handled).
 * @author Bharath Kumar S
 */

public enum UserType {
    /**
     * The basic user is the participant who has:
     * 1. Ability to view events and register for them
     * 2. Ability to view registered events
     * 3. Ability to view the Schedules and location of events
     */
    PARTICIPANT,
    /**
     * The Class Representative who :
     * 1. Has all abilities of a PARTICIPANT
     * 2. Can update the amount collection from his section
     * 3. Can view the total amount to be paid
     */
    CR,
    /**
     * The respective Event coordinators who has:
     * 1. All abilities of the PARTICIPANT
     * 2. Ability to modify the information of the event they registered for.
     * 3. Perform Registrations.
     * 4. Update Event Images and rounds
     */
    COORDINATOR,
    /**
     * Interrupt organisers who have the ability:
     * 1. of the COORDINATORS
     * 2. Add and Modify events.
     * 3. Add and Modify coordinators and CR
     * 4. Update Event Timings
     * 5. Declare money Received
     */
    ORGANISER,
    /**
     * The Administrator of the App,
     * 1. Has all abilities of the Organisers
     * 2. Can manage Organisers an their roles
     */
    ADMINISTRATOR
}
