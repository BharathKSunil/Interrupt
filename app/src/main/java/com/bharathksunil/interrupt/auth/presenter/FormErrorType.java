package com.bharathksunil.interrupt.auth.presenter;

/**
 * This enum accumulates the different types of errors possible on a form
 * i.e., on a EditText field entered by the user
 *
 * @author Bharath on 26-01-2018.
 */

public enum FormErrorType {
    /**
     * This error type means that the user entered Field is empty and should have some value
     * to accept it as an input.(Required field)
     */
    EMPTY,
    /**
     * This error type means that the user entered filed is Syntactically incorrect(unstructured email)
     * or does not match the input requirements(such as passwords strengths)
     * or the system expected an integer but a string was passed
     */
    INVALID,
    /**
     * This error type means that the user input field is not correct and doesn't match the input
     * expected(such as incorrect email or password or pin)
     */
    INCORRECT
}
