package com.file.manager.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class StorageProperties {

    @Value("${storage.location}")
    public String location;
}
