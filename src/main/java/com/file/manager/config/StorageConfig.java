package com.file.manager.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "storage")
public class StorageConfig {

    @Getter
    @Setter
    private String location;
}
