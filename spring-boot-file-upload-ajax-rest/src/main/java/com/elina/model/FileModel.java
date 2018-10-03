package com.elina.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "file_model_table")
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateDuration() {
        return dateDuration;
    }

    public void setDateDuration(Date dateDuration) {
        this.dateDuration = dateDuration;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}