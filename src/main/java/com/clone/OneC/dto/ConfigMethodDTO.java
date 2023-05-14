package com.clone.OneC.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Set;
@Getter
@Setter
public class ConfigMethodDTO {
    private String nameMethod;
    private String returnValue;
    private String pathRequest;
    private Map<String, String> args;
    private String typeMethod;
    private Map<String, String> typeAnnotation;
}
