package com.elina.service;

import com.elina.model.FileModel;
import com.elina.repository.FileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Component
public class FileCollector {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private static final int TIME = 60000;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileManager fileManager;

    @Scheduled(fixedRate = TIME)
    public void clearOldFiles() {
        logger.debug("FILE COLLECTOR START DELETING ...");
        Date date = new Date();
        List<FileModel> fileModels = fileRepository.findByDateDurationBefore(date);
        fileModels.forEach(fileModel -> {
            try {
                logger.debug("FILE COLLECTOR try to delete " + fileModel.getName()
                        + " with date duration " + fileModel.getDateDuration());
                fileManager.deleteFile(fileModel);
                fileRepository.delete(fileModel);
                logger.debug("FILE COLLECTOR delete " + fileModel.getName());
            } catch (IOException e) {
                logger.error("FILE COLLECTOR get error with  " + fileModel.getName());
                e.printStackTrace();
            }
        });
    }
}
