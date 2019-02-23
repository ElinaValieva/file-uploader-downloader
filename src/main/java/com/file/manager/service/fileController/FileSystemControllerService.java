package com.file.manager.service.fileController;


import com.file.manager.common.DateUtils;
import com.file.manager.dto.FileInfo;
import com.file.manager.dto.FileModel;
import com.file.manager.exception.BusinessLogicException;
import com.file.manager.exception.ErrorCode;
import com.file.manager.repository.FileRepository;
import com.file.manager.service.fileManager.FileManagerService;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Main service to upload/download files
 * use FileManagerService to works with files
 */
@Service
public class FileSystemControllerService implements FileControllerService {

    private final FileRepository fileRepository;

    private final FileManagerService fileManagerService;

    private final Logger logger = LoggerFactory.getLogger(FileSystemControllerService.class);

    @Autowired
    public FileSystemControllerService(FileRepository fileRepository, FileManagerService fileManagerService) {
        this.fileRepository = fileRepository;
        this.fileManagerService = fileManagerService;
    }

    @Override
    public FileInfo uploadFile(FileModel fileModel) throws IOException, BusinessLogicException, ParseException {
        logger.debug("Uploading file ...");
        String[] specialSymbol = {"+", "=", "[", "]", "\"", ",", "'", ".", ",", "?", " "};
        Date dateDuration = DateUtils.parseToDateTime(fileModel.getDateDurationDescription().replace("T", " ").concat(":00"));
        String originMultipartName = fileModel.getMultipartFile().getOriginalFilename();
        String fileName = fileModel.getFileName();

        int prefix = 1;

        if (fileModel.getMultipartFile().isEmpty())
            throw new BusinessLogicException(ErrorCode.EMPTY_FILE.getMessage());

        if (Arrays.stream(specialSymbol).parallel().anyMatch(fileName::contains))
            throw new BusinessLogicException(ErrorCode.WRONG_FILENAME.getMessage());

        if (fileName.isEmpty()) {
            fileName = originMultipartName;
            fileModel.setFileName(fileName);
        } else fileModel.setFileName(fileName + "." + FilenameUtils.getExtension(originMultipartName));

        if (DateUtils.checkCurrentDay(dateDuration))
            throw new BusinessLogicException(ErrorCode.WRONG_DATE_DURATION.getMessage());

        while (fileRepository.findByName(fileModel.getFileName()) != null) {
            fileModel.setFileName(fileName + "-(" + prefix + ")" + "." + FilenameUtils.getExtension(originMultipartName));
            logger.debug("Set new fileName {}", fileModel.getFileName());
            prefix++;
        }

        FileInfo fileInfo = new FileInfo();
        fileInfo.setName(fileModel.getFileName());
        fileInfo.setToken(fileManagerService.generateToken(fileModel));
        fileInfo.setDateDuration(dateDuration);
        logger.debug("Try to save file {} in repository...", fileInfo.getName());
        fileManagerService.saveFile(fileModel);
        logger.debug("Save file {} in repository...", fileInfo.getName());
        logger.debug("Try to save file info for file {} to database", fileInfo.getName());
        fileRepository.save(fileInfo);
        logger.debug("Save file info for file  {} in database", fileInfo.getName());
        return fileInfo;
    }

    @Override
    public Resource downloadFile(String token) throws BusinessLogicException, IOException {
        logger.debug("Downloading file ...");
        FileInfo fileInfo = fileRepository.findByToken(token);

        if (fileInfo == null)
            throw new BusinessLogicException(ErrorCode.WRONG_TOKEN.getMessage());

        logger.debug("Try to download file {}", fileInfo.getName());
        return fileManagerService.loadFile(fileInfo);
    }

    @Override
    public void deleteFile(String token) throws IOException, BusinessLogicException {
        logger.debug("Deleting file ...");
        FileInfo fileInfo = fileRepository.findByToken(token);

        if (fileInfo == null)
            throw new BusinessLogicException(ErrorCode.CANNOT_FIND_FILE.getMessage());
        logger.debug("Try to delete file {} in repository", fileInfo.getName());
        fileManagerService.deleteFile(fileInfo.getName());
        logger.debug("Delete file {} in repository", fileInfo.getName());
        logger.debug("Try to delete file info for file {} in database", fileInfo.getName());
        fileRepository.delete(fileInfo);
        logger.debug("Delete file info for file {}", fileInfo.getName());
    }

    @Override
    public void deleteFiles() throws BusinessLogicException, IOException {
        logger.debug("Deleting  all files ...");
        List<FileInfo> fileInfos = (List<FileInfo>) fileRepository.findAll();

        if (fileInfos.isEmpty())
            throw new BusinessLogicException(ErrorCode.EMPTY_DIRECTORY.getMessage());

        logger.debug("Try to delete all files in repository");
        fileRepository.deleteAll();
        logger.debug("Delete all files in repository");
        logger.debug("Try to delete files info in database");
        fileManagerService.deleteAll();
        logger.debug("Delete all files info");
    }
}
