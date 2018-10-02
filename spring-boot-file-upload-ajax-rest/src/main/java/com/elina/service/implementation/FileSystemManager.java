package com.elina.service.implementation;

import com.elina.config.StorageConfig;
import com.elina.exception.BusinessLogicException;
import com.elina.exception.ErrorCode;
import com.elina.model.FileModel;
import com.elina.model.FileModelDTO;
import com.elina.service.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileSystemManager implements FileManager {

    private static final String PATH = "C:/Users/Elina/Desktop/test/";

    public final Path rootDirectory = Paths.get(PATH);

    @Override
    public void saveFile(FileModelDTO fileModelDTO) throws IOException {
        try (InputStream inputStream = fileModelDTO.getMultipartFile().getInputStream()) {
            Path path = getFileDirectory(fileModelDTO.getFileName());
            Files.copy(inputStream, path);
        }
    }

    @Override
    public String generateToken(FileModelDTO fileModelDTO) throws UnsupportedEncodingException {
        String fileName = fileModelDTO.getFileName();
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(fileName.getBytes("UTF-8"));
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootDirectory.toFile());
    }

    @Override
    public void deleteFile(String fileName) throws IOException {
        Path path = getFileDirectory(fileName);
        Files.delete(path);
    }

    @Override
    public void deleteFile(FileModel fileModel) throws IOException {
        Path path = getFileDirectory(fileModel.getName());
        Files.delete(path);
    }

    @Override
    public Resource loadFile(FileModel fileModel) throws MalformedURLException, BusinessLogicException {
        Path file = getFileDirectory(fileModel.getName());
        org.springframework.core.io.Resource resource = new UrlResource(file.toUri());

        if (resource.exists() || resource.isReadable())
            return resource;
        else
            throw new BusinessLogicException(ErrorCode.CANNOT_FIND_FILE.getMessage());
    }

    @Override
    public Path getFileDirectory(String fileName) {
        return rootDirectory.resolve(fileName);
    }
}
