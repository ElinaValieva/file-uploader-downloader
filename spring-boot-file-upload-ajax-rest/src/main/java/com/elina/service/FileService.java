package com.elina.service;

import com.elina.exception.BusinessLogicException;
import com.elina.model.FileModel;
import com.elina.model.FileModelDTO;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;

public interface FileService {

    FileModel uploadFile(FileModelDTO fileModelDTO) throws IOException, BusinessLogicException, ParseException;

    Resource downloadFile(String token) throws BusinessLogicException, MalformedURLException;

    void deleteFile(String fileName) throws IOException, BusinessLogicException;

    void deleteFiles() throws BusinessLogicException;
}
