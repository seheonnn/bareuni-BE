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
    public ResponseEntity uploadImage(@RequestPart(value = "files", required = false) List<MultipartFile> files) throws Exception{
        String url = uploadService.uploadImage(files);
        return new ResponseEntity<>(url, HttpStatus.OK);
    }
}
