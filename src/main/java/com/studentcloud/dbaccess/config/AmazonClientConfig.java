package com.studentcloud.dbaccess.config;

import com.amazonaws.services.s3.AmazonS3;
import com.studentcloud.dbaccess.service.Utils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonClientConfig {

    @Bean
    public AmazonS3 getAmazonClient() {
        return Utils.getAmazonClient();
    }
}
