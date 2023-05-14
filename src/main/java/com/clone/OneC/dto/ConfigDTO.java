package com.clone.OneC.dto;

import org.springframework.stereotype.Component;

import java.util.List;

public record ConfigDTO(
        String name,
        String groupId,
        String javaVersion,
        List<String> dependencies
){}