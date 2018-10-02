package com.elina.service.implementation;

import com.elina.config.Utils;
import com.elina.exception.BusinessLogicException;
import com.elina.exception.ErrorCode;
import com.elina.model.FileModel;
import com.elina.model.FileModelDTO;
import com.elina.repository.FileRepository;
import com.elina.service.FileService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class FileSystemService implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileSystemManager fileManager;

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
     * @return
     * @throws IOException
     * @throws BusinessLogicException
     * @throws ParseException
     */
    @Override
    public FileModel uploadFile(FileModelDTO fileModelDTO) throws IOException, BusinessLogicException, ParseException {
        String[] specialSymbol = {"+", "=", "[", "]", "\"", ",", "'", ".", ",", "?", " "};
        Date dateDuration = Utils.parseToDate(fileModelDTO.getDateDurationDescription());
        String originMultipartName = fileModelDTO.getMultipartFile().getOriginalFilename();
        String fileName = fileModelDTO.getFileName();

        int prefix = 1;

        if (fileModelDTO.getMultipartFile().isEmpty())
            throw new BusinessLogicException(ErrorCode.EMPTY_FILE.getMessage());

        if (Arrays.stream(specialSymbol).parallel().anyMatch(fileName::contains))
            throw new BusinessLogicException(ErrorCode.WRONG_FILENAME.getMessage());

        if (fileName.isEmpty())
            fileModelDTO.setFileName(originMultipartName);
        else fileModelDTO.setFileName(fileName + "." + FilenameUtils.getExtension(originMultipartName));

        if (dateDuration.before(new Date()))
            throw new BusinessLogicException(ErrorCode.WRONG_DATE_DURATION.getMessage());

        while (fileRepository.findByName(fileModelDTO.getFileName()) != null) {
            fileModelDTO.setFileName(fileName + "-(" + prefix + ")" + "." + FilenameUtils.getExtension(originMultipartName));
            prefix++;
        }

        FileModel fileModel = new FileModel();
        fileModel.setName(fileModelDTO.getFileName());
        fileModel.setToken(fileManager.generateToken(fileModelDTO));
        fileModel.setDateDuration(dateDuration);
        fileManager.saveFile(fileModelDTO);
        fileRepository.save(fileModel);
        return fileModel;
    }

    @Override
    public Resource downloadFile(String token) throws BusinessLogicException, MalformedURLException {
        FileModel fileModel = fileRepository.findByToken(token);

        if (fileModel == null)
            throw new BusinessLogicException(ErrorCode.WRONG_TOKEN.getMessage());

        return fileManager.loadFile(fileModel);
    }

    @Override
    public void deleteFile(String token) throws IOException, BusinessLogicException {
        FileModel fileModel = fileRepository.findByToken(token);

        if (fileModel == null)
            throw new BusinessLogicException(ErrorCode.CANNOT_FIND_FILE.getMessage());

        fileManager.deleteFile(fileModel.getName());
        fileRepository.delete(fileModel);
    }

    @Override
    public void deleteFiles() throws BusinessLogicException {
        List<FileModel> fileModels = (List<FileModel>) fileRepository.findAll();

        if (fileModels.isEmpty())
            throw new BusinessLogicException(ErrorCode.EMPTY_DIRECTORY.getMessage());

        fileRepository.deleteAll();
        fileManager.deleteAll();

    }
}
