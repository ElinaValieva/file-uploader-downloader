package com.file.manager.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class URLs {

    public static final String UPLOAD = "/upload";
    public static final String DOWNLOAD = "/download/{token}";
    public static final String DELETE = "/delete/{token}";
    public static final String DELETE_ALL = "/delete/all";
    public static final String VIEW = "index";
    public static final String VIEW_URL = "/";
}
