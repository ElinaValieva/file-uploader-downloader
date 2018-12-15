package com.file.manager.service;

import com.file.manager.config.Utils;
import com.file.manager.model.FileModel;
import com.file.manager.repository.FileRepository;
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

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileManager fileManager;

    @Scheduled(fixedRate = TIME)
    public void clearOldFiles() throws ParseException {
        logger.debug("FILE COLLECTOR START DELETING ...");
        List<FileModel> fileModels = fileRepository.findByDateDurationBefore(Utils.getTodayDateTime());
        fileModels.forEach(fileModel -> {
            try {
                logger.debug("FILE COLLECTOR try to delete " + fileModel.getName()
                        + " with date duration " + fileModel.getDateDuration());
                fileManager.deleteFile(fileModel);
                fileRepository.delete(fileModel);
                logger.debug("FILE COLLECTOR delete {}", fileModel.getName());
            } catch (IOException e) {
                logger.error("FILE COLLECTOR get error with {0} {1}", fileModel.getName(), e);
            }
        });
    }
}
