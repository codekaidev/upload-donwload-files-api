package com.codekaidev.uploaddownloadfiles.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AWSConfig {

    private final String accessKey;
    private final String secretAccessKey;
    private final String region;
    private final String bucket;

    public AWSConfig(
            @Value("${aws.access-key}") String accessKey,
            @Value("${aws.secret-access-key}") String secretAccessKey,
            @Value("${aws.region}") String region,
            @Value("${aws.bucket}") String bucket) {
        this.accessKey = accessKey;
        this.secretAccessKey = secretAccessKey;
        this.region = region;
        this.bucket = bucket;
    }

    @Bean
    public AmazonS3 amazonS3Client() {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretAccessKey);
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .build();
    }

}
