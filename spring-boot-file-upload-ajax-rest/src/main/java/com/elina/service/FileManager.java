package com.elina.service;

import com.elina.exception.BusinessLogicException;
import com.elina.model.FileModel;
import com.elina.model.FileModelDTO;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.nio.file.Path;

public interface FileManager {

    void saveFile(FileModelDTO fileModelDTO) throws IOException;

    String generateToken(FileModelDTO fileModelDTO) throws UnsupportedEncodingException;

    void deleteAll();

    void deleteFile(String fileName) throws IOException;

    void deleteFile(FileModel fileModel) throws IOException;

    Resource loadFile(FileModel fileModel) throws MalformedURLException, BusinessLogicException;

    Path getFileDirectory(String fileName);
}
