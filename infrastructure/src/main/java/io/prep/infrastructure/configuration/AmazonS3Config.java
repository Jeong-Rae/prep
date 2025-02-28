package io.prep.infrastructure.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Config {
    @Value("${aws.prep.infrastructure.accesskey}")
    private String accessKey;

    @Value("${aws.prep.infrastructure.secretkey}")
    private String secretKey;

    @Value("${aws.prep.infrastructure.region}")
    private String region;

    @Bean
    public AmazonS3 amazonS3() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(
                credentials);
        AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
                                                 .withCredentials(credentialsProvider)
                                                 .withRegion(region)
                                                 .build();

        return amazonS3;
    }
}
