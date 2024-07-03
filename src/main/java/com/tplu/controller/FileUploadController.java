package com.tplu.controller;

import com.tplu.model.JarInfo;
import com.tplu.service.FileProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
@RestController
@RequestMapping("/upload")
@CrossOrigin(origins = "http://localhost:3000")
public class FileUploadController {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    private FileProcessingService fileProcessingService;

    @PostMapping
    public List<JarInfo> uploadFile(@RequestParam("file") MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            logger.info("File upload started: {}", file.getOriginalFilename());
            List<JarInfo> result = fileProcessingService.processFile(reader);
            if (result != null && !result.isEmpty()) {
                for (JarInfo jarInfo : result) {
                    logger.info("JarInfo: {}", jarInfo);
                }

            } else {
                logger.info("No JarInfo found or the result is empty");
            }
            logger.info("File processed successfully: {}", file.getOriginalFilename());
            return result;
        } catch (IOException e) {
            logger.error("Error processing file: {}", e.getMessage());
            throw new RuntimeException("Error processing file", e);
        }
    }
}
