package com.example.demo.service;

import com.example.demo.model.profile.Personal;
import com.example.demo.util.ErrorInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ValidatePersonalInfoService {

    private static final Log LOGGER = LogFactory.getLog(ValidatePersonalInfoService.class);

    public List<ErrorInfo> validatePersonalInfo(Personal personalInfo) {

        LOGGER.info("Inside ValidateUserService::validatePersonalInfo starts");

        List<ErrorInfo> errorInfoList = new ArrayList<>();


        LOGGER.info("Inside ValidateUserService::validatePersonalInfo starts");
        return errorInfoList;
    }

}
