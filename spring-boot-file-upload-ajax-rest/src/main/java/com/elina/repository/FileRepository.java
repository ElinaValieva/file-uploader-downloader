package com.elina.repository;

import com.elina.model.FileModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface FileRepository extends CrudRepository<FileModel, Long> {

    FileModel findByName(String name);

    FileModel findByToken(String token);

    List<FileModel> findByDateDurationBefore(Date date);
}