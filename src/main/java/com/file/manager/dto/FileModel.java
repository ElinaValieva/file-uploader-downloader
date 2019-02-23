package com.file.manager.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileModel {

    private String fileName;

    private MultipartFile multipartFile;

    private String dateDurationDescription;
}
