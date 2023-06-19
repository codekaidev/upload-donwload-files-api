package com.codekaidev.uploaddownloadfiles;

import com.amazonaws.services.s3.AmazonS3;
import com.codekaidev.uploaddownloadfiles.config.AWSConfig;
import com.codekaidev.uploaddownloadfiles.mapper.IMetadataFileMapper;
import com.codekaidev.uploaddownloadfiles.service.IUploadDownloadFileService;
import com.codekaidev.uploaddownloadfiles.service.MetadaFileService;
import com.codekaidev.uploaddownloadfiles.service.UploadDownloadAWSS3StorageService;
import com.codekaidev.uploaddownloadfiles.service.UploadDownloadLocalStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@SpringBootApplication
public class UploadDownloadFilesApp {

    private final MetadaFileService metadaFileService;
    private final IMetadataFileMapper iMetadataFileMapper;
    private final ResourceLoader resourceLoader;
    @Autowired
    private AWSConfig awsConfig;
    @Autowired
    private AmazonS3 amazonS3;

    public static void main(String[] args) {
        SpringApplication.run(UploadDownloadFilesApp.class, args);
    }

    /**
     * Create a bean map to determine the instance or provider that will be used for file upload and download
     *
     * @return the beans map
     */
    @Bean
    public Map<String, IUploadDownloadFileService> fileManageServiceMap() {
        return new HashMap<>() {{
            put("uploadDownloadAWSS3StorageService", new UploadDownloadAWSS3StorageService(awsConfig, amazonS3,
                    metadaFileService, iMetadataFileMapper));
            put("uploadDownloadLocalStorageService", new UploadDownloadLocalStorageService(metadaFileService,
                    resourceLoader, iMetadataFileMapper));
        }};
    }

    //	@Bean
    //	public CorsFilter corsFilter() {
    //		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
    //		CorsConfiguration corsConfiguration = new CorsConfiguration();
    //		corsConfiguration.setAllowCredentials(true);
    //		corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
    //		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
    //				"Accept", "Jwt-Token", "Authorization", "Origin, Accept", "X-Requested-With",
    //				"Access-Control-Request-Method", "Access-Control-Request-Headers"));
    //		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Jwt-Token",
	//		"Authorization",
    //				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials",
	//				"File-Name"));
    //		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
    //		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
    //		return new CorsFilter(urlBasedCorsConfigurationSource);
    //	}


}
