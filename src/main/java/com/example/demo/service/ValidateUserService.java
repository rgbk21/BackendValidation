package com.example.demo.service;

import com.example.demo.model.profile.Address;
import com.example.demo.model.profile.ValidateUserRequest;
import com.example.demo.model.profile.ValidateUserResponse;
import com.example.demo.util.ErrorInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.util.CommonUtils.createErrorInfo;
import static com.example.demo.util.CommonUtils.matchPattern;
import static com.example.demo.util.Constants.*;

@Service
public class ValidateUserService {

    private static final Log LOGGER = LogFactory.getLog(ValidateUserService.class);

    public ResponseEntity<ValidateUserResponse> validateRequest(ValidateUserRequest request, HttpServletRequest httpRequest) {

        LOGGER.info("Inside ValidateUserService::validateRequest starts");

        ResponseEntity<ValidateUserResponse> responseEntity = null;
        ValidateUserResponse validateUserResponse = null;

        Address enteredAddress = request.getAddress();
        List<ErrorInfo> errorInfoList = validateAddress(enteredAddress);
        if (CollectionUtils.isNotEmpty(errorInfoList)) {
            LOGGER.info("Errors found during validation::Size of errorInfoList is :: " + errorInfoList.size());
            LOGGER.info("Errors found during validation::errorInfoList :: " + errorInfoList);
        }


        if (CollectionUtils.isNotEmpty(errorInfoList)) {
            responseEntity = new ResponseEntity<>(validateUserResponse, HttpStatus.MULTI_STATUS);
        } else {
            responseEntity = new ResponseEntity<>(validateUserResponse, HttpStatus.OK);
        }

        return responseEntity;
    }

