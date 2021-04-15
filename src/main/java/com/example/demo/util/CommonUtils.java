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

}
