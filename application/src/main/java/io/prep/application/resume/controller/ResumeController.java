package io.prep.application.resume.controller;

import io.prep.application.resume.service.ResumeUploadService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URISyntaxException;
import java.net.URL;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/resumes")
public class ResumeController {

    private final ResumeUploadService resumeUploadService;

    private final static Logger LOGGER = LoggerFactory.getLogger(ResumeController.class);

    @PostMapping
    public ResponseEntity<Void> uploadResume(@RequestParam("file") MultipartFile file) throws
                                                                                       URISyntaxException {
        LOGGER.info("Uploading resume file");
        URL fileUrl = resumeUploadService.uploadAndSaveResume(file);

        return ResponseEntity.created(fileUrl.toURI()).build();
    }
}
