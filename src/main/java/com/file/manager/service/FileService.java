package com.file.manager.service;


import com.file.manager.exception.BusinessLogicException;
import com.file.manager.model.FileModel;
import com.file.manager.model.FileModelDTO;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.text.ParseException;


/**
 * Main service to upload/download files
 * use FileManager to works with files
 */
public interface FileService {

    FileModel uploadFile(FileModelDTO fileModelDTO) throws IOException, BusinessLogicException, ParseException;

    Resource downloadFile(String token) throws BusinessLogicException, IOException;

    void deleteFile(String fileName) throws IOException, BusinessLogicException;

    void deleteFiles() throws BusinessLogicException, IOException;
}
