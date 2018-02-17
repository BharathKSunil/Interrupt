package com.bharathksunil.interrupt.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Bharath on 10-01-2018.
 */

public class TextUtils {

    private static final String mustHaveDigit = "(?=.*[0-9])",
            mustHaveLowerCaseAlpha = "(?=.*[0-9])",
            mustHaveUpperCaseAlpha = "(?=.*[a-z])",
            mustHaveSpecialChar = "(?=.*[@#$%^&+=])",
            mustNotHaveWhiteSpaces = "(?=\\S+$)",
            mustHaveMinimal8Char = ".{8,}";
    /**
     * Password validation Regular expression, modules:
     * ^                 # start-of-string
     * (?=.*[0-9])       # a digit must occur at least once
     * (?=.*[a-z])       # a lower case letter must occur at least once
     * (?=.*[A-Z])       # an upper case letter must occur at least once --
     * (?=.*[@#$%^&+=])  # a special character must occur at least once
     * (?=\S+$)          # no whitespace allowed in the entire string
     * .{8,}             # anything, at least eight places though
     * $                 # end-of-string
     */
    private static final Pattern VALID_PASSWORD_REGEX = Pattern.compile(
            "^"
                    + mustHaveDigit
                    + mustHaveLowerCaseAlpha
                    + mustNotHaveWhiteSpaces
                    + mustHaveMinimal8Char
                    + "$"
    );

    /**
     * Phone number Validation Regular Expression to check if
     * ^                # start-of-string
     * [6789]           # contains 6 or 7 or 8 or 9
     * \\d{9}           # contains other 9 digits
     * $                # end-of-string
     */
    private static final Pattern VALID_PHONE_NUMBER_REGEX = Pattern.compile("^[6789]\\d{9}$");

    /**
     * Email Id Validation Regular Expression to validate an email ID
     */
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", Pattern.CASE_INSENSITIVE);

    private static final String DATE_PATTERN = "dd/MM/yyyy";


    //=============================== Method =============================================

    /**
     * This method checks if the emailID syntax is valid
     *
     * @param email the emailId to be validated
     * @return true, if the email is syntactically valid
     */
    public static boolean isEmailValid(CharSequence email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    /**
     * It checks if the phone number is a valid mobile number in India
     *
     * @param phone the mobile number to be validated
     * @return true, if the phone number is valid
     */
    public static boolean isPhoneNumberValid(CharSequence phone) {
        Matcher phone_matcher = VALID_PHONE_NUMBER_REGEX.matcher(phone);
        return phone_matcher.find();
    }

    /**
     * It checks if the password matches these criteria to ensure a strong password
     * C1: Contains 8 characters
     * C2: Contains minimum one digit
     * C3: Contains one Special Character
     *
     * @param password the password to be validated
     * @return true, if the password is strong and matches all criteria
     */
    public static boolean isPasswordStrong(CharSequence password) {
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
        return matcher.find();
    }

    /**
     * It checks if the date is in the format DATE_PATTERN and is a valid date
     *
     * @param date the date string to be checked
     * @return true, if the date is in the format and is valid
     */
    public static boolean isDateValid(CharSequence date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault());
        format.setLenient(false);
        try {
            format.parse(date.toString());
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * It checks if the string is empty or not
     * This is included here to avoid name collision with android.text.TextUtil
     *
     * @param string the string to be checked
     * @return true, if the string is empty
     */
    public static boolean isEmpty(CharSequence string) {
        return string == null || string.length() == 0;
    }

    public static boolean areEqual(CharSequence first, CharSequence second) {
        return android.text.TextUtils.equals(first, second);
    }

    public static boolean isDigitsOnly(CharSequence charSequence) {
        return android.text.TextUtils.isDigitsOnly(charSequence);
    }

    public static String getEmailAsFirebaseKey(String email) {
        return email.replaceAll("\\.", "_dot_");
    }

    public static String getEmailFromFirebaseKey(String key) {
        return key.replaceAll("_dot_", "\\.");
    }

}
