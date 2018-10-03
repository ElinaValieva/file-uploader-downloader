package com.elina.service.implementation;

import com.elina.config.StorageConfig;
import com.elina.exception.BusinessLogicException;
import com.elina.exception.ErrorCode;
import com.elina.model.FileModel;
import com.elina.model.FileModelDTO;
import com.elina.service.FileManager;
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

/**
 * Service for working with files
 * - save file
 * - load file
 * - delete file
 * - delete all files
 */
@Service
public class FileSystemManager implements FileManager {

    public final Path rootDirectory;

    private final Logger logger = LoggerFactory.getLogger(FileSystemManager.class);

    @Autowired
    public FileSystemManager(StorageConfig storageConfig) {
        rootDirectory = Paths.get(storageConfig.getLocation());
    }

    /**
     * save file by using InputStream
     * @param fileModelDTO
     * @throws IOException
     */
    @Override
    public void saveFile(FileModelDTO fileModelDTO) throws BusinessLogicException {
        logger.debug("Try to save file {}", fileModelDTO.getFileName());
        try (InputStream inputStream = fileModelDTO.getMultipartFile().getInputStream()) {
            Path path = getFileDirectory(fileModelDTO.getFileName());
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            logger.debug("Save file {}", fileModelDTO.getFileName());
        } catch (IOException e){
            throw new BusinessLogicException(ErrorCode.FILE_CONSIST_IN_REPOSITORY_ALREADY.getMessage());
        }
    }

    /**
     * generate key for downloading file with Base64 encoder
     * @param fileModelDTO
     * @return
     * @throws UnsupportedEncodingException
     */
    @Override
    public String generateToken(FileModelDTO fileModelDTO) throws UnsupportedEncodingException {
        String fileName = fileModelDTO.getFileName();
        Base64.Encoder encoder = Base64.getEncoder();
        return new String(encoder.encode(fileName.getBytes("UTF-8")));
    }

    /**
     * delete all files
     */
    @Override
    public void deleteAll() {
        logger.debug("Try to delete all files");
        FileSystemUtils.deleteRecursively(rootDirectory.toFile());
        logger.debug("Delete all files");
    }

    /**
     * delete file by fileName
     * @param fileName
     * @throws IOException
     */
    @Override
    public void deleteFile(String fileName) throws IOException {
        logger.debug("Try to delete file {}", fileName);
        Path path = getFileDirectory(fileName);
        Files.delete(path);
        logger.debug("Delete file {}", fileName);
    }

    /**
     * delete file by fileModel
     * @param fileModel
     * @throws IOException
     */
    @Override
    public void deleteFile(FileModel fileModel) throws IOException {
        logger.debug("Try to delete file {}", fileModel.getName());
        Path path = getFileDirectory(fileModel.getName());
        Files.delete(path);
        logger.debug("Delete file {}", fileModel.getName());
    }

    /**
     * load file by fileModel
     * @param fileModel
     * @return
     * @throws IOException
     * @throws BusinessLogicException if resource not exist
     */
    @Override
    public Resource loadFile(FileModel fileModel) throws IOException, BusinessLogicException {
        logger.debug("Try to load file {}", fileModel.getName());
        Path file = getFileDirectory(fileModel.getName());
        org.springframework.core.io.Resource resource = new UrlResource(file.toUri());

        if (resource.exists() || resource.isReadable()) {
            logger.debug("Load resource ...");
            return resource;
        } else
            throw new BusinessLogicException(ErrorCode.CANNOT_FIND_FILE.getMessage());
    }

    /**
     * get file location
     * create repository if it doesn't exist
     * @param fileName
     * @return
     * @throws IOException
     */
    @Override
    public Path getFileDirectory(String fileName) throws IOException {
        if (!Files.exists(rootDirectory)) {
            logger.debug("Try to create directory for files");
            Files.createDirectory(rootDirectory);
            logger.debug("Create directory files");
        }

        return rootDirectory.resolve(fileName);
    }
}
