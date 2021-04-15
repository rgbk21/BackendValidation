package com.example.demo.controller;

import com.example.demo.model.profile.ValidateUserRequest;
import com.example.demo.model.profile.ValidateUserResponse;
import com.example.demo.service.ValidateUserService;
import com.example.demo.util.RestURIConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ValidateUserController {

    @Autowired
    private ValidateUserService validateUserService;

    private static final Log LOGGER = LogFactory.getLog(ValidateUserController.class);

    @RequestMapping(value = RestURIConstants.VALIDATE_USER_URI, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ValidateUserResponse> validateAddress (@RequestBody ValidateUserRequest validateUserRequest, HttpServletRequest request)
            throws Exception {

        LOGGER.info("ValidateUserController::validateAddress starts");

        ResponseEntity<ValidateUserResponse> response = null;
        ValidateUserResponse userResponse = null;

        if (validateUserRequest != null && validateUserRequest.getAddress() != null) {
            response = validateUserService.validateRequest(validateUserRequest, request);
        }

        LOGGER.info("ValidateUserController::validateAddress ends");
        return response;
    }
}
