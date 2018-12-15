package com.file.manager.service;


import com.file.manager.exception.BusinessLogicException;
import com.file.manager.model.FileModel;
import com.file.manager.model.FileModelDTO;
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
public interface FileManager {

    void saveFile(FileModelDTO fileModelDTO) throws IOException, BusinessLogicException;

    String generateToken(FileModelDTO fileModelDTO) throws UnsupportedEncodingException;

    void deleteAll() throws IOException;

    void deleteFile(String fileName) throws IOException;

    void deleteFile(FileModel fileModel) throws IOException;

    Resource loadFile(FileModel fileModel) throws IOException, BusinessLogicException;

    Path getFileDirectory(String fileName) throws IOException;
}
