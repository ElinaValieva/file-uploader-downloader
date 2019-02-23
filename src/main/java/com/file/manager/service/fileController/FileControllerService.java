package com.file.manager.service.fileController;


import com.file.manager.exception.BusinessLogicException;
import com.file.manager.dto.FileInfo;
import com.file.manager.dto.FileModel;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.text.ParseException;


/**
 * Main service to upload/download files
 * use FileManagerService to works with files
 */
public interface FileControllerService {

    FileInfo uploadFile(FileModel fileModel) throws IOException, BusinessLogicException, ParseException;

    Resource downloadFile(String token) throws BusinessLogicException, IOException;

    void deleteFile(String fileName) throws IOException, BusinessLogicException;

    void deleteFiles() throws BusinessLogicException, IOException;
}
