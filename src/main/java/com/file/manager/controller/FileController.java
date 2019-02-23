package com.file.manager.controller;


import com.file.manager.common.URLs;
import com.file.manager.exception.BusinessLogicException;
import com.file.manager.dto.FileInfo;
import com.file.manager.dto.FileModel;
import com.file.manager.service.fileController.FileControllerService;
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
public class FileController {

    private final Logger logger = LoggerFactory.getLogger(FileController.class);
    private static final Gson gson = new Gson();

    private final FileControllerService fileControllerService;

    @Autowired
    public FileController(FileControllerService fileControllerService) {
        this.fileControllerService = fileControllerService;
    }

    @PostMapping(value = URLs.UPLOAD, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> uploadFileMulti(FileModel fileModel) throws IOException, BusinessLogicException, ParseException {
        logger.debug("Multiple file {} start for uploading...", fileModel.getFileName());
        FileInfo fileInfo = fileControllerService.uploadFile(fileModel);
        return ResponseEntity.ok(gson.toJson(fileInfo));
    }

    @GetMapping(URLs.DOWNLOAD)
    public ResponseEntity<Resource> downloadFileMulti(@PathVariable String token, HttpServletRequest httpServletRequest) throws IOException, BusinessLogicException {
        logger.debug("Multiple file start to downloading ...");
        Resource resource = fileControllerService.downloadFile(token);
        String contentType = httpServletRequest.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        logger.debug("File {} download ...", resource.getFilename());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @DeleteMapping(URLs.DELETE)
    public ResponseEntity<String> deleteFile(@PathVariable String token) throws IOException, BusinessLogicException {
        logger.debug("Multiple file {} start to delete ...", token);
        fileControllerService.deleteFile(token);
        return ResponseEntity.ok("Successfully delete ");
    }

    @DeleteMapping(URLs.DELETE_ALL)
    public ResponseEntity<String> deleteFiles() throws BusinessLogicException, IOException {
        logger.debug("Multiple file start to delete ...");
        fileControllerService.deleteFiles();
        return ResponseEntity.ok("Successfully delete all files");
    }
}
