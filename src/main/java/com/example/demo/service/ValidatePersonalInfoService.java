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
import java.util.Locale;

import static com.example.demo.util.CommonUtils.*;
import static com.example.demo.util.Constants.*;

@Component
public class ValidatePersonalInfoService {

    private static final Log LOGGER = LogFactory.getLog(ValidatePersonalInfoService.class);

    public List<ErrorInfo> validatePersonalInfo(Personal personalInfo) {

        LOGGER.info("Inside ValidateUserService::validatePersonalInfo starts");

        List<ErrorInfo> errorInfoList = new ArrayList<>();

        validateSSNTIN(personalInfo, errorInfoList);
        validateDateOfBirth(personalInfo, errorInfoList);
        validateFirstName(personalInfo, errorInfoList);
        validateMiddleName(personalInfo, errorInfoList);
        validateLastName(personalInfo, errorInfoList);

        LOGGER.info("Inside ValidateUserService::validatePersonalInfo ends");
        return errorInfoList;
    }

    private void validateLastName(Personal personalInfo, List<ErrorInfo> errorInfoList) {

        LOGGER.info("ValidatePersonalInfoService::validateLastName Starts");
        ErrorInfo errorInfo = null;
        boolean isValidName = true;

        String lastName = personalInfo.getLastName().trim();

        if (StringUtils.isNotBlank(lastName)) {
            if (lastName.length() > 30) {
                errorInfo = createErrorInfo("ERR_PERSONAL_INFO_LAST_NAME_TOO_LONG", "Personal Info Last Name cannot be greater than 30 characters");
                errorInfoList.add(errorInfo);
            } else {
                validateName(lastName, errorInfoList, LAST_NAME);
            }
        } else {
            errorInfo = createErrorInfo("ERR_PERSONAL_INFO_LAST_NAME_BLANK", "Personal Info Last Name cannot be blank");
            errorInfoList.add(errorInfo);
            isValidName = false;
        }

        LOGGER.info("ValidatePersonalInfoService::validateLastName Ends");
    }

    private void validateMiddleName(Personal personalInfo, List<ErrorInfo> errorInfoList) {

        LOGGER.info("ValidatePersonalInfoService::validateMiddleName Starts");
        ErrorInfo errorInfo = null;

        String middleName = personalInfo.getMiddleName().trim();

        // Middle Name can be empty
        if (StringUtils.isNotBlank(middleName)) {
            if (middleName.length() > 25) {
                errorInfo = createErrorInfo("ERR_PERSONAL_INFO_MIDDLE_NAME_TOO_LONG", "Personal Info Middle Name cannot be greater than 25 characters");
                errorInfoList.add(errorInfo);
            } else {
                validateName(middleName, errorInfoList, MIDDLE_NAME);
            }
        }

        LOGGER.info("ValidatePersonalInfoService::validateMiddleName Ends");
    }

    private void validateFirstName(Personal personalInfo, List<ErrorInfo> errorInfoList) {

        LOGGER.info("ValidatePersonalInfoService::validateFirstName Starts");
        ErrorInfo errorInfo = null;
        boolean isValidName = true;

        String firstName = personalInfo.getFirstName().trim();

        if (StringUtils.isNotBlank(firstName)) {
            if (firstName.length() > 25) {
                errorInfo = createErrorInfo("ERR_PERSONAL_INFO_FIRST_NAME_TOO_LONG", "Personal Info First Name cannot be greater than 25 characters");
                errorInfoList.add(errorInfo);
            } else {
                validateName(firstName, errorInfoList, FIRST_NAME);
            }
        } else {
            errorInfo = createErrorInfo("ERR_PERSONAL_INFO_FIRST_NAME_BLANK", "Personal Info First Name cannot be blank");
            errorInfoList.add(errorInfo);
            isValidName = false;
        }

        LOGGER.info("ValidatePersonalInfoService::validateFirstName Ends");

    }

