package com.example.demo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {

    public static ErrorInfo createErrorInfo(String errorCode, String message) {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setCode(errorCode);
        errorInfo.setMessageText(message);
        return errorInfo;
    }

    public static boolean matchPattern(String input, String regex) {
        boolean returnValue = false;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            returnValue = true;
        }
        return returnValue;
    }

    public static boolean anySpecialCharacters(String input, String[] validationList) {
        boolean hasSpecialChars = false;
        for (String s : validationList) {
            if (input.contains(s)) {
                hasSpecialChars = true;
                break;
            }
        }
        return hasSpecialChars;
    }

    public static boolean containsInvalidFieldNames(String input, String[] validationList) {
        boolean hasInvalidNames = false;
        for (String s : validationList) {
            if (input.contains(s)) {
                hasInvalidNames = true;
                break;
            }
        }
        return hasInvalidNames;
    }

    public static boolean consecutiveSpaces(String input) {
        String doubleSpace = "  ";
        return input.contains(doubleSpace);
    }

    public static boolean consecutiveQuotes(String input) {
        String doubleQuotes = "''";
        return input.contains(doubleQuotes);
    }

    public static boolean consecutivePeriods(String input) {
        String doublePeriods = "..";
        return input.contains(doublePeriods);
    }

    public static boolean consecutiveHyphens(String input) {
        String doubleHyphens = "--";
        return input.contains(doubleHyphens);
    }













}
