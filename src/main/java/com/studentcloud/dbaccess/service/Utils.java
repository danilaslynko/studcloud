package com.studentcloud.dbaccess.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collectors;

public class Utils {

    public static void getErrors(BindingResult bindingResult, Model model) {
        Map<String, String> errors = bindingResult.getFieldErrors().stream().collect(Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                FieldError::getDefaultMessage
        ));

        model.mergeAttributes(errors);
    }

    public static AmazonS3 getAmazonClient() {
        AWSCredentials credentials = new BasicAWSCredentials("AKIAIMNJ7J27RZZXRWBQ",
                "5BMEWu0FnjsC2g/3+jad49ScFrdgOU5yvJCpL88t");

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_WEST_1)
                .build();
    }
}
