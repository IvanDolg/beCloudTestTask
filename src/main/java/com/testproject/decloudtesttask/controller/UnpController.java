package com.testproject.decloudtesttask.controller;

import com.testproject.decloudtesttask.dto.UnpResultDto;
import com.testproject.decloudtesttask.exception.FileProcessingException;
import com.testproject.decloudtesttask.service.UnpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/unp")
public class UnpController {
    private final UnpService unpService;

    /**
     * Основное техническое задание
     */
    @PostMapping("/process-domains")
    public ResponseEntity<UnpResultDto> processDomainsFile(
            @RequestParam("file") MultipartFile file
    ) throws FileProcessingException, IOException {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File must not be empty.");
        }

        UnpResultDto result = unpService.processDomainsFile(file.getBytes());
        return ResponseEntity.ok(result);
    }

    /**
     * Дополнительное задание (с сохранением данных в бд)
     */
    @PostMapping("/process-and-save-domains")
    public ResponseEntity<UnpResultDto> processAndSaveDomainsFile(
            @RequestParam("file") MultipartFile file
    ) throws FileProcessingException, IOException {
        UnpResultDto result = unpService.processAndSaveDomainsFile(file.getBytes());
        return ResponseEntity.ok(result);
    }
}
