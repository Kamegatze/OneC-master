package com.clone.OneC.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Set;
@Getter
@Setter
public class ConfigEntityDTO {
    private String nameEntity;
    private String nameProject;
    private String packageName;
    private Map<String, String> fields;
}
