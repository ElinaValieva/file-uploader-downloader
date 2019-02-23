package com.file.manager.service.fileManager;


import com.file.manager.exception.BusinessLogicException;
import com.file.manager.dto.FileInfo;
import com.file.manager.dto.FileModel;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;

/**
 * Service for working with files
 * - save file
 * - load file
 * - delete file
 * - delete all files
 */
public interface FileManagerService {

    void saveFile(FileModel fileModel) throws IOException, BusinessLogicException;

    String generateToken(FileModel fileModel) throws UnsupportedEncodingException;

    void deleteAll() throws IOException;

    void deleteFile(String fileName) throws IOException;

    void deleteFile(FileInfo fileInfo) throws IOException;

    Resource loadFile(FileInfo fileInfo) throws IOException, BusinessLogicException;

    Path getFileDirectory(String fileName) throws IOException;
}
