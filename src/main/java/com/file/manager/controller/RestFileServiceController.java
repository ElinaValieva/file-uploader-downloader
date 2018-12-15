package com.file.manager.controller;


import com.file.manager.config.URLs;
import com.file.manager.exception.BusinessLogicException;
import com.file.manager.model.FileModel;
import com.file.manager.model.FileModelDTO;
import com.file.manager.service.FileService;
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

    /**
     * API for uploading file
     * @param fileModelDTO
     * @return JSON with fileName, dateDuration, token
     * @throws IOException
     * @throws BusinessLogicException
     * @throws ParseException
     */
    @PostMapping(value = URLs.UPLOAD, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> uploadFileMulti(FileModelDTO fileModelDTO) throws IOException, BusinessLogicException, ParseException {
        logger.debug("Multiple file {} start for uploading...", fileModelDTO.getFileName());
        FileModel fileModel = fileService.uploadFile(fileModelDTO);
        return ResponseEntity.ok(gson.toJson(fileModel));
    }

    /**
     * API for downloading file
     * @param token - key for downloading files like identification number
     * @param httpServletRequest
     * @return resource for downloading
     * @throws IOException
     * @throws BusinessLogicException
     */
    @GetMapping(URLs.DOWNLOAD)
    public ResponseEntity<Resource> downloadFileMulti(@PathVariable String token, HttpServletRequest httpServletRequest) throws IOException, BusinessLogicException {
        logger.debug("Multiple file start to downloading ...");
        Resource resource = fileService.downloadFile(token);
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

    /**
     * API for delete file
     * @param token
     * @return message
     * @throws IOException
     * @throws BusinessLogicException
     */
    @DeleteMapping(URLs.DELETE)
    public ResponseEntity<String> deleteFile(@PathVariable String token) throws IOException, BusinessLogicException {
        logger.debug("Multiple file {} start to delete ...", token);
        fileService.deleteFile(token);
        return ResponseEntity.ok("Successfully delete ");
    }

    /**
     * APi for deleting files
     * @return message
     * @throws BusinessLogicException
     * @throws IOException
     */
    @DeleteMapping(URLs.DELETE_ALL)
    public ResponseEntity<String> deleteFiles() throws BusinessLogicException, IOException {
        logger.debug("Multiple file start to delete ...");
        fileService.deleteFiles();
        return ResponseEntity.ok("Successfully delete all files");
    }
}
