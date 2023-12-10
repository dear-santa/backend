package com.dearsanta.app.util;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Setter
@Component
@Log4j
public class AWSS3 {

    private String accessKey;
    private String secretKey;
    private String bucketName;
    private String region;

    public AmazonS3 AwsS3Client() {
        log.info("accessKey: " + accessKey);
        log.info("secretKey: " + secretKey);
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion(region)
                .build();
        return s3Client;
    }

    public String getImageType(String contentType) {
        String imageType = "";
        if (contentType.equals("image/jpeg")) {
            imageType = "jpeg";
        }
        else if (contentType.equals("image/png")) {
            imageType = "png";
        }
        else {
            log.info("imageType: " + imageType);
            throw new IllegalArgumentException("이미지 파일만 업로드해주세요.");
        }
        return imageType;
    }

    public String uploadImage(String fileName, MultipartFile file) {
        String contentType = file.getContentType();
        String imageType = getImageType(contentType);
        String filePath = "images/" + fileName + "." + imageType;
        try {
            AwsS3Client().putObject(
                new PutObjectRequest(bucketName, filePath, file.getInputStream(), null)
                    .withCannedAcl(CannedAccessControlList.PublicRead)
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return AwsS3Client().getUrl(bucketName, filePath).toString();
    }
}
