package com.elina.service.implementation;

import com.elina.config.Utils;
import com.elina.exception.BusinessLogicException;
import com.elina.exception.ErrorCode;
import com.elina.model.FileModel;
import com.elina.model.FileModelDTO;
import com.elina.repository.FileRepository;
import com.elina.service.FileManager;
import com.elina.service.FileService;
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
 * use FileManager to works with files
 */
@Service
public class FileSystemService implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileManager fileManager;

    private final Logger logger = LoggerFactory.getLogger(FileSystemService.class);

    /**
     * 1. Check not empty file
     * 2. Check fileName (cannot contains special symbols +="[],.)
     * 3. If fileName is empty - set originFileName
     * 4. If file with same exist in storage, add prefix in fileName
     * 5. If dateDuration in past - throw exception
     * 6. Generate token with Base64 encoder
     * 7. Create fileModel and save in database
     *
     * @param fileModelDTO
     * @return fileModel
     * @throws IOException
     * @throws BusinessLogicException if file contain specific symbols, file empty, old dateDuration
     * @throws ParseException
     */
    @Override
    public FileModel uploadFile(FileModelDTO fileModelDTO) throws IOException, BusinessLogicException, ParseException {
        logger.debug("Uploading file ...");
        String[] specialSymbol = {"+", "=", "[", "]", "\"", ",", "'", ".", ",", "?", " "};
        Date dateDuration = Utils.parseToDateTime(fileModelDTO.getDateDurationDescription().replace("T", " ").concat(":00"));
        String originMultipartName = fileModelDTO.getMultipartFile().getOriginalFilename();
        String fileName = fileModelDTO.getFileName();

        int prefix = 1;

        if (fileModelDTO.getMultipartFile().isEmpty())
            throw new BusinessLogicException(ErrorCode.EMPTY_FILE.getMessage());

        if (Arrays.stream(specialSymbol).parallel().anyMatch(fileName::contains))
            throw new BusinessLogicException(ErrorCode.WRONG_FILENAME.getMessage());

        if (fileName.isEmpty()) {
            fileName = originMultipartName;
            fileModelDTO.setFileName(fileName);
        } else fileModelDTO.setFileName(fileName + "." + FilenameUtils.getExtension(originMultipartName));

        if (Utils.checkCurrentDay(dateDuration))
            throw new BusinessLogicException(ErrorCode.WRONG_DATE_DURATION.getMessage());

        while (fileRepository.findByName(fileModelDTO.getFileName()) != null) {
            fileModelDTO.setFileName(fileName + "-(" + prefix + ")" + "." + FilenameUtils.getExtension(originMultipartName));
            logger.debug("Set new fileName {}", fileModelDTO.getFileName());
            prefix++;
        }

        FileModel fileModel = new FileModel();
        fileModel.setName(fileModelDTO.getFileName());
        fileModel.setToken(fileManager.generateToken(fileModelDTO));
        fileModel.setDateDuration(dateDuration);
        logger.debug("Try to save file {} in repository...", fileModel.getName());
        fileManager.saveFile(fileModelDTO);
        logger.debug("Save file {} in repository...", fileModel.getName());
        logger.debug("Try to save file info for file {} to database", fileModel.getName());
        fileRepository.save(fileModel);
        logger.debug("Save file info for file  {} in database", fileModel.getName());
        return fileModel;
    }

    /**
     * download file
     *
     * @param token
     * @return file from repository
     * @throws BusinessLogicException if wrong key for downloading (token)
     * @throws IOException
     */
    @Override
    public Resource downloadFile(String token) throws BusinessLogicException, IOException {
        logger.debug("Downloading file ...");
        FileModel fileModel = fileRepository.findByToken(token);

        if (fileModel == null)
            throw new BusinessLogicException(ErrorCode.WRONG_TOKEN.getMessage());

        logger.debug("Try to download file {}", fileModel.getName());
        return fileManager.loadFile(fileModel);
    }

    /**
     * delete file by token
     *
     * @param token
     * @throws IOException
     * @throws BusinessLogicException if wrong key for deleting (token)
     */
    @Override
    public void deleteFile(String token) throws IOException, BusinessLogicException {
        logger.debug("Deleting file ...");
        FileModel fileModel = fileRepository.findByToken(token);

        if (fileModel == null)
            throw new BusinessLogicException(ErrorCode.CANNOT_FIND_FILE.getMessage());
        logger.debug("Try to delete file {} in repository", fileModel.getName());
        fileManager.deleteFile(fileModel.getName());
        logger.debug("Delete file {} in repository", fileModel.getName());
        logger.debug("Try to delete file info for file {} in database", fileModel.getName());
        fileRepository.delete(fileModel);
        logger.debug("Delete file info for file {}", fileModel.getName());
    }

    /**
     * delete all files from repository
     *
     * @throws BusinessLogicException if repository doesn't contain files
     * @throws IOException
     */
    @Override
    public void deleteFiles() throws BusinessLogicException, IOException {
        logger.debug("Deleting  all files ...");
        List<FileModel> fileModels = (List<FileModel>) fileRepository.findAll();

        if (fileModels.isEmpty())
            throw new BusinessLogicException(ErrorCode.EMPTY_DIRECTORY.getMessage());

        logger.debug("Try to delete all files in repository");
        fileRepository.deleteAll();
        logger.debug("Delete all files in repository");
        logger.debug("Try to delete files info in database");
        fileManager.deleteAll();
        logger.debug("Delete all files info");
    }
}
