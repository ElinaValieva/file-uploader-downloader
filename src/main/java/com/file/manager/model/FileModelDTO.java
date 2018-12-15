package com.file.manager.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileModelDTO {

    private String fileName;

    private MultipartFile multipartFile;

    private String dateDurationDescription;
}
