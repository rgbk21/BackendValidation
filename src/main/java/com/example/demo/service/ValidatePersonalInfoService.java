package com.example.demo.service;

import com.example.demo.model.profile.Personal;
import com.example.demo.util.Constants;
import com.example.demo.util.ErrorInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.demo.util.CommonUtils.createErrorInfo;
import static com.example.demo.util.CommonUtils.matchPattern;
import static com.example.demo.util.Constants.*;

@Component
public class ValidatePersonalInfoService {

    private static final Log LOGGER = LogFactory.getLog(ValidatePersonalInfoService.class);

    public List<ErrorInfo> validatePersonalInfo(Personal personalInfo) {

        LOGGER.info("Inside ValidateUserService::validatePersonalInfo starts");

        List<ErrorInfo> errorInfoList = new ArrayList<>();

        validateSSNTIN(personalInfo, errorInfoList);
        validateDateOfBirth(personalInfo, errorInfoList);



        LOGGER.info("Inside ValidateUserService::validatePersonalInfo starts");
        return errorInfoList;
    }

    private void validateDateOfBirth(Personal personalInfo, List<ErrorInfo> errorInfoList) {

        LOGGER.info("Validate Date of Birth Starts");
        ErrorInfo errorInfo = null;
        boolean isValidDob = true;

        if (StringUtils.isNotBlank(personalInfo.getDateOfBirth())) {
            String dateOfBirth = personalInfo.getDateOfBirth();

            // Maximum 10 characters of length are allowed in the dateOfBirth field
            if (dateOfBirth.length() > 10) {
                errorInfo = createErrorInfo("ERR_PERSONAL_INFO_DOB_GREATER_THAN_TEN_CHARS", "Personal Info DOB cannot be greater than 10 characters");
                errorInfoList.add(errorInfo);
                isValidDob = false;
            }

            // Date of birth must be in mm/dd/yyyy format
            if (!isValidDateFormatNew(dateOfBirth, DATE_PATTERN_NEW)) {
                errorInfo = createErrorInfo("ERR_PERSONAL_INFO_DOB_INVALID_FORMAT", "Personal Info DOB must be in mm/dd/yyyy format");
                errorInfoList.add(errorInfo);
                isValidDob = false;
            }

            // Age cannot be greater than 125 years
            // Date of birth cannot be equal to today's date
            // Date of birth cannot be greater than today's date
            validateAge(dateOfBirth, DATE_PATTERN_NEW, errorInfoList);

        } else {
            errorInfo = createErrorInfo("ERR_PERSONAL_INFO_DOB_BLANK", "Personal Info DOB cannot be blank");
            errorInfoList.add(errorInfo);
            isValidDob = false;
        }

        LOGGER.info("Valid DOB: " + isValidDob);

    }

    private void validateAge(String dateOfBirth, String pattern, List<ErrorInfo> errorInfoList) {

        LOGGER.info("ValidatePersonalInfoService::validateAge starts");

        ErrorInfo errorInfo = null;
        boolean isValidAge = true;

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        LocalDate dob = LocalDate.parse(dateOfBirth, dateTimeFormatter);
        LocalDate now = LocalDate.now();
        Period diff = Period.between(dob, now);
        int yearDiff = diff.getYears();
        int monthDiff = diff.getMonths();
        int dayDiff = diff.getDays();

        if (now.isAfter(dob)) {
            if (yearDiff <= 125) {
                if (yearDiff == 125) {
                    if (monthDiff != 0 || yearDiff != 0) {
                        errorInfo = createErrorInfo("ERR_PERSONAL_INFO_AGE_EXCEEDED", "Personal Info cannot be greater than 125");
                        errorInfoList.add(errorInfo);
                        isValidAge = false;
                    }
                }
            } else {
                errorInfo = createErrorInfo("ERR_PERSONAL_INFO_AGE_EXCEEDED", "Personal Info cannot be greater than 125");
                errorInfoList.add(errorInfo);
                isValidAge = false;
            }
        } else if (yearDiff == 0 && monthDiff == 0 && dayDiff == 0) {
            errorInfo = createErrorInfo("ERR_PERSONAL_INFO_DOB_TODAY", "Personal Info DOB cannot be today's date");
            errorInfoList.add(errorInfo);
            isValidAge = false;
        } else {
            errorInfo = createErrorInfo("ERR_PERSONAL_INFO_DOB_LATER_THAN_TODAY", "Personal Info DOB cannot be after today's date");
            errorInfoList.add(errorInfo);
            isValidAge = false;
        }

        LOGGER.info("ValidatePersonalInfoService:: isValidAge::" + isValidAge);

    }

    private boolean isValidDateFormatNew(String dateOfBirth, String datePattern) {

        boolean isValidDate = true;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        try {
            LocalDate ld = LocalDate.parse(dateOfBirth, formatter);
            LOGGER.info("Parsed date: " + ld.toString());
        } catch (DateTimeParseException e) {
            isValidDate = false;
            LOGGER.warn( "DateTimeParseException: " + e );
        }
        return isValidDate;
    }


    private boolean isValidDateFormat(String dateOfBirth, String datePattern) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
        try {
            date = sdf.parse(dateOfBirth);
            LOGGER.info("Parsed date: " + date.toString());
            if (!dateOfBirth.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException e) {
            LOGGER.warn("ParseException: " + e);
        }
        return date != null;
    }

    private void validateSSNTIN(Personal personalInfo, List<ErrorInfo> errorInfoList) {

        LOGGER.info("Validate SSN Starts");

        ErrorInfo errorCode = null;
        boolean isValidSSN = true;

        if (StringUtils.isNotBlank(personalInfo.getSsnTin())) {
            String clearedSSNTIN = clearHyphensFromSSN(personalInfo.getSsnTin());
            if (matchPattern(clearedSSNTIN, SPECIAL_TIN_LIST_DIGITAL)) {
                errorCode = createErrorInfo("ERR_PERSONAL_INFO_SSNTIN_SPECIAL", "Personal Info SSN-TIN cannot be special");
                errorInfoList.add(errorCode);
                isValidSSN = false;
            }
            if (!matchPattern(clearedSSNTIN, ONLY_DIGITS)) {
                errorCode = createErrorInfo("ERR_PERSONAL_INFO_SSNTIN_DIGITS_ONLY", "Personal Info SSN-TIN can only contain digits");
                errorInfoList.add(errorCode);
                isValidSSN = false;
            }
        } else {
            errorCode = createErrorInfo("ERR_PERSONAL_INFO_SSNTIN_BLANK", "Personal Info SSN-TIN cannot be blank");
            errorInfoList.add(errorCode);
            isValidSSN = false;
        }

        LOGGER.info("Valid SSN:" + isValidSSN);

    }

    private String clearHyphensFromSSN(String ssnTin) {
        return ssnTin.trim().replaceAll(Constants.HYPHEN_DELIMITER, StringUtils.EMPTY);
    }

}
