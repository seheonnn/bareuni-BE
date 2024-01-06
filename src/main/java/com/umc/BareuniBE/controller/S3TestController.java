package com.umc.BareuniBE.controller;

import com.umc.BareuniBE.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/uploadImage")
public class S3TestController {
    private final UploadService uploadService;
    @PostMapping(value = "")
    public ResponseEntity uploadImage(@RequestPart(value = "file", required = false) MultipartFile file) throws Exception{
        String url = uploadService.uploadImage(file);
        return new ResponseEntity<>(url, HttpStatus.OK);
    }

    @DeleteMapping(value = "")
    public ResponseEntity deleteImage(@RequestPart(value = "path", required = false) String path) {
        String image = path.substring(path.lastIndexOf('/')+1);
        return new ResponseEntity<>(uploadService.deleteImage(image), HttpStatus.OK);
    }

    @PostMapping(value = "/images")
    public ResponseEntity uploadImages(@RequestPart(value = "file", required = false) List<MultipartFile> file) throws Exception{
        List<String> urls = uploadService.uploadImages(file);
        return new ResponseEntity<>(urls, HttpStatus.OK);
    }

}