    private List<ErrorInfo> validateAddress(Address address) {

        LOGGER.info("Inside ValidateUserService::validateAddress starts");

        List<ErrorInfo> errorInfoList = new ArrayList<>();
        ErrorInfo errorCode = null;

        // Validating address line 1
        if (StringUtils.isNotBlank(address.getAddressLine1())) {
            errorCode = validateAddressLine(address.getAddressLine1().trim(), true);

            if (errorCode == null) {
                errorCode = preValidateAddressLines(address, address.getAddressLine1().trim(), ADDRESSLINE1);
            }

            if (errorCode != null) {
                errorInfoList.add(errorCode);
            }

            if (address.getAddressLine1().trim().length() >= 100) {
                errorCode = createErrorInfo("ERR_ADDRESS_LINE1_EXCEEDS_100_CHARS", "Address line 1 cannot exceed 100 characters.");
                errorInfoList.add(errorCode);
            }

        } else {
            errorCode = createErrorInfo("ERR_ADDRESS_LINE1_BLANK", "Address line 1 cannot be blank");
            errorInfoList.add(errorCode);
        }

        // Validating address line 2
        if (StringUtils.isNotBlank(address.getAddressLine2())) {
            errorCode = validateAddressLine(address.getAddressLine2().trim(), false);

            if (errorCode == null) {
                errorCode = preValidateAddressLines(address, address.getAddressLine2().trim(), ADDRESSLINE2);
            }

            if (errorCode != null) {
                errorInfoList.add(errorCode);
            }

            if (address.getAddressLine2().trim().length() >= 100) {
                errorCode = createErrorInfo("ERR_ADDRESS_LINE2_EXCEEDS_100_CHARS", "Address line 2 cannot exceed 100 characters.");
                errorInfoList.add(errorCode);
            }

        } else {
            errorCode = createErrorInfo("ERR_ADDRESS_LINE2_BLANK", "Address line 2 cannot be blank");
            errorInfoList.add(errorCode);
        }

        // Validating City
        if (StringUtils.isNotBlank(address.getCity())) {

            if (address.getCity().trim().length() >= 100) {
                errorCode = createErrorInfo("ERR_ADDRESS_CITY_EXCEEDS_100_CHARS", "Address city cannot exceed 100 characters.");
                errorInfoList.add(errorCode);
            }

            if (!matchPattern(address.getCity(), ALPHA_SPACE)) {
                if (!matchPattern(address.getCity(), ALPHA_NUMERIC)) {
                    errorCode = createErrorInfo("ERR_ADDRESS_CITY_NUMBER", "City can only contain alphabets and space");
                    errorInfoList.add(errorCode);
                } else {
                    errorCode = createErrorInfo("ERR_ADDRESS_CITY_SPECIAL_CHARACTER", "City cannot contain special characters");
                    errorInfoList.add(errorCode);
                }
            }
        } else {
            errorCode = createErrorInfo("ERR_ADDRESS_CITY_BLANK", "Address city cannot be blank");
            errorInfoList.add(errorCode);
        }

        // Validating State Code
        if (StringUtils.isNotBlank(address.getStateCode())) {
            if (address.getStateCode().trim().length() >= 100) {
                errorCode = createErrorInfo("ERR_ADDRESS_STATE_EXCEEDS_100_CHARS", "Address state cannot exceed 100 characters.");
                errorInfoList.add(errorCode);
            }
            if (!matchPattern(address.getStateCode(), ALPHA)) {
                errorCode = createErrorInfo("ERR_ADDRESS_STATE_ONLY_ALPHABETS_ALLOWED", "Address state can only contain alphabets");
                errorInfoList.add(errorCode);
            }
        } else {
            errorCode = createErrorInfo("ERR_ADDRESS_STATE_CODE_BLANK", "Address state cannot be blank");
            errorInfoList.add(errorCode);
        }

        // Validating Pin Code
        if (StringUtils.isNotBlank(address.getPostalCode())) {
            if (address.getPostalCode().trim().length() >= 10) {
                errorCode = createErrorInfo("ERR_ADDRESS_PINCODE_EXCEEDS_10_CHARS", "Address pin code cannot exceed 10 characters.");
                errorInfoList.add(errorCode);
            }
            if (!matchPattern(address.getPostalCode(), ZIP_REGEX)) {
                errorCode = createErrorInfo("ERR_ADDRESS_PINCODE_INVALID", "Address pin code is invalid");
                errorInfoList.add(errorCode);
            }
        } else {
            errorCode = createErrorInfo("ERR_ADDRESS_PINCODE_BLANK", "Address Pin Code cannot be blank");
            errorInfoList.add(errorCode);
        }

        LOGGER.info("Inside ValidateUserService::validateAddress ends");

        return errorInfoList;
    }

    private ErrorInfo preValidateAddressLines(Address address, String addressLine, String addressLineVal) {

        ErrorInfo errorInfo = null;
        if (addressLine != null && matchPattern(addressLine.toLowerCase(), INVALID_ADDRESSLINES)) {
            errorInfo = createErrorInfo("ERR_INVALID_ADDRESS_LINE_VALUES", "NONE, UNKNOWN, UNKOWN are not valid values for address line");
        }
        return errorInfo;
    }

    private ErrorInfo validateAddressLine(String addressLine, boolean isLine1Address) {

        ErrorInfo errorInfo = null;
        String firstChar = String.valueOf(addressLine.charAt(0));
        String errorCode = null;

        if (!matchPattern(firstChar, ALPHA_NUMERIC)) {
            errorCode = (isLine1Address ? "ERR_FIRST_SPL_CHAR_LINE1" : "ERR_FIRST_SPL_CHAR_LINE2");
            errorInfo = createErrorInfo(errorCode, "Please use only letters and numbers");
            LOGGER.info("Error validating address::" + errorCode + "::Please use only letters or numbers");
        } else if (!matchPattern(firstChar, ADDR_LINE_REGEX)) {
            errorCode = (isLine1Address ? "ERR_INVALID_LINE1" : "ERR_INVALID_LINE2");
            errorInfo = createErrorInfo(errorCode, "Please do not use special characters in the address line");
            LOGGER.info("Error validating address::" + errorCode + "::Please do not use special characters in the address line");
        }
        return errorInfo;
    }

}
