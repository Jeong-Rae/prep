package io.prep.application.resume.controller;

import io.prep.application.resume.service.ResumeUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/resumes")
public class ResumeController {

    private final ResumeUploadService resumeUploadService;

    @PostMapping("/")
    public ResponseEntity<Void> uploadResume(@RequestParam("file") MultipartFile file) {
        String fileUrl = resumeUploadService.uploadAndSaveResume(file);

        return ResponseEntity.created(URI.create(fileUrl)).build();
    }
}
