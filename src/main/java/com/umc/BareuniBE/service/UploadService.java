package com.umc.BareuniBE.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UploadService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.cloudfront}")
    private String cfUrl;

    @Value("${cloud.aws.s3.uploadPath}")
    private String uploadPath;

    private final AmazonS3Client amazonS3Client;

    public String uploadImage(List<MultipartFile> files) throws IOException {

        // iterator
        List<String> imagesUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            String originfileName = file.getOriginalFilename();
            String filePath = uploadPath + UUID.randomUUID() + originfileName.substring(originfileName.lastIndexOf("."));;
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            metadata.addUserMetadata("originfilename", URLEncoder.encode(originfileName, StandardCharsets.UTF_8));
            PutObjectResult result = amazonS3Client.putObject(bucket, filePath, file.getInputStream(), metadata);
            imagesUrls.add(cfUrl + filePath);
        }
        return imagesUrls.toString();

        // stream
//        return files.stream()
//                .map(file -> {
//                    String originfileName = file.getOriginalFilename();
//                    String filePath = uploadPath + UUID.randomUUID() + originfileName.substring(originfileName.lastIndexOf("."));;
//                    ObjectMetadata metadata = new ObjectMetadata();
//                    metadata.setContentType(file.getContentType());
//                    metadata.setContentLength(file.getSize());
//                    metadata.addUserMetadata("originfilename", URLEncoder.encode(originfileName, StandardCharsets.UTF_8));
//                    try {
//                        PutObjectResult result = amazonS3Client.putObject(bucket, filePath, file.getInputStream(), metadata);
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                    return cfUrl + filePath;
//                })
//                .collect(Collectors.joining());

    }
}
