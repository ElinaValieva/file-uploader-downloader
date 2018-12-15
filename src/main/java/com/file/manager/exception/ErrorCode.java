package com.file.manager.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCode {
    WRONG_DATE_DURATION(1, "Wrong date duration, try again. "),
    EMPTY_FILE(2, "Empty file, can't load."),
    WRONG_TOKEN(3, "Wrong token-key. "),
    CANNOT_FIND_FILE(4, "Cannot find file."),
    WRONG_FILENAME(5, "File cannot contail special symbols like [] + = \" . , whitespace "),
    EMPTY_DIRECTORY(6, "Empty directory."),
    FILE_CONSIST_IN_REPOSITORY_ALREADY(7, "System error");

    private int code;
    private String reason;

    public String getMessage() {
        return String.format("[CODE]: %d, [MESSAGE]: %s", code, reason);
    }
}