package com.umc.BareuniBE.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UploadService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.cloudfront}")
    private String url;

    @Value("${cloud.aws.s3.uploadPath}")
    private String uploadPath;

    private final AmazonS3Client amazonS3Client;

    public String uploadImage(MultipartFile file) throws IOException {
        String filePath = UUID.randomUUID() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        metadata.addUserMetadata("originfilename", URLEncoder.encode(uploadPath + filePath, StandardCharsets.UTF_8));
        amazonS3Client.putObject(bucket, uploadPath + filePath, file.getInputStream(), metadata);
        return url + uploadPath + filePath;
    }

    public String deleteImage(String image) {
        amazonS3Client.deleteObject(bucket, uploadPath + image);
        return "삭제 성공";
    }

    // 여러 개의 이미지를 업로드하는 경우
//    public List<String> uploadImages(List<MultipartFile> files) {
//        // stream
//        List<String> imagesUrls = new ArrayList<>();
//        files.stream().map(file -> {
//            String filePath = UUID.randomUUID() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//            ObjectMetadata metadata = new ObjectMetadata();
//            metadata.setContentType(file.getContentType());
//            metadata.setContentLength(file.getSize());
//            metadata.addUserMetadata("originfilename", URLEncoder.encode(uploadPath + filePath, StandardCharsets.UTF_8));
//            imagesUrls.add(url + uploadPath + filePath);
//            try {
//                amazonS3Client.putObject(bucket, uploadPath + filePath, file.getInputStream(), metadata);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            return "Uploaded successfully!";
//        }).collect(Collectors.toList());
//        return imagesUrls;
//    }
}
