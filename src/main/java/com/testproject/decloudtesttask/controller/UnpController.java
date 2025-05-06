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
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/unp")
public class UnpController {
    private final UnpService unpService;

    /**
     *
     * @param file
     * @return
     * @throws FileProcessingException
     * @throws IOException
     */
    @PostMapping("/process-domains")
    public ResponseEntity<UnpResultDto> processDomainsFile(@RequestParam("file") MultipartFile file) throws FileProcessingException, IOException {
        UnpResultDto result = unpService.processDomainsFile(file.getBytes());
        return ResponseEntity.ok(result);
    }

    /**
     *
     * @param file
     * @return
     * @throws FileProcessingException
     * @throws IOException
     */
    @PostMapping("/process-unp")
    public ResponseEntity<List<String>> processUnpFile(@RequestParam("file") MultipartFile file) throws FileProcessingException, IOException {
        List<String> result = unpService.processUnpFile(file.getBytes());
        return ResponseEntity.ok(result);
    }
}
