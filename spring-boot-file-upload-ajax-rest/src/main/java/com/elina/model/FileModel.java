package com.elina.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "file")
public class FileModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "date_duration")
    private Date dateDuration;

    @Column(name = "token")
    private String token;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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