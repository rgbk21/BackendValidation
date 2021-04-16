package com.example.demo.service;

import com.example.demo.model.profile.Address;
import com.example.demo.model.profile.Personal;
import com.example.demo.model.profile.ValidateUserRequest;
import com.example.demo.model.profile.ValidateUserResponse;
import com.example.demo.util.ErrorInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class ValidateUserService {


    private static final Log LOGGER = LogFactory.getLog(ValidateUserService.class);

    @Autowired
    private ValidateAddressService validateAddressService;

    @Autowired
    private ValidatePersonalInfoService validatePersonalInfoService;

    public ResponseEntity<ValidateUserResponse> validateRequest(ValidateUserRequest validateUserRequest, HttpServletRequest httpRequest) {

        LOGGER.info("Inside ValidateUserService::validateRequest starts");

        ResponseEntity<ValidateUserResponse> responseEntity = null;
        ValidateUserResponse validateUserResponse = new ValidateUserResponse();

        Address enteredAddress = validateUserRequest.getAddress();
        List<ErrorInfo> validateAddrErrors = validateAddressService.validateAddress(enteredAddress);
        if (CollectionUtils.isNotEmpty(validateAddrErrors)) {
            LOGGER.info("Errors found during validation of address::Size of errorInfoList is :: " + validateAddrErrors.size());
            LOGGER.info("Errors found during validation of address::errorInfoList :: " + validateAddrErrors);
        }

        Personal personalInfo = validateUserRequest.getPersonal();
        List<ErrorInfo> validatePersonalInfoErrors = validatePersonalInfoService.validatePersonalInfo(personalInfo);
        if (CollectionUtils.isNotEmpty(validatePersonalInfoErrors)) {
            LOGGER.info("Errors found during validation of personalInfo::Size of errorInfoList is :: " + validatePersonalInfoErrors.size());
            LOGGER.info("Errors found during validation of personalInfo::errorInfoList :: " + validatePersonalInfoErrors);
        }

        List<ErrorInfo> errorInfoList = ListUtils.union(validateAddrErrors, validatePersonalInfoErrors);
        validateUserResponse.setErrorInfoList(errorInfoList);

        if (CollectionUtils.isNotEmpty(errorInfoList)) {
            LOGGER.info("Errors found during validation::errorInfoList :: " + errorInfoList);
            responseEntity = new ResponseEntity<>(validateUserResponse, HttpStatus.MULTI_STATUS);
        } else {
            LOGGER.info("Errors found during validation::errorInfoList :: " + errorInfoList);
            responseEntity = new ResponseEntity<>(validateUserResponse, HttpStatus.OK);
        }

        LOGGER.info("Inside ValidateUserService::validateRequest ends");
        return responseEntity;
    }

}
