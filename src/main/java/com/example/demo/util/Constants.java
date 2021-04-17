package com.example.demo.util;

public class Constants {

    // Regex to check whether a line is: alphanumeric
    public static final String ALPHA_NUMERIC = "^[a-zA-Z0-9]+$";

    // Regex to check whether a line is: alphanumeric, space, special characters
    public static final String ADDR_LINE_REGEX = "^[a-zA-Z0-9 //#.-]+$";

    // Regex to check whether a line is: alphabetical, space
    public static final String ALPHA_SPACE = "^[a-zA-Z ]+$";

    // Regex to check whether a line is: alphabetical
    public static final String ALPHA = "^[a-zA-Z]+$";

    // Regex to check whether a zip code is:
    public static final String ZIP_REGEX = "^[0-9]{5,5}+$";

    public static final String INVALID_ADDRESSLINES = ".*\\b(none|unknown|unkown)\\b.*";

    // Matches: 300000000, 400000000, 500000000 || 000000000
    public static final String SPECIAL_TIN_LIST_DIGITAL = "[3-5][0]{8}|[0]{9}";

    // Will match all exactly 9 digit numbers. No special characters or alphabets.
    public static final String ONLY_DIGITS = "\\d{9}";

    // First position must be an alphabetical character, all other positions may contain alpha, space, single quote ('), period (.), and hyphen (-)
    public static final String ALPHA_CHARACTER_REGEX = "^[a-zA-Z][a-zA-Z '.-]*$";

    // Will match any string that contains at least one digit
    public static final String STRING_CONTAINS_NUMBERS = ".*\\d+.*";

    // Will match any string that contains a special character. So anything that is NOT one of these characters.
    public static final String STRING_CONTAINS_SPECIAL_CHARACTERS = ".*[^a-zA-Z '.-].*";

    public static final String[] SPECIAL_CHARS_FOR_NAME  = {"~", "`", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "_", "+", "=", "|", "{", "}", ";", ",", ":", "?", "/", "<", ">", "\\", "\""};

    public static final String[] INVALID_FIELD_LIST_NAMES = {"UNKNOWN", "UNKOWN", "N/A", "N / A", "?", "??", "???", "????", "DONTKNOW", "DON'T KNOW", "NOTPROVIDED", "NOT PROVIDED", "NOT APPLICABLE", "NOTAPPLICABLE"};

    public static final String DATE_PATTERN = "mm/dd/yyyy";
    public static final String DATE_PATTERN_NEW = "MM/dd/uuuu";

    public static final String HYPHEN_DELIMITER = "-";

    public static final String ADDRESSLINE1 = "addressLine1";
    public static final String ADDRESSLINE2 = "addressLine2";

    public static final String FIRST_NAME = "firstName";
    public static final String MIDDLE_NAME = "middleName";
    public static final String LAST_NAME = "lastName";

}