    private void validateName(String name, List<ErrorInfo> errorInfoList, String fieldName) {

        LOGGER.info("ValidatePersonalInfoService::validateName Starts");
        LOGGER.info("Validating field: " + fieldName);

        ErrorInfo errorInfo = null;
        boolean isValidName = true;

        // Should only contain alphabetical characters
        if (!matchPattern(name, ALPHA_CHARACTER_REGEX)) {
            errorInfo = createErrorInfo("ERR_PERSONAL_INFO_ILLEGAL_FIELD::" + fieldName, "Personal Info Name should only contain alphabetical characters");
            errorInfoList.add(errorInfo);
            isValidName = false;
        }

        // Should not contain any special characters other than space, single quote ('), period (.), and hyphen (-)
        if (anySpecialCharacters(name, SPECIAL_CHARS_FOR_NAME)) {
            errorInfo = createErrorInfo("ERR_PERSONAL_INFO_ILLEGAL_FIELD::" + fieldName, "Personal Info Name should only contain alphabetical characters");
            errorInfoList.add(errorInfo);
            isValidName = false;
        }

        // Should not contain more than one consecutive space
        if (consecutiveSpaces(name)) {
            errorInfo = createErrorInfo("ERR_PERSONAL_INFO_ILLEGAL_FIELD::" + fieldName, "Personal Info Name cannot contain consecutive spaces");
            errorInfoList.add(errorInfo);
            isValidName = false;
        }

        // Should not contain more than one consecutive single quote (')
        if (consecutiveQuotes(name)) {
            errorInfo = createErrorInfo("ERR_PERSONAL_INFO_ILLEGAL_FIELD::" + fieldName, "Personal Info Name cannot contain consecutive quotes");
            errorInfoList.add(errorInfo);
            isValidName = false;
        }

        // Should not contain more than one consecutive period (.)
        if (consecutivePeriods(name)) {
            errorInfo = createErrorInfo("ERR_PERSONAL_INFO_ILLEGAL_FIELD::" + fieldName, "Personal Info Name cannot contain consecutive periods");
            errorInfoList.add(errorInfo);
            isValidName = false;
        }

        // Should not contain more than one consecutive hyphen (-)
        if (consecutiveHyphens(name)) {
            errorInfo = createErrorInfo("ERR_PERSONAL_INFO_ILLEGAL_FIELD::" + fieldName, "Personal Info Name cannot contain consecutive periods");
            errorInfoList.add(errorInfo);
            isValidName = false;
        }

        // name should not have any numbers in it
        // Except the last name in which we will exclude the last character.
        if (fieldName.equals(LAST_NAME) ? matchPattern(name.substring(0, name.length() - 1), STRING_CONTAINS_NUMBERS) : matchPattern(name, STRING_CONTAINS_NUMBERS)) {
            errorInfo = createErrorInfo("ERR_PERSONAL_INFO_ILLEGAL_FIELD::" + fieldName, "Personal Info Name cannot contain numbers");
            errorInfoList.add(errorInfo);
            isValidName = false;
        }

        // Should not contain any special characters
        if (matchPattern(name, STRING_CONTAINS_SPECIAL_CHARACTERS)) {
            errorInfo = createErrorInfo("ERR_PERSONAL_INFO_ILLEGAL_FIELD::" + fieldName, "Personal Info Name cannot contain special characters");
            errorInfoList.add(errorInfo);
            isValidName = false;
        }

        // Shouldn't be: UNKNOWN, UNKOWN, N/A, N / A, ?, ??, ???, ????, DONTKNOW, DON'T KNOW, NOTPROVIDED, NOT PROVIDED, NOT APPLICABLE, NOTAPPLICABLE
        if (containsInvalidFieldNames(name.toUpperCase(), INVALID_FIELD_LIST_NAMES)) {
            errorInfo = createErrorInfo("ERR_PERSONAL_INFO_ILLEGAL_FIELD::" + fieldName, "Personal Info Name cannot contain invalid names");
            errorInfoList.add(errorInfo);
            isValidName = false;
        }

        LOGGER.info("Is name valid for field :: " + fieldName + " :: " + isValidName);
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
