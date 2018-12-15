package com.file.manager.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "file_model_table")
@Data
public class FileModel {

    @Id
    @SequenceGenerator(name = "seq-gen", sequenceName = "MY_SEQ_GEN")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq-gen")
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "date_duration")
    private Date dateDuration;

    @Column(name = "token")
    private String token;
}