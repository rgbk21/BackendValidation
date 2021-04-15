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

    public static final String ADDRESSLINE1 = "addressLine1";
    public static final String ADDRESSLINE2 = "addressLine2";

}
