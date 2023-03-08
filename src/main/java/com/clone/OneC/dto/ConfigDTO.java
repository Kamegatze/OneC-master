package com.clone.OneC.dto;

import java.util.List;

public record ConfigDTO(
        String name,
        String description,
        String packaging,
        String javaVersion,
        List<String> dependencies
) {
}


