package com.clone.OneC.controller;

import com.clone.OneC.dto.ConfigDTO;
import com.clone.OneC.service.ConfigurationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/project")
@AllArgsConstructor
public class ProjectCreatorController {

    private final ConfigurationService configurationService;

    @PostMapping
    public ResponseEntity createProject(@RequestBody ConfigDTO config) throws IOException {
        String uri = configurationService.getUri(config);

        RestTemplate restTemplate = new RestTemplate();

        byte[] zip = restTemplate.getForObject(uri, byte[].class);

        configurationService.toConfigure(zip);

        return ResponseEntity.ok("Ok");
    }

    @GetMapping("/test")
    public void test() throws IOException {
        configurationService.test();
    }

}
