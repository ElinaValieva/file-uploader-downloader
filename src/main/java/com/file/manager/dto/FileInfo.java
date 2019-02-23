package com.file.manager.dto;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "file_model_table", schema = "file_manager")
@Data
public class FileInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "date_duration")
    private Date dateDuration;

    @Column(name = "token")
    private String token;
}