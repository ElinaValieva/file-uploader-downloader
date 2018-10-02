package com.elina.model;

import org.springframework.web.multipart.MultipartFile;

public class FileModelDTO {

    private String fileName;

    private MultipartFile multipartFile;

    private String dateDurationDescription;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public String getDateDurationDescription() {
        return dateDurationDescription;
    }

    public void setDateDurationDescription(String dateDurationDescription) {
        this.dateDurationDescription = dateDurationDescription;
    }
}
