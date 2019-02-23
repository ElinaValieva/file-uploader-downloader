package com.file.manager.service.fileManager;


import com.file.manager.configuration.StorageProperties;
import com.file.manager.dto.FileInfo;
import com.file.manager.dto.FileModel;
import com.file.manager.exception.BusinessLogicException;
import com.file.manager.exception.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

@Service
public class FileSystemManagerService implements FileManagerService {

    private final Path rootDirectory;

    private final Logger logger = LoggerFactory.getLogger(FileSystemManagerService.class);

    @Autowired
    public FileSystemManagerService(StorageProperties storageProperties) {
        rootDirectory = Paths.get(storageProperties.location);
    }

    @Override
    public void saveFile(FileModel fileModel) throws BusinessLogicException {
        logger.debug("Try to save file {}", fileModel.getFileName());
        try (InputStream inputStream = fileModel.getMultipartFile().getInputStream()) {
            Path path = getFileDirectory(fileModel.getFileName());
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            logger.debug("Save file {}", fileModel.getFileName());
        } catch (IOException e) {
            throw new BusinessLogicException(ErrorCode.FILE_CONSIST_IN_REPOSITORY_ALREADY.getMessage());
        }
    }

    @Override
    public String generateToken(FileModel fileModel) throws UnsupportedEncodingException {
        String fileName = fileModel.getFileName();
        Base64.Encoder encoder = Base64.getEncoder();
        return new String(encoder.encode(fileName.getBytes("UTF-8")));
    }

    @Override
    public void deleteAll() {
        logger.debug("Try to delete all files");
        FileSystemUtils.deleteRecursively(rootDirectory.toFile());
        logger.debug("Delete all files");
    }

    @Override
    public void deleteFile(String fileName) throws IOException {
        logger.debug("Try to delete file {}", fileName);
        Path path = getFileDirectory(fileName);
        Files.delete(path);
        logger.debug("Delete file {}", fileName);
    }

    @Override
    public void deleteFile(FileInfo fileInfo) throws IOException {
        logger.debug("Try to delete file {}", fileInfo.getName());
        Path path = getFileDirectory(fileInfo.getName());
        Files.delete(path);
        logger.debug("Delete file {}", fileInfo.getName());
    }

    @Override
    public Resource loadFile(FileInfo fileInfo) throws IOException, BusinessLogicException {
        logger.debug("Try to load file {}", fileInfo.getName());
        Path file = getFileDirectory(fileInfo.getName());
        org.springframework.core.io.Resource resource = new UrlResource(file.toUri());

        if (resource.exists() || resource.isReadable()) {
            logger.debug("Load resource ...");
            return resource;
        } else
            throw new BusinessLogicException(ErrorCode.CANNOT_FIND_FILE.getMessage());
    }

    @Override
    public Path getFileDirectory(String fileName) throws IOException {
        if (!Files.exists(rootDirectory)) {
            logger.debug("Try to create directory for files");
            Files.createDirectories(rootDirectory);
            logger.debug("Create directory files");
        }

        return rootDirectory.resolve(fileName);
    }
}
