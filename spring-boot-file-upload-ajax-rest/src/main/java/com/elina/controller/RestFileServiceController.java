package com.elina.controller;

import com.elina.config.URLs;
import com.elina.exception.BusinessLogicException;
import com.elina.model.FileModel;
import com.elina.model.FileModelDTO;
import com.elina.service.FileService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;

@RestController
public class RestFileServiceController {

    private final Logger logger = LoggerFactory.getLogger(RestFileServiceController.class);
    private static final Gson gson = new Gson();

    @Autowired
    private FileService fileService;

    @PostMapping(value = URLs.UPLOAD, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadFileMulti(FileModelDTO fileModelDTO) throws IOException, BusinessLogicException, ParseException {
        System.out.println(fileModelDTO.getDateDurationDescription());
        logger.debug("Multiple file " + fileModelDTO.getFileName() + "start for uploading...");
        FileModel fileModel = fileService.uploadFile(fileModelDTO);
        logger.debug("File " + fileModelDTO.getFileName() + " upload ...");
        return ResponseEntity.ok(gson.toJson(fileModel));
    }

    @GetMapping(URLs.DOWNLOAD)
    public ResponseEntity<?> downloadFileMulti(@PathVariable String token, HttpServletRequest httpServletRequest) throws IOException, BusinessLogicException {
        logger.debug("Multiple file start to downloading ...");
        Resource resource = fileService.downloadFile(token);
        String contentType = httpServletRequest.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        logger.debug("File " + resource.getFilename() + " download ...");
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @DeleteMapping(URLs.DELETE)
    public ResponseEntity<?> deleteFile(@PathVariable String token) throws IOException, BusinessLogicException {
        logger.debug("Multiple file " + token + " start to delete ...");
        fileService.deleteFile(token);
        logger.debug("File deleted ...");
        return ResponseEntity.ok("Successfully delete ");
    }

    @DeleteMapping(URLs.DELETE_ALL)
    public ResponseEntity<?> deleteFiles() throws BusinessLogicException {
        logger.debug("Multiple file start to delete ...");
        fileService.deleteFiles();
        logger.debug("All files deleted ...");
        return ResponseEntity.ok("Successfully delete all files");
    }
}
