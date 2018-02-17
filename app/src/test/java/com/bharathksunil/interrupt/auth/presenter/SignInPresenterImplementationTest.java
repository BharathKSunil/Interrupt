package com.bharathksunil.interrupt.auth.presenter;

import android.support.annotation.NonNull;

import com.bharathksunil.interrupt.auth.presenter.FormErrorType;
import com.bharathksunil.interrupt.auth.presenter.SignInPresenter;
import com.bharathksunil.interrupt.auth.presenter.SignInPresenterImplementation;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Random;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * This runs some tests on the SignIn presenter. All the tests must pass.
 *
 * @author Bharath on 26-01-2018.
 */
public class SignInPresenterImplementationTest {

    private final String CORRECT_EMAIL = "bharathk.sunil.k@gmail.com";
    private final String CORRECT_PASSWORD = "12Bh@rath12";
    @SuppressWarnings({"", "FieldCanBeLocal"})
    private final String INCORRECT_EMAIL = "joey@gmail.com";
    @SuppressWarnings({"", "FieldCanBeLocal"})
    private final String INCORRECT_PASSWORD = "Joey@1234";

    private String INVALID_EMAIL() {
        String[] invalidEmails = new String[]{
                "plainaddress",
                "#@%^%#$@#$@#.com",
                "@example.com",
                "Joe Smith <email@example.com>",
                "email.example.com",
                "email@example@example.com",
                ".email@example.com",
                "email.@example.com",
                "email..email@example.com",
                "あいうえお@example.com",
                "email@example.com (Joe Smith)",
                "email@example",
                "email@-example.com",
                "email@example.web",
                "email@111.222.333.44444",
                "email@example..com",
                "Abc..123@example.com"
        };
        return invalidEmails[new Random().nextInt(invalidEmails.length)];
    }

    private String WEAK_PASSWORD() {
        String[] invalidPasswords =
                new String[]{
                        "@",            //no Normal, Capital Characters & no Digit & Length < 8
                        "1",            //no Normal, Special, Capital Characters & Length < 8
                        "B",            //no Special Characters & no Digits & Length < 8
                        "bh",           //no Special, Capital Characters & no Digit & Length < 8
                        "bh@",          //no Capital Characters & no Digit & Length < 8
                        "12bh@",        //no Capital Characters & Length < 8
                        "12Bh",         //no Special Characters & Length < 8
                        "12Bh@",        //Length < 8
                        "12bharath12",  //no Special Character & Capital Character
                        "12bh@rath12",  //no Capital Character
                        "12Bharath12",  //no Special Characters
                        "Bharath",      //no Digits & Special Characters & Length < 8
                        "Bh@rath"       //no Digits & Length < 8
                };
        return invalidPasswords[new Random().nextInt(invalidPasswords.length)];
    }

    @Mock
    private SignInPresenter.View view;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    //============================Presenter->View Interaction Tests================================/

    /**
     * This test checks if the view will be notified if the login is successful
     */
    @Test
    public void successfullySignedInTest() {
        when(view.getEmailField()).thenReturn(CORRECT_EMAIL);
        when(view.getPasswordField()).thenReturn(CORRECT_PASSWORD);

        SignInPresenter.Repository repository = new SignInPresenter.Repository() {
            @Override
            public void signInWithEmailAndPassword(@NonNull String email, @NonNull String password,
                                                   @NonNull SignInCallbacks signInCallbacks) {
                signInLogic(email, password, signInCallbacks);
            }
        };

        SignInPresenterImplementation signInPresenter = new SignInPresenterImplementation(repository);
        signInPresenter.setView(view);
        signInPresenter.onSignInButtonClicked();

        verify(view).onUserSignedIn();
    }

    /**
     * This test checks if the presenter will detect empty email field
     */
    @Test
    public void onEmptyEmailEnteredTest() {
        when(view.getEmailField()).thenReturn("");
        when(view.getPasswordField()).thenReturn(CORRECT_PASSWORD);

        SignInPresenter.Repository repository = new SignInPresenter.Repository() {
            @Override
            public void signInWithEmailAndPassword(@NonNull String email, @NonNull String password,
                                                   @NonNull SignInCallbacks signInCallbacks) {
                signInLogic(email, password, signInCallbacks);
            }
        };

        SignInPresenterImplementation signInPresenter = new SignInPresenterImplementation(repository);
        signInPresenter.setView(view);
        signInPresenter.onSignInButtonClicked();

        verify(view).onEmailError(ArgumentMatchers.eq(FormErrorType.EMPTY));
    }

    /**
     * This test checks if the presenter will detect invalid email fields
     */
    @Test
    public void onInvalidEmailIdEnteredTest() {

        String email = INVALID_EMAIL();
        System.out.println("Incorrect Email Passed: " + email);

        when(view.getEmailField()).thenReturn(email);
        when(view.getPasswordField()).thenReturn(CORRECT_PASSWORD);
        SignInPresenter.Repository repository = new SignInPresenter.Repository() {
            @Override
            public void signInWithEmailAndPassword(@NonNull String email, @NonNull String password,
                                                   @NonNull SignInCallbacks signInCallbacks) {
                signInLogic(email, password, signInCallbacks);
            }
        };
        SignInPresenterImplementation signInPresenter = new SignInPresenterImplementation(repository);
        signInPresenter.setView(view);
        signInPresenter.onSignInButtonClicked();

        verify(view).onEmailError(ArgumentMatchers.eq(FormErrorType.INVALID));
    }

