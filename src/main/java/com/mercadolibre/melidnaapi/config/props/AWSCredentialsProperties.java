package com.mercadolibre.melidnaapi.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("cloud.aws.credentials")
public class AWSCredentialsProperties {
    private String accessKey;
    private String secretKey;
}
