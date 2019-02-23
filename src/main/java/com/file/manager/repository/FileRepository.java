package com.file.manager.repository;


import com.file.manager.dto.FileInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface FileRepository extends CrudRepository<FileInfo, Long> {

    FileInfo findByName(String name);

    FileInfo findByToken(String token);

    List<FileInfo> findByDateDurationBefore(Date date);
}