    /**
     * This test checks if the presenter will be able to detect empty password fields
     */
    @Test
    public void onEmptyPasswordEnteredTest() {

        when(view.getEmailField()).thenReturn(CORRECT_EMAIL);
        when(view.getPasswordField()).thenReturn("");

        SignInPresenter.Repository repository = new SignInPresenter.Repository() {
            @Override
            public void signInWithEmailAndPassword(@NonNull String email, @NonNull String password,
                                                   @NonNull SignInCallbacks signInCallbacks) {
                signInLogic(email, password, signInCallbacks);
            }
        };
        SignInPresenterImplementation signInPresenter = new SignInPresenterImplementation(repository);
        signInPresenter.setView(view);
        signInPresenter.onSignInButtonClicked();

        verify(view).onPasswordError(ArgumentMatchers.eq(FormErrorType.EMPTY));
    }

    /**
     * This test checks if the presenter will be able to detect invalid and less strong password
     * fields; the fields must have:
     * C1: 8 characters
     * C2: minimum one digit
     * C3: one Special Character
     */
    @Test
    public void onInvalidOrWeakPasswordEnteredTest() {

        when(view.getEmailField()).thenReturn(CORRECT_EMAIL);
        when(view.getPasswordField()).thenReturn(WEAK_PASSWORD());

        SignInPresenter.Repository repository = new SignInPresenter.Repository() {
            @Override
            public void signInWithEmailAndPassword(@NonNull String email, @NonNull String password,
                                                   @NonNull SignInCallbacks signInCallbacks) {
                signInLogic(email, password, signInCallbacks);
            }
        };
        SignInPresenterImplementation signInPresenter = new SignInPresenterImplementation(repository);
        signInPresenter.setView(view);
        signInPresenter.onSignInButtonClicked();

        verify(view).onPasswordError(ArgumentMatchers.eq(FormErrorType.INVALID));

    }

    //=====================Repository->Presenter->View Interaction Tests===========================/

    /**
     * This test will check if the presenter is notified if the email id was not in the repository
     */
    @Test
    public void onEmailIncorrectTest() {

        when(view.getEmailField()).thenReturn(INCORRECT_EMAIL);
        when(view.getPasswordField()).thenReturn(CORRECT_PASSWORD);

        SignInPresenter.Repository repository = new SignInPresenter.Repository() {
            @Override
            public void signInWithEmailAndPassword(@NonNull String email, @NonNull String password,
                                                   @NonNull SignInCallbacks signInCallbacks) {
                signInLogic(email, password, signInCallbacks);
            }
        };

        SignInPresenterImplementation signInPresenter = new SignInPresenterImplementation(repository);
        signInPresenter.setView(view);
        signInPresenter.onSignInButtonClicked();

        verify(view).onEmailError(ArgumentMatchers.eq(FormErrorType.INCORRECT));
    }

    /**
     * This test will check if the presenter is notified if the password had a mismatch
     */
    @Test
    public void onPasswordIncorrectTest() {

        when(view.getEmailField()).thenReturn(CORRECT_EMAIL);
        when(view.getPasswordField()).thenReturn(INCORRECT_PASSWORD);

        SignInPresenter.Repository repository = new SignInPresenter.Repository() {
            @Override
            public void signInWithEmailAndPassword(@NonNull String email, @NonNull String password,
                                                   @NonNull SignInCallbacks signInCallbacks) {
                signInLogic(email, password, signInCallbacks);
            }
        };

        SignInPresenterImplementation signInPresenter = new SignInPresenterImplementation(repository);
        signInPresenter.setView(view);
        signInPresenter.onSignInButtonClicked();

        verify(view).onPasswordError(ArgumentMatchers.eq(FormErrorType.INCORRECT));

    }

    /**
     * This test will check if the presenter will be norified if there was an unexpected error in
     * the repository
     */
    @Test
    public void onUnexpectedErrorTest() {

        when(view.getEmailField()).thenReturn(CORRECT_EMAIL);
        when(view.getPasswordField()).thenReturn(CORRECT_PASSWORD);

        SignInPresenter.Repository repository = new SignInPresenter.Repository() {
            @Override
            public void signInWithEmailAndPassword(@NonNull String email, @NonNull String password,
                                                   @NonNull SignInCallbacks signInCallbacks) {
                signInCallbacks.onRepositoryException();
            }
        };

        SignInPresenterImplementation signInPresenter = new SignInPresenterImplementation(repository);
        signInPresenter.setView(view);
        signInPresenter.onSignInButtonClicked();

        verify(view).onUnexpectedError();

    }

    /**
     * This test will check if the presenter will be notified if there UserManager tries to signIn again
     */
    @Test
    public void detectUserAlreadySignedInTest() {

        when(view.getEmailField()).thenReturn(CORRECT_EMAIL);
        when(view.getPasswordField()).thenReturn(CORRECT_PASSWORD);

        SignInPresenter.Repository repository = new SignInPresenter.Repository() {
            @Override
            public void signInWithEmailAndPassword(@NonNull String email, @NonNull String password,
                                                   @NonNull SignInCallbacks signInCallbacks) {
                signInCallbacks.isAlreadySignedIn();
            }
        };

        SignInPresenterImplementation signInPresenter = new SignInPresenterImplementation(repository);
        signInPresenter.setView(view);
        signInPresenter.onSignInButtonClicked();

        verify(view).onUserAlreadySignedIn();

    }

    /**
     * This method emulates the logic of the repository Signing In the UserManager
     *
     * @param email           the email id
     * @param password        the password
     * @param signInCallbacks the signIn callbacks
     */
    private void signInLogic(String email, String password, SignInPresenter.Repository.SignInCallbacks signInCallbacks) {
        if (email.equals(CORRECT_EMAIL)) {
            if (password.equals(CORRECT_PASSWORD)) {
                signInCallbacks.onSignInSuccessful();
            } else
                signInCallbacks.onPasswordIncorrect();
        } else
            signInCallbacks.onEmailIncorrect();
    }
}