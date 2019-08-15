package com.mercadolibre.melidnaapi.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.mercadolibre.melidnaapi.config.props.AWSCredentialsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSCredentialsConfig {

    @Bean
    AWSCredentials awsCredentials(AWSCredentialsProperties props) {
        return new BasicAWSCredentials(props.getAccessKey(), props.getSecretKey());
    }

    @Bean
    AWSCredentialsProvider awsCredentialsProvider(AWSCredentials awsCredentials) {
        return new AWSStaticCredentialsProvider(awsCredentials);
    }

}
