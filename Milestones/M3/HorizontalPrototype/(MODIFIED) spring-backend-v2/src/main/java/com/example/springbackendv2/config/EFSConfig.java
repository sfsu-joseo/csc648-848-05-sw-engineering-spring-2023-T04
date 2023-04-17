package com.example.springbackendv2.config;


import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.elasticfilesystem.AmazonElasticFileSystem;
import com.amazonaws.services.elasticfilesystem.AmazonElasticFileSystemClientBuilder;
import com.example.springbackendv2.service.impl.FileServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EFSConfig {

    @Value("${efs.region}")
    private String region;

    @Value("${efs.file-system-id}")
    private String fileSystemId;

    @Value("${efs.mount-path}")
    private String mountPath;

    @Bean
    public AmazonElasticFileSystem efsClient() {
        return AmazonElasticFileSystemClientBuilder.standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(region, "elasticfilesystem." + region + ".amazonaws.com"))
                .build();
    }

    @Bean
    public FileServiceImpl efsStorageService(AmazonElasticFileSystem efsClient) {
        return new FileServiceImpl(efsClient, fileSystemId, mountPath);
    }
}
