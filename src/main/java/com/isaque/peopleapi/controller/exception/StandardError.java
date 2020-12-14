package com.isaque.peopleapi.controller.exception;

import lombok.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StandardError {

    private Instant timestamp;
    private Integer status;
    private String path;
    private String expcetionName;
    private Map<String, String> errors = new HashMap<>();

    public StandardError(Instant timestamp, Integer status, String path, String expcetionName) {
        super();
        this.timestamp = timestamp;
        this.status = status;
        this.path = path;
        this.expcetionName = expcetionName;
    }
}
