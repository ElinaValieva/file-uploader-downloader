package com.file.manager.service;

import com.file.manager.common.DateUtils;
import com.file.manager.dto.FileInfo;
import com.file.manager.repository.FileRepository;
import com.file.manager.service.fileManager.FileManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;


/**
 * Background service to delete old files
 * every minute find old files and delete them
 */
@Component
public class FileCollector {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private static final int TIME = 60000;

    private final FileRepository fileRepository;

    private final FileManagerService fileManagerService;

    @Autowired
    public FileCollector(FileRepository fileRepository, FileManagerService fileManagerService) {
        this.fileRepository = fileRepository;
        this.fileManagerService = fileManagerService;
    }

    @Scheduled(fixedRate = TIME)
    public void clearOldFiles() throws ParseException {
        logger.debug("FILE COLLECTOR START DELETING ...");
        List<FileInfo> fileInfos = fileRepository.findByDateDurationBefore(DateUtils.getTodayDateTime());
        fileInfos.forEach(fileModel -> {
            try {
                logger.debug("FILE COLLECTOR try to delete " + fileModel.getName()
                        + " with date duration " + fileModel.getDateDuration());
                fileManagerService.deleteFile(fileModel);
                fileRepository.delete(fileModel);
                logger.debug("FILE COLLECTOR delete {}", fileModel.getName());
            } catch (IOException e) {
                logger.error("FILE COLLECTOR get error with {0} {1}", fileModel.getName(), e);
            }
        });
    }
}
